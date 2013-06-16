package edu.fcse.domcolorclassifier;

import edu.fcse.domcolorclassifier.colorutils.ColorConvertor;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import edu.fcse.domcolorclassifier.functions.distance.DistanceFunction;
import edu.fcse.domcolorclassifier.functions.smooting.SmoothingFunction;
import edu.fcse.domcolorclassifier.functions.weight.WeightFunction;

public class MethodToApply {
	SmoothingFunction smooth;
	CustColor.ColorSpace space;
	WeightFunction weight;
	DistanceFunction distance;
	double discardDistance;

	public MethodToApply(SmoothingFunction smooth, CustColor.ColorSpace space,
			WeightFunction weight, DistanceFunction distance,
			double discardDistance) {
		super();
		this.smooth = smooth;
		this.space = space;
		this.weight = weight;
		this.distance = distance;
		this.discardDistance = discardDistance;
	}

	public double getDiscardDistance() {
		return discardDistance;
	}

	public DistanceFunction getDistanceFunction() {
		return distance;
	}

	public float[][][] convertToColorSpace(float[][][] rgbdata) {
		switch (space) {
		case Lab:
			for (int i = 0; i < rgbdata.length; i++) {
				for (int j = 0; j < rgbdata[i].length; j++) {
					rgbdata[i][j] = ColorConvertor
							.convertRGB2LabNorm(rgbdata[i][j]);
				}
			}
			return rgbdata;
		case XYZ:

			for (int i = 0; i < rgbdata.length; i++) {
				for (int j = 0; j < rgbdata[i].length; j++) {

					rgbdata[i][j] = ColorConvertor
							.convertRGB2XYZ(rgbdata[i][j]);
				}
			}
			return rgbdata;

		default:
			return rgbdata;
		}
	}

	public SmoothingFunction getSmooth() {
		return smooth;
	}

	public CustColor.ColorSpace getSpace() {
		return space;
	}

	public WeightFunction getWeightFunction() {
		return weight;
	}

}
