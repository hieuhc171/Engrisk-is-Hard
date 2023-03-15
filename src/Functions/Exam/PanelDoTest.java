/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Exam;

import Menu.FormMain;
import Utils.SoundUtils;
import Utils.TextUtils;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

/**
 *
 * @author hieum
 */
public class PanelDoTest extends javax.swing.JPanel {

    private int testType;
    /**
     * Creates new form FormDoTest
     */
    public PanelDoTest(int testType) {
        this.testType = testType;
        initComponents();
        SetupQuestionPane();
        DisplayQuestion();
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
        jScrollPane1 = new javax.swing.JScrollPane();
        questionPane = new javax.swing.JTextPane();
        btnAnswer = new JButton[4];
        for(int i = 0; i < 4; i++) {
            btnAnswer[i] = new JButton();
            btnAnswer[i].setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
            btnAnswer[i].setPreferredSize(new java.awt.Dimension(300, 80));
        }

        setPreferredSize(new java.awt.Dimension(864, 480));

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        jScrollPane1.setPreferredSize(new java.awt.Dimension(600, 150));
        jScrollPane1.setViewportView(questionPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btnBack))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(132, 132, 132)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(100, 100, 100)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAnswer[0], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAnswer[2], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnAnswer[3], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnAnswer[1], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(100, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnBack)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnswer[0], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnswer[1], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAnswer[3], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnswer[2], javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelChooseType.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnBackActionPerformed

    public final static ArrayList<Question> questList = new ArrayList<>();
    private String correctAnswer = "";
    private int currentQuest = 0;
    private int timeLeft = 3;
    private void DisplayQuestion() {
        questionPane.setText("");
        TextUtils.AppendToPane(questionPane, "Câu hỏi số " + (currentQuest+1) + "\n\n", Color.CYAN, 14);
        if(testType == PanelChooseType.DEFINITION_TEST) {
            TextUtils.AppendToPane(questionPane, "Đây là định nghĩa của từ nào?\n\n", Color.BLACK, 18);
            TextUtils.AppendToPane(questionPane, questList.get(currentQuest).question, Color.RED, 22);
        }
        else {
            TextUtils.AppendToPane(questionPane, "Đây là cách đọc của từ nào?\n\n", Color.BLACK, 22);
            TextUtils.AppendToPane(questionPane, "Âm thanh sẽ được phát sau " + timeLeft, Color.RED, 18);

            audio_counter = new Timer(1000, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String oldLine = "Âm thanh sẽ được phát sau " + timeLeft;
                    timeLeft--;
                    String newLine = "Âm thanh sẽ được phát sau " + timeLeft;
                    TextUtils.ReplaceText(questionPane, oldLine, newLine);
                    System.out.println(questionPane.getDocument().getLength());
                    System.out.println(oldLine.length());
                    if(timeLeft <= 0) {
                        SoundUtils.PlaySoundFromURL(questList.get(currentQuest).question);
                        TextUtils.ReplaceText(questionPane, newLine, "");
                        audio_counter.stop();
                    }
                }
            });
        }

        correctAnswer = questList.get(currentQuest).answers[0];
        ShuffleAnswer(questList.get(currentQuest));

        for(int i = 0; i < 4; i++) {
            btnAnswer[i].setText(questList.get(currentQuest).answers[i]);
        }
        
        if(testType == PanelChooseType.PHONETIC_TEST) {
            java.util.Timer tt = new java.util.Timer(false);
            tt.schedule(new TimerTask() {
                @Override
                public void run() {
                    audio_counter.start();
                }
            }, 1000);
        }
    }

    private static javax.swing.Timer audio_counter;

    private void ShuffleAnswer(Question quest)
    {
        Random rnd = new Random();
        for (int i = quest.answers.length - 1; i > 0; i--)
        {
            int index = rnd.nextInt(i + 1);
            String a = quest.answers[index];
            quest.answers[index] = quest.answers[i];
            quest.answers[i] = a;
        }
    }

//    private int score =

    private void SetupQuestionPane() {
        StyledDocument doc = questionPane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);
        timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for(JButton button : btnAnswer) {
                    button.setBackground(Color.WHITE);
                }
                currentQuest++;
                DisplayQuestion();
                timer.stop();
            }
        });

        for(int i = 0; i < 4; i++) {
            btnAnswer[i].addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    JButton btn = (JButton) e.getSource();
                    if(btn != null)
                        btn.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 24));
                }
                @Override
                public void mouseExited(MouseEvent e) {
                    JButton btn = (JButton) e.getSource();
                    if(btn != null)
                        btn.setFont(new java.awt.Font("Segoe UI", Font.BOLD, 18));
                }
                @Override
                public void mouseClicked(MouseEvent e) {
                    JButton btn = (JButton) e.getSource();
                    if(btn != null) {
                        if(btn.getText().equals(correctAnswer)) {
                            btn.setBackground(new Color(151, 255, 96, 255));
                        }
                        else {
                            btn.setBackground(new Color(255, 123, 123, 255));
                            for(JButton button : btnAnswer) {
                                if(button.getText().equals(correctAnswer)) {
                                    button.setBackground(new Color(151, 255, 96, 255));
                                    break;
                                }
                            }
                        }
                        java.util.Timer tt = new java.util.Timer(false);
                        tt.schedule(new TimerTask() {
                            @Override
                            public void run() {
                                timer.start();
                            }
                        }, 0);
                    }
                }
            });
        }
    }

    private static javax.swing.Timer timer;

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton[] btnAnswer;
    private javax.swing.JButton btnBack;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextPane questionPane;
    // End of variables declaration//GEN-END:variables
}
