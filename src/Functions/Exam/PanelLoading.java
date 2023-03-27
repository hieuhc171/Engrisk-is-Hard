/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Exam;

import Menu.FormMain;
import Menu.PanelMenu;
import Utils.Constants;
import Utils.Image.ImageUtils;
import Utils.NetUtils;
import Utils.Word.WordObject;

import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
        KetNoiCSDL();
        InitializeQuestions(testType);
//        setBackground(new Color(209, 246, 246));
        ImageUtils.InitializeBackground(this, "menu.png", 864, 480);
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

    private SwingWorker worker;

    private void InitializeQuestions(int testType) {
        worker = new SwingWorker() {
            @Override
            protected String doInBackground() throws Exception {
                for(int i = 0; i < totalQuest; i++) {
                    final String query = "SELECT Text FROM " + tbList[i % 3] + " ORDER BY RAND() LIMIT 1000";
                    PreparedStatement stm = cnn.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();
                    boolean questInited = false;
                    Question quest = new Question();
                    quest.questType = testType == PanelChooseType.DEFINITION_TEST ? new Random().nextInt(3) : Question.PHONETIC_TEST;
                    int index = 0;
                    while(rs.next() && index < 4) {
                        if(!questInited) {
                            NetUtils.DoGetRequest(Constants.WORD_DEFINITION_URL + rs.getString("Text"), json -> {
                                if(json != null) {
                                    WordObject word = new WordObject(json);
                                    if (testType == PanelChooseType.DEFINITION_TEST)
                                        switch (quest.questType) {
                                            case Question.WHICH_WORD
                                                -> quest.setQuestion(word.definitions.get(new Random().nextInt(word.definitions.size())).text);
                                            case Question.TRANSLATE
                                                -> {
                                                word.ParseViWord();
                                                if(!word.viWord.isBlank())
                                                    quest.setQuestion(word.viWord);
                                            }
                                            case Question.FILL_IN_THE_BLANK
                                                -> {
                                                String ques = word.definitions.get(new Random().nextInt(word.definitions.size())).example;
                                                int attempt = 10;
                                                while((ques.isBlank() || ques.toLowerCase().contains(word.enWord)) && attempt-- > 0) {
                                                    ques = word.definitions.get(new Random().nextInt(word.definitions.size())).example;
                                                }
                                                if(attempt > 0)
                                                    quest.setQuestion(ques.replace(word.enWord, "....."));
                                            }
                                        }
                                    else {
                                        String audio = "";
                                        for(int j = 0; j < word.phonetics.size(); j++) {
                                            audio = word.phonetics.get(j).audio;
                                            if(!audio.equals("")) break;
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
        };
        worker.execute();
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

        setPreferredSize(new java.awt.Dimension(864, 480));
        setLayout(null);

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack);
        btnBack.setBounds(6, 6, 72, 23);

        progressBar.setPreferredSize(new java.awt.Dimension(400, 50));
        add(progressBar);
        progressBar.setBounds(232, 139, 400, 50);

        lbProgress.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lbProgress.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbProgress.setPreferredSize(new java.awt.Dimension(400, 30));
        add(lbProgress);
        lbProgress.setBounds(232, 207, 400, 30);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        worker.cancel(true);
        FormMain.Instance().setContentPane(PanelMenu.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnBackActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JLabel lbProgress;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
