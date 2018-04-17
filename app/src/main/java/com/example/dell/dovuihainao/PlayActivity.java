package com.example.dell.dovuihainao;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.dell.dovuihainao.controllers.CategoryManager;
import com.example.dell.dovuihainao.model.Category;
import com.example.dell.dovuihainao.model.Question;

import java.util.ArrayList;
import java.util.Random;

public class PlayActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
    }

    public void action(View view) {
        int idCategory = Integer.parseInt(view.getTag().toString());
        Intent intent = new Intent(PlayActivity.this, AnswerActivity.class);

        intent.putExtra("idCategory", idCategory);
        startActivity(intent);
    }

    public void prev(View view){
        finish();
    }
}