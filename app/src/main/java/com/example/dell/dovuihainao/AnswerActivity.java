package com.example.dell.dovuihainao;

import android.app.Instrumentation;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dell.dovuihainao.adapter.CharAdapter;
import com.example.dell.dovuihainao.data.DBManager;
import com.example.dell.dovuihainao.model.Question;

import java.util.ArrayList;
import java.util.Random;
import java.util.UUID;

public class AnswerActivity extends AppCompatActivity {

    private TextView tvQuestion;
    private TextView tvNumQuestion;
    private TextView tvScore;
    private GridView gridViewAnswer;
    private GridView gridViewResult;
    private Button btnTip;
    private Button btnAbort;

    Question question;
    private ArrayList<Question> mListQuestions;
    private ArrayAdapter<String> mAdapter;
    private ArrayAdapter<String> mAdapterResult;
    private ArrayList<String> mItemAnswer;
    private ArrayList<String> mItemResult;
    private ArrayList<String> mItemAnswerRanDom;
    private String str;
    private int score = 0;
    private int numQuestion = 1;
    private SharedPreferences.Editor editor;
    private ArrayList<String> mListRemainWord;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer);

        tvQuestion = (TextView) findViewById(R.id.textViewQuestion);
        tvNumQuestion = (TextView) findViewById(R.id.textViewNumQuestion);
        tvScore = (TextView) findViewById(R.id.textViewScore);
        gridViewAnswer = (GridView) findViewById(R.id.gvAnswer);
        gridViewResult = (GridView) findViewById(R.id.gvResult);
        btnTip = (Button) findViewById(R.id.btnTip);
        btnAbort = (Button) findViewById(R.id.btnAbort);



        Intent intent = getIntent();
        int idCategory  = intent.getIntExtra("idCategory", 0);
//        mListQuestions = categoryManager.getQuestions(idCategory);
        DBManager db  = new DBManager(this);
        mListQuestions = db.getQuestionByCategoryId(idCategory);
        question = getRandomQuestion(mListQuestions.size());

        tvQuestion.setText(question.getQuestion());
        tvNumQuestion.setText("Câu hỏi " + numQuestion);

        mItemAnswer = new ArrayList<>();
        mItemResult = new ArrayList<>();

        loadDataGridView(question);

        SharedPreferences pref = getSharedPreferences("MyPref", MODE_PRIVATE);
        editor = pref.edit();

        if(pref.getInt("score", 0) == 0){
            editor.putInt("score", 500);
            editor.apply();
        }else{
            tvScore.setText(pref.getInt("score", 0)+ "");
        }
        mListRemainWord = new ArrayList<>();
        gridViewAnswer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mItemResult.add(mItemAnswerRanDom.get(i));
                mAdapterResult = new CharAdapter(AnswerActivity.this, R.layout.item_word_result, mItemResult);
                gridViewResult.setAdapter(mAdapterResult);
                mListRemainWord =  mItemAnswerRanDom;
                mListRemainWord.remove(i);
                ArrayAdapter<String> mAdapterRemainWord = new CharAdapter(AnswerActivity.this, R.layout.item_word_answer, mListRemainWord);
                gridViewAnswer.setAdapter(mAdapterRemainWord);
                if(mItemResult.size() == question.getAnswer().length()){
                    String answer = "";
                    for(int k = 0; k < mItemResult.size(); k++){
                        answer = answer.concat(mItemResult.get(k));
                    }
                    checkAnswer(answer);
                }
            }
        });

        gridViewResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                mListRemainWord.add(mItemResult.get(i));
                mItemResult.remove(i);
                mAdapterResult.notifyDataSetChanged();
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    public void tip(View view){
        score = Integer.parseInt(tvScore.getText().toString());
        if(score <= 10){
            new AlertDialog.Builder(AnswerActivity.this)
                    .setTitle("O OH")
                    .setMessage("Your diamond not enough to use our tip.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create()
                    .show();
        }else{
            new AlertDialog.Builder(AnswerActivity.this)
                    .setTitle("Get a tip")
                    .setMessage("You must pay 10 diamond to use our tip")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            char[] answerQuestion = question.getAnswer().toLowerCase().toCharArray();
                            int position = mItemResult.size();
                            if(position == question.getAnswer().length()){
                                String answer = "";
                                for(int k = 0; k < mItemResult.size(); k++){
                                    answer = answer.concat(mItemResult.get(k));
                                }
                                checkAnswer(answer);
                            }else{
                                mItemResult.add(answerQuestion[position]+ "");
                                mAdapterResult = new CharAdapter(AnswerActivity.this, R.layout.item_word_result, mItemResult);
                                gridViewResult.setAdapter(mAdapterResult);

                                score -= 10;
                                editor.putInt("score", score);
                                editor.apply();
                                tvScore.setText(score + "");
                            }

                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    })
                    .create()
                    .show();
        }
    }

    public void loadDataGridView(Question QuestionLoad){
        str = QuestionLoad.getAnswer().toLowerCase();
        mItemAnswerRanDom = randomString( str);
        mAdapter = new CharAdapter(AnswerActivity.this, R.layout.item_word_answer, mItemAnswerRanDom);
        gridViewAnswer.setAdapter(mAdapter);
    }

    public void resetGridViewResult(){
        mItemResult = new ArrayList<>();
        mAdapterResult = new CharAdapter(AnswerActivity.this, R.layout.item_word_answer, mItemResult);
        gridViewResult.setAdapter(mAdapterResult);
    }
    public ArrayList<String> randomString(String str){
        int n;
        ArrayList<String> result = new ArrayList<>();
        Random random = new Random();
        str = str.concat(getStringRandom(4));
        String[] newString = str.split(" ");
        for(int i = 0; i < newString.length; i++){
            for(int j = 0; j < newString[i].length(); j++){
                mItemAnswer.add(String.valueOf(newString[i].charAt(j)));
            }
        }
        int size = mItemAnswer.size();

        for(int i=0; i<size; i++){
            n = random.nextInt(mItemAnswer.size() - 0) + 0;
            result.add(mItemAnswer.get(n));
            mItemAnswer.remove(n);
        }
        return result;
    }

    private String  getStringRandom(int size){
        StringBuilder stringRandom = new StringBuilder(size);
        char[] chars = getResources().getString(R.string.anphabe).toCharArray();
        Random random = new Random();
        char tempChar;
        for(int i=0; i < size; i++){
            stringRandom.append(chars[random.nextInt(chars.length)]);
        }
        return stringRandom.toString();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 999 && resultCode==RESULT_OK){
            score = Integer.parseInt(tvScore.getText().toString());
            score += 20;
            editor.putInt("score", score);
            editor.apply();
            tvScore.setText(score + "");
            if(mListQuestions.size() < 1){
                Toast.makeText(AnswerActivity.this,"You are win!", Toast.LENGTH_LONG).show();
                finish();
            }else{
                question = getRandomQuestion(mListQuestions.size());
                setQuestion(question);
            }
        }else{
            Toast.makeText(this, "Have  few error!", Toast.LENGTH_SHORT).show();
        }
    }

    public void checkAnswer(String answerString){
        if (answerString.equals(question.getAnswer().toLowerCase())){
            mListQuestions.remove(question);
            numQuestion += 1;
            Intent intent = new Intent(AnswerActivity.this, CongratulationActivity.class);
            startActivityForResult(intent, 999);
        }else{
            Toast.makeText(this,"Try Again!", Toast.LENGTH_SHORT).show();
            resetGridViewResult();
            loadDataGridView(question);
        }
    }

    public Question getRandomQuestion(int numRandom){
        Question rQuestion;
        if(mListQuestions.size() > 0){
            Random random = new Random();
            int num = random.nextInt(numRandom);
            Question question = mListQuestions.get(num);
            return question;
        }else{
            Toast.makeText(this, "Don't have any question", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    public void abort(View view){
        score = Integer.parseInt(tvScore.getText().toString());
        mListQuestions.remove(question);
        if(score < 20){
            Toast.makeText(this,"Your diamond less than 20 should you can't user action Abort for this question", Toast.LENGTH_SHORT).show();
        }else{
            new AlertDialog.Builder(this)
                    .setTitle("Abort Question")
                    .setMessage("If you abort this question you must pay 20 diamond.")
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            score -= 20;
                            editor.putInt("score", score);
                            editor.apply();
                            tvScore.setText(score + "");
                            if(mListQuestions.size() < 1){
                                Toast.makeText( AnswerActivity.this,"You are win!", Toast.LENGTH_SHORT).show();
                                finish();
                            }else{
                                question = getRandomQuestion(mListQuestions.size());
                                setQuestion(question);
                            }
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    }).create()
                    .show();
        }
    }

    public void setQuestion(Question sQuestion){
        tvQuestion.setText(sQuestion.getQuestion());
        tvNumQuestion.setText("Câu hỏi " + numQuestion);
        loadDataGridView(sQuestion);
        resetGridViewResult();
    }

    public void prev(View view) {
        finish();
    }
}
