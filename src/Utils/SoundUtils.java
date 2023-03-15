/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
 
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

/**
 *
 * @author hieum
 */
public class SoundUtils {
    public static void PlaySoundFromURL(String url) {
        new SoundUtils().play(url);
    }
    
    private void play(String url) {
        Player player = null;
        String mp3Source = url;
        try {
            URLConnection urlConnection = new URL(mp3Source).openConnection();
            urlConnection.connect();
            player = new Player(urlConnection.getInputStream());
        } 
        catch (Exception e) {
            System.out.println(e.getMessage());
        }

        try {
            player.play(300);
        } catch (JavaLayerException e) {
            System.out.println(e.getMessage());
        }
    }
}
