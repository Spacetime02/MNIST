package main;

import java.io.File;
import java.io.IOException;
import java.util.List;

import core.data.Data;
import core.data.MNISTData;
import core.nn.ActivationFunctionFactory;
import core.nn.FeedForwardNetwork;
import core.nn.FeedForwardNetworkBuilder;
import core.nn.MNISTTestResult;
import core.nn.MNISTTrainer;
import io.MNISTLoader;
import io.idx.IDX1UByteReader;
import io.idx.IDX3UByteReader;

public final class Main {

	private Main() {}

	public static void main(String[] args) throws IOException {
		final int    hiddenLayerSize  = 1000;
		final int    hiddenLayerCount = 4;
		final double leakSlope        = 0.01;
		final double learningRate     = 0.01;
		final int    batchSize        = 20;
		final int    epochCount       = 12;

		File dir = new File("data");

		MNISTLoader loader = new MNISTLoader(dir);

		Data train = loader.load("train");
//		Data test  = loader.load("test");

		FeedForwardNetworkBuilder builder = new FeedForwardNetworkBuilder()
				.addLayer(MNISTData.IN_SIZE, null);
		for (int layerIndex = 0; layerIndex < hiddenLayerCount; layerIndex++)
			builder.setActivationFunction(layerIndex, ActivationFunctionFactory.createLeakyReLU(leakSlope))
					.addLayer(hiddenLayerSize, null);
		builder.setActivationFunction(hiddenLayerCount, ActivationFunctionFactory.createFastLogistic())
				.setOutputSize(MNISTData.OUT_SIZE)
				.setLearningRate(learningRate)
				.setWeightInitializer(FeedForwardNetworkBuilder.gaussianWeightInitializer());

		FeedForwardNetwork network = builder.build();

		MNISTTrainer trainer = new MNISTTrainer(train, network);
		trainer.shuffleData();
		trainer.setBatchSize(batchSize);
		List<MNISTTestResult[][]> allRes     = trainer.trainEpochs(epochCount);
		MNISTTestResult[][]       networkRes = allRes.get(0);
		MNISTTestResult[]         last       = networkRes[epochCount - 1];

		double totalLoss = 0;
		for (MNISTTestResult res : last)
			totalLoss += res.getLoss();
		System.out.println(totalLoss);
//		benchmark();
	}

	@SuppressWarnings("unused")
	private static void benchmark() {
		double[] toCalc = new double[200000000];
		double[] res    = new double[200000000];
		for (int i = 0; i < 200000000; i++)
			toCalc[i] = i - 100000000;
		long      sum = 0;
		long      time, delta;
		final int num = 4;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.abs(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.abs(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.sqrt(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.sqrt(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.sqrt(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.sqrt(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.exp(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.exp(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.log(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.log(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.tanh(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.tanh(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.pow(toCalc[i], toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.pow(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.log1p(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.log1p(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.atan(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.atan(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = Math.cbrt(toCalc[i]);
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "Math.cbrt(x)", sum);
		sum = 0;
		for (int j = 0; j < num; j++) {
			time = System.nanoTime();
			for (int i = 0; i < 200000000; i++)
				res[i] = toCalc[i];
			delta = System.nanoTime() - time;
			time = System.nanoTime();
			sum += delta;
		}
		System.out.printf("%15s: %15dns%n", "x", sum);
	}

	@SuppressWarnings("unused")
	private static void testIO() {
		byte[][][] images;
		try (IDX3UByteReader imgReader = new IDX3UByteReader(new File("data/test-images.idx3-ubyte"))) {
			images = imgReader.read();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		byte[] labels;
		try (IDX1UByteReader labelReader = new IDX1UByteReader(new File("data/test-labels.idx1-ubyte"))) {
			labels = labelReader.read();
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		for (int i = 0; i < 3; i++) {
			for (byte[] row : images[i]) {
				for (byte b : row)
					System.out.print(b >= 0 ? '.' : '#');
				System.out.println();
			}
			System.out.println(labels[i]);
			for (int j = 0; j < 28; j++)
				System.out.print('=');
			System.out.println('\n');
		}
	}

}
