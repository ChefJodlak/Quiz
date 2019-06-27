package com.example.quiz.quiz;

import org.json.*;

public class GetQuestions {

    public String questionID, question, answer1, answer2, answer3, answer4, unparsedJSON;

    public GetQuestions(String unparsedJSON){
        this.unparsedJSON = unparsedJSON;
        parseJSON(unparsedJSON);
    }

    public void setQuestionID(String questionID) { this.questionID = questionID; }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setAnswer1(String answer1) {
        this.answer1 = answer1;
    }

    public void setAnswer2(String answer2) { this.answer2 = answer2; }

    public void setAnswer3(String answer3) {
        this.answer3 = answer3;
    }

    public void setAnswer4(String answer4) {
        this.answer4 = answer4;
    }

    public void parseJSON(String unparsedJSON){
        try {
            JSONObject obj = new JSONObject(unparsedJSON);
            setQuestionID(obj.getString("questionId"));
            setQuestion(obj.getString("question"));
            JSONArray arr = obj.getJSONArray("answers");
            for(int i = 0; i < arr.length(); i++){
                if (i == 0){
                    setAnswer1(arr.getString(i));
                }else if (i == 1){
                    setAnswer2(arr.getString(i));
                }else if (i == 2){
                    setAnswer3(arr.getString(i));
                }else if (i == 3){
                    setAnswer4(arr.getString(i));
                }
            }


        }catch(JSONException e){
            e.printStackTrace();
        }

    }



}
