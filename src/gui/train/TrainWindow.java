package gui.train;

import java.util.List;

import core.data.Data;
import core.nn.FeedForwardNetwork;
import core.nn.MNISTTrainer;
import gui.SecondaryWindow;

public class TrainWindow extends SecondaryWindow {

	private List<FeedForwardNetwork> networks;
	private MNISTTrainer trainer;

	public TrainWindow() {
		JPanel
	}

	@Override
	protected void prepare(Data train, Data test, List<FeedForwardNetwork> networks) {
		this.networks = networks;
		this.trainer = new MNISTTrainer(train, networks);
	}

}
