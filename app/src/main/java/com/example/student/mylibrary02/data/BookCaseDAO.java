package com.example.student.mylibrary02.data;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/23.
 */

public class BookCaseDAO implements BookDAO {
    ArrayList<Book> mylist;

    public BookCaseDAO()
    {
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
        return getBookById(id);
    }

    @Override
    public Book getBookById(int id)
    {
        for (Book book : mylist)
        {
            if (book.id == id)
                return book;
        }
        return null;
    }

    @Override
    public boolean update(Book book) {
        for (Book b : mylist)
        {
            if (b.id == book.id)
            {
                b.name = book.name;
                b.isbn = book.isbn;
                b.author = book.author;
                b.publication_date = book.publication_date;
                b.press = book.press;
                b.category = book.category;
                b.introduction = book.introduction;
                b.pricing = book.pricing;
                b.score = book.score;
                b.bookcase = book.bookcase;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        for (Book book : mylist)
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
            if (getBookById(i + 1) == null)
            {
                return i + 1;
            }
        }
        return mylist.size() + 1;
    }
}
