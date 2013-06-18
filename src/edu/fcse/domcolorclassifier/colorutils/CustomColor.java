package edu.fcse.domcolorclassifier.colorutils;
public class CustomColor {
	enum ColorCode {
		AARRGGBB, RRGGBB, RRGGBBAA,
	}

	/**
	 * rgba values 0-1
	 */
	double red;
	double green;
	double blue;
	// double alpha;
	ColorCode code;
	String colorName;

	/**
	 * @param a
	 *            alpha value 0-255
	 * @param r
	 *            red value 0-255
	 * @param g
	 *            green value 0-255
	 * @param b
	 *            blue value 0-255
	 */
	public CustomColor(int a, int r, int g, int b) {
		double divider = a + r + g + b;
		if (divider == 0) {
			// alpha = 0;
			red = 0;
			green = 0;
			blue = 0;
		} else {
			double rr = r, gg = g, bb = b;
			// alpha = aa / divider;
			red = rr / divider;
			green = gg / divider;
			blue = bb / divider;
		}
	}

	public CustomColor(ColorCode colorCode, String hexString, String colorName) {
		this.colorName = colorName;
		switch (colorCode) {
		case AARRGGBB:
			initARGB(hexString);
			break;
		case RRGGBB:
			initRGB(hexString);
			break;
		case RRGGBBAA:
			initRGBA(hexString);
			break;
		}
	}

	private void initARGB(String hex) {
		code = ColorCode.AARRGGBB;
		// String a = hex.substring(0, 2);
		String r = hex.substring(2, 4);
		String g = hex.substring(4, 6);
		String b = hex.substring(6);
		// alpha = Integer.parseInt(a, 16);
		// double aa=Integer.parseInt(a,16);
		double rr = Integer.parseInt(r, 16);
		double gg = Integer.parseInt(g, 16);
		double bb = Integer.parseInt(b, 16);
		// alpha = aa/(rr+bb+gg);
		red = rr / (rr + bb + gg);
		green = gg / (rr + bb + gg);
		blue = bb / (rr + bb + gg);
	}

	private void initRGB(String hex) {
		code = ColorCode.RRGGBB;
		String r = hex.substring(0, 2);
		String g = hex.substring(2, 4);
		String b = hex.substring(4);
		// alpha=1;
		double rr = Integer.parseInt(r, 16);
		double gg = Integer.parseInt(g, 16);
		double bb = Integer.parseInt(b, 16);
		red = rr / (rr + bb + gg);
		green = gg / (rr + bb + gg);
		blue = bb / (rr + bb + gg);
	}

	private void initRGBA(String hex) {
		code = ColorCode.RRGGBBAA;
		String r = hex.substring(0, 2);
		String g = hex.substring(2, 4);
		String b = hex.substring(4, 6);
		// String a = hex.substring(6);
		// double aa=Integer.parseInt(a,16);
		double rr = Integer.parseInt(r, 16);
		double gg = Integer.parseInt(g, 16);
		double bb = Integer.parseInt(b, 16);
		// alpha = aa/(rr+bb+gg);
		red = rr / (rr + bb + gg);
		green = gg / (rr + bb + gg);
		blue = bb / (rr + bb + gg);
	}

	public ColorCode getCode() {
		return code;
	}

	public double getRed() {
		return red;
	}

	public double getGreen() {
		return green;
	}

	public double getBlue() {
		return blue;
	}

	// public double getAlpha() {
	// return alpha;
	// }

	public String getColorName() {
		return colorName;
	}

	public double getDistance(CustomColor other) {
		// double a = alpha - other.alpha;
		double r = red - other.red;
		double g = green - other.green;
		double b = blue - other.blue;
		return Math.sqrt(r * r + b * b + g * g);

	}

}