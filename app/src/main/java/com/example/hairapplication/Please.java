package com.example.hairapplication;

import android.widget.Button;

public class Please {
    String please; // 참견해주세요 제목
    String name;  // 작성자
    String date; // 작성일자
    String contents; // 내용
    int num; // 고유 번호
    int access; // 비밀글 접근성
    String image; // 이미지 이름
    String image2; // 이미지 이름

    public Please(int num, String please, String name, String date, String contents, String image, String image2) {
        this.num = num;
        this.please = please;
        this.name = name;
        this.date = date;
        this.contents = contents;
        this.image = image;
        this.image2 = image2;
    }


    public Please(int num, String please, String name, String date, String contents, String image, String image2, int access) {
        this.num = num;
        this.please = please;
        this.name = name;
        this.date = date;
        this.contents = contents;
        this.image = image;
        this.image2 = image2;
        this.access = access;

    }

    public int getAccess() {
        return access;
    }

    public void setAccess(int access) {
        this.access = access;
    }

    public String getPlease() {
        return please;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setPlease(String please) {
        this.please = please;
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

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getImage2() {
        return image2;
    }

    public void setImage2(String image2) {
        this.image2 = image2;
    }
}
