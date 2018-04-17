package com.example.dell.dovuihainao.controllers;

import android.content.Context;

import com.example.dell.dovuihainao.MainActivity;
import com.example.dell.dovuihainao.data.DBManager;
import com.example.dell.dovuihainao.model.Category;
import com.example.dell.dovuihainao.model.Question;

import java.util.ArrayList;

/**
 * Created by Delll on 1/31/2018.
 */

public class CategoryManager {
    private static CategoryManager sInstance = null;
    private ArrayList<Category> listCategory;
    private ArrayList<Question> listQuestion;
    private Context context;

    private CategoryManager(){
        listCategory = new ArrayList<>();
        listQuestion = new ArrayList<>();
    }

    public static CategoryManager getInstance(){
        if(sInstance == null){
            sInstance = new CategoryManager();
        }
        return sInstance;
    }

    public void load(){
        listCategory.add(new Category(1 , "Fun Question"));
        listCategory.add(new Category(2 , "Math Question"));
        listCategory.add(new Category(3 , "Image Question"));

//        DBManager db = new DBManager(MainActivity.this);
//        listQuestion.add(new );
//        listQuestion.add(new Question(2,"Question2", "Answer2", null, 1));
//        listQuestion.add(new Question(3,"Question3", "Answer3", null, 2));
//        listQuestion.add(new Question(4,"Question4", "Answer4", null, 2));
//        listQuestion.add(new Question(5,"Question5", "Answer5", null, 3));
//        listQuestion.add(new Question(6,"Question6", "Answer6", null, 3));

        DBManager db = new DBManager(context);
        Question question = new Question(1,"Question1", "Answer1", null, 1);
        Question q2 = new Question(2,"Question2", "Answer2", null, 1);
        Question q3 = new Question(3,"Question3", "Answer3", null, 1);
        Question q4 = new Question(4,"Question4", "Answer4", null, 1);
        Question q5 = new Question(5,"Question5", "Answer5", null, 1);
//        db.addQuestion(question);
//        db.addQuestion(q2);
//        db.addQuestion(q3);
//        db.addQuestion(q4);
//        db.addQuestion(q5);

        listQuestion = db.getALlQuestion();
    }

    public ArrayList<Category> getCategories(){
        return listCategory;
    }

    public ArrayList<Question> getQuestions(int idCategory){
        ArrayList<Question> list = new ArrayList<>();
        for (Question q : listQuestion) {
            if (q.getIdCategory() == idCategory) {
                list.add(q);
            }
        }
        return list;
    }
}
