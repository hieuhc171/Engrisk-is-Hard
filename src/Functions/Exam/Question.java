/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Functions.Exam;

/**
 *
 * @author chieu
 */
public class Question {
    public String question;
    public String[] answers = new String[4];
    public int questType;

    public static final int WHICH_WORD = 0;
    public static final int TRANSLATE = 1;
    public static final int FILL_IN_THE_BLANK = 2;
    public static final int PHONETIC_TEST = 3;

    public Question() {
    }

    public Question(String question, String[] answers, int questType) {
        this.question = question;
        this.answers = answers;
        this.questType = questType;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(String answer, int index) {
        this.answers[index] = answer;
    }
    
    
}
