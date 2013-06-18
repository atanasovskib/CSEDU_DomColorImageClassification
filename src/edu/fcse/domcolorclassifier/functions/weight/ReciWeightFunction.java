package edu.fcse.domcolorclassifier.functions.weight;

public class ReciWeightFunction extends WeightFunction {

	@Override
	public double getWeight(int x, int y, int centerX, int centerY) {
		int delX, delY;
		delX = centerX - x;
		delY = centerY - y;
		double imenitel = Math.sqrt(delX * delX + delY * delY);
		if (imenitel == 0) {
			imenitel = 1;
		}
		return 1.0 / imenitel;
	}

}
