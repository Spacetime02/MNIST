package gui.train;

import java.awt.BorderLayout;
import java.awt.Font;
import java.util.Arrays;
import java.util.List;

import javax.swing.Box;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;

import core.nn.FeedForwardNetwork;
import gui.Colors;
import gui.GUIUtils;
import gui.plaf.SimpleScrollBarUI;

public class SelectionMenu extends JPanel {

	private static final long serialVersionUID = 1L;

//	private List<FeedForwardNetwork> networks;

//	private JScrollPane scrollPane;

	private Box checkBoxContainer;

	private boolean[] enabled;

	public SelectionMenu() {
		super(false);
		initUI();
	}

	private void initUI() {
		setBackground(Colors.BACKGROUND_0);

		setLayout(new BorderLayout());

		checkBoxContainer = GUIUtils.vBox();

		Box hBox = GUIUtils.hCenter(checkBoxContainer);
//		hBox.setOpaque(true);
//		hBox.setBackground(GUI.BACKGROUND);

		JScrollPane scrollPane = new JScrollPane(hBox);
//		scrollPane.setOpaque(true);
		scrollPane.getViewport().setBackground(Colors.BACKGROUND_0);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);

		JScrollBar bar = scrollPane.getVerticalScrollBar();

//		ScrollBarUI sbui = new BasicScrollBarUI() {
//
//			@Override
//			protected void configureScrollBarColors() {
//				super.configureScrollBarColors();
//				thumbColor = Colors.BACKGROUND_1;
//				thumbHighlightColor = Color.PINK;
//			};
//
//		};

		bar.setUI(new SimpleScrollBarUI());
//		bar.setBackground(Colors.BACKGROUND_1);

		add(scrollPane, BorderLayout.CENTER);
	}

	public void setup(List<FeedForwardNetwork> networks) {
		int len = networks.size();

		enabled = new boolean[len];
		Arrays.fill(enabled, true);

		checkBoxContainer.removeAll();

		Font font = new Font(Font.DIALOG, Font.PLAIN, 32);

		for (int netIndex = 0; netIndex < len; netIndex++) {
			final int finNetIndex = netIndex;

			FeedForwardNetwork net = networks.get(finNetIndex);

			JCheckBox checkBox = new JCheckBox(net.getName(), true);

			checkBox.setFont(font);

			checkBox.setBackground(Colors.BACKGROUND_1);
			checkBox.setForeground(Colors.ON_BACKGROUND);

			checkBox.addActionListener(e -> enabled[finNetIndex] = checkBox.isSelected());

			Box hBox = GUIUtils.hBox(checkBox, GUIUtils.hGlue());

			hBox.setOpaque(true);
			hBox.setBackground(Colors.BACKGROUND_1);

			GUIUtils.setup(checkBoxContainer, hBox);

			if (netIndex < len - 1)
				checkBoxContainer.add(GUIUtils.vStrut(16));
		}
	}

}
