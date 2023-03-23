/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Play2;

import Menu.FormMain;
import Menu.PanelMenu;
import Utils.Constants;
import Utils.NetUtils;
import Utils.TextUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.TimerTask;
import java.util.function.Consumer;

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
        SetupTimer();
        InitializeResult();
        InitializeCounter();
    }

    private static javax.swing.Timer initWordCounter;
    private void SetupTimer() {
        initWordCounter = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ModifyWordList();
            }
        });
        initWordCounter.start();
    }

    private Connection cnn;

    private void KetNoiCSDL() {
        cnn = Database.Database.KetNoiCSDL();
        if(cnn == null) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        else {

        }
    }

    private final int num = 4;
    private final JButton[][] btnWords = new JButton[num][num];
    private void InitializeWordList() {
        int x_offset = 50;
        int y_offset = 100;
        Random gen = new Random();
        for (int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                btnWords[i][j] = new JButton(String.valueOf((char) (gen.nextInt(26) + 65)).toUpperCase());
                btnWords[i][j].setBounds(x_offset + j * 80, y_offset + i * 80, 75, 75);
                btnWords[i][j].setFont(new Font("Arial", Font.BOLD, 18));
                btnWords[i][j].setBackground(Color.WHITE);
                btnWords[i][j].addMouseListener(ml);
                this.add(btnWords[i][j]);
            }
        }
    }

    private void ModifyWordList() {
        Random gen = new Random();
        if(VowelCount() < 7 || DistinctCount() < 12) {
            for (int i = 0; i < num; i++) {
                for (int j = 0; j < num; j++) {
                    btnWords[i][j].setText(String.valueOf((char) (gen.nextInt(26) + 65)).toUpperCase());
                }
            }
        }
        if(VowelCount() >= 7 && DistinctCount() >= 12) initWordCounter.stop();
    }

    private int VowelCount() {
        int cnt = 0;
        ArrayList<String> vowels = new ArrayList<>(Arrays.asList("A", "I", "U", "E", "O"));
        for(int i = 0; i < num; i++) {
            for(int j = 0; j < num; j++) {
                if(vowels.contains(btnWords[i][j].getText())) {
                    cnt++;
                }
            }
        }
        return cnt;
    }

    private int DistinctCount() {
        ArrayList<String> wordList = new ArrayList<>();
        for(int i = 0; i < num; i++) {
            for(int j = 0; j < num; j++) {
                if(!wordList.contains(btnWords[i][j].getText())) {
                    wordList.add(btnWords[i][j].getText());
                }
            }
        }
        return wordList.size();
    }

    private final MouseListener ml = new MouseListener();
    class MouseListener extends MouseAdapter {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            JButton btn = (JButton) e.getSource();
//            if(btn.getBackground() == Color.GREEN) {
////                btn.setBackground(Color.WHITE);
//            }
//            else {
//                if(!playTimeCounter.isRunning()) playTimeCounter.start();
//                btn.setBackground(Color.GREEN);
//                wordArray.add(btn.getText());
//                DisplayInput();
//            }
//        }

        @Override
        public void mousePressed(MouseEvent e) {
            JButton btn = (JButton) e.getSource();
            if(btn.getBackground() == Color.GREEN) {
//                btn.setBackground(Color.WHITE);
            }
            else {
                if(!playTimeCounter.isRunning()) playTimeCounter.start();
                btn.setBackground(Color.GREEN);
                wordArray.add(btn.getText());
                DisplayInput();
            }
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

    private final JTextPane wordArea = new JTextPane();
    private final JTextPane pointArea = new JTextPane();
    private final JScrollPane wordPane = new JScrollPane(wordArea, JScrollPane.VERTICAL_SCROLLBAR_NEVER, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private final JScrollPane pointPane = new JScrollPane(pointArea, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    private final JLabel wordTitle = new JLabel("WORD", SwingConstants.CENTER);
    private final JLabel pointTitle = new JLabel("POINT", SwingConstants.CENTER);
    private final JLabel lbInput = new JLabel("", SwingConstants.CENTER);
    private final JButton btnClear = new JButton("CLEAR");
    private final JButton btnSubmit = new JButton("SUBMIT");
    private void InitializeResult() {
        popup.setOpaque(true);
        popup.setHorizontalAlignment(SwingConstants.CENTER);
        popup.setFont(new Font("Arial", Font.BOLD, 20));
        popup.setForeground(new Color(255, 255, 255));
        popup.setBounds(522, 300, 180, 40);
        this.add(popup);

        wordTitle.setBounds(452, 100, 216, 30);
        wordTitle.setFont(new Font("Arial", Font.BOLD, 15));
        wordTitle.setBorder(BorderFactory.createLineBorder(Color.black));
        wordTitle.setOpaque(true);
        wordTitle.setBackground(Color.GREEN);
//        wordTitle.setForeground(Color.WHITE);

        pointTitle.setBounds(672, 100, 96, 30);
        pointTitle.setFont(new Font("Arial", Font.BOLD, 15));
        pointTitle.setBorder(BorderFactory.createLineBorder(Color.black));
        pointTitle.setOpaque(true);
        pointTitle.setBackground(Color.GREEN);
//        pointTitle.setForeground(Color.WHITE);

        wordPane.setBackground(Color.WHITE);
        wordPane.setBounds(450, 130, 220, 210);
        wordPane.getHorizontalScrollBar().setModel(pointPane.getHorizontalScrollBar().getModel());
        wordArea.setBackground(Color.WHITE);
        wordArea.removeMouseListener(wordArea.getMouseListeners()[0]);
        wordArea.removeMouseListener(wordArea.getMouseListeners()[1]);

        StyledDocument doc = wordArea.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        pointPane.setBackground(Color.WHITE);
        pointPane.setBounds(670, 130, 100, 210);
        pointArea.setBackground(Color.WHITE);
        pointArea.removeMouseListener(pointArea.getMouseListeners()[0]);
        pointArea.removeMouseListener(pointArea.getMouseListeners()[1]);

        doc = pointArea.getStyledDocument();
        center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        lbInput.setBorder(BorderFactory.createLineBorder(Color.black));
        lbInput.setBounds(453, 340, 315, 35);
        lbInput.setFont(new Font("Arial", Font.BOLD, 15));
        lbInput.setOpaque(true);
        lbInput.setBackground(Color.WHITE);

        btnClear.setBounds(450, 375, 160, 40);
        btnClear.setFont(new Font("Arial", Font.BOLD, 15));
        btnClear.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!playTimeCounter.isRunning()) {
                    JOptionPane.showMessageDialog(null, "Chọn một chữ cái để bắt đầu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ResetComponents();
            }
        });
        btnSubmit.setBounds(610, 375, 160, 40);
        btnSubmit.setFont(new Font("Arial", Font.BOLD, 15));
        btnSubmit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(!playTimeCounter.isRunning()) {
                    JOptionPane.showMessageDialog(null, "Chọn một chữ cái để bắt đầu!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if(lbInput.getText().isBlank()) return;
                ValuatingInput(lbInput.getText());
                ResetComponents();
            }
        });

        this.add(wordTitle);
        this.add(pointTitle);
        this.add(wordPane);
        this.add(pointPane);

        this.add(lbInput);
        this.add(btnClear);
        this.add(btnSubmit);

        popup.setVisible(false);
    }

    private final ArrayList<String> wordArray = new ArrayList<>();
    private void DisplayInput() {
        lbInput.setText("");
        StringBuilder _word = new StringBuilder();
        for(var c : wordArray) {
            _word.append(c);
        }
        lbInput.setText(_word.toString());
    }

    private ArrayList<String> wordList = new ArrayList<>();
    private int totalScore = 0;
    private void ValuatingInput(String text) {
        if(wordList.contains(lbInput.getText())) {
            DisplayPopup(DUPLICATE, 0);
            return;
        }
        DisplayPopup(CHECKING, 0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.DoGetRequest(Constants.WORD_DEFINITION_URL + text.toLowerCase(), json -> {
                    if(json != null) {
                        int scoreGot = text.length() - 1;
                        DisplayPopup(RIGHT, scoreGot);
                        totalScore += scoreGot;
                        TextUtils.AppendToPane(wordArea, text + "\n", Color.BLACK, 15);
                        TextUtils.AppendToPane(pointArea, "+" + scoreGot + "\n", Color.BLACK, 15);
                        wordList.add(text);
                    }
                    else {
                        DisplayPopup(WRONG, 0);
                    }
                });
            }
        }).start();
    }

    private void ResetComponents() {
        lbInput.setText("");
        wordArray.clear();
        for(int i = 0; i < num; i++) {
            for (int j = 0; j < num; j++) {
                btnWords[i][j].setBackground(Color.WHITE);
            }
        }
    }

    private JLabel popup = new JLabel();
    private int displayTime = 1000;
    private final int RIGHT = 0;
    private final int WRONG = 1;
    private final int DUPLICATE = 2;

    private final int CHECKING = 3;
    private static javax.swing.Timer displayPopupCounter;
    private void DisplayPopup(int type, int score) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                switch (type) {
                    case RIGHT -> {
                        popup.setBackground(new Color(123, 203, 72));
                        popup.setText("+" + score);
                    }
                    case WRONG -> {
                        popup.setBackground(new Color(201, 59, 59));
                        popup.setText("WRONG");
                    }
                    case DUPLICATE -> {
                        popup.setBackground(new Color(190, 125, 66));
                        popup.setText("DUPLICATE");
                    }
                    case CHECKING ->  {
                        popup.setBackground(new Color(192, 182, 67));
                        popup.setText("CHECKING...");
                    }
                }
                popup.setVisible(true);
                displayPopupCounter = new Timer(2000, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        if(popup.getText() != "CHECKING...") {
                            popup.setVisible(false);
                            displayPopupCounter.stop();
                        }
                    }
                });
                displayPopupCounter.start();
            }
        }).start();
    }

    private static javax.swing.Timer playTimeCounter;
    private final JLabel counterDisplay = new JLabel("03:00");
    private int timeLeft = 180;

    private void InitializeCounter() {
        counterDisplay.setFont(new Font("Arial", Font.PLAIN, 24));
        counterDisplay.setBounds(350, 25, 164, 50);
        this.add(counterDisplay);
        CountTimeLeft();
    }
    private void CountTimeLeft() {
        playTimeCounter = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                counterDisplay.setText("0" + timeLeft / 60 + ":" + (timeLeft % 60 < 10 ? "0" : "") + timeLeft % 60);
                if(timeLeft == 0) {
                    playTimeCounter.stop();
                    JOptionPane.showMessageDialog(null, "Bạn đạt được " + totalScore + " điểm!", "Hết giờ", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
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
