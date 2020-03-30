package gui.plaf;

import java.awt.Color;

import javax.swing.plaf.basic.BasicScrollBarUI;

public class SimpleScrollBarUI extends BasicScrollBarUI {

	public SimpleScrollBarUI() {
		thumbColor = Color.GREEN;
		thumbDarkShadowColor = Color.BLUE;
		thumbHighlightColor = Color.RED;
		thumbLightShadowColor = Color.ORANGE;
		trackColor = Color.MAGENTA;
	}

}
