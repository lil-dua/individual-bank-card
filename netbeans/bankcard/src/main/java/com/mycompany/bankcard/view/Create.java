/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bankcard.view;

import com.mycompany.bankcard.config.BytesConfig;
import com.mycompany.bankcard.config.ImageConfig;
import com.mycompany.bankcard.data.SqlManager;
import com.mycompany.bankcard.model.BankCard;
import com.mycompany.bankcard.repository.BankCardManager;
import com.mycompany.bankcard.repository.BankCardRepository;
import java.awt.Image;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import javax.smartcardio.CardException;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.APPROVE_OPTION;
import static javax.swing.JOptionPane.ERROR_MESSAGE;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;
import static javax.swing.JOptionPane.showMessageDialog;

/**
 *
 * @author admin
 */
public class Create extends javax.swing.JFrame {

    /**
     * Creates new form Create
     */
    private BankCardRepository bankCardRepository;
    private BankCardManager bankCardManager;
    private final ImageConfig imagePanel = new ImageConfig();
    private File fileAvatarFile;
    public Create() {
        initComponents();
        bankCardManager=BankCardManager.getInstance();
        bankCardRepository=new BankCardRepository(bankCardManager);
        imagePanel.setBounds(2, 2, 195, 296);
        panelimage.add(imagePanel);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        panelimage = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        inputNameCard = new javax.swing.JTextField();
        btnCreate = new javax.swing.JButton();
        btnSelectImage = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("HOME");
        jLabel1.setToolTipText("");

        panelimage.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 1, true));
        panelimage.setName(""); // NOI18N

        javax.swing.GroupLayout panelimageLayout = new javax.swing.GroupLayout(panelimage);
        panelimage.setLayout(panelimageLayout);
        panelimageLayout.setHorizontalGroup(
            panelimageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 198, Short.MAX_VALUE)
        );
        panelimageLayout.setVerticalGroup(
            panelimageLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 298, Short.MAX_VALUE)
        );

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel4.setText("Name Card");

        inputNameCard.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        inputNameCard.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        inputNameCard.setToolTipText("");

        btnCreate.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnCreate.setText("Create");
        btnCreate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCreateActionPerformed(evt);
            }
        });

        btnSelectImage.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnSelectImage.setText("Select");
        btnSelectImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectImageActionPerformed(evt);
            }
        });

        btnBack.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addComponent(btnSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(106, 106, 106)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(panelimage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(54, 54, 54)
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(inputNameCard, javax.swing.GroupLayout.PREFERRED_SIZE, 264, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(337, 337, 337)
                        .addComponent(jLabel1)))
                .addContainerGap(149, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(116, 116, 116)
                        .addComponent(panelimage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(35, 35, 35)
                        .addComponent(jLabel1)
                        .addGap(156, 156, 156)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(inputNameCard, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCreate, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(14, 14, 14))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnSelectImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectImageActionPerformed
        getImage();
    }//GEN-LAST:event_btnSelectImageActionPerformed

    private void btnCreateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCreateActionPerformed
        createInformationCard();
    }//GEN-LAST:event_btnCreateActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        new Home().setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    /**
     * @param args the command line arguments
     */
    
    private void getImage() {
        final JFileChooser fileChooser = new JFileChooser();
        int state = fileChooser.showOpenDialog(this);

        if (state == APPROVE_OPTION) {
            File fileAvatar = fileChooser.getSelectedFile();
            Image avatar = setImage(fileAvatar);
            imagePanel.setImage(avatar);
        }
    }
    
    private Image setImage(File fileAvatar) {
        try {
            URL url = fileAvatar.toURI().toURL();
            this.fileAvatarFile = fileAvatar;
            return new ImageIcon(url).getImage();
        } catch (MalformedURLException e) {
            return null;
        }
    }
    private void createInformationCard() {
        String nameCard = inputNameCard.getText().trim();
        
        String errorMessage = createInfor(nameCard);

        if (errorMessage != null) {
            showMessageDialog(this, errorMessage, "Error", ERROR_MESSAGE);
            return;
        }
        showMessageDialog(this, "Create information bank successful.", null, INFORMATION_MESSAGE);
        new Home().setVisible(true);
        setVisible(false);
    }
    private String createInfor(String nameCard) {
        String errorMessage = validate(nameCard);

        if (errorMessage != null) {
            return errorMessage;
        }

        try {
            BankCard bankCard=bankCardRepository.createInformationBankCard(nameCard);
            if (bankCard==null){
                return "Create information bank failed!";
            }
            byte[] avatar = fileAvatarFile != null ? BytesConfig.fromFile(fileAvatarFile) : null;
            boolean test = bankCardRepository.updateAvatar(avatar);
            System.out.println(test);
            return null;
        } catch (CardException e) {
            return "Error! An error occurred. Please try again later.";
        }
    }
    private String validate(String nameCard) {

        if (nameCard == null || nameCard.isBlank()) {
            return "Enter your name card";
        }
        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCreate;
    private javax.swing.JButton btnSelectImage;
    private javax.swing.JTextField inputNameCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panelimage;
    // End of variables declaration//GEN-END:variables
}
