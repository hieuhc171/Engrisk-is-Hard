/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Definition;

import Menu.FormMain;
import Menu.PanelMenu;
import Utils.*;
import Utils.Image.ImageObject;
import Utils.Image.ImageUtils;
import Utils.Sound.SoundUtils;
import Utils.Word.WordObject;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author chieu
 */
public class PanelDefinition extends javax.swing.JPanel {

    private static PanelDefinition _instance;
    public static PanelDefinition Instance() {
        if(_instance == null) 
            _instance = new PanelDefinition();
        return _instance;
    }
    /**
     * Creates new form PanelDefinition
     */
    public PanelDefinition() {
        initComponents();
        KetNoiCSDL();
        GenerateSearchListener();
        InitializeIllustration();
        
        scrollOutput.setViewportView(tfOutput);
        DefaultCaret caret = (DefaultCaret) tfOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
//        setBackground(new Color(209, 246, 246));
        ImageUtils.InitializeBackground(this, "menu.png", 864, 480);
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
    
    private void GenerateSearchListener() {
        tfOutput.removeMouseListener(tfOutput.getMouseListeners()[0]);
        tfOutput.removeMouseListener(tfOutput.getMouseListeners()[1]);
        dropdownList.setVisible(false);
        scrollPane.setVisible(false);
        tfInput.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {        
                DisplaySuggestion();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {        
                DisplaySuggestion();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {        
                DisplaySuggestion();
            }
        });
        
        dropdownList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int i = dropdownList.getSelectedIndex();
                if(i < 0) return;
                if(dropdownList.getModel().getSize() == 1 && i == 1) return;
                tfInput.setText(dropdownList.getSelectedValue());
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
        tfInput = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        scrollPane = new javax.swing.JScrollPane();
        dropdownList = new javax.swing.JList<>();
        scrollOutput = new javax.swing.JScrollPane();
        loading = new javax.swing.JLabel();
        tfOutput = new javax.swing.JTextPane();
        checkbox = new javax.swing.JCheckBox();

        setPreferredSize(new java.awt.Dimension(856, 480));
        setLayout(null);

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });
        add(btnBack);
        btnBack.setBounds(6, 6, 72, 23);

        tfInput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        tfInput.setPreferredSize(new java.awt.Dimension(150, 35));
        add(tfInput);
        tfInput.setBounds(44, 55, 150, 35);

        btnSearch.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSearch.setText("Search");
        btnSearch.setPreferredSize(new java.awt.Dimension(100, 35));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        add(btnSearch);
        btnSearch.setBounds(244, 55, 100, 35);

        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setMaximumSize(new java.awt.Dimension(32767, 300));
        scrollPane.setPreferredSize(new java.awt.Dimension(150, 130));

        dropdownList.setBorder(null);
        dropdownList.setModel(new DefaultListModel());
        dropdownList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        dropdownList.setFixedCellHeight(25);
        dropdownList.setFixedCellWidth(25);
        dropdownList.setMaximumSize(new java.awt.Dimension(1000, 300));
        scrollPane.setViewportView(dropdownList);

        add(scrollPane);
        scrollPane.setBounds(44, 90, 150, 130);

        scrollOutput.setBackground(new java.awt.Color(255, 255, 255));
        scrollOutput.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollOutput.setPreferredSize(new java.awt.Dimension(400, 380));
        scrollOutput.setViewportView(null);

        loading.setBackground(new java.awt.Color(255, 255, 255));
        loading.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        loading.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        loading.setText("LOADING...");
        loading.setOpaque(true);
        scrollOutput.setViewportView(loading);

        tfOutput.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        scrollOutput.setViewportView(tfOutput);

        add(scrollOutput);
        scrollOutput.setBounds(393, 55, 400, 380);

        checkbox.setText("Dịch");
        add(checkbox);
        checkbox.setBounds(244, 96, 100, 20);
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(PanelMenu.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnBackActionPerformed

    private JLabel illustration = new JLabel("?");
    private JLabel lbTotalImages = new JLabel("0/0");
    private int totalImages = 0;
    private int currentImage = 0;
    private ArrayList<ImageIcon> imageList = new ArrayList<>();
    private void InitializeIllustration() {
        illustration.setBounds(44, 150, 250, 250);
        illustration.setFont(new Font("Arial", Font.PLAIN, 24));
        illustration.setOpaque(true);
        illustration.setBackground(Color.WHITE);
        illustration.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(illustration);

        lbTotalImages.setBounds(144, 415, 50, 35);
        lbTotalImages.setFont(new Font("Arial", Font.PLAIN, 14));
        lbTotalImages.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(lbTotalImages);

        JButton btnLeft = new JButton("<");
        btnLeft.setBounds(94, 415, 50, 35);
        btnLeft.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(btnLeft);
        btnLeft.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalImages == 0) return;
                if(currentImage == 1) currentImage = totalImages;
                else currentImage--;
                illustration.setText("");
                illustration.setIcon(imageList.get(currentImage-1));
                lbTotalImages.setText(currentImage + "/" + totalImages);
            }
        });

        JButton btnRight = new JButton(">");
        btnRight.setBounds(194, 415, 50, 35);
        btnRight.setFont(new Font("Arial", Font.BOLD, 20));
        this.add(btnRight);
        btnRight.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(totalImages == 0) return;
                if(currentImage == totalImages) currentImage = 1;
                else currentImage++;
                illustration.setText("");
                illustration.setIcon(imageList.get(currentImage-1));
                lbTotalImages.setText(currentImage + "/" + totalImages);
            }
        });
    }


    private final String[] tableNames = {"wordlessthan7", "wordlessthan8", "wordlessthan9", "wordlessthan10", "wordlessthan11", "wordlessthan13", "wordmorethan13"};
    private final int[] milestones = {7, 8, 9, 10, 11, 13, 100};
    private boolean isWordSearchedBefore(String word) {
        for(int i = 0; i < milestones.length; i++) {
            if (word.length() < milestones[i]) {
                String query = "SELECT COUNT(DefinitionID) AS Cnt FROM definitions D INNER JOIN " + tableNames[i] + " W ON D.WordID = W.WordID";
                PreparedStatement stm = null;
                try {
                    stm = cnn.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();
                    if(rs.next()) {
                        return rs.getInt("Cnt") > 0;
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return false;
    }

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        // TODO add your handling code here:
        if(tfInput.getText().length() == 0) return;
        dropdownList.setVisible(false);
        scrollPane.setVisible(false);
        loading.setText("LOADING...");
        illustration.setText("LOADING...");
        scrollOutput.setViewportView(loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.DoGetRequest(Constants.WORD_DEFINITION_URL + tfInput.getText(), json -> {
                    if(json != null) {
                        WordObject wordObject = new WordObject(json);
                        DisplayWord(wordObject);
                    }
                    else {
                        loading.setText("NOT FOUND");
                        JOptionPane.showMessageDialog(null, "Không tìm thấy định nghĩa!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                });
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                var images = ImageUtils.ImagesFromURL(tfInput.getText());
                if(images.isEmpty()) return;
                for(var image : images) {
                    if(image.tag.contains(tfInput.getText())) {
                        try {
                            URL url = new URL(image.url);
                            Image img = ImageIO.read(url).getScaledInstance(300, 300, Image.SCALE_SMOOTH);
                            ImageIcon icon = new ImageIcon(img);
                            imageList.add(icon);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                if(imageList.size() > 0) {
                    totalImages = imageList.size();
                    currentImage = 1;
                    illustration.setText("");
                    illustration.setIcon(imageList.get(0));
                    lbTotalImages.setText(currentImage + "/" + totalImages);
                }
                else {
                    illustration.setText("?");
                    illustration.setIcon(null);
                    lbTotalImages.setText("0/0");
                }
            }
        }).start();
        
    }//GEN-LAST:event_btnSearchActionPerformed

    private void DisplayWord(WordObject wordObject) {
        tfOutput.setText("");
        scrollOutput.setViewportView(tfOutput);
        if(checkbox.isSelected()) {
            wordObject.ParseViWord();
            TextUtils.AppendToPane(tfOutput, wordObject.enWord + " - " + wordObject.viWord + "\n", Color.RED, 22);
        }
        else
            TextUtils.AppendToPane(tfOutput, wordObject.enWord + "\n", Color.RED, 22);
        for(int i = 0; i < wordObject.phonetics.size(); i++) {
            if(wordObject.phonetics.get(i).text != "" && wordObject.phonetics.get(i).audio != "") {
                TextUtils.AppendToPane(tfOutput, " " + wordObject.phonetics.get(i).text + "\n", Color.BLUE, 16);
                Image image;
                try {
                    image = ImageIO.read(new File(System.getProperty("user.dir") + "/materials/definition_buttons/audio.png")).getScaledInstance(14, 14, Image.SCALE_SMOOTH);
                    ImageIcon icon = new ImageIcon(image);
                    JButton btn = new JButton();
                    btn.setIcon(icon);
                    String soundURL = wordObject.phonetics.get(i).audio;
                    btn.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mousePressed(MouseEvent e)
                        {
                            SoundUtils.PlaySoundFromURL(soundURL);
                        }
                    });
                    tfOutput.insertComponent(btn);
                } catch (IOException ex) {
                    Logger.getLogger(PanelDefinition.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        int index = 1;
        for(int i = 0; i < wordObject.definitions.size(); i++) {
            if(i == 0 || wordObject.definitions.get(i).partOfSpeech != wordObject.definitions.get(i-1).partOfSpeech) {
                TextUtils.AppendToPane(tfOutput, "\n" + wordObject.definitions.get(i).partOfSpeech + "\n", Color.GREEN, 20);
                index = 1;
            }
            TextUtils.AppendToPane(tfOutput, (index != 1 ? "\n" : "") + index++ + ". " + wordObject.definitions.get(i).text + "\n", Color.BLACK, 16);
            if(!wordObject.definitions.get(i).example.isBlank())
                TextUtils.AppendToPane(tfOutput, "Example: " + wordObject.definitions.get(i).example + "\n", Color.MAGENTA, 14);
        }

        tfOutput.setCaretPosition(0);
//        SaveWordToDatabase(wordObject);
    }

    private void DisplaySuggestion() {
        var itemList = (DefaultListModel) dropdownList.getModel();
        itemList.removeAllElements();
        if(tfInput.getText().length() < 4) {
            dropdownList.setVisible(false);
            scrollPane.setVisible(false);
            return;
        }
        dropdownList.setVisible(true);
        scrollPane.setVisible(true);
        String query;
        for(var tableName : tableNames) {
            query = "SELECT * FROM " + tableName + " WHERE Text LIKE '%" + tfInput.getText() + "%'";
            try {
                PreparedStatement stm = cnn.prepareStatement(query);
                ResultSet rs = stm.executeQuery();
                while(rs.next()) {
                    itemList.addElement(rs.getString("Text"));
                }
            }
            catch(NumberFormatException | SQLException ex) {
                java.util.logging.Logger.getLogger(PanelMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
                JOptionPane.showMessageDialog(this, "Lỗi SQL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
        if(itemList.getSize() == 0) {
            dropdownList.setVisible(false);
            return;
        }
        scrollPane.setSize(new Dimension(150, (Math.min(itemList.getSize() * 25, 150))));
    }

    //TODO: need fix
    private void SaveWordToDatabase(WordObject word) {
        for(int i = 0; i < milestones.length; i++) {
            if(word.enWord.length() < milestones[i]) {
                String query = "SELECT WordID FROM " + tableNames[i] + " WHERE Text = " + word.enWord;
                int wordID = 0;
                PreparedStatement stm = null;
                try {
                    Statement stmt = cnn.createStatement();
                    stmt.execute("SET FOREIGN_KEY_CHECKS=0");
                    stmt.close();

                    stm = cnn.prepareStatement(query);
                    ResultSet rs = stm.executeQuery();

                    while(rs.next()) {
                        wordID = rs.getInt("WordID");
                    }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                query = "";
                for(int j = 0; j < word.definitions.size(); j++) {
                    query += "\nINSERT INTO definitions (PartOfSpeech, Example, Text, WordID) VALUES ('"
                            + word.definitions.get(j).partOfSpeech + "', '" + word.definitions.get(j).example
                            + "', '" + word.definitions.get(j).text + "', " + wordID + ");";
                }
                for(int j = 0; j < word.phonetics.size(); j++) {
                    if(!word.phonetics.get(j).text.isBlank() && !word.phonetics.get(j).audio.isBlank())
                        query += "\nINSERT INTO phonetics (Text, Audio, WordID) VALUES ('"
                            + word.phonetics.get(j).text + "', '" + word.phonetics.get(j).audio + "', " + wordID + ");";
                }
                try {
                    stm = cnn.prepareStatement(query);
                    stm.executeUpdate();

                    Statement stmt = cnn.createStatement();
                    stmt.execute("SET FOREIGN_KEY_CHECKS=1");
                    stmt.close();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnSearch;
    private javax.swing.JCheckBox checkbox;
    private javax.swing.JList<String> dropdownList;
    private javax.swing.JLabel loading;
    private javax.swing.JScrollPane scrollOutput;
    private javax.swing.JScrollPane scrollPane;
    private javax.swing.JTextField tfInput;
    private javax.swing.JTextPane tfOutput;
    // End of variables declaration//GEN-END:variables

   
}
