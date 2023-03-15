package Menu;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class User {

    public int userID;
    public String username;
    public int level;
    public int exp;

    public static int[] expNeeded = new int[10];

    public User() {
        userID = -1;
        username = "default_user";
        level = 0;
        exp = 0;
        GenerateExpNeeded();
    }
    private Connection cnn;
    private void KetNoiCSDL() {
        cnn = Database.Database.KetNoiCSDL();
        if(cnn == null) {
            JOptionPane.showMessageDialog(null, "Lỗi kết nối CSDL!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        else {

        }
    }
    private void GenerateExpNeeded() {
        KetNoiCSDL();
        String query = "SELECT ExpNeeded FROM level";
        try {
            PreparedStatement stm = cnn.prepareStatement(query);
            ResultSet rs = stm.executeQuery();
            int i = 0;
            while (rs.next()) {
                expNeeded[i] = rs.getInt("ExpNeeded");
                i++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setInfo(int userID, String username, int level, int exp) {
        this.userID = userID;
        this.username = username;
        this.level = level;
        this.exp = exp;
    }

    private static User _instance;
    public static User Instance() {
        if(_instance == null) {
            _instance = new User();
        }
        return _instance;
    }
}
