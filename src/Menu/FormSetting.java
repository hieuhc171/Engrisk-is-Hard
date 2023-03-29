/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package Menu;

import Utils.Constants;
import Utils.Image.ImageUtils;
import Utils.NetUtils;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 9999
 */
public class FormSetting extends javax.swing.JFrame {

    private static FormSetting _instance;
    public static FormSetting Instance() {
        if(_instance == null)
            _instance = new FormSetting();
        return _instance;
    }
    /**
     * Creates new form FormSetting
     */
    public FormSetting() {
        this.setTitle("SETTINGS");
        getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
        setUndecorated(true);
        getRootPane().setWindowDecorationStyle(JRootPane.NONE);
        initComponents();
        KetNoiCSDL();
        InitializeGUI();
        this.setResizable(false);
        this.getContentPane().setBackground(new Color(164, 236, 255));
//        ImageUtils.InitializeBackground((JPanel) this.getContentPane(), "login.png", 300, 250);
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

    private JLabel lbBGM = new JLabel("Nhạc nền");
    private JLabel lbSE = new JLabel("Hiệu ứng");
    private JSlider slideBGM = new JSlider(SwingConstants.HORIZONTAL, 0, 5, PanelMenu.Instance().BGM.volumeScale);
    private JSlider sliderSE = new JSlider(SwingConstants.HORIZONTAL, 0, 5, PanelMenu.Instance().SE.volumeScale);
    private JButton btnDownload = new JButton("Tải từ điển");
    private void InitializeGUI() {
        lbBGM.setBounds(25, 25, 100, 35);
        lbBGM.setFont(new Font("Arial", Font.BOLD, 18));
        this.getContentPane().add(lbBGM);

        lbSE.setBounds(25, 80, 100, 35);
        lbSE.setFont(new Font("Arial", Font.BOLD, 18));
        this.getContentPane().add(lbSE);

        slideBGM.setBounds(150, 25, 125, 35);
        slideBGM.setMajorTickSpacing(1);
        slideBGM.setPaintLabels(true);
        slideBGM.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                PanelMenu.BGM.volumeScale = slideBGM.getValue();
                PanelMenu.BGM.checkVolume();
            }
        });
        this.getContentPane().add(slideBGM);

        sliderSE.setBounds(150, 80, 125, 35);
        sliderSE.setMajorTickSpacing(1);
        sliderSE.setPaintLabels(true);
        sliderSE.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                PanelMenu.SE.volumeScale = sliderSE.getValue();
            }
        });
        this.getContentPane().add(sliderSE);

        btnDownload.setBounds(150, 135, 125, 35);
        btnDownload.setFont(new Font("Arial", Font.BOLD, 18));
        btnDownload.setForeground(new java.awt.Color(51, 51, 51));
        btnDownload.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDownloadActionPerformed(evt);
            }
        });
        this.getContentPane().add(btnDownload);

        JButton btnSave = new JButton("Lưu");
        btnSave.setBounds(50, 200, 75, 35);
        btnSave.setFont(new Font("Arial", Font.BOLD, 16));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Utils.Config.SaveConfig();
                FormSetting.Instance().setVisible(false);
            }
        });
        this.add(btnSave);

        JButton btnCancel = new JButton("Hủy");
        btnCancel.setBounds(200, 200, 75, 35);
        btnCancel.setFont(new Font("Arial", Font.BOLD, 16));
        btnCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FormSetting.Instance().setVisible(false);
            }
        });
        this.add(btnCancel);
    }

    private void btnDownloadActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDownloadActionPerformed
        // TODO add your handling code here:
        if(PanelMenu.Instance().isDictionaryDownloaded) {
            JOptionPane.showMessageDialog(this, "Từ điển đã tải rồi!", "Đừng ấn nữa", JOptionPane.WARNING_MESSAGE);
            return;
        }
        String[] options = {"Xác nhận", "Huỷ bỏ"};
        int choice = JOptionPane.showOptionDialog(this, "Đồng ý tải từ điển?", "Xác nhận", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        if(choice == 1) return;
        DownloadDictionary();
    }//GEN-LAST:event_btnDownloadActionPerformed
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


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 250, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormSetting.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormSetting().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
