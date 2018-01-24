package com.example.student.mylibrary02.data;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/24.
 */

public class BookDAODBImpl implements BookDAO {

    public BookDAODBImpl()
    {

    }

    @Override
    public boolean add(Book book) {
        return false;
    }

    @Override
    public ArrayList<Book> getList() {
        return null;
    }

    @Override
    public Book getBook(int id) {
        return null;
    }

    @Override
    public boolean update(Book book) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public int getNewBookId() {
        return 0;
    }
}
