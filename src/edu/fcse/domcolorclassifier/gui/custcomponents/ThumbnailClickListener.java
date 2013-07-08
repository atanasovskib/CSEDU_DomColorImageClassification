package edu.fcse.domcolorclassifier.gui.custcomponents;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Blagoj Atanasovski
 */
public class ThumbnailClickListener implements MouseListener {
    
    private BufferedImage imageToLoad;
    private JLabel labelToLoadInto;
    private FrameSizeChangeListener sizeListener;
    private LabelSizeChangeListener labelListener;
    public ThumbnailClickListener(FrameSizeChangeListener listener, LabelSizeChangeListener labelListener,BufferedImage source, JLabel dest) {
        this.imageToLoad = source;
        this.labelToLoadInto = dest;
        this.sizeListener = listener;
        this.labelListener=labelListener;
    }
    
    @Override
    public void mouseClicked(MouseEvent e) {
        int width = labelToLoadInto.getWidth();
        int hei = labelToLoadInto.getHeight();
        
        Icon icon = new ImageIcon(imageToLoad);
        int biW = imageToLoad.getWidth();
        int biH = imageToLoad.getHeight();
        double scale;
        if (width > hei) {
            scale = ((double) hei) / ((double) biH);
        } else {
            scale = ((double) width) / ((double) biW);
        }
        BufferedImage bi = new BufferedImage(
                (int) (scale * imageToLoad.getWidth()),
                (int) (scale * imageToLoad.getHeight()),
                BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bi.createGraphics();
        g.scale(scale, scale);
        
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        labelToLoadInto.setIcon(new ImageIcon(bi));
        labelToLoadInto.setText("");
        labelListener.setImage(imageToLoad);
        sizeListener.setImageLoaded(true);
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e) {
    }
    
    @Override
    public void mouseEntered(MouseEvent e) {
    }
    
    @Override
    public void mouseExited(MouseEvent e) {
    }
}
