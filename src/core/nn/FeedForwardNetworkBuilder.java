package core.nn;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.DoubleSupplier;

import util.function.IntToDoubleTriFunction;

public class FeedForwardNetworkBuilder {

	public static IntToDoubleTriFunction gaussianWeightInitializer() {
		return gaussianWeightInitializer(new Random());
	}

	public static IntToDoubleTriFunction gaussianWeightInitializer(Random randy) {
		return noParameterWeightInitializer(randy::nextGaussian);
	}

	public static IntToDoubleTriFunction noParameterWeightInitializer(DoubleSupplier supplier) {
		return (layerIndex, index, nextIndex) -> supplier.getAsDouble();
	}

	private Integer                        outputSize          = null;
	private final List<Integer>            layerSizes          = new ArrayList<>();
	private final List<ActivationFunction> activationFunctions = new ArrayList<>();
	private Double                         learningRate        = null;
	private IntToDoubleTriFunction         weightInitializer   = null;

	public FeedForwardNetworkBuilder addLayer(int layerSize, ActivationFunction activationFunction) {
		layerSizes.add(layerSize);
		activationFunctions.add(activationFunction);
		return this;
	}

	public FeedForwardNetworkBuilder setLayerSize(int layerIndex, int layerSize) {
		layerSizes.set(layerIndex, layerSize);
		return this;
	}

	public FeedForwardNetworkBuilder setActivationFunction(int layerIndex, ActivationFunction activationFunction) {
		activationFunctions.set(layerIndex, activationFunction);
		return this;
	}

	public FeedForwardNetworkBuilder setOutputSize(int outputSize) {
		this.outputSize = outputSize;
		return this;
	}

	public FeedForwardNetworkBuilder setLearningRate(double learningRate) {
		this.learningRate = learningRate;
		return this;
	}

	public FeedForwardNetworkBuilder setWeightInitializer(IntToDoubleTriFunction weightInitializer) {
		this.weightInitializer = weightInitializer;
		return this;
	}

	/**
	 * Replaces all {@code ActivationFunctions} that are equal to {@code null} with {@code ActivationFunctionFactory.createIdentity()}.
	 */
	public FeedForwardNetwork build() {
		if (outputSize == null)
			throw new IllegalStateException("The output size has not been set.");
		if (learningRate == null)
			throw new IllegalStateException("The learning rate has not been set.");
		if (weightInitializer == null)
			throw new IllegalStateException("The weight generator has not been set.");
		int   numLayers    = layerSizes.size();
		int[] layerSizeArr = new int[numLayers];
		for (int layerIndex = 0; layerIndex < numLayers; layerIndex++)
			layerSizeArr[layerIndex] = layerSizes.get(layerIndex);
		activationFunctions.replaceAll(af -> af == null ? ActivationFunctionFactory.createIdentity() : af);
		ActivationFunction[] activationFunctionArr = activationFunctions.toArray(new ActivationFunction[numLayers]);
		return new FeedForwardNetwork(weightInitializer, layerSizeArr, outputSize, activationFunctionArr, learningRate);
	}

}
