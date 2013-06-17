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
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

/**
 *
 * @author Blagoj Atanasovski
 */
public class VisualizationHelper {

    private static VisualizationHelper instance;
    private BufferedImage originalFile;
    private Map<CustColor, BufferedImage> colloredForCenter;
    private String originalFilename;
    private List<CustColor> gravCenters;
    private AlgorithmToApplyWithVisualization algo;

    public List<CustColor> getGravCenters() {
        return gravCenters;
    }

    public String getOriginalFilename() {
        return originalFilename;
    }

    private VisualizationHelper(ClassificationFrame frame, MethodToApply method, AlgorithmToApply algorithm, String fileName, List<CustColor> gravCenters) {

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
        this.gravCenters = gravCenters;
        originalFilename = fileName;
        frame.notifyVizuStarted();
        try {
            ClassificationResultWithVisualization rezu = algo.classifyImage(new File(originalFilename),
                    method, gravCenters);
            File origFile = new File(originalFilename);
            originalFile = ImageIO.read(origFile);
            Map<CustColor, List<int[]>> map = rezu.getPixelsToBeColored();
            int i = 0;
            for (CustColor c : map.keySet()) {
                File newFile = new File("tmp_" + i);
                copyFile(origFile, newFile);
                i++;
                BufferedImage tmp = ImageIO.read(newFile);
                colloredForCenter.put(c, tmp);
                Iterator<int[]> ite = map.get(c).iterator();
                while (ite.hasNext()) {
                    int[] next = ite.next();
                    float[] values = c.getValues();
                    Color cc = new Color((int) values[0], (int) values[1], (int) values[2]);
                    tmp.setRGB(next[0], next[1], cc.getRGB());
                }
            }
        } catch (IOException ex) {
            frame.notifyVizuEnd();
            Logger.getLogger(VisualizationHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void copyFile(File sourceFile, File destFile) throws IOException {
        if (!destFile.exists()) {
            destFile.createNewFile();
        }

        FileChannel source = null;
        FileChannel destination = null;

        try {
            source = new FileInputStream(sourceFile).getChannel();
            destination = new FileOutputStream(destFile).getChannel();
            destination.transferFrom(source, 0, source.size());
        } finally {
            if (source != null) {
                source.close();
            }
            if (destination != null) {
                destination.close();
            }
        }
    }

    public static VisualizationHelper init(ClassificationFrame frame, MethodToApply method, AlgorithmToApply algorithm, String fileName, List<CustColor> gravCenters) {
        if (instance != null && instance.getOriginalFilename().equals(fileName) && gravCenters.equals(gravCenters)) {
            return instance;
        }
        return instance = new VisualizationHelper(frame, method, algorithm, fileName, gravCenters);
    }
}
