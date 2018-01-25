package com.example.student.mylibrary02.data;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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

    private void saveFile()
    {
        File file = new File(context.getFilesDir(), "mybooks.txt");
        try {
            FileWriter fileWriter = new FileWriter(file);
            Gson gson = new Gson();
            String data = gson.toJson(mylist);
            fileWriter.write(data);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void load()
    {
        File file = new File(context.getFilesDir(), "mybooks.txt");
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String str = bufferedReader.readLine();
            Gson gson = new Gson();
            mylist = gson.fromJson(str, new TypeToken<ArrayList<Book>>(){}.getType());
            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean add(Book book) {
        mylist.add(book);
        saveFile();
        return true;
    }

    @Override
    public ArrayList<Book> getList() {
        load();
        return mylist;
    }

    @Override
    public Book getBook(int id) {
        load();

        return getBookById(id);
    }

    public Book getBookById(int id)
    {
        for (Book book : mylist)
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
        load();
        for (Book b:mylist)
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
                saveFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        load();
        for (Book book : mylist)
        {
            if (book.id == id)
            {
                mylist.remove(book);
                saveFile();
                return true;
            }
        }
        return false;
    }

    @Override
    public int getNewBookId() {
        load();
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
