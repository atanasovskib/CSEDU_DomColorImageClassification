package edu.fcse.domcolorclassifier.gui.custcomponents;

import edu.fcse.domcolorclassifier.Classificator;
import edu.fcse.domcolorclassifier.gui.ClassificationFrame;
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
                        c.classifyFile(files.get(currentFile), true);
                        frame.updateTxtRezPanel();
                        
                    } else {
                        setShouldClassify(false);
                    }
                    currentFile++;
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    continue;
                }
            }
        } while (getShouldClassify() && cont);
        setShouldClassify(false);
        frame.setDone();
        
    }
}
