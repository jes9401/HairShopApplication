package com.example.hairapplication;

public class ReviewComment {
    String name;  // 작성자
    String date; // 작성일자
    String comment; // 내용
    int Index; // pleaseList의 인덱스


    public ReviewComment(String name, String date, String comment) {
        this.name = name;
        this.date = date;
        this.comment = comment;
        this.Index = Index;
    }

    public int getIndex() {
        return Index;
    }



    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
