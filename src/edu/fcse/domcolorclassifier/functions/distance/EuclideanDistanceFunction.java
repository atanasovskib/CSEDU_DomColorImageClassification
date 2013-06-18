package edu.fcse.domcolorclassifier.functions.distance;

public class EuclideanDistanceFunction implements DistanceFunction {

	@Override
	public double getDistance(float[] values1, float[] values2) {
		double sum = 0;
		for (int i = 0; i < values1.length; i++) {
			double tmp = values1[i] - values2[i];
			sum += tmp * tmp;
		}
		return Math.sqrt(sum);
	}

}
