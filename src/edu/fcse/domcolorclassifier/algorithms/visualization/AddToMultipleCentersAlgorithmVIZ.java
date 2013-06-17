package edu.fcse.domcolorclassifier.algorithms.visualization;

import edu.fcse.domcolorclassifier.ClassificationResultWithVisualization;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import edu.fcse.domcolorclassifier.ImgData;
import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import edu.fcse.domcolorclassifier.functions.distance.DistanceFunction;
import edu.fcse.domcolorclassifier.functions.weight.WeightFunction;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import javax.imageio.ImageIO;

/**
 * Add to Multiple Color Centers Algorithm. This algorithm differs from the
 * Basic Algorithm in such a way it classifies the pixels as not only belonging
 * to one centroid but to more. It differs from the EqDistCountDoubleAlgorithm
 * that it does not look for the closest grav. centers but calculates the value
 * for all of them
 *
 * @author Blagoj Atansovski
 *
 */
public class AddToMultipleCentersAlgorithmVIZ implements AlgorithmToApplyWithVisualization {

    private final int THRESHHOLD = 220;

    @Override
    public ClassificationResultWithVisualization classifyImage(File fileToClassify, MethodToApply method, List<CustColor> gravityCenters) throws IOException {
        Map<CustColor, Double> colorAppearance = new HashMap<>();
        Map<CustColor, List<int[]>> magic = new HashMap<>();
        for (CustColor cc : gravityCenters) {
            colorAppearance.put(cc, 0.0);
            magic.put(cc, new LinkedList<int[]>());
        }
        BufferedImage imageToClassify = ImageIO.read(fileToClassify);
        ImgData imgData = new ImgData(imageToClassify);
        DistanceFunction distanceF = method.getDistanceFunction();
        WeightFunction weiF = method.getWeightFunction();
        float[][][] pixels = method.getSmooth().smooth(imgData.getRgbdata());
        float[][][] pixelsD = method.convertToColorSpace(pixels);
        int width = imageToClassify.getWidth();
        int height = imageToClassify.getHeight();

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {

                double weight = weiF.getWeight(i, j, height / 2, width / 2);
                for (int k = 0; k < gravityCenters.size(); k++) {
                    CustColor curr = gravityCenters.get(k);
                    float[] valuesCurr = curr.getValues();
                    double currDistance = distanceF.getDistance(valuesCurr,
                            pixelsD[i][j]);
                    if (currDistance <= THRESHHOLD) {
                        magic.get(curr).add(new int[]{j, i});
                    }
                    double R = 1 / currDistance;
                    colorAppearance.put(curr, colorAppearance.get(curr)
                            + weight * R);
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
        ClassificationResultWithVisualization rez = new ClassificationResultWithVisualization(fileToClassify.getName(), max, magic, width, height);
        return rez;
    }
}
