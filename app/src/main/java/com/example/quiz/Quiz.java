package com.example.quiz;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import com.example.quiz.data.Endpoint;
import com.example.quiz.quiz.CheckQuestions;
import com.example.quiz.quiz.GetQuestions;

import java.util.Locale;


/**
 * The Quiz class has all of the methods that are needed to generate a Quiz GUI and to check
 * if User has provided a correct answer.
 */
public class Quiz extends AppCompatActivity {

    private static final long COUNTDOWN_IN_MILLIS = 10000;
    private CountDownTimer countDownTimer;
    private ColorStateList textColorDefaultCd;
    private TextView textViewCountDown;
    private long timeLeftInMillis;
    private String resultAnswer;
    private String questionId;
    private int userAnswer;
    private Button answer1, answer2, answer3, answer4;
    String token;

    private void setQuestionId(String questionId) {
        this.questionId = questionId;
    }
    public void setUserAnswer(int userAnswer) {
        this.userAnswer = userAnswer;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        final String json = intent.getStringExtra("json");
        token = intent.getStringExtra("token");
        setContentView(R.layout.activity_quiz);
        setQuizValues(json);
        timeLeftInMillis = COUNTDOWN_IN_MILLIS;
        startCountDown();
        textViewCountDown = findViewById(R.id.countdown);
        textColorDefaultCd = textViewCountDown.getTextColors();
        answer1 = findViewById(R.id.answer1);
        answer2 = findViewById(R.id.answer2);
        answer3 = findViewById(R.id.answer3);
        answer4 = findViewById(R.id.answer4);

        //After pressing the first button CheckAnswer() method is checking if
        // this answer was correct
        answer1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(1);
                setChosenAnswer(answer1);
                countDownTimer.cancel();
                new CheckAnswer().execute();
            }
        });

        //After pressing the second button CheckAnswer() method is checking if
        // this answer was correct
        answer2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(2);
                setChosenAnswer(answer2);
                countDownTimer.cancel();
                new CheckAnswer().execute();
            }
        });

        //After pressing the third button CheckAnswer() method is checking if
        // this answer was correct
        answer3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(3);
                setChosenAnswer(answer3);
                countDownTimer.cancel();
                new CheckAnswer().execute();
            }
        });

        //After pressing the fourth button CheckAnswer() method is checking if
        // this answer was correct
        answer4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserAnswer(4);
                setChosenAnswer(answer4);
                countDownTimer.cancel();
                new CheckAnswer().execute();
            }
        });

    }

    /**
     * Set the values of the Buttons and EditText with the values that were parsed in GetQuestion
     * class.
     *
     * @param result            unparsed json with the all data needed for the Quiz
     */
    private void setQuizValues(String result){
        GetQuestions questions = new GetQuestions(result);

        ((TextView)findViewById(R.id.question)).setText(questions.question);
        ((Button)findViewById(R.id.answer1)).setText(questions.answer1);
        ((Button)findViewById(R.id.answer2)).setText(questions.answer2);
        ((Button)findViewById(R.id.answer3)).setText(questions.answer3);
        ((Button)findViewById(R.id.answer4)).setText(questions.answer4);
        setQuestionId(questions.questionID);
    }

    /**
     * Start CountDown from the time in ms set in COUNTDOWN_IN_MILLIS variable. If timer = 0
     * then execute CheckAnswer class.
     */
    private void startCountDown() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateCountDownText();
            }

            @Override
            public void onFinish() {
                timeLeftInMillis = 0;
                updateCountDownText();
                new CheckAnswer().execute();
            }
        }.start();
    }

    /**
     * Update Count Down Timer each second.
     */
    private void updateCountDownText() {

        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        String timeFormatted = String.format(Locale.getDefault(), "%2d",  seconds);

        textViewCountDown.setText(timeFormatted);

        if (timeLeftInMillis < 4000) {
            textViewCountDown.setTextColor(Color.RED);
        } else {
            textViewCountDown.setTextColor(textColorDefaultCd);
        }
    }


    /**
     * Check if the answer provided by the user is correct.
     *
     * @param resultAnswer          unparsed JSON with the correct answer
     */
    public void checkAnswer(String resultAnswer){
        CheckQuestions checkAnswer = new CheckQuestions(resultAnswer);
        int correctAnswer = checkAnswer.correctAnswer;

        Button buttons[] = new Button[]{answer1, answer2, answer3, answer4};
        setCorrectAnswer(buttons[correctAnswer-1]);

        if(checkAnswer.getIsCorrectAnswer()){
            showSuccessAlert();
        }else{
            showFailedAlert();
        }
    }

    /**
     * Block the user from going back.
     */
    @Override
    public void onBackPressed() {
    }

    /**
     * Change the color of the button chosen by the User to Gray.
     * @param button            button pressed by the user
     */
    private void setChosenAnswer(Button button){
        button.setBackgroundResource(R.drawable.chosen_answer_button);
    }

    /**
     * Change the color of the button with the correct answer to Green.
     * @param button            button with the correct answer
     */
    private void setCorrectAnswer(Button button){
        button.setBackgroundResource(R.drawable.correct_answer_button);
    }

    /**
     * Display Success alert.
     */
    public void showSuccessAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setCancelable(false);
        myAlert.setMessage("Poprawna odpowiedź!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToMainScreen();
                    }
                })
                .create();
        myAlert.show();
    }
    /**
     * Display Fail alert.
     */
    public void showFailedAlert(){
        AlertDialog.Builder myAlert = new AlertDialog.Builder(this);
        myAlert.setCancelable(false);
        myAlert.setMessage("Nieoprawna odpowiedź! :(")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        goToMainScreen();
                    }
                })
                .create();
        myAlert.show();
    }

    /**
     * Redirect user to the Main Screen and close currect Activity.
     */
    private void goToMainScreen()
    {
        Intent intent = new Intent(this, MainScreen.class);
        intent.putExtra("token", token);
        startActivity(intent);
        finish();
    }


    /**
     * The CheckAnswer is an Async class for sending the request with the User Answer to the
     * endpoint, with the provided token identification purposes.
     */
    public class CheckAnswer extends AsyncTask<String, Void, String> {

        /**
         * Send HTTP request to the endpoint, providing questionId, userAnswer and token.
         *
         * @return          response
         */
        @Override
        protected String doInBackground(String... params){

            try {
                //1. Create okHttp Client object
                OkHttpClient client = new OkHttpClient();
                Endpoint endpoint = new Endpoint();

                //2. Define request being sent to the server
                Request request = new Request.Builder()
                        .url(endpoint.getUrl()+"questions/answer/"+questionId+"/"+userAnswer)
                        .header("QuizIdentity", token)
                        .build();

                //3. Transport the request and wait for response to process next
                Response response = client.newCall(request).execute();
                resultAnswer = response.body().string();
                return resultAnswer;

            }
            catch(Exception e) {
                return null;
            }
        }

        /**
         * Check if user provided correct answer.
         * @param s         response
         */
        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            checkAnswer(resultAnswer);
        }

    }

}
