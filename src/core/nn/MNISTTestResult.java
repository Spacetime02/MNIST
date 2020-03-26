package core.nn;

import java.util.Arrays;
import java.util.Objects;

public final class MNISTTestResult {

	private static int maxIndex(double[] arr) {
		Objects.requireNonNull(arr);
		if (arr.length == 0)
			throw new IllegalArgumentException("Empty Array");

		int    maxI   = 0;
		double maxVal = arr[0];
		double val;

		for (int i = 1; i < arr.length; i++) {
			val = arr[i];
			if (val > maxVal) {
				maxI = i;
				maxVal = val;
			}
		}

		return maxI;
	}

	private final double[] predOutVect;
	private final double[] outVect;

	private final int predOutVal;
	private final int outVal;

	public MNISTTestResult(double[] predictedOutputVector, double[] correctOutputVector) {
		int len = predictedOutputVector.length;

		if (correctOutputVector.length != len)
			throw new IllegalArgumentException("Length mismatch: predictedOutputVector.length (" + predictedOutputVector.length + " != correctOutputVector.length (" + correctOutputVector.length + ")");

		this.predOutVect = Arrays.copyOf(predictedOutputVector, predictedOutputVector.length);
		this.outVect = Arrays.copyOf(correctOutputVector, correctOutputVector.length);

		predOutVal = maxIndex(predictedOutputVector);
		outVal = maxIndex(correctOutputVector);
	}

	public int getOutputSize() {
		return outVect.length;
	}

	public double[] getPredictedOutputVector() {
		return Arrays.copyOf(predOutVect, predOutVect.length);
	}

	public double[] getCorrectOutputVector() {
		return Arrays.copyOf(outVect, outVect.length);
	}

	public double[] getDeltaVector() {
		double[] deltas = new double[outVect.length];
		for (int i = 0; i < deltas.length; i++)
			deltas[i] = predOutVect[i] - outVect[i];
		return deltas;
	}

	// TODO use network's loss function
	public double getLoss() {
		double loss = 0;
		for (double d : getDeltaVector())
			loss += d * d;
		return loss;
	}

	public int getPredictedOutputValue() {
		return predOutVal;
	}

	public int getCorrectOutputValue() {
		return outVal;
	}

	public boolean isCorrect() {
		return outVal == predOutVal;
	}

}
