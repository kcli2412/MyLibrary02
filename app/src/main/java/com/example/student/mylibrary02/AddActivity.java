package com.example.student.mylibrary02;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.student.mylibrary02.data.Book;
import com.example.student.mylibrary02.tools.FileManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class AddActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6, et7, et8;
    RatingBar rb;
    ImageView imv;
    Uri imgUri;
    String strIntent;
    int bookId;
    String bookImagename;
    int bookcase = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        strIntent = intent.getStringExtra("ISBN");

        imv = (ImageView) findViewById(R.id.add_image);

        et1 = findViewById(R.id.add_name);
        et2 = findViewById(R.id.add_isbn);
        et3 = findViewById(R.id.add_author);
        et4 = findViewById(R.id.add_publication);
        et5 = findViewById(R.id.add_press);
        et6 = findViewById(R.id.add_category);
        et7 = findViewById(R.id.add_introduction);
        et8 = findViewById(R.id.add_pricing);
        rb = findViewById(R.id.add_score);
        et2.setText(strIntent);

        bookId = MainActivity.dao.getNewBookId();
        bookImagename = String.valueOf(bookcase) + "_" + String.valueOf(bookId) + ".jpg";

        setTitle(R.string.app_add);
    }

    public void clickAdd(View v)
    {
        String name = et1.getText().toString();
        if (name.isEmpty() || name.length() == 0)
        {
            new AlertDialog.Builder(this)
                    .setTitle("書籍資訊")
                    .setMessage("請輸入書名 " + name)
                    .setNeutralButton("關閉", null)
                    .show();
            return;
        }
        String isbn = et2.getText().toString();
        String author = et3.getText().toString();
        String publication_date = et4.getText().toString();
        String press = et5.getText().toString();
        String category = et6.getText().toString();
        String introduction = et7.getText().toString();
        int pricing = et8.getText().toString().length() == 0 ? 0 : Integer.valueOf(et8.getText().toString());
        float score =  rb.getRating();

        Book book = new Book(bookId, bookImagename, name, isbn, author, publication_date,
                press, category, introduction, pricing, score, bookcase);
        MainActivity.dao.add(book);
        finish();
    }

    public void clickAddImage(View v)
    {
        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(it, 100);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
        {
            switch (requestCode)
            {
                case 100:
                    Bundle pBundle = data.getExtras();
                    Bitmap bmp = (Bitmap) pBundle.get("data");
                    try {
                        FileManager.saveBitmap(this, bmp, bookImagename);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imv.setImageBitmap(FileManager.loadBitmap(this, bookImagename));
                    break;
            }
        }
        else
        {
            Toast.makeText(this, requestCode == 100 ? "沒有拍到照片" : "沒有選取照片", Toast.LENGTH_LONG).show();
        }
    }
}
