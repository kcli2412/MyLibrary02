package com.example.student.mylibrary02;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.student.mylibrary02.data.Book;

import java.util.ArrayList;

/**
 * Created by Student on 2018/1/23.
 */

public class BookAdapter extends BaseAdapter {
    Context context;
    ArrayList<Book> mylist = new ArrayList<>();

    public BookAdapter(Context context, ArrayList<Book> mylist)
    {
        this.context = context;
        this.mylist = mylist;
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return null;
    }
}
