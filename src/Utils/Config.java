package Utils;

import Menu.PanelMenu;
import Menu.User;

import java.io.*;

public class Config {
    public static void SaveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            bw.write(String.valueOf(PanelMenu.BGM.volumeScale));
            bw.newLine();
            bw.write(String.valueOf(PanelMenu.SE.volumeScale));
            bw.newLine();
            if(User.Instance().userID == -1) {
                bw.write(String.valueOf(User.Instance().level));
                bw.newLine();
                bw.write(String.valueOf(User.Instance().exp));
                bw.newLine();
            }
            bw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void LoadConfig() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("config.txt"));
            String s = br.readLine();
            PanelMenu.BGM.volumeScale = Integer.parseInt(s);
            s = br.readLine();
            PanelMenu.SE.volumeScale = Integer.parseInt(s);
            if(User.Instance().userID == -1) {
                s = br.readLine();
                User.Instance().level = Integer.parseInt(s);
                s = br.readLine();
                User.Instance().exp = Integer.parseInt(s);
            }

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
