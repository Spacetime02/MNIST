package core.nn;

import util.function.IntToDoubleTriFunction;

public class Layer {

	private final int size;
	private final int nextSize;

	private final double[][] weights; // indexed as biases[index][nextIndex]

//	TODO private final double[] biases;

	private final ActivationFunction activationFunction;

	public Layer(IntToDoubleTriFunction weightGenerator, int layerIndex, int size, int nextSize, ActivationFunction activationFunction) {
		this.size = size;
		this.nextSize = nextSize;
		weights = new double[size][];
		for (int index = 0; index < size; index++) {
			double[] weightGroup = new double[nextSize];
			for (int nextIndex = 0; nextIndex < nextSize; nextIndex++)
				weightGroup[nextIndex] = weightGenerator.applyAsDouble(layerIndex, index, nextIndex);
			weights[index] = weightGroup;
		}
		this.activationFunction = activationFunction;
	}

	/**
	 * This may change the values stored in x.
	 */
	public double[] feedForward(double[] valsIn) {
		// Sum
		double[] valsOut = feedForwardNoActivation(valsIn);
		// Activate
		activationFunction.applyAll(valsOut);
		return valsOut;
	}

	/**
	 * This may change the values stored in x.
	 */
	public double[] feedForwardNoActivation(double[] valsIn) {
		if (valsIn.length != size)
			throw new IllegalArgumentException("Wrong valsIn length. (" + valsIn.length + ").");

		double[] valsOut = new double[nextSize];
		for (int index = 0; index < size; index++) {
			double   valIn       = valsIn[index];
			double[] weightGroup = weights[index];
			for (int nextIndex = 0; nextIndex < nextSize; nextIndex++)
				valsOut[nextIndex] += valIn * weightGroup[nextIndex];
		}

		return valsOut;
	}

	/**
	 * This may change the values stored in valsIn.
	 */
	public double[] feedBackward(double[] valsInNoAct, double[] valsIn, double[] deltasIn) {
		if (valsInNoAct.length != size)
			throw new IllegalArgumentException("Wrong valsInNoAct length (" + valsInNoAct.length + ").");
		if (valsIn.length != size)
			throw new IllegalArgumentException("Wrong valsIn length (" + valsIn.length + ").");
		if (deltasIn.length != nextSize)
			throw new IllegalArgumentException("Wrong deltasIn length (" + deltasIn.length + ").");

		// Activate
		activationFunction.derivativeAll(valsInNoAct, valsIn);

		// Sum
		double[] deltasOut = new double[size];
		for (int index = 0; index < size; index++) {
			double   valIn       = valsIn[index];
			double[] weightGroup = weights[index];
			for (int nextIndex = 0; nextIndex < nextSize; nextIndex++)
				deltasOut[index] += deltasIn[nextIndex] * valIn * weightGroup[nextIndex];
		}

		return deltasOut;
	}

	/**
	 * This may change the values stored in values.
	 */
	public void updateWeights(double learningRate, int layerIndex, double[][][] values, double[][] x, double[][][] deltas) {
		for (int iterIndex = 0; iterIndex < values.length; iterIndex++) {
			double[] vals = layerIndex > 0 ? values[iterIndex][layerIndex - 1] : x[iterIndex];
			double[] dels = deltas[iterIndex][layerIndex];

			for (int index = 0; index < size; index++) {
//				System.out.println(layerIndex + ", " + iterIndex + ", " + index + ", " + size);
				double val = vals[index] * learningRate;

				double[] weightGroup = weights[index];

				for (int nextIndex = 0; nextIndex < nextSize; nextIndex++)
					weightGroup[nextIndex] -= val * dels[nextIndex];
			}
		}
	}

	public int getSize() {
		return size;
	}

	public double getWeight(int index, int nextIndex) {
		return weights[index][nextIndex];
	}

	public void setWeight(double value, int index, int nextIndex) {
		weights[index][nextIndex] = value;
	}

	public ActivationFunction getActivationFunction() {
		return activationFunction;
	}

}
