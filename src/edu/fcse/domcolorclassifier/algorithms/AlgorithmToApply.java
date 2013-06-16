package edu.fcse.domcolorclassifier.algorithms;

import java.awt.image.BufferedImage;
import java.util.List;

import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.colorutils.CustColor;

public interface AlgorithmToApply {
	/**
	 * Classifies a image by dominant color as belonging to one of the colors
	 * defined in gravityCenters
	 * 
	 * @param imageToClassify
	 *            a buffered image that will be classified
	 * @param method
	 *            to be used in the classification, tells whether to use
	 *            smoothing or no, the distance function and the weight function
	 * @param gravityCenters
	 *            the colors that the image can be classified into called
	 *            gravity centers
	 * @param fixedValue
	 *            if true the value added when counting a pixel as belonging to
	 *            one of the centers is fixed, otherwise 1/R where R=distance
	 *            from pixel color to gravity center color
	 * @return
	 */
	public String classifyImage(BufferedImage imageToClassify,
			MethodToApply method, List<CustColor> gravityCenters,
			boolean fixedValue);
}
