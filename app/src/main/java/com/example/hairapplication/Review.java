package com.example.hairapplication;

public class Review {
    String review; // 참견해주세요 제목
    String name;  // 작성자
    String date; // 작성일자
    String contents; // 내용
    String image; // 이미지 이름
    String image2; // 이미지 이름

    int num;
    float rate;


    public Review(int num, String review, String name, String date, String contents, String image, String image2, float rate) {
        this.num = num;
        this.review = review;
        this.name = name;
        this.date = date;
        this.contents = contents;
        this.image = image;
        this.rate = rate;
        this.image2 = image2;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }

    public String getReview() {
        return review;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setReview(String review) {
        this.review = review;
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
