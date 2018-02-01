package com.example.student.mylibrary02;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.student.mylibrary02.data.Book;
import com.example.student.mylibrary02.tools.FileManager;

import java.io.File;

public class DetailActivity extends AppCompatActivity {
    Book book;
    int id;
    TextView tv1, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    ImageView iv;
    RatingBar rb;

    boolean fastBack = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        id = getIntent().getIntExtra("id", 0);

        tv1 = findViewById(R.id.detail_name);
        tv2 = findViewById(R.id.detail_isbn);
        tv3 = findViewById(R.id.detail_author);
        tv4 = findViewById(R.id.detail_publication);
        tv5 = findViewById(R.id.detail_press);
        tv6 = findViewById(R.id.detail_category);
        tv7 = findViewById(R.id.detail_introduction);
        tv8 = findViewById(R.id.detail_pricing);
        rb = findViewById(R.id.detail_score);
        iv = findViewById(R.id.detail_image);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (fastBack)
        {
            finish();
        }

        rb.setIsIndicator(true);

        book = MainActivity.dao.getBook(id);
        if (book != null)
        {
            iv.setImageBitmap(FileManager.loadBitmap(this, book.imagename));
            tv1.setText(book.name);
            tv2.setText(book.isbn);
            tv3.setText(book.author);
            tv4.setText(book.publication_date);
            tv5.setText(book.press);
            tv6.setText(book.category);
            tv7.setText(book.introduction);
            tv8.setText(String.valueOf(book.pricing));
            rb.setRating(book.score);

            setTitle(book.name);
        }
    }

    public void clickBack(View v)
    {
        finish();
    }

    public void clickDelete(View v)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this);
        builder.setTitle("刪除確認");
        builder.setMessage("確認要刪除本筆資料嗎");
        builder.setPositiveButton("確認", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.dao.delete(book.id);
                finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    public void clickEdit(View v)
    {
        Intent intent = new Intent(DetailActivity.this, EditActivity.class);
        intent.putExtra("id", book.id);
        fastBack = true;
        startActivity(intent);
    }
}
