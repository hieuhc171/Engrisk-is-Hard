/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Exam;

import Menu.FormMain;
import Menu.PanelMenu;
import Utils.Constants;
import Utils.NetUtils;
import Utils.WordUtils.WordObject;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.swing.*;

/**
 *
 * @author hieum
 */
public class PanelLoading extends javax.swing.JPanel {

    /**
     * Creates new form PanelLoading
     * @param testType
     */
    public PanelLoading(int testType) {
        initComponents();
        setBackground(new Color(209, 246, 246));
        KetNoiCSDL();
        UIManager.put("progressBar.background", Color.ORANGE);
        UIManager.put("progressBar.foreground", Color.BLUE);
        UIManager.put("progressBar.selectionBackground", Color.RED);
        UIManager.put("progressBar.selectionForeground", Color.GREEN);

        InitializeQuestions(testType);
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
    
    public final static int totalQuest = 10;
    private final String[] tbList = { "wordlessthan7", "wordlessthan8",
                                "wordlessthan9", "wordlessthan10",
                                "wordlessthan11", "wordlessthan13",
                                "wordmorethan13", "wordmorethan13"};
    
    private void InitializeQuestions(int testType) {
        new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception {
                for(int i = 0; i < totalQuest; i++) {
                    final String query = "SELECT Text FROM " + tbList[i % 3] + " ORDER BY RAND() LIMIT 1000";
                    PreparedStatement stm = cnn.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();
                    boolean questInited = false;
                    Question quest = new Question();
                    int index = 0;
                    while(rs.next() && index < 4) {
                        if(!questInited) {
                            NetUtils.DoGetRequest(Constants.WORD_DEFINITION_URL + rs.getString("Text"), json -> {
                                if(json != null) {
                                    WordObject word = new WordObject(json);
                                    if (testType == PanelChooseType.DEFINITION_TEST)
                                        quest.setQuestion(word.definitions.get(new Random().nextInt(word.definitions.size())).text);
                                    else {
                                        String audio = "";
                                        for(int j = 0; j < word.phonetics.size(); j++) {
                                            audio = word.phonetics.get(j).audio;
                                            if(audio != null) break;
                                        }
                                        if(!audio.isBlank())
                                            quest.setQuestion(audio);
                                    }
                                }
                            });
                            if(quest.question == null) continue;
                            questInited = true;
                        }
                        quest.setAnswers(rs.getString("Text"), index++);
                    }
                    PanelDoTest.questList.add(quest);
                    publish(i);
                    Thread.sleep(500);
                }
                
                for(int i = 11; i <= 13; i++) {
                    publish(i);
                    Thread.sleep(1000);
                }
                
                return "Finish";
            }

            @Override
            protected void process(List chunks) {
                int i = (int) chunks.get(chunks.size()-1);
                if(i < 10) {
                    progressBar.setValue((i+1) * 10);
                    lbProgress.setText("Đang khởi tạo... Câu " + (i+1) + " trên " + totalQuest);
                }
                else {
                    lbProgress.setText("Đã khởi tạo xong. Kiểm tra sẽ bắt đầu sau " + (14 - i));
                }
            }

            @Override
            protected void done() {
                FormMain.Instance().setContentPane(new PanelDoTest(testType));
                FormMain.Instance().validate();
            }
        }.execute();
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
        progressBar = new javax.swing.JProgressBar();
        lbProgress = new javax.swing.JLabel();

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        progressBar.setPreferredSize(new java.awt.Dimension(400, 50));

        lbProgress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbProgress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProgress.setPreferredSize(new java.awt.Dimension(400, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(232, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(232, 232, 232))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addGap(110, 110, 110)
                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lbProgress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(243, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelMenu.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel lbProgress;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
