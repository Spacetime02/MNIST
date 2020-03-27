package core.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public final class MNISTData implements Data {

	public static final int IMAGE_SIZE = 28;
	public static final int IN_SIZE    = IMAGE_SIZE * IMAGE_SIZE;
	public static final int OUT_SIZE   = 10;

	private final byte[][] input;
	private final byte[]   output;

	/**
	 * Input and output should not later be modified. Input must be an array of 28x28 arrays.
	 */
	public MNISTData(byte[][][] imgData, byte[] lblData) {
		Objects.requireNonNull(imgData);
		Objects.requireNonNull(lblData);
		if (imgData.length != lblData.length)
			throw new IllegalArgumentException("input.length (" + imgData.length + ") != output.length (" + lblData.length + ")");
		this.input = new byte[imgData.length][IN_SIZE];
		for (int i = 0; i < imgData.length; i++) {
			if (imgData[i].length != IMAGE_SIZE)
				throw new IllegalArgumentException();
			for (int j = 0; j < IMAGE_SIZE; j++) {
				if (imgData[i][j].length != IMAGE_SIZE)
					throw new IllegalArgumentException();
				for (int k = 0; k < IMAGE_SIZE; k++) {
					this.input[i][j * IMAGE_SIZE + k] = imgData[i][j][k];
				}
			}
		}
		this.output = Arrays.copyOf(lblData, lblData.length);
	}

	@Override
	public int getSize() {
		return output.length;
	}

	@Override
	public DataPoint getDataPoint(int index) {
		return new MNISTDataPoint(index);
	}

	@Override
	public void shuffle() {
		List<Integer> perm = new ArrayList<>(output.length);
		for (int i = 0; i < output.length; i++)
			perm.add(i);
		Collections.shuffle(perm);
		byte[][] shuffledInput  = new byte[output.length][];
		byte[]   shuffledOutput = new byte[output.length];
		for (int i = 0; i < output.length; i++) {
			shuffledInput[i] = input[perm.get(i)];
			shuffledOutput[i] = output[perm.get(i)];
		}
		System.arraycopy(shuffledInput, 0, input, 0, output.length);
		System.arraycopy(shuffledOutput, 0, output, 0, output.length);
	}

	private class MNISTDataPoint implements DataPoint {

		private double[] in;
		private double[] out;

		private MNISTDataPoint(int index) {
			byte[] byteIn = input[index];
			in = new double[IN_SIZE];
			for (int i = 0; i < IN_SIZE; i++)
				in[i] = Byte.toUnsignedInt(byteIn[i]) / 255d;
			out = new double[OUT_SIZE];
			out[output[index]] = 1;
		}

		@Override
		public double[] getInput() {
			return Arrays.copyOf(in, IN_SIZE);
		}

		@Override
		public double[] getOutput() {
			return Arrays.copyOf(out, OUT_SIZE);
		}

	}

}
