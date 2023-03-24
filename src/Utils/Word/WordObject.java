/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils.Word;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import Utils.Constants;
import Utils.NetUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author hieum
 */
public class WordObject {
    public String enWord;
    public String viWord;
    public ArrayList<Definition> definitions = new ArrayList<>();
    public ArrayList<Phonetic> phonetics = new ArrayList<>();
    
    public WordObject(String json) {
        JSONArray root = new JSONArray();
        JSONObject data = new JSONObject();
        try {
            JSONParser jParser = new JSONParser();
            root = (JSONArray) jParser.parse(json);
            data = (JSONObject) root.get(0);
            enWord = data.get("word").toString();
            ParsePhonetics((JSONArray) data.get("phonetics"));
            ParseDefinitions((JSONArray) data.get("meanings"));
        } 
        catch (ParseException ex) {
            Logger.getLogger(WordObject.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void ParseViWord() {
        NetUtils.DoGetRequest(Constants.WORD_TRANSLATION_URL_ALTER + enWord, json -> {
            JSONParser jParser = new JSONParser();
            try {
                JSONArray jArray = (JSONArray) jParser.parse(json);
                jArray = (JSONArray) jArray.get(0);
                viWord = jArray.get(0).toString();
            } catch (ParseException e) {
                System.out.println("Không dịch được từ này!!!");
            }
        });
    }
    
    private void ParsePhonetics(JSONArray jArray) {
        for(int i = 0; i < jArray.size(); i++) {
            JSONObject object = (JSONObject) jArray.get(i);
            String text = object.containsKey("text") ? object.get("text").toString() : "";
            String audio = object.containsKey("audio") ? object.get("audio").toString() : "";
            phonetics.add(new Phonetic(text, audio));
        }
    }
    
    private void ParseDefinitions(JSONArray jArray) {
        for(int i = 0; i < jArray.size(); i++) {
            JSONObject object = (JSONObject) jArray.get(i);
            String partOfSpeech = object.containsKey("partOfSpeech") ? object.get("partOfSpeech").toString() : "";
            JSONArray jDefinitions = (JSONArray) object.get("definitions");
            for(int j = 0; j < jDefinitions.size(); j++) {
                JSONObject jObject = (JSONObject) jDefinitions.get(j);
                String definition = jObject.containsKey("definition") ? jObject.get("definition").toString() : "";
                String example = jObject.containsKey("example") ? jObject.get("example").toString() : "";
                definitions.add(new Definition(partOfSpeech, definition, example));
            }
        }
        
    }
}
