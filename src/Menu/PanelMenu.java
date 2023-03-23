/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Menu;

import Functions.Definition.PanelDefinition;
import Functions.Exam.PanelChooseType;
import Functions.Play1.PanelPlay1;
import Functions.Play2.PanelPlay2;
import Functions.Play3.PanelPlay3;
import Functions.Play4.PanelPlay4;
import Utils.Constants;
import Utils.NetUtils;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author chieu
 */
public class PanelMenu extends javax.swing.JPanel {

    private static PanelMenu _instance;
    public static PanelMenu Instance() {
        if(_instance == null) 
            _instance = new PanelMenu();
        return _instance;
    }
    /**
     * Creates new form PanelMenu
     */
    public PanelMenu() {
        initComponents();
        setBackground(new Color(209, 246, 246));
        CheckDatabase();
        SetupButtons();
    }
    
    private boolean isDictionaryDownloaded = false;
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
    
    private void CheckDatabase() {
        KetNoiCSDL();
        String query = "select COUNT(wordID) as 'Total' from wordlessthan7";
        try {
            PreparedStatement stm = cnn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            if(rs.next()) {
                isDictionaryDownloaded = Integer.parseInt(rs.getString("Total")) != 0;
            }
        }
        catch(NumberFormatException | SQLException ex) {
            java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(this, "Lỗi SQL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private boolean CheckDownloaded() {
        if(!isDictionaryDownloaded) {
            JOptionPane.showMessageDialog(this, "Chưa tải từ điển!", "Thông báo", JOptionPane.WARNING_MESSAGE);
        }
        return isDictionaryDownloaded;
    }
    
    private void SetupButtons() {
        int edge = 60;
        String filePath = "dictionary.png";
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/button_icons/" + filePath))
                    .getScaledInstance(edge, edge, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            btnDefinition.setIcon(icon);
            btnDefinition.setHorizontalTextPosition(SwingConstants.CENTER);
            btnDefinition.setVerticalTextPosition(SwingConstants.BOTTOM);
        } catch (IOException ex) {
            Logger.getLogger(PanelDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        btnDefinition.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e)
            {
                btnDefinition.setText("Definition");
            }
            @Override
            public void mouseEntered(MouseEvent e)
            {
                btnDefinition.setText("Tra định nghĩa");
            }
        });
        
        filePath = "exam.png";
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/button_icons/" + filePath))
                    .getScaledInstance(edge, edge, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            btnExam.setIcon(icon);
            btnExam.setHorizontalTextPosition(SwingConstants.CENTER);
            btnExam.setVerticalTextPosition(SwingConstants.BOTTOM);
        } catch (IOException ex) {
            Logger.getLogger(PanelDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }
        btnExam.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseExited(MouseEvent e)
            {
                btnExam.setText("Exam");
            }
            @Override
            public void mouseEntered(MouseEvent e)
            {
                btnExam.setText("Kiểm tra");
            }
        });

        String filePath1 = "hangman.png";
        String filePath2 = "state10.png";
        ImageIcon[] icons = new ImageIcon[2];
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/button_icons/" + filePath1))
                    .getScaledInstance(edge, edge, Image.SCALE_SMOOTH);
            icons[0] = new ImageIcon(image);
            btnHangman.setIcon(icons[0]);
            btnHangman.setHorizontalTextPosition(SwingConstants.CENTER);
            btnHangman.setVerticalTextPosition(SwingConstants.BOTTOM);

            image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/hangman/" + filePath2))
                    .getScaledInstance(edge, edge, Image.SCALE_SMOOTH);
            icons[1] = new ImageIcon(image);
        } catch (IOException ex) {
            Logger.getLogger(PanelDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }

        filePath = "wordshake.png";
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/button_icons/" + filePath))
                    .getScaledInstance(edge, edge, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);
            btnWordshake.setIcon(icon);
            btnWordshake.setHorizontalTextPosition(SwingConstants.CENTER);
            btnWordshake.setVerticalTextPosition(SwingConstants.BOTTOM);
        } catch (IOException ex) {
            Logger.getLogger(PanelDefinition.class.getName()).log(Level.SEVERE, null, ex);
        }

        filePath = "dummy.png";
        try {
            Image image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/placeholders/" + filePath))
                    .getScaledInstance(edge, edge, Image.SCALE_SMOOTH);
            ImageIcon icon = new ImageIcon(image);

            play3.setIcon(icon);
            play3.setHorizontalTextPosition(SwingConstants.CENTER);
            play3.setVerticalTextPosition(SwingConstants.BOTTOM);

            play4.setIcon(icon);
            play4.setHorizontalTextPosition(SwingConstants.CENTER);
            play4.setVerticalTextPosition(SwingConstants.BOTTOM);
        } catch (IOException ex) {
            Logger.getLogger(PanelDefinition.class.getName()).log(Level.SEVERE, null, ex);
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

        btnDefinition = new javax.swing.JButton();
        btnHangman = new javax.swing.JButton();
        btnWordshake = new javax.swing.JButton();
        btnExam = new javax.swing.JButton();
        panel1 = new java.awt.Panel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        btnDownload = new javax.swing.JButton();
        play3 = new javax.swing.JButton();
        play4 = new javax.swing.JButton();

        setBackground(new java.awt.Color(255, 255, 255));
        setPreferredSize(new java.awt.Dimension(864, 480));

        btnDefinition.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDefinition.setForeground(new java.awt.Color(51, 51, 51));
        btnDefinition.setText("Definition");
        btnDefinition.setMaximumSize(new java.awt.Dimension(150, 100));
        btnDefinition.setMinimumSize(new java.awt.Dimension(150, 100));
        btnDefinition.setPreferredSize(new java.awt.Dimension(150, 100));
        btnDefinition.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDefinitionActionPerformed(evt);
            }
        });

        btnHangman.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnHangman.setForeground(new java.awt.Color(51, 51, 51));
        btnHangman.setText("The Hangman");
        btnHangman.setMaximumSize(new java.awt.Dimension(150, 100));
        btnHangman.setMinimumSize(new java.awt.Dimension(150, 100));
        btnHangman.setPreferredSize(new java.awt.Dimension(150, 100));
        btnHangman.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHangmanActionPerformed(evt);
            }
        });

        btnWordshake.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnWordshake.setForeground(new java.awt.Color(51, 51, 51));
        btnWordshake.setText("WordShake");
        btnWordshake.setMaximumSize(new java.awt.Dimension(150, 100));
        btnWordshake.setMinimumSize(new java.awt.Dimension(150, 100));
        btnWordshake.setPreferredSize(new java.awt.Dimension(150, 100));
        btnWordshake.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnWordshakeActionPerformed(evt);
            }
        });

        btnExam.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnExam.setForeground(new java.awt.Color(51, 51, 51));
        btnExam.setText("Exam");
        btnExam.setMaximumSize(new java.awt.Dimension(150, 100));
        btnExam.setMinimumSize(new java.awt.Dimension(150, 100));
        btnExam.setPreferredSize(new java.awt.Dimension(150, 100));
        btnExam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExamActionPerformed(evt);
            }
        });

        panel1.setBackground(new java.awt.Color(0, 51, 153));
        panel1.setMaximumSize(new java.awt.Dimension(32767, 20));
        panel1.setMinimumSize(new java.awt.Dimension(100, 2));
        panel1.setPreferredSize(new java.awt.Dimension(0, 3));

        javax.swing.GroupLayout panel1Layout = new javax.swing.GroupLayout(panel1);
        panel1.setLayout(panel1Layout);
        panel1Layout.setHorizontalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        panel1Layout.setVerticalGroup(
            panel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 3, Short.MAX_VALUE)
        );

        jPanel2.setBackground(new java.awt.Color(0, 51, 153));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel2.setPreferredSize(new java.awt.Dimension(100, 3));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1, Short.MAX_VALUE)
        );

        jLabel1.setFont(new java.awt.Font("Segoe Script", 1, 36)); // NOI18N
        jLabel1.setText("LEARN");

        jLabel2.setFont(new java.awt.Font("Segoe Script", 1, 36)); // NOI18N
        jLabel2.setText("GAMES");

        btnDownload.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnDownload.setForeground(new java.awt.Color(51, 51, 51));
        btnDownload.setText("Tải từ điển");
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });

        play3.setForeground(new java.awt.Color(51, 51, 51));
        play3.setText("?");
        play3.setMaximumSize(new java.awt.Dimension(150, 100));
        play3.setMinimumSize(new java.awt.Dimension(150, 100));
        play3.setPreferredSize(new java.awt.Dimension(150, 100));
        play3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                play3ActionPerformed(evt);
            }
        });

        play4.setForeground(new java.awt.Color(51, 51, 51));
        play4.setText("?");
        play4.setMaximumSize(new java.awt.Dimension(150, 100));
        play4.setMinimumSize(new java.awt.Dimension(150, 100));
        play4.setPreferredSize(new java.awt.Dimension(150, 100));
        play4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                play4ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(panel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDownload)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 864, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(btnDefinition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(50, 50, 50)
                                .addComponent(btnExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel1))
                        .addContainerGap(496, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnHangman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2))
                        .addGap(50, 50, 50)
                        .addComponent(btnWordshake, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addGap(50, 50, 50)
                        .addComponent(play3, javax.swing.GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
                        .addGap(50, 50, 50)
                        .addComponent(play4, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                        .addGap(88, 88, 88))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addComponent(btnDownload)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addGap(2, 2, 2)
                .addComponent(panel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnDefinition, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnExam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(35, 35, 35)
                .addComponent(jLabel2)
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnHangman, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnWordshake, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(play3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(play4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(34, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnDefinitionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDefinitionActionPerformed
        // TODO add your handling code here:
        if(!CheckDownloaded()) return;
        FormMain.Instance().setContentPane(PanelDefinition.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnDefinitionActionPerformed

    private void btnExamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExamActionPerformed
        // TODO add your handling code here:
        if(!CheckDownloaded()) return;
        FormMain.Instance().setContentPane(PanelChooseType.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnExamActionPerformed
    
    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        // TODO add your handling code here:
        if(isDictionaryDownloaded) {
            JOptionPane.showMessageDialog(this, "Từ điển đã tải rồi!", "Đừng ấn nữa", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] options = {"Xác nhận", "Huỷ bỏ"};
        int choice = JOptionPane.showOptionDialog(this, "Đồng ý tải từ điển?", "Xác nhận", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(choice == 1) return;
        DownloadDictionary();
    }//GEN-LAST:event_btnDownloadActionPerformed

    private void btnHangmanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHangmanActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(new PanelPlay1());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnHangmanActionPerformed

    private void btnWordshakeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnWordshakeActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(new PanelPlay2());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnWordshakeActionPerformed

    private void play3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play3ActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelPlay3.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_play3ActionPerformed

    private void play4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_play4ActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelPlay4.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_play4ActionPerformed

    private void DownloadDictionary() {
        Thread downloadThread = new Thread(new Runnable() {
            public void run() {
                NetUtils.DoGetRequest(Constants.WORD_LIST_URL, result -> {
                    JSONArray data = new JSONArray();
                    try {
                        JSONParser jParser = new JSONParser();
                        data = (JSONArray) jParser.parse(result);

                        int length = data.size();
                        FormDownloadProcess.Instance().setVisible(true);
                        StringBuilder query = new StringBuilder();
                        for(int i = 0; i < data.size(); i++) {
                            query.append(SaveWord(data.get(i).toString()) + "\n");
                            ProgressUpdate(i, data.get(i).toString(), length);
                        }
                        try {
                            PreparedStatement stm = cnn.prepareStatement(query.toString());
                            stm.executeUpdate();
                        }   
                        catch (SQLException ex) {
                            Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    catch (ParseException ex) {
                        Logger.getLogger(PanelMenu.class.getName()).log(Level.SEVERE, null, ex);
                        JOptionPane.showMessageDialog(null, "Lỗi SQL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                    finally {
                        FormDownloadProcess.Instance().setVisible(false);
                    }
                });
            }
        });
        downloadThread.start();
    }
    
    private void ProgressUpdate(int index, String word, int size) {
        FormDownloadProcess.progressBar.setValue(index * FormDownloadProcess.progressBar.getWidth() / size);
//        FormDownloadProcess.textArea.append(word + "\n");
//        int end = FormDownloadProcess.textArea.getLineEndOffset(0);
//        FormDownloadProcess.textArea.replaceRange("", 0, end);
    }
    
    private String SaveWord(String word) {
        String tableName = "word";
        if(word.length() < 7) tableName += "lessthan7";
        else if(word.length() < 8) tableName += "lessthan8";
        else if(word.length() < 9) tableName += "lessthan9";
        else if(word.length() < 10) tableName += "lessthan10";
        else if(word.length() < 11) tableName += "lessthan11";
        else if(word.length() < 13) tableName += "lessthan13";
        else tableName += "morethan13";
        return "INSERT INTO " + tableName + "(Text) VALUES ('" + word + "')";
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnDefinition;
    private javax.swing.JButton btnDownload;
    private javax.swing.JButton btnExam;
    private javax.swing.JButton btnHangman;
    private javax.swing.JButton btnWordshake;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private java.awt.Panel panel1;
    private javax.swing.JButton play3;
    private javax.swing.JButton play4;
    // End of variables declaration//GEN-END:variables
}
