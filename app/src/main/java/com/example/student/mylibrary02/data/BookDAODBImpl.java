package com.example.student.mylibrary02.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/24.
 */

public class BookDAODBImpl implements BookDAO {
    Context context;
    ArrayList<Book> mylist;
    SQLiteDatabase db;

    public BookDAODBImpl(Context context)
    {
        this.context = context;
        mylist = new ArrayList<>();
        BookDBHelper helper = new BookDBHelper(context);
        db = helper.getReadableDatabase();
    }

    @Override
    public boolean add(Book book) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", book.id);
        contentValues.put("name", book.name);
        contentValues.put("isbn", book.isbn);
        contentValues.put("author", book.author);
        contentValues.put("publication_date", book.publication_date);
        contentValues.put("press", book.press);
        contentValues.put("category", book.category);
        contentValues.put("introduction", book.introduction);
        contentValues.put("pricing", book.pricing);
        contentValues.put("score", book.score);
        contentValues.put("bookcase", book.bookcase);
        db.insert("books", null, contentValues);
        return true;
    }

    @Override
    public ArrayList<Book> getList()
    {
        mylist = new ArrayList<>();
        Cursor cursor = db.query("books", new String[] {"_id", "name", "isbn", "author", "publication_date",
                        "press", "category", "introduction", "pricing", "score", "bookcase"},
                null, null, null, null, null);
        if (cursor.moveToFirst())
        {
            Book book = new Book(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7), cursor.getInt(8),
                    cursor.getInt(9), cursor.getInt(10));
            mylist.add(book);
            while (cursor.moveToNext())
            {
                book = new Book(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getString(2),
                        cursor.getString(3), cursor.getString(4), cursor.getString(5),
                        cursor.getString(6), cursor.getString(7), cursor.getInt(8),
                        cursor.getInt(9), cursor.getInt(10));
                mylist.add(book);
            }
        }
        return mylist;
    }

    @Override
    public Book getBook(int id) {
        return getBookById(id);
    }

    public Book getBookById(int id)
    {
        Cursor cursor = db.query("books", new String[] {"_id", "name", "isbn", "author", "publication_date",
                        "press", "category", "introduction", "pricing", "score", "bookcase"},
                "_id=?", new String[] {String.valueOf(id)}, null, null, null);
        if (cursor.moveToFirst())
        {
            Book book = new Book(cursor.getInt(0), cursor.getString(1), cursor.getString(2),
                    cursor.getString(3), cursor.getString(4), cursor.getString(5),
                    cursor.getString(6), cursor.getString(7), cursor.getInt(8),
                    cursor.getInt(9), cursor.getInt(10));
            return book;
        }
        return null;
    }

    @Override
    public boolean update(Book book) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", book.name);
        contentValues.put("isbn", book.isbn);
        contentValues.put("author", book.author);
        contentValues.put("publication_date", book.publication_date);
        contentValues.put("press", book.press);
        contentValues.put("category", book.category);
        contentValues.put("introduction", book.introduction);
        contentValues.put("pricing", book.pricing);
        contentValues.put("score", book.score);
        contentValues.put("bookcase", book.bookcase);
        db.update("books", contentValues, "_id=?", new String[] { String.valueOf(book.id)});
        return true;
    }

    @Override
    public boolean delete(int id) {
        db.delete("books", "_id=?", new String[] {String.valueOf(id)});
        return true;
    }

    @Override
    public int getNewBookId() {
        mylist = getList();
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
