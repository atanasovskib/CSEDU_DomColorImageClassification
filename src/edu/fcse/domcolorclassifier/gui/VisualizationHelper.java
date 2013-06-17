package edu.fcse.domcolorclassifier.gui;

import edu.fcse.domcolorclassifier.ClassificationResultWithVisualization;
import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.algorithms.AddToMultipleCentersAlgorithm;
import edu.fcse.domcolorclassifier.algorithms.AlgorithmToApply;
import edu.fcse.domcolorclassifier.algorithms.visualization.AlgorithmToApplyWithVisualization;
import edu.fcse.domcolorclassifier.algorithms.BasicAlgorithm;
import edu.fcse.domcolorclassifier.algorithms.BasicWithDiscardDistanceAlgorithm;
import edu.fcse.domcolorclassifier.algorithms.EqDistCountDoubleAlgorithm;
import edu.fcse.domcolorclassifier.algorithms.visualization.AddToMultipleCentersAlgorithmVIZ;
import edu.fcse.domcolorclassifier.algorithms.visualization.AddToMultipleCentersMaxDistAlgorithmVIZ;
import edu.fcse.domcolorclassifier.algorithms.visualization.BasicAlgorithmVIZ;
import edu.fcse.domcolorclassifier.algorithms.visualization.BasicWithDiscardDistanceAlgorithmVIZ;
import edu.fcse.domcolorclassifier.algorithms.visualization.EqDistCountDoubleAlgorithmVIZ;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Blagoj Atanasovski
 */
public class VisualizationHelper {

    private static VisualizationHelper instance;
    private BufferedImage originalFile;
    private Map<String, BufferedImage> colloredForCenter;
    private String originalFilename;
    private List<CustColor> gravCenters;
    private MethodToApply method;
    private AlgorithmToApplyWithVisualization algo;

    public List<CustColor> getGravCenters() {
        return gravCenters;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    private VisualizationHelper(MethodToApply method, AlgorithmToApply algorithm, String fileName, List<CustColor> gravCenters) {

        if (algorithm instanceof BasicAlgorithm) {
            algo = new BasicAlgorithmVIZ();
        } else if (algorithm instanceof BasicWithDiscardDistanceAlgorithm) {
            algo = new BasicWithDiscardDistanceAlgorithmVIZ();
        } else if (algorithm instanceof EqDistCountDoubleAlgorithm) {
            algo = new EqDistCountDoubleAlgorithmVIZ();
        } else if (algorithm instanceof AddToMultipleCentersAlgorithm) {
            algo = new AddToMultipleCentersAlgorithmVIZ();
        } else {
            algo = new AddToMultipleCentersMaxDistAlgorithmVIZ();
        }
        this.method = method;
        this.gravCenters = gravCenters;
        originalFilename = fileName;
    }

    public void classify(ClassificationFrame frame) {
        frame.notifyVizuStarted();
        try {
            ClassificationResultWithVisualization rezu = algo.classifyImage(new File(originalFilename), method, gravCenters);
        } catch (IOException ex) {
            frame.notifyVizuEnd();
            Logger.getLogger(VisualizationHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static VisualizationHelper init(MethodToApply method, AlgorithmToApply algorithm, String fileName, List<CustColor> gravCenters) {
        if (instance != null && instance.getOriginalFilename().equals(fileName) && gravCenters.equals(gravCenters)) {
            return instance;
        }
        return instance = new VisualizationHelper(method, algorithm, fileName, gravCenters);
    }
}
