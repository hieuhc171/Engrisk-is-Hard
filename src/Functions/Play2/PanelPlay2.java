/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Play2;

import Menu.FormMain;
import Menu.PanelMenu;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.util.Random;

/**
 *
 * @author hieum
 */
public class PanelPlay2 extends javax.swing.JPanel {

//    private static PanelPlay2 _instance;
//    public static PanelPlay2 Instance() {
//        if(_instance == null)
//            _instance = new PanelPlay2();
//        return _instance;
//    }
    /**
     * Creates new form PanelPlay2
     */
    public PanelPlay2() {
        initComponents();
        setBackground(new Color(209, 246, 246));
        btnBack.setBounds(6, 6, 72, 23);

        KetNoiCSDL();
        InitializeWordList();
        InitializeResult();
    }

    private Connection cnn;

    private void KetNoiCSDL() {
        cnn = Database.Database.KetNoiCSDL();
        if(cnn == null) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {

        }
    }

    private final int num = 4;
    private JButton[][] btnWords = new JButton[num][num];
    private void InitializeWordList() {
        Random gen = new Random();
        int x_offset = 50;
        int y_offset = 75;
        for(int i = 0; i < num; i++) {
            for(int j = 0; j < num; j++) {
                btnWords[i][j] = new JButton(String.valueOf((char)(gen.nextInt(26) + 65)));
                btnWords[i][j].setBounds(x_offset + j * 80, y_offset + i * 80, 75, 75);
                btnWords[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                btnWords[i][j].setBackground(Color.WHITE);
                btnWords[i][j].addMouseListener(ml);
                this.add(btnWords[i][j]);
            }
        }
    }

    private MouseListener ml = new MouseListener();
    class MouseListener extends MouseAdapter {
        @Override
        public void mouseClicked(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            if(btn.getBackground() == Color.GREEN) {
                btn.setBackground(Color.WHITE);
            }
            else btn.setBackground(Color.GREEN);
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            if(btn.getBackground() == Color.GREEN) return;
            btn.setBackground(Color.ORANGE);
        }

        @Override
        public void mouseExited(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            if(btn.getBackground() == Color.GREEN) return;
            btn.setBackground(Color.WHITE);
        }
    }

    private JTextArea wordArea = new JTextArea();
    private JTextArea pointArea = new JTextArea();
    private JScrollPane wordPane = new JScrollPane(wordArea);
    private JScrollPane pointPane = new JScrollPane(pointArea);
    private JLabel wordTitle = new JLabel("WORD", SwingConstants.CENTER);
    private JLabel pointTitle = new JLabel("POINT", SwingConstants.CENTER);
    private JLabel input = new JLabel("", SwingConstants.CENTER);
    private JButton cancel = new JButton("CANCEL");
    private JButton submit = new JButton("SUBMIT");
    private void InitializeResult() {
        wordTitle.setBounds(452, 75, 216, 30);
        pointTitle.setBounds(672, 75, 96, 30);
        wordTitle.setFont(new Font("Arial", Font.BOLD, 13));
        pointTitle.setFont(new Font("Arial", Font.BOLD, 13));
        wordTitle.setBorder(BorderFactory.createLineBorder(Color.black));
        pointTitle.setBorder(BorderFactory.createLineBorder(Color.black));
        wordTitle.setOpaque(true);
        pointTitle.setOpaque(true);
        wordTitle.setBackground(Color.GREEN);
        pointTitle.setBackground(Color.GREEN);
//        wordTitle.setForeground(Color.WHITE);
//        pointTitle.setForeground(Color.WHITE);

        wordPane.setBackground(Color.WHITE);
        wordArea.setBackground(Color.WHITE);
        pointPane.setBackground(Color.WHITE);
        pointArea.setBackground(Color.WHITE);
        wordPane.setBounds(450, 105, 220, 210);
        pointPane.setBounds(670, 105, 100, 210);
        wordArea.removeMouseListener(wordArea.getMouseListeners()[0]);
        wordArea.removeMouseListener(wordArea.getMouseListeners()[1]);
        pointArea.removeMouseListener(pointArea.getMouseListeners()[0]);
        pointArea.removeMouseListener(pointArea.getMouseListeners()[1]);

        input.setBorder(BorderFactory.createLineBorder(Color.black));
        input.setBounds(453, 315, 315, 35);
        input.setFont(new Font("Arial", Font.BOLD, 15));
        input.setOpaque(true);
        input.setBackground(Color.WHITE);

        cancel.setBounds(450, 350, 160, 40);
        submit.setBounds(610, 350, 160, 40);
        cancel.setFont(new Font("Arial", Font.BOLD, 15));
        submit.setFont(new Font("Arial", Font.BOLD, 15));

        this.add(wordTitle);
        this.add(pointTitle);
        this.add(wordPane);
        this.add(pointPane);

        this.add(input);
        this.add(cancel);
        this.add(submit);

    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnBack = new javax.swing.JButton();

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addContainerGap(786, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addContainerGap(451, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelMenu.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    // End of variables declaration//GEN-END:variables
}
