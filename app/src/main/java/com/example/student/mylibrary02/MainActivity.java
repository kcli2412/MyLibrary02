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
import android.widget.Toast;

import com.example.student.mylibrary02.data.Book;
import com.example.student.mylibrary02.data.BookDAO;
import com.example.student.mylibrary02.data.BookDAOFactory;
import com.example.student.mylibrary02.data.DBType;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    public static BookDAO dao;
    DBType dbType;
    ListView lv;
    ArrayList<String> bookNames;
    //ArrayAdapter<String> adapter;
    BookAdapter bookAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = findViewById(R.id.listView);
        dbType = DBType.CLOUD;
        dao = BookDAOFactory.getDAOInstance(MainActivity.this, dbType);
        bookNames = new ArrayList<>();
        //adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, bookNames);
        bookAdapter = new BookAdapter(MainActivity.this, dao.getList());
        //lv.setAdapter(adapter);
        lv.setAdapter(bookAdapter);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("id", dao.getList().get(position).id);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshData();
    }

    public void refreshData()
    {
        bookNames.clear();
        for (Book book : dao.getList())
        {
            bookNames.add(book.id + "_id, " + book.name);
        }
//        adapter.notifyDataSetChanged();

        bookAdapter.refresh(dao.getList());
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
                new IntentIntegrator(this).initiateScan();
                break;
            case R.id.menu_edit:
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
                break;
            case R.id.menu_options:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null) {
            if(result.getContents() == null) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("ISBN", result.getContents());
                startActivity(intent);
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
