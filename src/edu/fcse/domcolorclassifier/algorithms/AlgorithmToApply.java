package edu.fcse.domcolorclassifier.algorithms;

import edu.fcse.domcolorclassifier.ClassificationResult;
import java.awt.image.BufferedImage;
import java.util.List;

import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import java.io.File;
import java.io.IOException;

public interface AlgorithmToApply {

   
    public ClassificationResult classifyImage(File fileToClassify,
            MethodToApply method, List<CustColor> gravityCenters) throws IOException;
}
