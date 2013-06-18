package edu.fcse.domcolorclassifier;

import edu.fcse.domcolorclassifier.colorutils.CustColor;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Blagoj Atanasovski
 */
public class ClassificationResultWithVisualization {

    private String fileName;
    private int width, height;
    private CustColor classifiedAs;
    private Map<CustColor, List<int[]>> pixelsToBeColored;

    public ClassificationResultWithVisualization(String fileName, CustColor classifiedAs, Map<CustColor, List<int[]>> pixelsToBeColored, int width, int height) {
        this.fileName = fileName;
        this.classifiedAs = classifiedAs;
        this.pixelsToBeColored = pixelsToBeColored;
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

    public Map<CustColor, List<int[]>> getPixelsToBeColored() {
        return pixelsToBeColored;
    }
}
