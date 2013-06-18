package edu.fcse.domcolorclassifier.functions.weight;

public class ExpWeightFunction extends WeightFunction {

	@Override
	public double getWeight(int x, int y, int centerX, int centerY) {
		int delX, delY;
		delX = centerX - x;
		delY = centerY - y;
		return Math.pow(Math.E, -Math.sqrt(delX * delX + delY * delY));
	}

}
