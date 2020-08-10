package com.gsp.qrcodegenerator.ui;

import com.github.sarxos.webcam.Webcam;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class frmCamera extends javax.swing.JFrame {

    /**
     * Creates new form frmCamera
     */
    Webcam webcam;
    frmMain frm;
    public frmCamera() {
        initComponents();
        detectWebCam();
    }
    
    public frmCamera(frmMain _frm) {
        initComponents();
        frm = _frm;
        detectWebCam();        
    }
    
    public void detectWebCam(){
        webcam = Webcam.getDefault();
        
        if (webcam != null) {           
            Dimension[] dims = webcam.getViewSizes();
            for (Dimension dim : dims) {
                System.out.println(dim.width + " " + dim.height);
            }

            webcam.setViewSize(new Dimension(640, 480));
            System.out.println("Webcam: " + webcam.getName());
            webcam.open();
            
            new viewer().start();
        } else {
                System.out.println("No webcam detected");
        }

    }
    
    void closeWindow(){
        this.dispose();
    }
    
    class viewer extends Thread{
        public void run(){
            while(true){
                try {
                    // get image
                    BufferedImage image = webcam.getImage();
                    ImageIcon view = new ImageIcon(image);
                    lblView.setIcon(view);
                    // save image to PNG file
                    ImageIO.write(image, "PNG", new File("read.png"));
                    if(frm.readQR())
                    {
                    break;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(frmCamera.class.getName()).log(Level.SEVERE, null, ex);
                }
            }                                    
            webcam.close();
            closeWindow();                    
        }
    }

    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblView = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(640, 480));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblView, javax.swing.GroupLayout.DEFAULT_SIZE, 475, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(lblView, javax.swing.GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    public static void main(String args[]) {
        
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmCamera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmCamera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmCamera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmCamera.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmCamera().setVisible(true);
            }
        });
    }

    
    private javax.swing.JLabel lblView;
    
}
