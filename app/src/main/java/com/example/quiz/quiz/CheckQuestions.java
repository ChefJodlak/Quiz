package com.example.quiz.quiz;
import org.json.*;

/**
 * The GetQuestion class is made for parsing the json with the correct answer.
 */
public class CheckQuestions {

    public int correctAnswer;
    public boolean isCorrectAnswer;

    public void setCorrectAnswer(int correctAnswer) {
        this.correctAnswer = correctAnswer;
    }
    public void setIsCorrectAnswer(boolean isCorrectAnswer) {
        this.isCorrectAnswer = isCorrectAnswer;
    }
    public int getCorrectAnswer() {
        return correctAnswer;
    }
    public boolean getIsCorrectAnswer() {
        return isCorrectAnswer;
    }

    /**
     * Public method for checking the correct answer.
     *
     * @param unparsedJSON          unparsed json with the correct answers
     */
    public CheckQuestions(String unparsedJSON){
        checkAnswer(unparsedJSON);
    }

    /**
     * Method for parsing a json and setting the variables
     *
     * @param unparsedJSON          unparsed json with the correct answers
     */
    private void checkAnswer(String unparsedJSON){

        try {
            JSONObject obj = new JSONObject(unparsedJSON);

            setCorrectAnswer(obj.getInt("correctAnswer"));
            setIsCorrectAnswer(obj.getBoolean("isCorrectAnswer"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
