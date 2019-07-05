package com.example.quiz.login;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * UserData is a storage for the Username and Token.
 */
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

    /**
     * Method for the setting the values of the username and token variables
     *
     * @param unparsedJSON      unparsed json with the token
     * @param userLogin         username
     */
    public UserData(String unparsedJSON, String userLogin){
        obtainToken(unparsedJSON);
        setUserLogin(userLogin);
    }

    /**
     * Method for the parsing json and setter for the token
     *
     * @param unparsedJSON          unparsed json with the token
     */
    private void obtainToken(String unparsedJSON){
        try {
        JSONObject obj = new JSONObject(unparsedJSON);

        setToken(obj.getString("QuizIdentity"));
        }catch(JSONException e){
            e.printStackTrace();
        }
    }

}
