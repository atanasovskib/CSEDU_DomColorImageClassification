/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fcse.domcolorclassifier.gui.custcomponents;

import edu.fcse.domcolorclassifier.gui.ClassificationFrame;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import javax.swing.JLabel;

/**
 *
 * @author Blagoj Atanasovski
 */
public class FrameSizeChangeListener implements ComponentListener {

    private JLabel labelToLoadInto;
    private ClassificationFrame frame;
    private boolean hasImage;

    public FrameSizeChangeListener(ClassificationFrame frame, JLabel dest) {
        this.labelToLoadInto = dest;
        this.frame = frame;
    }

    public void setImageLoaded(boolean hasImage) {
        this.hasImage = hasImage;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (hasImage) {

            int frameW = frame.getWidth() / 2;
            int frameH = frame.getHeight() / 2;
            labelToLoadInto.setSize(frameW, frameH);
        }
    }

    @Override
    public void componentMoved(ComponentEvent e) {
    }

    @Override
    public void componentShown(ComponentEvent e) {
    }

    @Override
    public void componentHidden(ComponentEvent e) {
    }
}
