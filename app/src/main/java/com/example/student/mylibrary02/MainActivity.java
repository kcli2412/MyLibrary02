package com.example.student.mylibrary02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.student.mylibrary02.data.Book;
import com.example.student.mylibrary02.data.BookDAO;
import com.example.student.mylibrary02.data.BookDAOFactory;
import com.example.student.mylibrary02.data.DBType;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static BookDAO dao;
    DBType dbType;
    ListView lv;
    ArrayList<String> booksName;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbType = DBType.BookCase;
        dao = BookDAOFactory.getDAOInstance(dbType);
        lv = findViewById(R.id.listView);

        booksName = new ArrayList<>();
        adapter = new ArrayAdapter<String>(
                MainActivity.this, android.R.layout.simple_list_item_1, booksName);
        /*lv.setAdapter(adapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", dao.getList().get(position).id);
                startActivity(intent);
            }
        });*/

        BookAdapter adapter = new BookAdapter(MainActivity.this, dao.getList());
        lv.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("MainActivity", "onResume: " + dao.getList());
        if (dao.getList() == null)
        {
            Log.d("MainActivity", "NULL");
        }
        booksName.clear();
        for (Book book:dao.getList())
        {
            Log.d("MainActivity name", book.name);
            booksName.add(book.name);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.menu_add:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_edit:
                break;
            case R.id.menu_options:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
