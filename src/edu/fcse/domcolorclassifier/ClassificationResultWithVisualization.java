package edu.fcse.domcolorclassifier;

import edu.fcse.domcolorclassifier.colorutils.CustColor;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blagoj Atanasovski
 */
public class ClassificationResultWithVisualization extends ClassificationResult {

    private Map<CustColor, List<int[]>> pixelsToBeColored;

    public ClassificationResultWithVisualization(String fileName, CustColor classifiedAs, Map<CustColor, List<int[]>> pixelsToBeColored, Map<CustColor, Double> centerValues, int width, int height) {
        super(fileName, classifiedAs, centerValues, width, height);
        this.pixelsToBeColored = pixelsToBeColored;
    }

    public Map<CustColor, List<int[]>> getPixelsToBeColored() {
        return pixelsToBeColored;
    }
}
