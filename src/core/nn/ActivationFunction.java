package core.nn;

import java.util.Objects;

public abstract class ActivationFunction {

	public abstract double apply(double x);

	public abstract double derivative(double x, double y);

	/**
	 * Modifies x.
	 */
	public double[] applyAll(double[] x) {
		Objects.requireNonNull(x);
		for (int i = 0; i < x.length; i++)
			x[i] = apply(x[i]);
		return x;
	}

	/**
	 * Modifies y.
	 */
	public double[] derivativeAll(double[] x, double[] y) {
		Objects.requireNonNull(x);
		Objects.requireNonNull(y);
		if (x.length != y.length)
			throw new IllegalArgumentException("Length mismatch.");
		for (int i = 0; i < x.length; i++)
			y[i] = derivative(x[i], y[i]);
		return y;
	}

	@Override
	public abstract String toString();

}
