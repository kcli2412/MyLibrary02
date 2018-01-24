package com.example.student.mylibrary02.data;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/24.
 */

public class BookFileDAO implements BookDAO {
    Context context;
    ArrayList<Book> mylist;

    public BookFileDAO(Context context)
    {
        this.context = context;
        mylist = new ArrayList<>();
    }

    @Override
    public boolean add(Book book) {
        mylist.add(book);
        return true;
    }

    @Override
    public ArrayList<Book> getList() {
        return mylist;
    }

    @Override
    public Book getBook(int id) {
        for (Book book:mylist)
        {
            if (book.id == id)
            {
                return book;
            }
        }

        return null;
    }

    @Override
    public boolean update(Book book) {
        for (Book b:mylist)
        {
            if (b.id == book.id)
            {
                b.name = book.name;

                return true;
            }
        }

        return false;
    }

    @Override
    public boolean delete(int id) {
        for (Book book:mylist)
        {
            if (book.id == id)
            {
                mylist.remove(book);

                return true;
            }
        }

        return false;
    }

    @Override
    public int getNewBookId() {
        for (int i = 0; i < mylist.size(); i++)
        {
            if (getBook(i + 1) == null)
            {
                return i + 1;
            }
        }

        return mylist.size() + 1;
    }
}
