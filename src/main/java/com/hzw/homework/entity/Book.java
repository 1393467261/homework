package com.hzw.homework.entity;

/**
 * Copyright@www.localhost.com.
 * Author:H.zw
 * Date:2018/5/9 21:09
 * Description:
 */
public class Book {

    private String bookId;
    private String bookName;
    private float price;

    public Book(String bookId, String bookName, float price) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.price = price;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }
}
