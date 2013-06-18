package edu.fcse.domcolorclassifier;

import java.awt.color.CMMException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import edu.fcse.domcolorclassifier.algorithms.visualization.AddToMultipleCentersAlgorithmVIZ;
import edu.fcse.domcolorclassifier.algorithms.AlgorithmToApply;
import edu.fcse.domcolorclassifier.colorutils.ColorConvertor;
import edu.fcse.domcolorclassifier.colorutils.CustColor;

public class Classificator {

    private List<CustColor> gravityCenters;
    private AlgorithmToApply algorithm;
    private MethodToApply method;
    private List<String> filesForClassification;
    private List<ClassificationResult> classifiedFiles;
    private CustColor.ColorSpace space;

    public List<CustColor> getGravityCenters() {
        return gravityCenters;
    }

    public Classificator(File initFolder, CustColor.ColorSpace colorSpace, List<CustColor> gravityCentersInRGB, AlgorithmToApply algorithm, MethodToApply method) {
        gravityCenters = new ArrayList<>(gravityCentersInRGB.size());
        this.space = colorSpace;
        for (int i = 0; i < gravityCentersInRGB.size(); i++) {
            CustColor curr = gravityCentersInRGB.get(i);

            float[] det = convertRGBtoColorSpace(space, curr.getValues());
            gravityCenters.add(new CustColor(curr.getName(), det));
        }

        this.algorithm = algorithm;
        this.method = method;
        initFilesForClassification(initFolder);
        classifiedFiles = new ArrayList<>(filesForClassification.size());
    }

    private void initFilesForClassification(File folder) {
        if (filesForClassification == null) {
            filesForClassification = new ArrayList<>();
        }
        if (folder.isDirectory()) {
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    filesForClassification.add(file.getAbsolutePath());
                } else {
                    initFilesForClassification(file);
                }
            }
        } else {
            if (folder.isFile()) {
                filesForClassification.add(folder.getAbsolutePath());
            }
        }

    }

    public ClassificationResult classifyFile(String fileName, boolean addToListOfClassified) throws IOException {
        System.gc();
        ClassificationResult result =
                algorithm.classifyImage(new File(fileName),
                method, gravityCenters);

        if (addToListOfClassified) {
            addToClassifiedFiles(result);
        }
        return result;
    }

    private synchronized void addToClassifiedFiles(ClassificationResult result) {
        classifiedFiles.add(result);
    }

    public synchronized List<String> getFilesForClassification() {
        return filesForClassification;
    }

    public synchronized List<ClassificationResult> getClassifiedFiles() {
        return classifiedFiles;
    }

    private float[] convertRGBtoColorSpace(CustColor.ColorSpace space,
            float values[]) {
        switch (space) {
            case RGB:
                return values;
            case XYZ:
                return ColorConvertor.convertRGB2XYZ(values);
            case Lab:
                return ColorConvertor.convertRGB2LabNorm(values);
        }
        System.err.println("Only rgb, xyz and Lab supported");
        System.exit(-1);
        return null;
    }
}
