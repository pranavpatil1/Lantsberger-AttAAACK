
/**
 * The Questions class stores a list of Question objects and prints out the question for the user
 * It also notifies the user if they answered correctly or incorrectly 
 *
 * @Aruna Sudarshan, Keerthana
 * @5/21/2018
 */
import java.util.ArrayList;

public class Question
{
    private String correctAnswer; //stores correct answer to the question
    private String questions; //stores the actual question
    private String[] answers; //array that stores the 4 answer choices
    private String explanation;
    
    /**
     * Questions class constructor instantiates the question, the array of answer choices, and the correct answer
     */
    public Question(String[] answerChoices, String answer, String question, String exp)
    {
       answers = new String[4];
       for(int i = 0; i < 4; i++)
       {
           int randIndex = 0;
           while(!(answers[randIndex] == null))
               randIndex = (int)(Math.random() * 4);
           answers[randIndex] = answerChoices[i];
       }
       correctAnswer = answer;
       questions = question;
       explanation = exp;
    }
    /**
     * method getAnswer gets the answer choice string numbered 1, 2, 3, 4
     * @param   number
     * @return  the answer string
     */
    public String getAnswer(int index)
    {
        return answers[index - 1];
    }
    /**
     * method getQuestion gets the question string
     * @return  the question string
     */
    public String getQString()
    {
        return questions;
    }
    /**
     * the correct method returns a boolean variable indicating whether the user entered the correct answer
     * @param: int userAnswer is the answer choice entered by the user
     * @return: true if the user entered the correct answer, false if the user entered the wrong answer
     */
    public boolean correct(String userAnswer)
    {
        return (userAnswer.equals(correctAnswer));
    }
    /**
     * shuffles the answer choices for the question
     * Keerthana
     */
    public void answerShuffle()
    {             
        for (int i=0; i< answers.length; i++) 
        {
            int random = (int)(Math.random() * answers.length);
            String temp = answers[i];
            answers[i] = answers[random];
            answers[random] = temp;
        }
    }
    /**
     * @return explanation for the correct answer
     * Keerthana
     */
    public String getExp()
    {
        return explanation;
    }
}



