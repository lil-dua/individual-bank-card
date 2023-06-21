/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.bankcard.view;

import com.mycompany.bankcard.model.BankCard;
import com.mycompany.bankcard.repository.BankCardManager;
import com.mycompany.bankcard.repository.BankCardRepository;
import com.mycompany.bankcard.util.Constans;
import static com.mycompany.bankcard.util.Constans.CARD_HAS_BLOCKED;
import javax.smartcardio.CardException;
import javax.swing.JOptionPane;

/**
 *
 * @author admin
 */
public class Login extends javax.swing.JFrame {
    private final BankCardManager bankCardManager;
    private final BankCardRepository bankCardRepository;
    /**
     * Creates new form Login
     */
    public Login() {
        
        initComponents();
        bankCardManager= BankCardManager.getInstance();
        bankCardRepository= new BankCardRepository(bankCardManager);
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
        pinCodeField = new javax.swing.JPasswordField();
        btnLogin = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setName("login"); // NOI18N
        setSize(800,500);

        jLabel1.setFont(new java.awt.Font("Segoe UI Semibold", 1, 36)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("LOGIN");
        jLabel1.setToolTipText("");

        pinCodeField.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        pinCodeField.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        btnLogin.setFont(new java.awt.Font("Segoe UI Semibold", 1, 24)); // NOI18N
        btnLogin.setText("Login");
        btnLogin.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLoginActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Segoe UI Semibold", 1, 18)); // NOI18N
        jLabel4.setText("Input PIN");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 275, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(pinCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 255, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(270, 270, 270))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(322, 322, 322))))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(357, 357, 357)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(339, 339, 339)
                        .addComponent(jLabel1)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(47, 47, 47)
                .addComponent(jLabel1)
                .addGap(92, 92, 92)
                .addComponent(jLabel4)
                .addGap(30, 30, 30)
                .addComponent(pinCodeField, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(57, 57, 57)
                .addComponent(btnLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(102, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnLoginActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLoginActionPerformed
        String pinString=pinCodeField.getText();
        String errorMessage = connectCard(pinString);
        if (errorMessage != null) {
            JOptionPane.showMessageDialog(this, errorMessage, "Error", JOptionPane.ERROR_MESSAGE);
            
            return;
        }
        if (getInfor() == null) {
            new Create().setVisible(true);
            setVisible(false);
            return;
        }
        new Home().setVisible(true);
        setVisible(false);
    }//GEN-LAST:event_btnLoginActionPerformed

    /**
     * @param args the command line arguments
     */
     private String connectCard(String pin){
         String error = validate(pin);
         if(error!=null){
             return error;
         }
         try {
            Integer retriesRemaining;
            bankCardManager.connect();
            retriesRemaining = bankCardRepository.verify(pin);
            if (retriesRemaining == null) {
                return null;
            }
            if (retriesRemaining == 0) {
                return CARD_HAS_BLOCKED;
            }
            return "Error\nThe retries remaining is " + retriesRemaining;
        } catch (CardException e) {
            return "Card connection failed!";
        }
     } 

     private String validate( String pin) {
        if (pin == null || pin.isBlank()) {
            return "Enter your PIN";
        }

        if (pin.length() != 6) {
            return "Your PIN is invalid!";
        }

        return null;
    }
     private BankCard getInfor(){
         return bankCardRepository.getInformationCard();
     }
             
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogin;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPasswordField pinCodeField;
    // End of variables declaration//GEN-END:variables
}