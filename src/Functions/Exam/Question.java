/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Functions.Exam;

/**
 *
 * @author 9999
 */
public class Question {
    public String question;
    public String[] answers = new String[4];

    public Question() {
    }
    
    public Question(String question, String[] answers) {
        this.question = question;
        this.answers = answers;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswers(String answer, int index) {
        this.answers[index] = answer;
    }
    
    
}
