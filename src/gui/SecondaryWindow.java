package gui;

import java.util.List;

import javax.swing.JPanel;

import core.data.Data;
import core.nn.FeedForwardNetwork;

public abstract class SecondaryWindow extends JPanel {

	private static final long serialVersionUID = 1L;

	private 
	
	public SecondaryWindow() {
		
	}

	public abstract void setup(Data train, Data test, List<FeedForwardNetwork> networks) {
		prepare(train, test, networks);
	}

}
