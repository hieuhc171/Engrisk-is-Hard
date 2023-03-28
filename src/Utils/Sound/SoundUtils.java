/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.Sound;

import java.io.File;
import java.net.URL;
import java.net.URLConnection;
 
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

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


    Clip clip;
    File soundURL[] = new File[30];
    FloatControl fc;
    int volumeScale = 3;
    float volume;

    public SoundUtils() {

        soundURL[CORRECT] = new File(System.getProperty("user.dir") + "/materials/sounds/correct.wav");
        soundURL[INCORRECT] = new File(System.getProperty("user.dir") + "/materials/sounds/incorrect.wav");
        soundURL[WIN] = new File(System.getProperty("user.dir") + "/materials/sounds/win.wav");
        soundURL[LOSE] = new File(System.getProperty("user.dir") + "/materials/sounds/lose.wav");
        soundURL[CLICK] = new File(System.getProperty("user.dir") + "/materials/sounds/click.wav");
    }

    public static final int CORRECT = 0;
    public static final int INCORRECT = 1;
    public static final int WIN = 2;
    public static final int LOSE = 3;
    public static final int CLICK = 4;

    public void setFile(int i) {

        try {

            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }
        catch(Exception e) {

        }
    }

    public void play() {

        clip.start();
    }

    public void loop() {

        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {

        clip.stop();
    }

    public void checkVolume() {

        switch(volumeScale) {
            case 0: volume = -80f; break;
            case 1: volume = -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
