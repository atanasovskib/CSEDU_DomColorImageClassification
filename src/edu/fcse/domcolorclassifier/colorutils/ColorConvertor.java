package edu.fcse.domcolorclassifier.colorutils;
public class ColorConvertor {
	public static float[] convertRGB2LabNorm(float[] data) {

		data = convertRGB2XYZ(data);
		double var_X = data[0] * 100.0 / 95.047; // ref_X = 95.047 Observer= 2,
		// Illuminant= D65
		double var_Y = data[1] * 100.0 / 100.00; // ref_Y = 100.000
		double var_Z = data[2] * 100.0 / 108.883; // ref_Z = 108.883

		if (var_X > 0.008856)
			var_X = Math.pow(var_X, (1.0 / 3.0));
		else
			var_X = (7.787 * var_X) + (16 / 116);
		if (var_Y > 0.008856)
			var_Y = Math.pow(var_Y, (1.0 / 3.0));
		else
			var_Y = (7.787 * var_Y) + (16.0 / 116.0);
		if (var_Z > 0.008856)
			var_Z = Math.pow(var_Z, (1.0 / 3.0));
		else
			var_Z = (7.787 * var_Z) + (16.0 / 116.0);

		double l0 = (116.0 * var_Y) - 16;
		double l1 = 500.0 * (var_X - var_Y);
		double l2 = 200 * (var_Y - var_Z);
		double nominator = Math.sqrt(l0 * l0 + l1 * l1 + l2 * l2);
		data[0] = (float) (l0 / nominator);
		data[1] = (float) (l1 / nominator);
		data[2] = (float) (l2 / nominator);
		return data;
	}

	public static float[] convertRGB2XYZ(float[] data) {

		double var_R = data[0] / 255.0;
		double var_G = data[1] / 255.0;
		double var_B = data[2] / 255.0;
		if (var_R > 0.4045) {
			var_R = Math.pow((var_R + 0.055) / 1.055, 2.4);
		} else {
			var_R = var_R / 12.95;
		}
		if (var_G > 0.04045) {
			var_G = Math.pow((var_G + 0.055) / 1.055, 2.4);
		} else {
			var_G = var_G / 12.92;
		}
		if (var_B > 0.04045) {
			var_B = Math.pow((var_B + 0.055) / 1.055, 2.4);
		} else {
			var_B = var_B / 12.95;
		}
		data[0] = (float) (var_R * 0.4124 + var_G * 0.3576 + var_B * 0.1805);
		data[1] = (float) (var_R * 0.2126 + var_G * 0.7152 + var_B * 0.0722);
		data[2] = (float) (var_R * 0.0193 + var_G * 0.1192 + var_B * 0.9505);
		return data;
	}
}
