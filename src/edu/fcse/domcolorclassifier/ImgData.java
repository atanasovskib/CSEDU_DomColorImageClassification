package edu.fcse.domcolorclassifier;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ImgData {
	float[][][] rgbdata;

	public ImgData(BufferedImage img) {
		rgbdata = new float[img.getHeight()][img.getWidth()][3];
		for (int i = 0; i < rgbdata.length; i++) {
			for (int j = 0; j < rgbdata[0].length; j++) {
				Color c = new Color(img.getRGB(j, i));
				rgbdata[i][j][0] = c.getRed();
				rgbdata[i][j][1] = c.getGreen();
				rgbdata[i][j][2] = c.getBlue();
			}
		}
	}

	public float[][][] getRgbdata() {
		return rgbdata;
	}

}
