package com.example.student.mylibrary02;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.student.mylibrary02.data.Book;

import static com.example.student.mylibrary02.MainActivity.dao;

public class DetailActivity extends AppCompatActivity {
    Book book;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    RatingBar rb;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        tv1 = findViewById(R.id.detail_name);
        tv2 = findViewById(R.id.detail_isbn);
        tv3 = findViewById(R.id.detail_author);
        tv4 = findViewById(R.id.detail_publication);
        tv5 = findViewById(R.id.detail_press);
        tv6 = findViewById(R.id.detail_category);
        tv7 = findViewById(R.id.detail_introduction);
        tv8 = findViewById(R.id.detail_pricing);

        rb = findViewById(R.id.detail_score);

        id = getIntent().getIntExtra("id", 0);
    }

    @Override
    protected void onResume() {
        super.onResume();

        book = dao.getBook(id);

        tv1.setText(book.name);
        tv2.setText(book.isbn);
        tv3.setText(book.author);
        tv4.setText(book.publication_date);
        tv5.setText(book.press);
        tv6.setText(book.category);
        tv7.setText(book.introduction);
        tv8.setText(String.valueOf(book.pricing));
        rb.setNumStars(book.score);
    }

    public void clickBack(View v)
    {
        finish();
    }

    public void clickDelete(View v)
    {

    }

    public void clickEdit(View v)
    {
        Intent intent = new Intent(DetailActivity.this, EditActivity.class);
        startActivity(intent);
    }
}
