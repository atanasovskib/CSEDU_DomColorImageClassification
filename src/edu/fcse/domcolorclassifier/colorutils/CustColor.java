package edu.fcse.domcolorclassifier.colorutils;
import java.util.Arrays;

public class CustColor {
	public enum ColorSpace {
		RGB, XYZ, Lab,
	}

	/**
	 * for RGB first=R;second=G;third=B
	 */
	float[] values;
	String name;

	public CustColor(String name, float[] values) {
		this.values = values;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public float[] getValues() {
		return values;
	}

	public String toString() {
		return name + "#" + Arrays.toString(values);
	}

	@Override
	public boolean equals(Object obj) {
		CustColor c = (CustColor) obj;
		return Arrays.equals(values, c.values);
	}

}