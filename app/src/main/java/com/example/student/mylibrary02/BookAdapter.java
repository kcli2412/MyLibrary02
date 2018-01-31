package com.example.student.mylibrary02;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.student.mylibrary02.data.Book;
import com.example.student.mylibrary02.tools.FileManager;

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

    public void refresh(ArrayList<Book> mylist)
    {
        this.mylist = mylist;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mylist.size();
    }

    @Override
    public Object getItem(int position) {
        return mylist.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;

        if (view == null)
        {
            LayoutInflater inflater = LayoutInflater.from(context);
            view = inflater.inflate(R.layout.bookitem, null);
            viewHolder = new ViewHolder();
            viewHolder.iv = (ImageView) view.findViewById(R.id.item_image);
            viewHolder.tv1 = (TextView) view.findViewById(R.id.item_name);
            viewHolder.tv2 = (TextView) view.findViewById(R.id.item_author);
            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.iv.setImageBitmap(FileManager.loadBitmap(context, String.valueOf(mylist.get(i).id) + ".jpg"));
        viewHolder.tv1.setText(mylist.get(i).name);
        viewHolder.tv2.setText(mylist.get(i).author);

        return view;
    }

    static class ViewHolder
    {
        ImageView iv;
        TextView tv1;
        TextView tv2;
    }
}
