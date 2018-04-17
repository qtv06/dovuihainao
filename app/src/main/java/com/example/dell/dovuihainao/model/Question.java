package com.example.dell.dovuihainao.model;

import java.io.Serializable;

/**
 * Created by Delll on 1/31/2018.
 */

public class Question implements Serializable{
    private int id;
    private String question;
    private String answer;
    private byte[] image;
    private int idCategory;

    public Question(int id, String question, String answer, byte[] image, int idCategory) {
        this.id = id;
        this.question = question;
        this.answer = answer;
        this.image = image;
        this.idCategory = idCategory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", image='" + image + '\'' +
                ", idCategory='" + idCategory + '\'' +
                '}';
    }
}
