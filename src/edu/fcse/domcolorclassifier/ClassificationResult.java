package edu.fcse.domcolorclassifier;

import edu.fcse.domcolorclassifier.colorutils.CustColor;
import java.util.Map;

/**
 *
 * @author Blagoj Atanasovski
 */
public class ClassificationResult {

    private String fileName;
    private int width, height;
    private CustColor classifiedAs;
    private Map<CustColor, Double> centerValues;

    public ClassificationResult(String fileName, CustColor classifiedAs, Map<CustColor, Double> centerValues, int width, int height) {
        this.fileName = fileName;
        this.classifiedAs = classifiedAs;
        this.centerValues = centerValues;
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public CustColor getClassifiedAs() {
        return classifiedAs;
    }

    public void setClassifiedAs(CustColor classifiedAs) {
        this.classifiedAs = classifiedAs;
    }

    public Map<CustColor, Double> getCenterValues() {
        return centerValues;
    }

    public String getCenterValuesAsString() {
        StringBuilder sb = new StringBuilder();
        for (CustColor cc : centerValues.keySet()) {
            sb.append(cc.getName()).append(":").append(centerValues.get(cc));
            sb.append("\n");
        }
        return sb.toString();
    }

    public void setCenterValues(Map<CustColor, Double> centerValues) {
        this.centerValues = centerValues;
    }
}
