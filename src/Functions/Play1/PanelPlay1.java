/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Play1;

import Menu.FormMain;
import Menu.PanelMenu;
import Utils.Constants;
import Utils.Image.ImageUtils;
import Utils.NetUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;

/**
 *
 * @author hieum
 */
public class PanelPlay1 extends javax.swing.JPanel {

    private static PanelPlay1 _instance;
    public static PanelPlay1 Instance() {
//        if(_instance == null)
        _instance = new PanelPlay1();
        return _instance;
    }
    /**
     * Creates new form PanelPlay1
     */
    public PanelPlay1() {
        initComponents();
//        setBackground(new Color(209, 246, 246));
        btnBack.setBounds(6, 6, 72, 23);

        KetNoiCSDL();
        InitializeHangmanStates();
        InitializeVirtualKeyboard();
        InitializeGUI();
        this.add(background);
    }

    private JLabel background = ImageUtils.GetBackground(this, "menu.png", 864, 480);
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

    private int currentIndex = 0;
    private final int MAXSTATE = 11;
    private ImageIcon[] hangmanStates = new ImageIcon[MAXSTATE];

    private int level;
    private JLabel currentState;
    private JLabel lives;
    private void InitializeHangmanStates() {
        int size = 300;
        for(int i = 0; i < MAXSTATE; i++) {
            Image image = null;
            try {
                image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/hangman/state" + i + ".png"))
                        .getScaledInstance(size, size, Image.SCALE_SMOOTH);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            hangmanStates[i] = new ImageIcon(image);
        }
        currentState = new JLabel();
//        currentState.setIcon(hangmanStates[0]);
        currentState.setOpaque(true);
        currentState.setBackground(Color.WHITE);
        currentState.setBounds(50, 50, size, size);
        currentState.setFont(new Font("Arial", Font.BOLD, 20));
        currentState.setHorizontalAlignment(SwingConstants.CENTER);
        currentState.setVerticalAlignment(SwingConstants.CENTER);

//        currentState.setVerticalTextPosition(300 - 20);

        lives = new JLabel("0/0");
        lives.setBounds(150, 330, 100, 20);
        lives.setHorizontalAlignment(SwingConstants.CENTER);

        this.add(lives);
        this.add(currentState);
    }

    private void SetHangmanState() {
        currentIndex = (level - 4) * 2;
        currentState.setIcon(hangmanStates[currentIndex]);
    }

    private JButton[] missingWord;
    private String chosenWord;
    private JButton[] keyboard = new JButton[26];
    private boolean gameStart;

    private JLabel loading = new JLabel("LOADING...");

    private void InitializeWord()
    {
        if(missingWord != null)
            for(var btn : missingWord)
                this.remove(btn);

        missingWord = new JButton[level];
        int x = 400;
        int y = 50;
        for (int i = 0; i < missingWord.length; i++)
        {
            missingWord[i] = new JButton();
            missingWord[i].setBounds(
                    x + i * (50 + (399 - 50 * missingWord.length) / (missingWord.length-1)), y, 50, 50);
            missingWord[i].setFont(new Font("Arial", Font.BOLD, 20));
            missingWord[i].setEnabled(false);
//            missingWord[i].setForeground(Color.WHITE);
            missingWord[i].setHorizontalAlignment(SwingConstants.CENTER);
            missingWord[i].setVerticalAlignment(SwingConstants.CENTER);
//            missingWord[i].setBackground(Color.WHITE);
            this.add(missingWord[i]);
            this.remove(background);
            this.add(background);
            this.revalidate();
            this.repaint();
        }
    }

    private void InitializeVirtualKeyboard() {
        int x = 375;
        int y = 200;
        int edge = 40;
        for (int i = 0; i < 9; i++) {
            keyboard[i] = new JButton();
            keyboard[i].setBounds(x + i * 50, y, edge, edge);
            keyboard[i].setFont(new Font("Arial", Font.PLAIN, 15));
            keyboard[i].setText(String.valueOf((char)(i + 65)));
            this.add(keyboard[i]);
        }
        for (int i = 0; i < 9; i++) {
            keyboard[i + 9] = new JButton();
            keyboard[i + 9].setBounds(x + i * 50, y + 50, edge, edge);
            keyboard[i + 9].setFont(new Font("Arial", Font.PLAIN, 15));
            keyboard[i + 9].setText(String.valueOf((char)(i + 9 + 65)));
            this.add(keyboard[i + 9]);
        }
        for (int i = 0; i < 8; i++) {
            keyboard[i + 18] = new JButton();
            keyboard[i + 18].setBounds(x + i * 50, y + 100, edge, edge);
            if(i == 4) {

                keyboard[i + 18].setFont(new Font("Arial", Font.PLAIN, 13));
            }
            else keyboard[i + 18].setFont(new Font("Arial", Font.PLAIN, 15));
            keyboard[i + 18].setText(String.valueOf((char)(i + 18 + 65)));

            this.add(keyboard[i + 18]);
        }

        for(var bt : keyboard) {
            bt.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    JButton btn = (JButton)e.getSource();
                    if(!gameStart) {
                        JOptionPane.showMessageDialog(null, "Ấn 'Bắt đầu' để chơi!", "Thông báo", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    btn.setEnabled(false);
                    boolean found = false;
                    for(int i = 0; i < chosenWord.length(); i++) {
                        if(chosenWord.toCharArray()[i] == (btn.getText().toLowerCase().toCharArray()[0])) {
                            missingWord[i].setText(String.valueOf((char)(chosenWord.toCharArray()[i] - 32)));
                            found = true;
                        }
                    }
                    if(!found) {
                        currentIndex++;
                        currentState.setIcon(hangmanStates[currentIndex]);
                        if(currentIndex == MAXSTATE - 1) {
                            String[] options = {"Xác nhận", "Huỷ bỏ"};
                            int choice = JOptionPane.showOptionDialog(PanelPlay1.this, "Từ phải tìm là " + chosenWord.toUpperCase() + "\nXem định nghĩa từ này nhé?", "Thua rồi!!", JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, options, options[0]);
                            if(choice == 0) {
                                FormLearn popup = new FormLearn(chosenWord);
                                popup.setLocationRelativeTo(PanelPlay1.Instance());
                                popup.setVisible(true);
//                                return;
                            }
                            else {
                                StartDoClick(null);
                            }
                        }
                        else {

                        }
                        lives.setText((currentIndex - (level - 4) * 2) + "/" + GetDifficulty());
                    }
                    else {
                        boolean won = false;
                        for(var b : missingWord) {
                            if(!b.getText().isBlank()) won = true;
                            else return;
                        }
                        if(won) {
                            JOptionPane.showMessageDialog(null, "Thắng rồi!!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                }
            });
        }
    }

    private int GetDifficulty()
    {
        return (6 - level) * 3 + level;
    }

    private JButton start = new JButton("Bắt đầu");
    private String json = null;
    private void InitializeGUI() {
        start.setBounds(150, 375, 100, 50);
        start.setFont(new Font("Arial", Font.BOLD, 12));
        start.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                currentState.setText("LOADING...");
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        StartDoClick(t -> {
                            currentState.setText("");
                        });
                    }
                }).start();
            }
        });

        this.add(start);
    }

    private void StartDoClick(Consumer<String> consumer) {
        String query = "SELECT * FROM wordlessthan7 WHERE CHAR_LENGTH(Text) >= 4 ORDER BY RAND() LIMIT 1000";
        try {
            PreparedStatement stm = cnn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            while(json == null && rs.next()) {
                chosenWord = rs.getString("Text");
                NetUtils.DoGetRequest(Constants.WORD_DEFINITION_URL + chosenWord, result -> {
                    json = result;
                    consumer.accept("");
                });
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
        gameStart = true;
        level = chosenWord.length();
        lives.setText("0/" + GetDifficulty());
        start.setText("Chơi lại");
        InitializeWord();
        SetHangmanState();
        for(var btn : keyboard) {
            btn.setEnabled(true);
        }
        json = null;
        JOptionPane.showMessageDialog(null, "Trò chơi bắt đầu! Tìm từ có " + level + " chữ cái!!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
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
    }// </editor-fold>

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelMenu.Instance());
        FormMain.Instance().validate();
    }


    // Variables declaration - do not modify
    private javax.swing.JButton btnBack;
    // End of variables declaration
}
