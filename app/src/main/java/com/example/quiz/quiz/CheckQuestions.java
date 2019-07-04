package com.example.quiz.quiz;
import org.json.*;


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

    public CheckQuestions(String unparsedJSON){
        checkAnswer(unparsedJSON);
    }

    public void checkAnswer(String unparsedJSON){

        try {
            JSONObject obj = new JSONObject(unparsedJSON);

            setCorrectAnswer(obj.getInt("correctAnswer"));
            setIsCorrectAnswer(obj.getBoolean("isCorrectAnswer"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }
}
