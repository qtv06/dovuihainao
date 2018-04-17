package com.example.dell.dovuihainao.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.dell.dovuihainao.model.Question;

import java.util.ArrayList;

/**
 * Created by dell on 09/04/2018.
 */

public class DBManager extends SQLiteOpenHelper {
    private static final String TAG = "DBManager";
    private static final String DATABASE_NAME = "funquestion_db";
    private static final String TABLE_NAME = "question";
    private static final String ID = "id";
    private static final String QUESTION = "question";
    private static final String ANSWER = "answer";
    private static final String IMG = "image";
    private static final String CATEGORY_ID = "category_id";
    private static int VERSION = 2;
    private final Context context;

    private String sql = "create table "+ TABLE_NAME + " ( "+
            ID + " integer primary key AUTOINCREMENT, "+
            QUESTION + " text, "+
            ANSWER + " text, "+
            IMG + " blob, "+
            CATEGORY_ID + " integer)";

    public DBManager(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
        this.context = context;
        Log.d(TAG, "DBManager: ");
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sql);
        Log.d(TAG, "Oncreate:");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);
        Log.d(TAG, "onUpgrade: ");
    }

    public void addQuestion(Question question) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(QUESTION, question.getQuestion());
        contentValues.put(ANSWER, question.getAnswer());
        contentValues.put(IMG, question.getImage());
        contentValues.put(CATEGORY_ID, question.getIdCategory());
        db.insert(TABLE_NAME, null, contentValues);
        db.close();
        Log.d(TAG, "addFunQuestion: Successfully!");
    }

    public ArrayList<Question> getALlQuestion() {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{ID, QUESTION, ANSWER, IMG, CATEGORY_ID};
        Cursor c = db.rawQuery("select * from " + TABLE_NAME, null);
        ArrayList<Question> arr = new ArrayList<>();
        while (c.moveToNext()) {
            arr.add(new Question(c.getInt(0), c.getString(1), c.getString(2), c.getBlob(3), c.getInt(4)));
        }
        c.close();
        db.close();
        return arr;
    }

    public ArrayList<Question> getQuestionByCategoryId(int category_id) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = new String[]{ID, QUESTION, ANSWER, IMG, CATEGORY_ID};
        Cursor c = db.rawQuery("select * from " + TABLE_NAME + " where category_id = " + category_id, null);
        ArrayList<Question> arr = new ArrayList<>();
        while (c.moveToNext()) {
            arr.add(new Question(c.getInt(0), c.getString(1), c.getString(2), c.getBlob(3), c.getInt(4)));
        }
        c.close();
        db.close();
        return arr;
    }
}
