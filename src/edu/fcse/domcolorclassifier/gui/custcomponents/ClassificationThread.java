package edu.fcse.domcolorclassifier.gui.custcomponents;

import edu.fcse.domcolorclassifier.ClassificationResult;
import edu.fcse.domcolorclassifier.Classificator;
import edu.fcse.domcolorclassifier.gui.ClassificationFrame;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author Blagoj Atanasovski
 */
public class ClassificationThread extends Thread {

    private ClassificationFrame frame;
    private Classificator c;
    private boolean shouldContinue;
    private int currentFile;

    public ClassificationThread(ClassificationFrame frame, Classificator c) {
        this.frame = frame;
        this.c = c;
        currentFile = 0;
        setShouldClassify(true);
    }

    public ClassificationThread(ClassificationFrame frame, Classificator c, int continueFrom) {
        this.frame = frame;
        this.c = c;
        currentFile = continueFrom;
        setShouldClassify(true);
    }

    public final synchronized void setShouldClassify(boolean should) {
        shouldContinue = should;
    }

    public synchronized boolean getShouldClassify() {
        return shouldContinue;
    }

    @Override
    public void run() {
        boolean cont = false;
        do {
            List<String> files = c.getFilesForClassification();
            cont = currentFile < files.size();
            if (getShouldClassify()) {
                try {

                    if (cont) {
                        String fileName = files.get(currentFile);
                        fileName = fileName.substring(fileName.lastIndexOf(File.separatorChar)+1);
                        frame.updateTxtRezPanel("Working on file: \n" + fileName + "\nPlease wait...");
                        ClassificationResult rez = c.classifyFile(files.get(currentFile), true);
                        frame.updateTxtRezPanel(rez);

                    } else {
                        setShouldClassify(false);
                    }
                    currentFile++;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    frame.updateTxtRezPanel("Error while procesing file: " + files.get(currentFile));
                    currentFile++;
                    continue;
                }
            }
        } while (getShouldClassify() && cont);
        setShouldClassify(false);
        frame.setDone();

    }
}
