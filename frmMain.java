
package com.gsp.qrcodegenerator.ui;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.NotFoundException;
import com.google.zxing.Result;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.gsp.qrcodegenerator.algos.Crypto;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import net.glxn.qrgen.core.image.ImageType;
import net.glxn.qrgen.javase.QRCode;


public class frmMain extends javax.swing.JFrame {

   
    Map hintMap = new HashMap();
    String filePath = "qr.png";       
    String readPath = "read.png";       
    public Color foreColor = Color.black;
    public Color backColor = Color.white;
    String overlayImage = "";

    public frmMain() {
        initComponents();
        hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        hintMap.put(EncodeHintType.CHARACTER_SET, "ISO-8859-1");                
    }

   
    @SuppressWarnings("unchecked")
    
    private void initComponents() {

        cmdGenerate = new javax.swing.JButton();
        cmdRead = new javax.swing.JButton();
        lblQR = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtText = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        jLabel3 = new javax.swing.JLabel();
        cmdChooseForecolor = new javax.swing.JButton();
        cmdChooseBackColor = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtImage = new javax.swing.JTextField();
        cmdAddImage = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        txtCaption = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cmdGenerate.setText("Generate");
        cmdGenerate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdGenerateActionPerformed(evt);
            }
        });

        cmdRead.setText("Read");
        cmdRead.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdReadActionPerformed(evt);
            }
        });

        jLabel1.setText("Text/Image URL:");

        jLabel2.setText("Password:");

        jLabel3.setText("Leave password empty for not using encryption");

        cmdChooseForecolor.setText("Change Forecolor");
        cmdChooseForecolor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChooseForecolorActionPerformed(evt);
            }
        });

        cmdChooseBackColor.setText("Change Backcolor");
        cmdChooseBackColor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdChooseBackColorActionPerformed(evt);
            }
        });

        jLabel4.setText("Overlay Image:");

        txtImage.setEditable(false);

        cmdAddImage.setText("Browse");
        cmdAddImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cmdAddImageActionPerformed(evt);
            }
        });

        jLabel5.setText("Caption Text:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(146, 146, 146)
                        .addComponent(lblQR, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                        .addGap(65, 65, 65)
                        .addComponent(cmdRead, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5)
                                .addGap(26, 26, 26)
                                .addComponent(txtCaption))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtText)
                                    .addComponent(txtPassword)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel3)
                                        .addGap(0, 125, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cmdChooseForecolor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmdChooseBackColor)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmdGenerate))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(cmdAddImage)))))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txtText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtImage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cmdAddImage))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCaption, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmdChooseForecolor)
                    .addComponent(cmdChooseBackColor)
                    .addComponent(cmdGenerate))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(215, 215, 215)
                        .addComponent(cmdRead))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(lblQR, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        pack();
    }
    private void cmdGenerateActionPerformed(java.awt.event.ActionEvent evt) {
        generateQR();
        
    }
    
    
    void generateQR(){
        try {
            BitMatrix matrix ;
            if(txtPassword.getPassword().length > 0){
               
                matrix = new MultiFormatWriter().encode("[ENC]" + 
                    new Crypto().encrypt(txtPassword.getText(), txtText.getText()),
                    BarcodeFormat.QR_CODE, 200, 200, hintMap);
                
            }
            else{
                
                matrix = new MultiFormatWriter().encode(txtText.getText(), BarcodeFormat.QR_CODE, 200, 200, hintMap);
                
            }
            
            
            MatrixToImageWriter.writeToFile(matrix, filePath.substring(filePath.lastIndexOf('.') + 1), new File(filePath));            
            
            Image overlay = null;
            if(!txtImage.getText().equals("")){
                 overlay = ImageIO.read(new File(txtImage.getText()));                 
            }            
            
            //making changes to the image
            int matrixWidth = matrix.getWidth();            
            
            BufferedImage curImage = new BufferedImage(matrixWidth, matrixWidth,
				BufferedImage.TYPE_INT_RGB);

            curImage.createGraphics();            
            Graphics2D graphics = (Graphics2D) curImage.getGraphics();
            graphics.setColor(backColor);
            graphics.fillRect(0, 0, matrixWidth, matrixWidth);                       
            
           
            graphics.setColor(foreColor);

            for (int i = 0; i < matrixWidth; i++) {
                    for (int j = 0; j < matrixWidth; j++) {
                            if (matrix.get(i, j)) {
                                    graphics.fillRect(i, j, 1, 1);
                            }
                    }
            }
           
            if(overlay != null){
                float deltaHeight = curImage.getHeight() - overlay.getHeight(this);
                float deltaWidth  = curImage.getWidth()  - overlay.getWidth(this);
                graphics.drawImage(overlay, (int)Math.round(deltaWidth/2), (int)Math.round(deltaHeight/2), null);
            }
            
            if(!txtCaption.getText().equals("")){
                graphics.drawString(txtCaption.getText(), 40, curImage.getHeight() - 10);
            }
            
            ImageIO.write(curImage, "png", new File(filePath));
            
                ImageIcon image = new ImageIcon(curImage);                        
            lblQR.setIcon(image);
            lblQR.repaint();
            this.repaint();
            
        } catch (WriterException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } 
        catch (UnsupportedEncodingException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeySpecException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cmdReadActionPerformed(java.awt.event.ActionEvent evt) {
        new frmCamera(this).setVisible(true);        
    }

    public boolean readQR(String fileName){
        boolean isRead = false;
        File read = new File(fileName);
        if (!read.exists()){
            return false;
        }
        try {            
            BinaryBitmap binaryBitmap = new BinaryBitmap(new HybridBinarizer(
                    new BufferedImageLuminanceSource(
                            ImageIO.read(new FileInputStream(fileName)))));
            Result qrCodeResult = new MultiFormatReader().decode(binaryBitmap,
                    hintMap);            
            
            isRead = true;
            new frmResult(qrCodeResult.getText()).setVisible(true);
        } catch (IOException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NotFoundException ex) {
            Logger.getLogger(frmMain.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return isRead;
    }
    
    public boolean readQR()
    {
        return readQR(readPath);
    }
    
    private void cmdChooseForecolorActionPerformed(java.awt.event.ActionEvent evt) {      
        new frmColor(this, 1).setVisible(true);
    }

    private void cmdChooseBackColorActionPerformed(java.awt.event.ActionEvent evt) {        
        new frmColor(this, 2).setVisible(true);
    }

    private void cmdAddImageActionPerformed(java.awt.event.ActionEvent evt) {       
        new frmFileSelect(this).setVisible(true);
    }

    public void addOverlay(String path){
        txtImage.setText(path);
        overlayImage = path;
    }
    
    public static void main(String args[]) {
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }
    private javax.swing.JButton cmdAddImage;
    private javax.swing.JButton cmdChooseBackColor;
    private javax.swing.JButton cmdChooseForecolor;
    private javax.swing.JButton cmdGenerate;
    private javax.swing.JButton cmdRead;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel lblQR;
    private javax.swing.JTextField txtCaption;
    private javax.swing.JTextField txtImage;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtText;
    
}
