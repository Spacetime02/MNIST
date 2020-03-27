package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import core.data.MNISTData;
import core.nn.ActivationFunctionFactory;
import core.nn.FeedForwardNetwork;
import core.nn.FeedForwardNetworkBuilder;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private CardLayout layout;

	private JPanel mainMenu;

	private List<FeedForwardNetwork> network;
//	private JPanel

	public GUI() {
		super("Neural Networks");
		initNetwork();
		initUI();
	}

	// TODO network starts as null.
	private void initNetwork() {
		network = new ArrayList<>(1);

		FeedForwardNetworkBuilder builder = new FeedForwardNetworkBuilder()
				.addLayer(MNISTData.IN_SIZE, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(512, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(128, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(32, ActivationFunctionFactory.createFastLogistic())
				.setOutputSize(MNISTData.OUT_SIZE)
				.setLearningRate(0.0015)
				.setWeightInitializer(FeedForwardNetworkBuilder.gaussianWeightInitializer());

		network.add(builder.build());
	}

	private void initUI() {
		layout = new CardLayout();
		initMainMenu();
	}

	private void initMainMenu() {
		mainMenu = new JPanel(true);
		mainMenu.setLayout(new BoxLayout(mainMenu, BoxLayout.Y_AXIS));

		JButton createButton = new JButton("Create Network");
		createButton.addActionListener(e -> {});

		JButton loadButton = new JButton("Load Network");
		loadButton.addActionListener(e -> {});

		JButton trainButton = new JButton("Train Network");
		trainButton.addActionListener(e -> {});

		JButton testButton = new JButton("Test Network");
		testButton.addActionListener(e -> {});

		JButton saveButton = new JButton("Save Network");
		saveButton.addActionListener(e -> {});

		// @formatter:off
		GUI.setup(mainMenu,
				GUI.vGlue(),
				GUI.hBox(
						GUI.hGlue(),
						createButton,
						GUI.hGlue()
						),
				GUI.vGlue(),
				GUI.hBox(
						GUI.hGlue(),
						loadButton,
						GUI.hGlue()
						),
				GUI.vGlue(),
				GUI.hBox(
						GUI.hGlue(),
						trainButton,
						GUI.hGlue()
						),
				GUI.vGlue(),
				GUI.hBox(
						GUI.hGlue(),
						testButton,
						GUI.hGlue()
						),
				GUI.vGlue(),
				GUI.hBox(
						GUI.hGlue(),
						saveButton,
						GUI.hGlue()
						),
				GUI.vGlue()
				);
		// @formatter:on
	}

	static Box.Filler hGlue() {
		return (Box.Filler) Box.createHorizontalGlue();
	}

	static Box.Filler vGlue() {
		return (Box.Filler) Box.createVerticalGlue();
	}

	static Box.Filler glue() {
		return (Box.Filler) Box.createGlue();
	}

	static Box.Filler hStrut(int size) {
		return (Box.Filler) Box.createHorizontalStrut(size);
	}

	static Box.Filler vStrut(int size) {
		return (Box.Filler) Box.createVerticalStrut(size);
	}

	static Box.Filler rigidArea(int width, int height) {
		return rArea(new Dimension(width, height));
	}

	static Box.Filler rArea(Dimension dim) {
		return (Box.Filler) Box.createRigidArea(dim);
	}

	static Box hBox(Component... children) {
		return setup(Box.createHorizontalBox(), children);
	}

	static Box vBox(Component... children) {
		return setup(Box.createVerticalBox(), children);
	}

	static BoxLayout hLayout(Container parent) {
		return layout(parent, BoxLayout.X_AXIS);
	}

	static BoxLayout vLayout(Container parent) {
		return layout(parent, BoxLayout.Y_AXIS);
	}

	static BoxLayout layout(Container parent, int axis) {
		BoxLayout layout = new BoxLayout(parent, axis);
		if (parent != null)
			parent.setLayout(layout);
		return layout;
	}

	static <T extends Container> T setup(T parent, Component... children) {
		if (parent != null)
			for (Component child : children)
				parent.add(child);
		return parent;
	}

}

/*
@formatter:off
package main;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

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

	private static final String OPTIONS = "Options:\n"
			+ "1\tTrain Network\n"
			+ "2\tTest Network\n"
			+ "3\tLoad Weights\n"
			+ "4\tSave Weights";

	private Main() {}

	public static void main(String[] args) {
		String in;
		
		Integer opt;
		
		PrintStream o = System.out;

		Scanner i = new Scanner(System.in);
		
		while (opt == null) {
			o.println(OPTIONS);
			try
		}
		
		switch (i.nextInt()) {
		case 1:
			o.println
			train();
		}
	}

	private static void train() {

	}

	public static void tr() throws IOException, InterruptedException, ExecutionException {

//		FeedForwardNetwork tn = new FeedForwardNetworkBuilder()
//				.addLayer(3, ActivationFunctionFactory.createLeakyReLU(0.01))
//				.addLayer(3, ActivationFunctionFactory.createLeakyReLU(0.01))
//				.setOutputSize(3)
//				.setLearningRate(0.1)
//				.setWeightInitializer((a, b, c) -> b == c ? 1 : 0)
//				.build();
//		System.out.println(Arrays.toString(tn.predict(new double[] { -1, 2, 4 })));

		final int batchSize  = 120;
		final int epochCount = 256;

		File dir = new File("data");

		MNISTLoader loader = new MNISTLoader(dir);

		Data train = loader.load("train");
//		Data test  = loader.load("test");

		FeedForwardNetworkBuilder builder = new FeedForwardNetworkBuilder()
				.addLayer(MNISTData.IN_SIZE, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(128, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(64, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(32, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(16, ActivationFunctionFactory.createFastLogistic())
				.setOutputSize(MNISTData.OUT_SIZE)
				.setLearningRate(0.0015)
				.setWeightInitializer(FeedForwardNetworkBuilder.gaussianWeightInitializer());

		FeedForwardNetwork network = builder.build();

		MNISTTrainer trainer = new MNISTTrainer(train, network);
//		trainer.shuffleData();
		trainer.setBatchSize(batchSize);

		double[] loss = new double[epochCount];
		double[] err  = new double[epochCount];

		List<MNISTTestResult[][]> allRes = trainer.trainEpochs(epochCount);

		MNISTTestResult[][] networkRes = allRes.get(0);

		for (int epochIndex = 0; epochIndex < epochCount; epochIndex++) {
			MNISTTestResult[] epochRes = networkRes[epochIndex];

			double errRate = 0;
			double avgLoss = 0;
			for (MNISTTestResult res : epochRes) {
				avgLoss += res.getLoss();
				if (!res.isCorrect())
					errRate++;
			}
			System.out.println(errRate);
			avgLoss /= trainer.getDataSize();
			errRate /= trainer.getDataSize();
			loss[epochIndex] = avgLoss;
			err[epochIndex] = errRate;
		}
		System.out.println("Epoch\tAverage Loss");
		for (int epochIndex = 0; epochIndex < epochCount; epochIndex++)
			System.out.println(epochIndex + "\t" + loss[epochIndex] + "\t" + err[epochIndex]);

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
@formatter:on
*/
