/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bankcard.view;

import com.mycompany.bankcard.config.BytesConfig;
import com.mycompany.bankcard.config.ImageConfig;
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
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Update extends javax.swing.JFrame {

    /**
     * Creates new form Update
     */
    private BankCardRepository bankCardRepository;
    private BankCardManager bankCardManager;
    private final ImageConfig imagePanel = new ImageConfig();
    private File fileAvatarFile;
    private byte[] imageAvatar;

    public Update() {
        initComponents();
        bankCardManager = BankCardManager.getInstance();
        bankCardRepository = new BankCardRepository(bankCardManager);
        imagePanel.setBounds(2, 2, 195, 296);
        panelimage.add(imagePanel);
        setData();
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
        btnSelectImage = new javax.swing.JButton();
        btnUpdate = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("UPDATE INFORMATION");
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

        btnSelectImage.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnSelectImage.setText("Select");
        btnSelectImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSelectImageActionPerformed(evt);
            }
        });

        btnUpdate.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnUpdate.setText("Update");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
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
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(196, 196, 196)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(panelimage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(39, 39, 39)
                                .addComponent(jLabel4)
                                .addGap(18, 18, 18)
                                .addComponent(inputNameCard, javax.swing.GroupLayout.PREFERRED_SIZE, 333, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(155, 155, 155)
                                .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(55, 55, 55)
                                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(24, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(158, 158, 158)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(inputNameCard, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(217, 217, 217))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(panelimage, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSelectImage, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnUpdate, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(32, 32, 32))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        new Home().setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSelectImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSelectImageActionPerformed
        getImage();
    }//GEN-LAST:event_btnSelectImageActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        updateInformation();
    }//GEN-LAST:event_btnUpdateActionPerformed
    private void updateInformation() {
        String nameCard = inputNameCard.getText().trim();
        String errorMessage = updateInformation(nameCard);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        JOptionPane.showMessageDialog(this, "Update successful!", "Notification", JOptionPane.INFORMATION_MESSAGE);
    }

    private void setData() {
        BankCard bankCard = getInformmation();
        Image avatar = getImage(bankCard.getImageAvatar());
        imagePanel.setImage(avatar);
        if (bankCard == null) {
            JOptionPane.showMessageDialog(this, "Error!\n Please try again later.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        inputNameCard.setText(bankCard.getNameCard());
    }

    public Image getImage(byte[] data) {
        this.imageAvatar = data;
        return new ImageIcon(data).getImage();
    }

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
        this.imageAvatar = BytesConfig.fromFile(fileAvatar);
        return new ImageIcon(imageAvatar).getImage();
    }

    public BankCard getInformmation() {
        return bankCardRepository.getInformationCard();
    }

    public String updateInformation(String nameCard) {
        String errorMessage = validate(nameCard);

        if (errorMessage != null) {
            return errorMessage;
        }

        try {
            if (bankCardRepository.updateInformation(nameCard)) {
                if (bankCardRepository.updateAvatar(imageAvatar)) {
                    return null;
                }
            }

            return "Profile update failed!";
        } catch (CardException e) {
            return "Please try again later.";
        }
    }

    private String validate(String nameCard) {
        if (nameCard == null || nameCard.isBlank()) {
            return "Enter your name card.";
        }

        return null;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSelectImage;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JTextField inputNameCard;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel panelimage;
    // End of variables declaration//GEN-END:variables
}
