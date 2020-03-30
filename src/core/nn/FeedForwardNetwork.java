package core.nn;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import core.data.DataPoint;
import util.function.IntToDoubleTriFunction;

public class FeedForwardNetwork implements Comparable<FeedForwardNetwork> {

	private static final int THREAD_COUNT = Runtime.getRuntime().availableProcessors();

	private static final ExecutorService THREAD_POOL = Executors.newFixedThreadPool(THREAD_COUNT);

	private static int idCounter = 0;

	static {
		System.out.println("Thread Count = " + THREAD_COUNT);
	}

	private final int id = idCounter++;

	private final String name;

	private final int inputSize;
	private final int outputSize;

	private final int layerCount;

	private final Layer[] layers;

	private double learningRate;

	public FeedForwardNetwork(String name, IntToDoubleTriFunction weightGenerator, int[] layerSizes, int outputSize, ActivationFunction[] activationFunctions, double learningRate) {
		this.name = name;
		layerCount = activationFunctions.length;
		if (layerSizes.length != layerCount)
			throw new IllegalArgumentException("layerSizes.length (" + layerSizes.length + ") != activationFunctions.length" + activationFunctions.length + ")");
		inputSize = layerSizes[0];
		this.outputSize = outputSize;
		this.learningRate = learningRate;
		layers = new Layer[layerCount];
		for (int layerIndex = 0; layerIndex < layerCount; layerIndex++)
			layers[layerIndex] = new Layer(weightGenerator, layerIndex, layerSizes[layerIndex], layerIndex + 1 < layerCount ? layerSizes[layerIndex + 1] : outputSize, activationFunctions[layerIndex]);
	}

	public double[] predict(double[] x) {
		if (x.length != inputSize)
			throw new IllegalArgumentException("Wrong input size (" + x.length + ").");

		double[] pred = Arrays.copyOf(x, x.length);
		for (Layer layer : layers)
			pred = layer.feedForward(pred);

		return pred;
	}

	public double[][] train(DataPoint[] batch) throws InterruptedException, ExecutionException {
		int len = batch.length;

		double[][] x = new double[len][];
		double[][] y = new double[len][];

		for (int iterIndex = 0; iterIndex < batch.length; iterIndex++) {
			DataPoint dp = batch[iterIndex];

			double[] in  = dp.getInput();
			double[] out = dp.getOutput();

			x[iterIndex] = Arrays.copyOf(in, in.length);
			y[iterIndex] = Arrays.copyOf(out, out.length);
		}

		return train(x, y);
	}

	public double[][] train(double[][] x, double[][] y) throws InterruptedException, ExecutionException {
		Objects.requireNonNull(x);
		Objects.requireNonNull(y);
		int iterCount = x.length;
		if (iterCount != y.length)
			throw new IllegalArgumentException("Batch size mismatch: input batch size (" + iterCount + ") != output batch size (" + y.length + ").");

		double[][] pred = new double[iterCount][];

		// Excludes Input Layer, Includes Output Layer
		double[][][] valuesNoAct = new double[iterCount][layerCount][];
		double[][][] values      = new double[iterCount][layerCount][];
		double[][][] deltas      = new double[iterCount][layerCount][];

		@SuppressWarnings("rawtypes")
		Future[] futures = new Future[THREAD_COUNT];

		for (int threadIndex = 0; threadIndex < THREAD_COUNT; threadIndex++) {
			int      start = threadIndex;
			Runnable task  = () -> {
								for (int iterIndex = start; iterIndex < iterCount; iterIndex += THREAD_COUNT) {
									double[] curX = x[iterIndex];
									double[] curY = y[iterIndex];
									Objects.requireNonNull(curX);
									Objects.requireNonNull(curY);
									if (curX.length != inputSize)
										throw new IllegalArgumentException("Wrong input size (" + curX.length + ") on iteration " + iterIndex + ".");
									if (curY.length != outputSize)
										throw new IllegalArgumentException("Wrong output size (" + curY.length + ") on iteration " + iterIndex + ".");

									// Forward Propagation (value calculation)
									double[] curVals = Arrays.copyOf(curX, inputSize);
									for (int layerIndex = 0; layerIndex < layerCount; layerIndex++) {
										curVals = layers[layerIndex].feedForwardNoActivation(curVals);
										valuesNoAct[iterIndex][layerIndex] = Arrays.copyOf(curVals, curVals.length);
										layers[layerIndex].getActivationFunction().applyAll(curVals);
										values[iterIndex][layerIndex] = Arrays.copyOf(curVals, curVals.length);
									}
									pred[iterIndex] = curVals;

									// Backward Propagation (delta calculation)
									double[] curDels = new double[outputSize];
									for (int index = 0; index < outputSize; index++)
										curDels[index] = curVals[index] - curY[index];
									for (int layerIndex = layerCount - 1; layerIndex >= 0; layerIndex--) {
										deltas[iterIndex][layerIndex] = Arrays.copyOf(curDels, curDels.length);
//									System.out.println(layerIndex);
//									System.out.println(iterIndex);
//									System.out.println(values[iterIndex].length);
										if (layerIndex > 0)
											curDels = layers[layerIndex].feedBackward(valuesNoAct[iterIndex][layerIndex - 1], values[iterIndex][layerIndex - 1], deltas[iterIndex][layerIndex]);
									}
								}
							};
			futures[threadIndex] = THREAD_POOL.submit(task);
		}

		for (Future<?> future : futures)
			future.get();

		// Adjustment (weight calculation)
		for (int layerIndex = 0; layerIndex < layerCount; layerIndex++)
			layers[layerIndex].updateWeights(learningRate, layerIndex, values, x, deltas);

		return pred;
	}

	public String getName() {
		return name;
	}

	public int getLayerCount() {
		return layerCount;
	}

	public Layer getLayer(int layerIndex) {
		return layers[layerIndex];
	}

	public int getLayerSize(int layerIndex) {
		if (layerIndex == layerCount)
			return outputSize;
		else
			return layers[layerIndex].getSize();
	}

	public double getInputSize() {
		return inputSize;
	}

	public double getOutputSize() {
		return outputSize;
	}

	public String getWeightsAsString() {
		StringBuilder builder = new StringBuilder();
		for (int layerIndex = 0; layerIndex < layerCount; layerIndex++) {
			builder.append("Layer " + layerIndex + ":\n")
					.append(layers[layerIndex].getWeightsAsString());
		}
		return builder.toString();
	}

	public int getID() {
		return id;
	}

	@Override
	public int compareTo(FeedForwardNetwork o) {
		if (this == o)
			return 0;
//		else if (!name.equalsIgnoreCase(o.name))
//			return name.compareToIgnoreCase(o.name);
//		else if (!name.equals(o.name))
//			return name.compareTo(o.name);
		else if (id != o.id)
			return Integer.compare(id, o.id);
		else
			return Integer.compare(System.identityHashCode(this), System.identityHashCode(o));
	}

	@Override
	public String toString() {
		return "FeedForwardNetwork [name=" + name + ", learningRate=" + learningRate + ", layers=" + Arrays.toString(layers) + ", outputSize=" + outputSize + "]";
	}

	@Override
	public int hashCode() {
		return name.hashCode() ^ id;
	}

}
