/**
 * class QuestionList contains an array list of questions 
 * and returns questions in random order
 *
 * @Aruna Sudarshan
 * @5/30/2018
 */

import java.util.ArrayList;
public class QuestionList
{
    private ArrayList<Question> questList; //arrayList containing all possible questions
    private int size;
    
    /**
     * the QuestionList constructor instantiates the questList arrayList
     */
    public QuestionList()
    {
        questList = new ArrayList<Question>();
        size = 0;
    }
    
    /**
     * the loadQuestions method takes in a question and adds it into the questionlist
     * @param Question quests: a question object to be added into the list of questions
     */
    public void loadQuestions(Question quests)
    {
        questList.add(quests);
        size++;
    }
    
    /**
     * the getQuestion method picks a random question from the list of questions and moves it to the end of the list
     * it decrements the size to ensure the question will not get picked again
     * @return Question that has been randomly chosen
     */
    public Question getQuestion()
    {
        if (size == 0)
            size = questList.size();
        int index = (int)(Math.random() * size);
        Question thatQuest = questList.get(index);
        questList.remove(index);
        questList.add(thatQuest);
        size --;
        return thatQuest;
    }
}

