package com.example.student.mylibrary02;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.student.mylibrary02.data.Book;
import com.example.student.mylibrary02.tools.FileManager;
import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddActivity extends AppCompatActivity {
    EditText et1, et2, et3, et4, et5, et6, et7, et8;
    RatingBar rb;
    ImageView imv;
    String isbnIntent;
    int bookId;
    String bookImagename;
    int bookcase = 1;
    Book urlBook;
    String urlBookImagename;
    Boolean isPicasso = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        Intent intent = getIntent();
        isbnIntent = intent.getStringExtra("ISBN");

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

        bookId = MainActivity.dao.getNewBookId();
        bookImagename = String.valueOf(bookcase) + "_" + String.valueOf(bookId) + ".jpg";

        setTitle(R.string.app_add);

        if (isbnIntent == null)
            return;

        if (isNumeric(isbnIntent) == false)
        {
            new AlertDialog.Builder(this)
                    .setTitle("ISBN")
                    .setMessage("輸入的ISBN值為 " + isbnIntent + " 資料格式錯誤!!")
                    .setNeutralButton("關閉", null)
                    .show();
            et2.setText(isbnIntent);
            return;
        }

        urlBook = new Book(0, "", "", "", "",
                "", "", "", "", 0,
                0, 0);

        new Thread()
        {
            @Override
            public void run() {
                super.run();
                if (isbnIntent == null)
                    isbnIntent = "";
                final String url = "http://192.83.186.170/search~S1*cht?/i"+isbnIntent+"/i"+isbnIntent+"/1%2C2%2C19%2CE/frameset&FF=i"+isbnIntent+"&1%2C%2C12/indexsort=-";
                Document doc = null;
                final ArrayList<String> bookinfo = new ArrayList<>();
                try {
                    doc = Jsoup.connect(url).get();
                    Elements headlines = doc.select("td");
                    for (Element headline : headlines) {
                        Elements headline_select = headline.select("img");
                        if (headline_select.attr("alt").equals("book jacket"))
                        {
                            urlBookImagename = headline_select.attr("src");
                        }
                        bookinfo.add(headline.text());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (int i = 0; i < bookinfo.size(); i++)
                        {
                            if ((i + 1) < bookinfo.size())
                                setBooKInfo(bookinfo.get(i), bookinfo.get(i + 1));
                        }
                        if (urlBook.isbn.length() > 0)
                        {
                            et1.setText(urlBook.name);
                            et2.setText(urlBook.isbn);
                            et3.setText(urlBook.author);
                            et5.setText(urlBook.press);
                            et6.setText(urlBook.category);
                            Picasso.with(AddActivity.this).load(urlBookImagename).into(imv);
                            isPicasso = true;
                        }
                    }
                });
            }
        }.start();
    }

    public boolean isNumeric(String str)
    {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
        {
            return false;
        }
        return true;
    }
    private void setBooKInfo(String str1, String str2)
    {
        switch (str1)
        {
            case "著者":
                urlBook.author = str2;
                break;
            case "題名":
                urlBook.name = str2;
                break;
            case "出版項":
                urlBook.press= str2;
                break;
            case "國際標準書號":
                urlBook.isbn = str2;
                break;
            case "標題":
                urlBook.category = str2;
                break;
        }
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

        if (isPicasso)
        {
            Bitmap bmp = ((BitmapDrawable)imv.getDrawable()).getBitmap();
            try {
                FileManager.saveBitmap(this, bmp, bookImagename);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
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
