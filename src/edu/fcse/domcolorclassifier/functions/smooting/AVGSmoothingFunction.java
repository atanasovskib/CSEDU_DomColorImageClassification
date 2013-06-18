package edu.fcse.domcolorclassifier.functions.smooting;

public class AVGSmoothingFunction extends SmoothingFunction {
	int numPasses;

	public AVGSmoothingFunction(int numPasses) {
		this.numPasses = numPasses;
	}

	@Override
	public float[][][] smooth(float[][][] tmp) {
		float[][][] toreturn = new float[tmp.length][tmp[0].length][3];
		for (int p = 0; p < numPasses; p++) {
			for (int i = 1; i < tmp.length - 1; i++) {
				for (int j = 1; j < tmp[0].length - 1; j++) {
					for (int k = 0; k < 3; k++) {
						float sum = tmp[i - 1][j - 1][k] + tmp[i - 1][j][k]
								+ tmp[i - 1][j + 1][k];
						sum += tmp[i][j - 1][k] + tmp[i][j][k]
								+ tmp[i][j + 1][k];
						sum += tmp[i + 1][j - 1][k] + tmp[i + 1][j][k]
								+ tmp[i + 1][j + 1][k];
						toreturn[i][j][k] = sum / 9.0f;
					}
				}
			}
			if (p != numPasses - 1) {
				tmp = toreturn.clone();
			}
		}
		return toreturn;

	}
}
