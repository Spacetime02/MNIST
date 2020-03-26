package core.nn;

public final class ActivationFunctionFactory {

	private ActivationFunctionFactory() {}

	public static final ActivationFunction createIdentity() {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return x;
			}

			@Override
			public double derivative(double x, double y) {
				return 1d;
			}

		};
	}

	public static final ActivationFunction createLinear(double slope) {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return slope * x;
			}

			@Override
			public double derivative(double x, double y) {
				return slope;
			}

		};
	}

	public static final ActivationFunction createReLU() {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return x > 0 ? x : 0;
			}

			@Override
			public double derivative(double x, double y) {
				return x > 0 ? 1 : 0;
			}

		};
	}

	public static final ActivationFunction createReLU(double slope) {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return x > 0 ? slope * x : 0;
			}

			@Override
			public double derivative(double x, double y) {
				return x > 0 ? slope : 0;
			}

		};
	}

	public static final ActivationFunction createLeakyReLU(double leakSlope) {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return x > 0 ? x : leakSlope * x;
			}

			@Override
			public double derivative(double x, double y) {
				return x > 0 ? 1 : leakSlope;
			}

		};
	}

	public static final ActivationFunction createLeakyReLU(double slope, double leakSlope) {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return x > 0 ? slope * x : leakSlope * x;
			}

			@Override
			public double derivative(double x, double y) {
				return x > 0 ? slope : leakSlope;
			}

		};
	}

	public static final ActivationFunction createLogistic() {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return (1 + Math.tanh(x / 2)) / 2;
			}

			@Override
			public double derivative(double x, double y) {
				return y * (1 - y);
			}

		};
	}

	public static final ActivationFunction createFastLogistic() {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return x / (1 + Math.abs(x));
			}

			@Override
			public double derivative(double x, double y) {
				double toSquare = y / x;
				return toSquare * toSquare;
			}

		};
	}

//	TODO public static final ActivationFunction createLogistic(double lowerBound, double upperBound, )

	public static final ActivationFunction createSoftplus() {
		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				return Math.log1p(Math.exp(x));
			}

			@Override
			public double derivative(double x, double y) {
				return 1 / (1 + Math.exp(-x));
			}

		};
	}

	public static final ActivationFunction createSoftplus(double negSlope, double posSlope, double sharpness, double xOffset, double yOffset) {
		final double diff  = posSlope - negSlope;
		final double coeff = diff / sharpness;

		return new ActivationFunction() {

			@Override
			public double apply(double x) {
				double ox = x - xOffset;
				return yOffset + negSlope * ox + coeff * Math.log1p(Math.exp(sharpness * ox));
			}

			@Override
			public double derivative(double x, double y) {
				return negSlope + diff / (1 + Math.exp(-sharpness * (x - xOffset)));
			}

		};

	}

}
