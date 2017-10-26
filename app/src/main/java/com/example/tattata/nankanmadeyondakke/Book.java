package com.example.tattata.nankanmadeyondakke;

import android.widget.LinearLayout;

/**
 * Created by tattata on 2017/08/17.
 * 本
 */

public class Book {
    private String title;
    private int kansu;
    private LinearLayout layout;
    public Book(String title, int kansu, LinearLayout layout) {
        this.title = title;
        this.kansu = kansu;
        this.layout = layout;
    }
    @Override
    public String toString() {
        return title + String.valueOf(0x1f) + String.valueOf(kansu);
    }
    public LinearLayout getLayout() {
        return  this.layout;
    }
}
