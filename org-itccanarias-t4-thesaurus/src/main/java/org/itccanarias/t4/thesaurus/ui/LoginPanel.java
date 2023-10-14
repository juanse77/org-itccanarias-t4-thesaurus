package org.itccanarias.t4.thesaurus.ui;

import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import org.openide.util.NbBundle;

public class LoginPanel extends JPanel {

    JPasswordField passField;
    JTextField userTextField;

    public LoginPanel() {
        this.initComponents();
    }

    public String getUser() {
        return this.userTextField.getText();
    }

    public void setUser(String user) {
        this.userTextField.setText(user);
    }

    public String getPassword() {
        return new String(this.passField.getPassword());
    }

    public void setPassword(String s) {
        this.passField.setText(s);
    }

    private void initComponents() {
        JPanel jPanel1 = new JPanel();
        JLabel jLabel1 = new JLabel();
        this.userTextField = new JTextField();
        JLabel jLabel2 = new JLabel();
        this.passField = new JPasswordField();
        JLabel jLabel3 = new JLabel();
        jPanel1.setBackground(new Color(255, 255, 255));
        jPanel1.setBorder(BorderFactory.createTitledBorder(""));
        jLabel1.setText(NbBundle.getMessage(LoginPanel.class, "LoginPanel.jLabel1.text_1"));
        this.userTextField.setText(NbBundle.getMessage(LoginPanel.class, "LoginPanel.userTextField.text_1"));
        jLabel2.setText(NbBundle.getMessage(LoginPanel.class, "LoginPanel.jLabel2.text_1"));
        this.passField.setText(NbBundle.getMessage(LoginPanel.class, "LoginPanel.passField.text_1"));
        jLabel3.setIcon(new ImageIcon(this.getClass().getResource("/org/itccanarias/t4/ws/image/logotipoDemiurge.png")));
        jLabel3.setText(NbBundle.getMessage(LoginPanel.class, "LoginPanel.jLabel3.text_1"));
        jLabel3.setMaximumSize(new Dimension(131, 205));
        jLabel3.setMinimumSize(new Dimension(131, 205));
        GroupLayout jPanel1Layout = new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap(108, 32767).addComponent(jLabel3, -2, -1, -2).addGap(107, 107, 107)).addGroup(jPanel1Layout.createSequentialGroup().addContainerGap().addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addComponent(jLabel2).addComponent(jLabel1, -2, 34, -2)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING, false).addComponent(this.userTextField, -1, 201, 32767).addComponent(this.passField))).addContainerGap(-1, 32767)));
        jPanel1Layout.setVerticalGroup(jPanel1Layout.createParallelGroup(Alignment.LEADING).addGroup(Alignment.TRAILING, jPanel1Layout.createSequentialGroup().addContainerGap().addComponent(jLabel3, -2, 31, -2).addPreferredGap(ComponentPlacement.RELATED, 19, 32767).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel1).addComponent(this.userTextField, -2, -1, -2)).addGap(18, 18, 18).addGroup(jPanel1Layout.createParallelGroup(Alignment.BASELINE).addComponent(jLabel2).addComponent(this.passField, -2, -1, -2)).addGap(18, 18, 18)));
        GroupLayout layout = new GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1, -2, -1, -2).addContainerGap(-1, 32767)));
        layout.setVerticalGroup(layout.createParallelGroup(Alignment.LEADING).addGroup(layout.createSequentialGroup().addContainerGap().addComponent(jPanel1, -1, -1, 32767).addContainerGap()));
    }
}
