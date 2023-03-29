/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package Functions.Play4;

import Functions.Definition.PanelDefinition;
import Menu.FormMain;
import Menu.PanelMenu;
import Utils.Constants;
import Utils.Image.ImageUtils;
import Utils.NetUtils;
import Utils.Sound.SoundUtils;
import Utils.TextUtils;
import Utils.Word.WordObject;
import com.mysql.jdbc.Connection;
import com.mysql.jdbc.PreparedStatement;
import java.awt.Color;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author hieum
 */
public class PanelPlay4 extends javax.swing.JPanel {

    private static PanelPlay4 _instance;
    public static PanelPlay4 Instance() {
        if(_instance == null) 
            _instance = new PanelPlay4();
        return _instance;
    }
    /**
     * Creates new form PanelPlay4
     */
    public PanelPlay4() {
        initComponents();
        KetNoiCSDL();
        HienthiDSTV();
        scrollOutput.setViewportView(tfOutput);
        DefaultCaret caret = (DefaultCaret) tfOutput.getCaret();
        caret.setUpdatePolicy(DefaultCaret.NEVER_UPDATE);
        ImageUtils.InitializeBackground(this, "menu.png", 864, 480);
    }
    private Connection cnn;
    
    private void KetNoiCSDL() {
        cnn = (Connection) Database.Database.KetNoiCSDL();
        if(cnn == null) {
            JOptionPane.showMessageDialog(this, "Lỗi kết nối CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
        else {
            
        }
    }
    public void HienthiDSTV()
    {
        //kết nối đến CSDL
        if(cnn==null)
        {
            JOptionPane.showMessageDialog(this, "Lỗi Kết nối CSDL");
            return;
        }
        else
        {
            String sql = "SELECT * FROM wordlessthan7";
            try {
                PreparedStatement stm = (PreparedStatement) cnn.prepareStatement(sql);
                ResultSet rs = stm.executeQuery(sql);
                DefaultTableModel dtm = (DefaultTableModel) tbtv.getModel();
                dtm.setRowCount(0);
                int dem =0;
                while(rs.next())
                {
                    String tuvung = rs.getString("Text");
                    dtm.addRow(new Object[] {tuvung});
                    dem++;
                }
                if(dem==0)
                    JOptionPane.showMessageDialog(this, "Chưa có sinh viên");
            } catch(SQLException ex){
                JOptionPane.showMessageDialog(null, "Lỗi xử lý dữ liệu với sql !");
            }
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

        btnBack = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbtv = new javax.swing.JTable();
        scrollOutput = new javax.swing.JScrollPane();
        loading = new javax.swing.JLabel();
        tfOutput = new javax.swing.JTextPane();
        btxem = new javax.swing.JButton();
        btquizz = new javax.swing.JButton();
        checkbox = new javax.swing.JCheckBox();

        setToolTipText("");

        btnBack.setText("BACK");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        tbtv.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Từ vừng"
            }
        ));
        jScrollPane1.setViewportView(tbtv);

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

        btxem.setText("Xem thông tin");
        btxem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btxemActionPerformed(evt);
            }
        });

        btquizz.setText("Quizz");
        btquizz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btquizzActionPerformed(evt);
            }
        });

        checkbox.setText("Dịch");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 82, Short.MAX_VALUE)
                        .addComponent(scrollOutput, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnBack)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btquizz)
                        .addGap(28, 28, 28))))
            .addGroup(layout.createSequentialGroup()
                .addGap(361, 361, 361)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(checkbox)
                    .addComponent(btxem))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btquizz))
                .addGap(2, 2, 2)
                .addComponent(btxem)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(checkbox)
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(scrollOutput, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 430, Short.MAX_VALUE))
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        // TODO add your handling code here:
        PanelMenu.BGM.play();
        FormMain.Instance().setContentPane(PanelMenu.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btxemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btxemActionPerformed
        // TODO add your handling code here:
        int i =this.tbtv.getSelectedRow();
        if(i<0){
            JOptionPane.showMessageDialog(this, "Bạn chưa chọn dòng cần xem");
            return;
        }
        DefaultTableModel model = (DefaultTableModel)this.tbtv.getModel();
        String tv=(String) model.getValueAt(i, 0);;
        scrollOutput.setViewportView(loading);
        new Thread(new Runnable() {
            @Override
            public void run() {
                NetUtils.DoGetRequest(Constants.WORD_DEFINITION_URL + tv, json -> {
                    WordObject wordObject = new WordObject(json);
                    DisplayWord(wordObject);
                });
            }

            
        }).start();
    }//GEN-LAST:event_btxemActionPerformed

    private void btquizzActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btquizzActionPerformed
        // TODO add your handling code here:
        FormMain.Instance().setContentPane(QuizzMonHocEnglish.Instance());
        FormMain.Instance().validate();
    }//GEN-LAST:event_btquizzActionPerformed
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
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btquizz;
    private javax.swing.JButton btxem;
    private javax.swing.JCheckBox checkbox;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel loading;
    private javax.swing.JScrollPane scrollOutput;
    private javax.swing.JTable tbtv;
    private javax.swing.JTextPane tfOutput;
    // End of variables declaration//GEN-END:variables
}
