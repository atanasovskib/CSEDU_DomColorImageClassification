/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.fcse.domcolorclassifier.gui.custcomponents;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class ImagePanel extends JPanel{

    private BufferedImage image;

    public ImagePanel(File imageFile) {
       try {                
          image = ImageIO.read(imageFile);
       } catch (IOException ex) {
            // handle exception...
       }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        g.drawImage(image, 0, 0, null);          
    }

}