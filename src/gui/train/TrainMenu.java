package gui.train;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JPanel;

import core.data.Data;
import core.nn.FeedForwardNetwork;
import core.nn.MNISTTrainer;
import gui.Colors;

public class TrainMenu extends JPanel {

	private static final long serialVersionUID = 1L;

	private List<FeedForwardNetwork> networks;

	private MNISTTrainer trainer;

	private CardLayout layout;

	private SelectionMenu selectionMenu;

	public TrainMenu() {
		super(true);
		initUI();
	}

	private void initUI() {
		layout = new CardLayout();
		setLayout(layout);
		setBackground(Colors.BACKGROUND_0);
		initSelectionMenu();
	}

	private void initSelectionMenu() {
		selectionMenu = new SelectionMenu();
		add(selectionMenu, "selection");
//		GUI.
	}

	public void setup(Data train, List<FeedForwardNetwork> networks) {
		this.networks = networks;
		this.trainer = new MNISTTrainer(train, networks);
		selectionMenu.setup(networks);
	}

}
