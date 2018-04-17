package com.example.dell.dovuihainao;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.dovuihainao.controllers.CategoryManager;
import com.example.dell.dovuihainao.data.DBManager;
import com.example.dell.dovuihainao.model.Question;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    private ImageButton imgButtonSound;
    private MediaPlayer player;
    private int stateSound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgButtonSound = findViewById(R.id.imgButtonSound);
        stateSound = Integer.parseInt(imgButtonSound.getTag().toString());
//        loadData();


        setSound();
    }

    public void loadData(){
        DBManager db = new DBManager(MainActivity.this);
        Question q1 = new Question(1,"Question1", "Answer1", null, 1);
        Question q2 = new Question(2,"Question2", "Answer2", null, 1);
        Question q3 = new Question(3,"Question3", "Answer3", null, 1);
        Question q4 = new Question(4,"Question4", "Answer4", null, 1);
        Question q5 = new Question(5,"Question5", "Answer5", null, 1);
        db.addQuestion(q1);
        db.addQuestion(q2);
        db.addQuestion(q3);
        db.addQuestion(q4);
        db.addQuestion(q5);
    }

    public void play(View view) {
        Intent intent = new Intent(MainActivity.this, PlayActivity.class);
        startActivity(intent);
    }


    public void setSound(){
        createSound();
        if(stateSound == 1){
            playSound();
        }else{
            player.stop();
        }
    }

    public void createSound(){
        player = MediaPlayer.create(this, R.raw.song_bacground);
    }
    public void changeSound(View view){
        if(stateSound == 0){
            stateSound = 1;
            imgButtonSound.setBackgroundResource(R.drawable.sound_on);
            playSound();

        }else{
            stateSound = 0;
            imgButtonSound.setBackgroundResource(R.drawable.sound_off);
            player.stop();
            createSound();
        }
    }

    public void playSound(){
        player.setLooping(true);
        player.start();
    }
}
