package edu.fcse.domcolorclassifier.algorithms;

import edu.fcse.domcolorclassifier.algorithms.*;
import edu.fcse.domcolorclassifier.ClassificationResult;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.List;

import edu.fcse.domcolorclassifier.ImgData;
import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import edu.fcse.domcolorclassifier.functions.distance.DistanceFunction;
import edu.fcse.domcolorclassifier.functions.weight.WeightFunction;
import java.awt.color.CMMException;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * The Basic Algorithm goes over each of the pixels in a image, finds which of
 * the color centers is closest to current pixel and counts the current pixel as
 * belonging to that color center, at the end the name of the color center with
 * the most counted pixels is returned as the result of the classification
 *
 * @author Blagoj Atanasovski
 *
 */
public class BasicWithDiscardDistanceAlgorithm implements AlgorithmToApply {

    @Override
    public ClassificationResult classifyImage(File fileToClassify, MethodToApply method, List<CustColor> gravityCenters) throws IOException {

        HashMap<CustColor, Double> colorAppearence = new HashMap<>();
        for (CustColor cc : gravityCenters) {
            colorAppearence.put(cc, 0.0);
        }
        try {
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

                    CustColor min = gravityCenters.get(0);
                    double minDistance = distanceF.getDistance(min.getValues(),
                            pixelsD[i][j]);
                    for (int k = 1; k < gravityCenters.size(); k++) {
                        CustColor curr = gravityCenters.get(k);
                        float[] valuesCurr = curr.getValues();
                        double currDistance = distanceF.getDistance(valuesCurr,
                                pixelsD[i][j]);
                        if (minDistance > currDistance) {
                            minDistance = currDistance;
                            min = curr;
                        } else if (minDistance == currDistance) {
                            min = null;
                            break;
                        }
                    }
                    if (min != null) {
                        double R;
                        if (minDistance <= method.getDiscardDistance()) {
                            if (method.getFixedValue()) {
                                R = 1;
                            } else {
                                R = 1 / minDistance;
                            }
                            double weight = weiF.getWeight(i, j, height / 2, width / 2);
                            weight *= R;
                            colorAppearence.put(min, colorAppearence.get(min)
                                    + weight);
                        }
                    }

                }
            }

            CustColor max = gravityCenters.get(0);
            System.out.println(colorAppearence.toString());
            double maxAppearence = colorAppearence.get(max);
            for (CustColor cc : colorAppearence.keySet()) {
                if (colorAppearence.get(cc) > maxAppearence) {
                    max = cc;
                    maxAppearence = colorAppearence.get(cc);
                }
            }
            ClassificationResult result = new ClassificationResult(fileToClassify.getAbsolutePath(), max, colorAppearence, width, height);
            return result;
        } catch (CMMException ex) {
            throw new IOException("Could not read file: " + fileToClassify);
        }
    }
}
