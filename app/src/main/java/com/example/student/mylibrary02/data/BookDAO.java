package com.example.student.mylibrary02.data;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/23.
 */

public interface BookDAO {
    public boolean add(Book book);
    public ArrayList<Book> getList();
    public Book getBook(int id);
    public boolean update(Book book);
    public boolean delete(int id);
    public int getNewBookId();
}
