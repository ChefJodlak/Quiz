package com.example.quiz.login;

import org.json.JSONException;
import org.json.JSONObject;

public class UserData {

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserLogin(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getToken() {
        return token;
    }

    public String getUserLogin() {
        return userLogin;
    }

    private String token, userLogin;

    public UserData(String unparsedJSON, String userLogin){
        obtainToken(unparsedJSON);
        setUserLogin(userLogin);
    }

    private void obtainToken(String unparsedJSON){
        try {
        JSONObject obj = new JSONObject(unparsedJSON);

        setToken(obj.getString("QuizIdentity"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
