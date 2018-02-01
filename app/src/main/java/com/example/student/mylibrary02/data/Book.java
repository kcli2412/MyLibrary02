package com.example.student.mylibrary02.data;

/**
 * Created by Student on 2018/1/23.
 */

public class Book {
    public int id;
    public String imagename; // 封面名稱
    public String name; // 書名
    public String isbn; // ISBN
    public String author; // 作者
    public String publication_date; // 出版日期
    public String press; // 出版社
    public String category; // 類別
    public String introduction; // 簡介
    public int pricing; // 定價
    public float score; // 評分
    public int bookcase; // 書櫃

    public Book(int id, String name, String isbn, String author, String publication_date,
                String press, String category, String introduction, int pricing, float score,
                int bookcase)
    {
        this.id = id;
        this.name = name;
        this.isbn = isbn;
        this.author = author;
        this.publication_date = publication_date;
        this.press = press;
        this.category = category;
        this.introduction = introduction;
        this.pricing = pricing;
        this.score = score;
        this.bookcase = bookcase;
    }
}
