package com.example.webviewtest;

public class Food {

    private String name;
    private String nowDate;
    private String date;
    private String count;

    public Food(String name, String nowDate, String date, String count) {
        this.name = name;
        this.nowDate = nowDate;
        this.date = date;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNowDate() {
        return nowDate;
    }

    public void setNowDate(String nowDate) {
        this.nowDate = nowDate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
