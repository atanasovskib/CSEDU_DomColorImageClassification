package edu.fcse.domcolorclassifier.algorithms;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.fcse.domcolorclassifier.ImgData;
import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import edu.fcse.domcolorclassifier.functions.distance.DistanceFunction;
import edu.fcse.domcolorclassifier.functions.weight.WeightFunction;

/**
 * Equal Distance Count Double Algorithm. This algorithm differs from the Basic
 * Algorithm in such a way that when a pixel is equally distanced to two or more
 * gravity centers it is added as belonging to all of the grav. centers that are
 * at that distance. If the argument fixedValue passed to the classifyImage
 * method is true than the value added to the closest grav. centers is always 1
 * multiplied by the result of the weight function otherwise the value added to
 * the closest centers is W/R where R=the distance from the color of the pixel
 * and the color of the grav. center and W is the result of the weight function
 * 
 * @author Blagoj Atansovski
 * 
 */
public class EqDistCountDoubleAlgorithm implements AlgorithmToApply {

	@Override
	public String classifyImage(BufferedImage imageToClassify,
			MethodToApply method, List<CustColor> gravityCenters,
			boolean fixedValue) {
		if (imageToClassify == null) {
			return "";
		}
		HashMap<CustColor, Double> colorAppearance = new HashMap<>();
		for (CustColor cc : gravityCenters) {
			colorAppearance.put(cc, 0.0);
		}

		ImgData imgData = new ImgData(imageToClassify);
		DistanceFunction distanceF = method.getDistanceFunction();
		WeightFunction weiF = method.getWeightFunction();
		float[][][] pixels = method.getSmooth().smooth(imgData.getRgbdata());
		float[][][] pixelsD = method.convertToColorSpace(pixels);
		int width = imageToClassify.getWidth();
		int height = imageToClassify.getHeight();
		List<CustColor> minimums = new ArrayList<CustColor>(
				gravityCenters.size());
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				minimums.clear();
				minimums.add(gravityCenters.get(0));
				double minDistance = distanceF.getDistance(minimums.get(0)
						.getValues(), pixelsD[i][j]);
				for (int k = 1; k < gravityCenters.size(); k++) {
					CustColor curr = gravityCenters.get(k);
					float[] valuesCurr = curr.getValues();
					double currDistance = distanceF.getDistance(valuesCurr,
							pixelsD[i][j]);
					if (minDistance > currDistance) {
						minDistance = currDistance;
						minimums.clear();
						minimums.add(curr);
					} else if (minDistance == currDistance) {
						minimums.add(curr);
					}
				}
				double weight = weiF.getWeight(i, j, height / 2, width / 2);
				double R;
				if (fixedValue) {
					R = 1;
				} else {
					R = 1 / minDistance;
				}
				weight *= R;
				for (CustColor minColor : minimums) {
					colorAppearance.put(minColor, colorAppearance.get(minColor)
							+ weight);
				}

			}
		}

		CustColor max = gravityCenters.get(0);
		System.out.println(colorAppearance.toString());
		double maxAppearence = colorAppearance.get(max);
		for (CustColor cc : colorAppearance.keySet()) {
			if (colorAppearance.get(cc) > maxAppearence) {
				max = cc;
				maxAppearence = colorAppearance.get(cc);
			}
		}
		return max.getName();
	}

}
