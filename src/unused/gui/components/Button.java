package unused.gui.components;

import java.awt.Graphics;
import java.awt.Rectangle;

import javax.swing.AbstractButton;
import javax.swing.JButton;
import javax.swing.plaf.basic.BasicButtonUI;
import javax.swing.plaf.metal.MetalButtonUI;

public class Button extends JButton {

	private static final long serialVersionUID = 1L;

	public static class UI extends BasicButtonUI {

		@Override
		protected void paintFocus(Graphics g, AbstractButton b, Rectangle viewRect, Rectangle textRect, Rectangle iconRect) {
			// TODO Auto-generated method stub
			super.paintFocus(g, b, viewRect, textRect, iconRect);
//			MetalButtonUI
		}

	}

	public static class Builder {

		public Builder() {

		}

	}

}
