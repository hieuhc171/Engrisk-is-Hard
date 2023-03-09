/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author chieu
 */
public class NetUtils {
    public static void DoGetRequest(String urlPath, Consumer<String> onFinish) {
        StringBuilder result = new StringBuilder();
        try {
            URL url = new URL(urlPath);
            HttpURLConnection cnn = (HttpURLConnection) url.openConnection();
            cnn.setRequestMethod("GET");
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(cnn.getInputStream()));
            for(String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
            onFinish.accept(result.toString());
        } 
        catch (IOException ex) {
            Logger.getLogger(NetUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
    