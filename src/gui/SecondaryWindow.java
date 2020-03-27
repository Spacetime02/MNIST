package gui;

import java.util.List;

import javax.swing.JFrame;

import core.data.Data;
import core.nn.FeedForwardNetwork;

public abstract class SecondaryWindow extends JFrame {

	private static final long serialVersionUID = 1L;

//	private 

	public SecondaryWindow(String name) {
		super(name);
		initUI();
	}

	protected abstract void initUI();

	protected abstract void prepare(Data train, Data test, List<FeedForwardNetwork> networks);

	public final void setup(Data train, Data test, List<FeedForwardNetwork> networks) {
		prepare(train, test, networks);
		setVisible(true);
		requestFocus();
	}

}
