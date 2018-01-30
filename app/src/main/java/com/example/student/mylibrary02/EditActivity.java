package com.example.student.mylibrary02;

import android.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import com.example.student.mylibrary02.data.Book;

public class EditActivity extends AppCompatActivity {
    Book book;
    int id;
    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed7, ed8;
    RatingBar rb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        id = getIntent().getIntExtra("id", 0);
        book = MainActivity.dao.getBook(id);

        ed1 = findViewById(R.id.edit_name);
        ed2 = findViewById(R.id.edit_isbn);
        ed3 = findViewById(R.id.edit_author);
        ed4 = findViewById(R.id.edit_publication);
        ed5 = findViewById(R.id.edit_press);
        ed6 = findViewById(R.id.edit_category);
        ed7 = findViewById(R.id.edit_introduction);
        ed8 = findViewById(R.id.edit_pricing);
        rb = findViewById(R.id.edit_score);

        ed1.setText(book.name);
        ed2.setText(book.isbn);
        ed3.setText(book.author);
        ed4.setText(book.publication_date);
        ed5.setText(book.press);
        ed6.setText(book.category);
        ed7.setText(book.introduction);
        ed8.setText(String.valueOf(book.pricing));
        rb.setRating(book.score);
    }

    public void clickBack(View v)
    {
        finish();
    }

    public void clickUpdate(View v)
    {
        String name = ed1.getText().toString();
        if (name.isEmpty() || name.length() == 0)
        {
            new AlertDialog.Builder(this)
                    .setTitle("書籍資訊")
                    .setMessage("請輸入書名 " + name)
                    .setNeutralButton("關閉", null)
                    .show();
            return;
        }
        int pricing = ed8.getText().toString().length() == 0 ? 0 : Integer.valueOf(ed8.getText().toString());
        Book b = new Book(book.id, name, ed2.getText().toString(), ed3.getText().toString(),
                ed4.getText().toString(), ed5.getText().toString(), ed6.getText().toString(),
                ed7.getText().toString(), pricing, rb.getRating(), 1);
        MainActivity.dao.update(b);
        finish();
    }
}
