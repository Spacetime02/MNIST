package gui;

import java.awt.Color;

public final class Colors {

	private Colors() {}

	private static final double[] levels = { 0.00, 0.05, 0.07, 0.08, 0.09, 0.11, 0.12, 0.14, 0.15, 0.16 };

	// Basic
	public static final Color BACKGROUND    = gray(0x12);
	public static final Color ON_BACKGROUND = gray(0xff);

	// Primary
	public static Color CREATE = new Color(0x90caf9); // Blue
	public static Color EDIT   = new Color(0xffcc80); // Orange
	public static Color LOAD   = new Color(0xef9a9a); // Red
	public static Color SAVE   = new Color(0xc5e1a5); // Light Green
	public static Color TRAIN  = new Color(0xce93d8); // Purple
	public static Color TEST   = new Color(0xfff59d); // Yellow

	// Darkened
	public static final Color BACKGROUND_0 = dark(Color.WHITE, 0);
	public static final Color BACKGROUND_1 = dark(Color.WHITE, 1);
	public static final Color BACKGROUND_2 = dark(Color.WHITE, 2);
	public static final Color BACKGROUND_3 = dark(Color.WHITE, 3);
	public static final Color BACKGROUND_4 = dark(Color.WHITE, 4);
	public static final Color BACKGROUND_5 = dark(Color.WHITE, 5);
	public static final Color BACKGROUND_6 = dark(Color.WHITE, 6);
	public static final Color BACKGROUND_7 = dark(Color.WHITE, 7);
	public static final Color BACKGROUND_8 = dark(Color.WHITE, 8);
	public static final Color BACKGROUND_9 = dark(Color.WHITE, 9);

	public static final Color CREATE_0 = dark(CREATE, 0);
	public static final Color CREATE_1 = dark(CREATE, 1);
	public static final Color CREATE_2 = dark(CREATE, 2);
	public static final Color CREATE_3 = dark(CREATE, 3);
	public static final Color CREATE_4 = dark(CREATE, 4);
	public static final Color CREATE_5 = dark(CREATE, 5);
	public static final Color CREATE_6 = dark(CREATE, 6);
	public static final Color CREATE_7 = dark(CREATE, 7);
	public static final Color CREATE_8 = dark(CREATE, 8);
	public static final Color CREATE_9 = dark(CREATE, 9);

	public static final Color EDIT_0 = dark(EDIT, 0);
	public static final Color EDIT_1 = dark(EDIT, 1);
	public static final Color EDIT_2 = dark(EDIT, 2);
	public static final Color EDIT_3 = dark(EDIT, 3);
	public static final Color EDIT_4 = dark(EDIT, 4);
	public static final Color EDIT_5 = dark(EDIT, 5);
	public static final Color EDIT_6 = dark(EDIT, 6);
	public static final Color EDIT_7 = dark(EDIT, 7);
	public static final Color EDIT_8 = dark(EDIT, 8);
	public static final Color EDIT_9 = dark(EDIT, 9);

	public static final Color LOAD_0 = dark(LOAD, 0);
	public static final Color LOAD_1 = dark(LOAD, 1);
	public static final Color LOAD_2 = dark(LOAD, 2);
	public static final Color LOAD_3 = dark(LOAD, 3);
	public static final Color LOAD_4 = dark(LOAD, 4);
	public static final Color LOAD_5 = dark(LOAD, 5);
	public static final Color LOAD_6 = dark(LOAD, 6);
	public static final Color LOAD_7 = dark(LOAD, 7);
	public static final Color LOAD_8 = dark(LOAD, 8);
	public static final Color LOAD_9 = dark(LOAD, 9);

	public static final Color SAVE_0 = dark(SAVE, 0);
	public static final Color SAVE_1 = dark(SAVE, 1);
	public static final Color SAVE_2 = dark(SAVE, 2);
	public static final Color SAVE_3 = dark(SAVE, 3);
	public static final Color SAVE_4 = dark(SAVE, 4);
	public static final Color SAVE_5 = dark(SAVE, 5);
	public static final Color SAVE_6 = dark(SAVE, 6);
	public static final Color SAVE_7 = dark(SAVE, 7);
	public static final Color SAVE_8 = dark(SAVE, 8);
	public static final Color SAVE_9 = dark(SAVE, 9);

	public static final Color TRAIN_0 = dark(TRAIN, 0);
	public static final Color TRAIN_1 = dark(TRAIN, 1);
	public static final Color TRAIN_2 = dark(TRAIN, 2);
	public static final Color TRAIN_3 = dark(TRAIN, 3);
	public static final Color TRAIN_4 = dark(TRAIN, 4);
	public static final Color TRAIN_5 = dark(TRAIN, 5);
	public static final Color TRAIN_6 = dark(TRAIN, 6);
	public static final Color TRAIN_7 = dark(TRAIN, 7);
	public static final Color TRAIN_8 = dark(TRAIN, 8);
	public static final Color TRAIN_9 = dark(TRAIN, 9);

	public static final Color TEST_0 = dark(TEST, 0);
	public static final Color TEST_1 = dark(TEST, 1);
	public static final Color TEST_2 = dark(TEST, 2);
	public static final Color TEST_3 = dark(TEST, 3);
	public static final Color TEST_4 = dark(TEST, 4);
	public static final Color TEST_5 = dark(TEST, 5);
	public static final Color TEST_6 = dark(TEST, 6);
	public static final Color TEST_7 = dark(TEST, 7);
	public static final Color TEST_8 = dark(TEST, 8);
	public static final Color TEST_9 = dark(TEST, 9);

	public static Color gray(int level) {
		return new Color(level, level, level);
	}

	public static Color dark(Color primary, int level) {
		return blend(BACKGROUND, primary, levels[level]);
	}

	public static Color blend(Color c1, Color c2, double fac) {
		if (c1.equals(c2))
			return c1;
		double fac1 = 1 - fac;

		int r = (int) Math.round(fac1 * c1.getRed() + fac * c2.getRed());
		int g = (int) Math.round(fac1 * c1.getGreen() + fac * c2.getGreen());
		int b = (int) Math.round(fac1 * c1.getBlue() + fac * c2.getBlue());

		return new Color(r, g, b);
	}

}
