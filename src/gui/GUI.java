package gui;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.IntSupplier;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;

import core.data.Data;
import core.data.MNISTData;
import core.nn.ActivationFunctionFactory;
import core.nn.FeedForwardNetwork;
import core.nn.FeedForwardNetworkBuilder;
import gui.train.TrainMenu;
import io.MNISTLoader;

public class GUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private CardLayout layout;

	private JPanel    mainMenu;
	private TrainMenu trainPane;

	private List<FeedForwardNetwork> networks;

	private Data trainData;
	private Data testData;
//	private JPanel

	// test
	public static void main(String[] args) throws IOException {
		File dir = new File("data");

		MNISTLoader loader = new MNISTLoader(dir);

		Data train = loader.load("train");
		Data test  = loader.load("test");

		new GUI(train, test);
	}

//	public static void main(String[] args) {
//		UIDefaults defaults = UIManager.getDefaults();
//		System.out.println(defaults.size() + " properties defined!");
//		String[]   colName = { "Key", "Value" };
//		String[][] rowData = new String[defaults.size()][2];
//		int        i       = 0;
//		for (Enumeration<Object> e = defaults.keys(); e.hasMoreElements(); i++) {
//			Object key = e.nextElement();
//			rowData[i][0] = key.toString();
//			rowData[i][1] = "" + defaults.get(key);
//			System.out.println(rowData[i][0] + " ,, " + rowData[i][1]);
//		}
//		Arrays.sort(rowData, Comparator.comparing(r -> r[0]));
//		JFrame f = new JFrame("UIManager properties default values");
//		JTable t = new JTable(rowData, colName);
//		f.setContentPane(new JScrollPane(t));
//		// f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.pack();
//		f.setVisible(true);
//	}

	public GUI(Data train, Data test) {
		super("Neural Networks");
		initNetwork();
		initUI();
		setVisible(true);
	}

	// TODO network starts as null.
	private void initNetwork() {
		networks = new ArrayList<>(1);

		FeedForwardNetworkBuilder builder = new FeedForwardNetworkBuilder()
				.addLayer(MNISTData.IN_SIZE, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(512, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(128, ActivationFunctionFactory.createLeakyReLU(0.01))
				.addLayer(32, ActivationFunctionFactory.createFastLogistic())
				.setOutputSize(MNISTData.OUT_SIZE)
				.setLearningRate(0.0015)
				.setWeightInitializer(FeedForwardNetworkBuilder.gaussianWeightInitializer())
				.setName("Stephen");

		networks.add(builder.build());

		for (int i = 0; i < 50; i++) {
			networks.add(new FeedForwardNetworkBuilder()
					.addLayer(MNISTData.IN_SIZE, ActivationFunctionFactory.createIdentity())
					.setOutputSize(MNISTData.OUT_SIZE)
					.setLearningRate(1)
					.setWeightInitializer((a, b, c) -> 1d)
					.setName("Filler " + i)
					.build());
		}
	}

	private void initUI() {
		setBackground(Colors.BACKGROUND_0);
		layout = new CardLayout();
		setLayout(layout);
		initMainMenu();
		initTrainPane();
		setExtendedState(MAXIMIZED_BOTH);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initMainMenu() {
		mainMenu = new JPanel(true);
		mainMenu.setBackground(Colors.BACKGROUND_0);
		GUIUtils.vLayout(mainMenu);

		Font font = new Font(Font.DIALOG, Font.PLAIN, 72);

		JButton createButton = mkButton("Create", font, Colors.CREATE_1, Colors.CREATE, d -> new Dimension(Math.max(d.width, getWidth() / 3), d.height), e -> {

		});

		JButton editButton = mkButton("Edit", font, Colors.EDIT_1, Colors.EDIT, createButton, e -> {

		});

		JButton loadButton = mkButton("Load", font, Colors.LOAD_1, Colors.LOAD, createButton, e -> {

		});

		JButton saveButton = mkButton("Save", font, Colors.SAVE_1, Colors.SAVE, createButton, e -> {

		});

		JButton trainButton = mkButton("Train", font, Colors.TRAIN_1, Colors.TRAIN, createButton, e -> {
			trainPane.setup(trainData, networks);
			layout.show(getContentPane(), "train");
		});

		JButton testButton = mkButton("Test", font, Colors.TEST_1, Colors.TEST, createButton, e -> {

		});

		// @formatter:off
		GUIUtils.vSpace(
				mainMenu,
				GUIUtils.hSpace(null, createButton),
				GUIUtils.hSpace(null, editButton),
				GUIUtils.hSpace(null, loadButton),
				GUIUtils.hSpace(null, saveButton),
				GUIUtils.hSpace(null, trainButton),
				GUIUtils.hSpace(null, testButton)
				);
		// @formatter:on

		add(mainMenu, "main");
	}

	private void initTrainPane() {
		trainPane = new TrainMenu();
		add(trainPane, "train");
	}

	public static JButton mkButton(String text, Font font, Color bg, Color fg, Component sizeSource, ActionListener listener) {
		return mkButton(text, font, bg, fg, sizeSource == null ? null : (Supplier<Dimension>) sizeSource::getSize, listener);
	}

	public static JButton mkButton(String text, Font font, Color bg, Color fg, IntSupplier width, IntSupplier height, ActionListener listener) {
		return mkButton(text, font, bg, fg, width == null ? height == null ? null : d -> new Dimension(d.width, height.getAsInt()) : height == null ? d -> new Dimension(width.getAsInt(), d.height) : d -> new Dimension(width.getAsInt(), height.getAsInt()), listener);
	}

	public static JButton mkButton(String text, Font font, Color bg, Color fg, Supplier<Dimension> size, ActionListener listener) {
		return mkButton(text, font, bg, fg, size == null ? null : d -> size.get(), listener);
	}

	@SuppressWarnings("serial")
	public static JButton mkButton(String text, Font font, Color bg, Color fg, UnaryOperator<Dimension> size, ActionListener listener) {
		JButton button;
		if (size == null)
			button = new JButton(" " + text + " ");
		else
			button = new JButton(" " + text + " ") {

				@Override
				public Dimension getPreferredSize() {
					return size.apply(super.getPreferredSize());
				}

			};

		ColorUIResource bgRes = new ColorUIResource(bg);
		button.addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				UIManager.put("Button.select", bgRes);
			}

		});

		button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		button.setFocusPainted(false);

		button.setFont(font);

		button.setBackground(bg);
		button.setForeground(fg);

		button.setBorder(null);

		if (listener != null)
			button.addActionListener(listener);

		return button;

	}

}
