/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bankcard.view;

import com.mycompany.bankcard.repository.BankCardManager;
import com.mycompany.bankcard.repository.BankCardRepository;
import javax.smartcardio.CardException;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Recharge extends javax.swing.JFrame {

    private final BankCardManager bankCardManager;
    private final BankCardRepository bankCardRepository;

    /**
     * Creates new form Recharge
     */
    public Recharge() {
        initComponents();
        bankCardManager = BankCardManager.getInstance();
        bankCardRepository = new BankCardRepository(bankCardManager);
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
        btnRecharge = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        inputRecharge = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("RECHARGE");
        jLabel1.setToolTipText("");

        btnRecharge.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnRecharge.setText("Enter");
        btnRecharge.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRechargeActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel4.setText("Input Recharge Money");

        inputRecharge.setFont(new java.awt.Font("Source Sans Pro Semibold", 1, 18)); // NOI18N
        inputRecharge.setHorizontalAlignment(javax.swing.JTextField.CENTER);

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
                .addGap(227, 227, 227)
                .addComponent(btnRecharge, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 69, Short.MAX_VALUE)
                .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(206, 206, 206))
            .addGroup(layout.createSequentialGroup()
                .addGap(308, 308, 308)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(inputRecharge, javax.swing.GroupLayout.PREFERRED_SIZE, 288, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(251, 251, 251))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(jLabel1)
                .addGap(97, 97, 97)
                .addComponent(jLabel4)
                .addGap(26, 26, 26)
                .addComponent(inputRecharge, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 103, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnRecharge, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(57, 57, 57))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        new Home().setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnRechargeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRechargeActionPerformed
        rechargeMoney();
    }//GEN-LAST:event_btnRechargeActionPerformed

    private void rechargeMoney() {
        long rechargeMoney = Long.parseLong(inputRecharge.getText().trim());
        String errorMessage = rechargeMoney(rechargeMoney);

        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Faild", JOptionPane.ERROR_MESSAGE);
            return;
        }
        JOptionPane.showMessageDialog(this, "Recharge money successful!\n Recharge + " + inputRecharge.getText().trim() + "VNĐ!", "Notification", JOptionPane.INFORMATION_MESSAGE);
        new Home().setVisible(true);
        setVisible(false);
    }

    public String rechargeMoney(long moneyNumber) {
        try {
            return bankCardRepository.recharge(moneyNumber) ? null : "Recharge money faild!";
        } catch (CardException e) {
            return "Faild \n Please try again later.";
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnRecharge;
    private javax.swing.JTextField inputRecharge;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    // End of variables declaration//GEN-END:variables
}
