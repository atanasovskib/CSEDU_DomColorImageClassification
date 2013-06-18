package edu.fcse.domcolorclassifier.functions.smooting;

public class MINSmoothingFunction extends SmoothingFunction {
	@Override
	public float[][][] smooth(float[][][] tmp) {
		float[][][] toreturn = new float[tmp.length][tmp[0].length][3];
		for (int i = 1; i < tmp.length - 1; i++) {
			for (int j = 1; j < tmp[0].length - 1; j++) {
				for (int k = 0; k < 3; k++) {
					float min1 = Math.min(
							Math.min(tmp[i - 1][j - 1][k], tmp[i - 1][j][k]),
							Math.min(tmp[i - 1][j + 1][k], tmp[i][j - 1][k]));
					float min2 = Math.min(
							Math.min(tmp[i][j][k], tmp[i][j + 1][k]),
							Math.min(tmp[i + 1][j - 1][k], tmp[i + 1][j][k]));
					min1 = Math.min(min1, min2);
					toreturn[i][j][k] = Math.min(min1, tmp[i][j + 1][k]);
				}
			}
		}
		return toreturn;

	}
}
