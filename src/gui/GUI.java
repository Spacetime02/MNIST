package gui;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;

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

	private CardLayout layout;

	private JPanel mainMenu;

	private FeedForwardNetwork network;
//	private JPanel

	public GUI() {
		super("Neural Networks");
		initNetwork();
		initUI();
	}

	// TODO network starts as null.
	private void initNetwork() {
		FeedForwardNetworkBuilder builder = new FeedForwardNetworkBuilder()
				.addLayer(MNISTData.IN_SIZE, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(512, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(128, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(32, ActivationFunctionFactory.createFastLogistic())
				.setOutputSize(MNISTData.OUT_SIZE)
				.setLearningRate(0.0015)
				.setWeightInitializer(FeedForwardNetworkBuilder.gaussianWeightInitializer());

		FeedForwardNetwork network = builder.build();
	}

	private void initUI() {
		layout = new CardLayout();
		initMainMenu();
	}

	private void initMainMenu() {
		mainMenu = new JPanel(true);

		JButton trainButton = new JButton("Train Network");
		trainButton.addActionListener(e -> {});

		JButton testButton = new JButton("Test Network");
		testButton.addActionListener(e -> {});

		JButton loadButton = new JButton("Load Network");
		loadButton.addActionListener(e -> {});

		JButton saveButton = new JButton("Save Network");
		loadButton.addActionListener(e -> {});

		JButton editButton = new JButton("Edit Network");
		editButton.addActionListener(e -> {});
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
