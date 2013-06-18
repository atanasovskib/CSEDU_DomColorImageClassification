/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fcse.domcolorclassifier.gui.custcomponents;

import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Blagoj Atanasovski
 */
public class LabelSizeChangeListener implements ComponentListener {

    private JLabel labelToLoadInto;
    private BufferedImage image;
    private int oldW;
    private int oldH;

    public LabelSizeChangeListener(BufferedImage image, JLabel dest) {
        this.labelToLoadInto = dest;
        this.image = image;
        oldW = labelToLoadInto.getWidth();
        oldH = labelToLoadInto.getHeight();
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    @Override
    public void componentResized(ComponentEvent e) {
        if (image != null) {
            int width = labelToLoadInto.getWidth();
            int hei = labelToLoadInto.getHeight();
            if (Math.abs(oldW - width) > 100 || Math.abs(oldH - hei) > 100) {
                Icon icon = new ImageIcon(image);
                oldW = width;
                oldH = hei;
                int biW = image.getWidth();
                int biH = image.getHeight();
                double scale;
                if (width > hei) {
                    scale = ((double) hei) / ((double) biH);
                } else {
                    scale = ((double) width) / ((double) biW);
                }
                BufferedImage bi = new BufferedImage(
                        (int) (scale * image.getWidth()),
                        (int) (scale * image.getHeight()),
                        BufferedImage.TYPE_INT_ARGB);
                Graphics2D g = bi.createGraphics();
                g.scale(scale, scale);

                icon.paintIcon(null, g, 0, 0);
                g.dispose();
                labelToLoadInto.setIcon(new ImageIcon(bi));
                labelToLoadInto.repaint();
            }
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
