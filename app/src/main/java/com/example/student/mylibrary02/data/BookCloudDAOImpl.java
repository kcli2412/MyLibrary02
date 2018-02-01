package com.example.student.mylibrary02.data;

import android.content.Context;

import com.example.student.mylibrary02.MainActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/24.
 */

public class BookCloudDAOImpl implements BookDAO {
    Context context;
    ArrayList<Book> mylist;
    FirebaseDatabase database;
    DatabaseReference reference;

    public BookCloudDAOImpl(final Context context)
    {
        this.context = context;
        mylist = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        reference = database.getReference("books");
        // Read from the database
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Gson gson = new Gson();
                mylist = gson.fromJson(value, new TypeToken<ArrayList<Book>>(){}.getType());
                if (mylist == null)
                {
                    mylist = new ArrayList<>();
                }
                ((MainActivity)context).refreshData();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void saveFile()
    {
        // Write a message to the database
        Gson gson = new Gson();
        String data = gson.toJson(mylist);
        reference.setValue(data);
    }

    @Override
    public boolean add(Book book) {
        if (mylist == null)
        {
            mylist = new ArrayList<>();
        }
        mylist.add(book);
        saveFile();
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
            {
                return book;
            }
        }
        return null;
    }

    @Override
    public boolean update(Book book) {
        for (Book b : mylist)
        {
            if (b.id == book.id)
            {
                b.imagename = book.imagename;
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
