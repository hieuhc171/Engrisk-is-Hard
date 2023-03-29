package Utils;

import Menu.PanelMenu;

import java.io.*;

public class Config {
    public static void SaveConfig() {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter("config.txt"));
            bw.write(String.valueOf(PanelMenu.BGM.volumeScale));
            bw.newLine();
            bw.write(String.valueOf(PanelMenu.SE.volumeScale));
            bw.newLine();

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

            br.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
