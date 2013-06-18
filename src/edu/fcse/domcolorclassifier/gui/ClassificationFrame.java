package edu.fcse.domcolorclassifier.gui;

import edu.fcse.domcolorclassifier.ClassificationResult;
import edu.fcse.domcolorclassifier.Classificator;
import edu.fcse.domcolorclassifier.MethodToApply;
import edu.fcse.domcolorclassifier.algorithms.AlgorithmToApply;
import edu.fcse.domcolorclassifier.colorutils.CustColor;
import edu.fcse.domcolorclassifier.gui.custcomponents.ClassificationThread;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author Blagoj Atanasovski
 */
public class ClassificationFrame extends javax.swing.JFrame {

    private Classificator classificator;
    private ClassificationThread cThread;
    private boolean clickOnList = false;
    private MethodToApply method;
    private AlgorithmToApply algor;
    private boolean isDone = false;

    public ClassificationFrame(File initFolder, CustColor.ColorSpace space, List<CustColor> centers, AlgorithmToApply algo, MethodToApply meth) throws IOException {
        initComponents();
        classificator = new Classificator(initFolder, space, centers, algo, meth);
        List<String> filesToBeClassified = classificator.getFilesForClassification();
        DefaultListModel listModel = new DefaultListModel();
        for (String fileName : filesToBeClassified) {
            listModel.addElement(fileName.substring(fileName.lastIndexOf(File.separatorChar) + 1));
        }
        this.datasetList.setModel(listModel);

        this.method = meth;
        this.algor = algo;
    }

    public void notifyVizuStarted() {
        this.setEnabled(false);
        jTabbedPane1.setSelectedIndex(1);
        largePreviewLabel.setText("Visialization started. Please wait!");
    }

    public void notifyVizuEnd() {
        this.setEnabled(true);
        largePreviewLabel.setText("Visualization done. Click on the Thumbnails to view results");
    }

    public void setDone() {
        rezTextArea.append("\n Done\n");
    }

    public synchronized void updateTxtRezPanel(String message) {
        if (jTabbedPane1.getSelectedIndex() == 0 && !clickOnList) {
            rezTextArea.append(message);
            rezTextArea.append("\n----------------------------\n");
        }
    }

    public synchronized void updateTxtRezPanel(ClassificationResult result) {
        if (jTabbedPane1.getSelectedIndex() == 0 && !clickOnList) {
            StringBuilder text = new StringBuilder();
            text.append("File: ");
            text.append(result.getFileName().substring(result.getFileName().lastIndexOf(File.separatorChar) + 1));
            text.append("\n Classified as: ");
            text.append(result.getClassifiedAs().getName());
            text.append("\nValues: ");
            text.append(result.getCenterValues().toString());
            text.append("\n-------------------------------\n");

            rezTextArea.append(text.toString());
        }

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        popupVisualizeMenuItem = new javax.swing.JMenuItem();
        jLabel1 = new javax.swing.JLabel();
        datasetScrollPane = new javax.swing.JScrollPane();
        datasetList = new javax.swing.JList();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        textRezPanel = new javax.swing.JPanel();
        showAllButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        rezTextArea = new javax.swing.JTextArea();
        visualRezPanel = new javax.swing.JPanel();
        imageDetailsScrollPane = new javax.swing.JScrollPane();
        imageDetailsVizLabel = new javax.swing.JLabel();
        thumbnailsScrollPane = new javax.swing.JScrollPane();
        thumnailHolderPanel = new javax.swing.JPanel();
        largePreviewLabel = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        cmdMenu = new javax.swing.JMenu();
        startMenuItem = new javax.swing.JMenuItem();
        pauseMenuItem = new javax.swing.JMenuItem();
        stopMenuItem = new javax.swing.JMenuItem();

        jPopupMenu1.setToolTipText("");

        popupVisualizeMenuItem.setText("Visualize classification");
        popupVisualizeMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                popupVisualizeMenuItemActionPerformed(evt);
            }
        });
        jPopupMenu1.add(popupVisualizeMenuItem);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DomColorClassification - Classification");

        jLabel1.setLabelFor(datasetScrollPane);
        jLabel1.setText("Data set");

        datasetList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        datasetList.setValueIsAdjusting(true);
        datasetList.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                datasetListMouseClicked(evt);
            }
        });
        datasetList.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                datasetListValueChanged(evt);
            }
        });
        datasetScrollPane.setViewportView(datasetList);

        showAllButton.setText("Show all");
        showAllButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                showAllButtonActionPerformed(evt);
            }
        });

        rezTextArea.setEditable(false);
        rezTextArea.setColumns(20);
        rezTextArea.setLineWrap(true);
        rezTextArea.setRows(5);
        jScrollPane1.setViewportView(rezTextArea);

        javax.swing.GroupLayout textRezPanelLayout = new javax.swing.GroupLayout(textRezPanel);
        textRezPanel.setLayout(textRezPanelLayout);
        textRezPanelLayout.setHorizontalGroup(
            textRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textRezPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(textRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 476, Short.MAX_VALUE)
                    .addGroup(textRezPanelLayout.createSequentialGroup()
                        .addComponent(showAllButton)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        textRezPanelLayout.setVerticalGroup(
            textRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(textRezPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(showAllButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 317, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Textual results", textRezPanel);

        imageDetailsScrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

        imageDetailsVizLabel.setText("Image Details go here");
        imageDetailsVizLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);
        imageDetailsScrollPane.setViewportView(imageDetailsVizLabel);

        thumbnailsScrollPane.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);

        javax.swing.GroupLayout thumnailHolderPanelLayout = new javax.swing.GroupLayout(thumnailHolderPanel);
        thumnailHolderPanel.setLayout(thumnailHolderPanelLayout);
        thumnailHolderPanelLayout.setHorizontalGroup(
            thumnailHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 263, Short.MAX_VALUE)
        );
        thumnailHolderPanelLayout.setVerticalGroup(
            thumnailHolderPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 151, Short.MAX_VALUE)
        );

        thumbnailsScrollPane.setViewportView(thumnailHolderPanel);

        largePreviewLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        largePreviewLabel.setText("tuka ide slika preview");

        javax.swing.GroupLayout visualRezPanelLayout = new javax.swing.GroupLayout(visualRezPanel);
        visualRezPanel.setLayout(visualRezPanelLayout);
        visualRezPanelLayout.setHorizontalGroup(
            visualRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(visualRezPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(visualRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(largePreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(visualRezPanelLayout.createSequentialGroup()
                        .addComponent(imageDetailsScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(thumbnailsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)))
                .addContainerGap())
        );
        visualRezPanelLayout.setVerticalGroup(
            visualRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(visualRezPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(visualRezPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(imageDetailsScrollPane)
                    .addComponent(thumbnailsScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 153, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(largePreviewLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 187, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Visualization", visualRezPanel);

        cmdMenu.setText("Controll");

        startMenuItem.setText("Start");
        startMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startMenuItemActionPerformed(evt);
            }
        });
        cmdMenu.add(startMenuItem);

        pauseMenuItem.setText("Pause");
        cmdMenu.add(pauseMenuItem);

        stopMenuItem.setText("Stop");
        stopMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stopMenuItemActionPerformed(evt);
            }
        });
        cmdMenu.add(stopMenuItem);

        jMenuBar1.add(cmdMenu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(datasetScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(datasetScrollPane)))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void datasetListValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_datasetListValueChanged
    }//GEN-LAST:event_datasetListValueChanged

    private void showAllButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_showAllButtonActionPerformed
        clickOnList = false;
        rezTextArea.setText("");
        for (ClassificationResult result : classificator.getClassifiedFiles()) {
            updateTxtRezPanel(result);
        }
    }//GEN-LAST:event_showAllButtonActionPerformed

    private void startMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startMenuItemActionPerformed
        startMenuItem.setEnabled(false);
        isDone = false;
        if (cThread != null) {
            cThread.setShouldClassify(false);
        }
        try {
            cThread.join();
        } catch (InterruptedException ex) {
            System.out.println("Old classification thread was interrupted. Starting new one");
        }
        cThread = new ClassificationThread(this, classificator);
        cThread.start();
        rezTextArea.setText("Working... Please wait");
        stopMenuItem.setEnabled(true);
    }//GEN-LAST:event_startMenuItemActionPerformed

    private void stopMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stopMenuItemActionPerformed
        isDone = true;
        startMenuItem.setEnabled(true);
        if (cThread != null) {
            cThread.setShouldClassify(false);
        }
        stopMenuItem.setEnabled(false);
    }//GEN-LAST:event_stopMenuItemActionPerformed

    private void datasetListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_datasetListMouseClicked
        if (evt.getButton() == MouseEvent.BUTTON3) {
            jPopupMenu1.show(datasetList, evt.getX(), evt.getY());
            datasetList.setSelectedIndex(datasetList.locationToIndex(evt.getPoint()));

        }
        if (jTabbedPane1.getSelectedIndex() == 0 && cThread != null) {
            this.clickOnList = true;
            int index = jTabbedPane1.getSelectedIndex();
            List<ClassificationResult> res = classificator.getClassifiedFiles();
            if (index >= res.size()) {
                rezTextArea.setText("File not yet classified");
            } else {
                ClassificationResult cr = res.get(index);
                StringBuilder text = new StringBuilder();
                text.append("File: ");
                text.append(cr.getFileName().substring(cr.getFileName().lastIndexOf(File.separatorChar) + 1));
                text.append("\n Classified as: ");
                text.append(cr.getClassifiedAs().getName());
                text.append("\nValues: ");
                text.append(cr.getCenterValues().toString());
                text.append("\n-------------------------------\n");
                rezTextArea.setText(text.toString());
            }
        }
    }//GEN-LAST:event_datasetListMouseClicked

    private void popupVisualizeMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_popupVisualizeMenuItemActionPerformed
        jTabbedPane1.setSelectedIndex(1);
        java.awt.GridLayout layout = new java.awt.GridLayout(1, 0);
        layout.setHgap(10);
        thumnailHolderPanel.removeAll();
        thumnailHolderPanel.setLayout(layout);
        List<CustColor> centers = classificator.getGravityCenters();
        VisualizationHelper helper = VisualizationHelper.init(this, method, algor,
                classificator.getFilesForClassification().get(datasetList.getSelectedIndex()), centers);
        BufferedImage[] thumbs = helper.getThumbs();
        for (BufferedImage bi : thumbs) {
            JLabel l1 = new javax.swing.JLabel(new ImageIcon(bi));
            thumnailHolderPanel.add(l1);
        }

        thumnailHolderPanel.repaint();
    }//GEN-LAST:event_popupVisualizeMenuItemActionPerformed
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenu cmdMenu;
    private javax.swing.JList datasetList;
    private javax.swing.JScrollPane datasetScrollPane;
    private javax.swing.JScrollPane imageDetailsScrollPane;
    private javax.swing.JLabel imageDetailsVizLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JPopupMenu jPopupMenu1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel largePreviewLabel;
    private javax.swing.JMenuItem pauseMenuItem;
    private javax.swing.JMenuItem popupVisualizeMenuItem;
    private javax.swing.JTextArea rezTextArea;
    private javax.swing.JButton showAllButton;
    private javax.swing.JMenuItem startMenuItem;
    private javax.swing.JMenuItem stopMenuItem;
    private javax.swing.JPanel textRezPanel;
    private javax.swing.JScrollPane thumbnailsScrollPane;
    private javax.swing.JPanel thumnailHolderPanel;
    private javax.swing.JPanel visualRezPanel;
    // End of variables declaration//GEN-END:variables
}
