/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.WordUtils;

/**
 *
 * @author hieum
 */
public class Definition {
    public String partOfSpeech; // Ngữ cảnh
    public String text;
    public String example;

    public Definition(String partOfSpeech, String context, String example) {
        this.partOfSpeech = partOfSpeech;
        this.text = context;
        this.example = example;
    }
}
