/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
 
import javax.sound.sampled.AudioFormat;
import static javax.sound.sampled.AudioFormat.Encoding.PCM_SIGNED;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import static javax.sound.sampled.AudioSystem.getAudioInputStream;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.DataLine.Info;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;
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
        } catch (Exception e) {
          System.out.println(e.getMessage());
        }

        try {
          player.play(300);
        } catch (JavaLayerException e) {
          System.out.println(e.getMessage());
        }
    }
}
