package com.example.student.mylibrary02;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.student.mylibrary02.data.Book;

import static com.example.student.mylibrary02.MainActivity.dao;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
    }

    public void clickAdd(View v)
    {
        EditText et1 = findViewById(R.id.add_name);
        EditText et2 = findViewById(R.id.add_isbn);
        EditText et3 = findViewById(R.id.add_author);
        EditText et4 = findViewById(R.id.add_publication);
        EditText et5 = findViewById(R.id.add_press);
        EditText et6 = findViewById(R.id.add_category);
        EditText et7 = findViewById(R.id.add_introduction);
        EditText et8 = findViewById(R.id.add_pricing);
        RatingBar rb = findViewById(R.id.add_score);

        String name = et1.getText().toString();
        String isbn = et2.getText().toString();
        String author = et3.getText().toString();
        String publication_date = et4.getText().toString();// "publication_date";
        String press = et5.getText().toString();//"press";
        String category = et6.getText().toString();//"category";
        String introduction = et7.getText().toString();//"introduction";
        int pricing = Integer.valueOf(et8.getText().toString());
        int score = Integer.valueOf(rb.getNumStars());
        int bookcase = 1;

        Book book = new Book(dao.getNewBookId(), name, isbn, author, publication_date,
                press, category, introduction, pricing, score, bookcase);
        dao.add(book);
        finish();
    }
}
