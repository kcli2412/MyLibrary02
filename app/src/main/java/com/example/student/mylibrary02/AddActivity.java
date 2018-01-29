package com.example.student.mylibrary02;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.student.mylibrary02.data.Book;

import java.io.FileNotFoundException;

public class AddActivity extends AppCompatActivity {
    ImageView imv;
    Uri imgUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        imv = (ImageView) findViewById(R.id.add_image);
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

        Book book = new Book(MainActivity.dao.getNewBookId(), name, isbn, author, publication_date,
                press, category, introduction, pricing, score, bookcase);
        MainActivity.dao.add(book);
        finish();
    }

    public void clickAddImage(View v)
    {
        // CH 10-1
//        Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        startActivityForResult(it, 100);
        // CH 10-2
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
//        {
//            ActivityCompat.requestPermissions(this,
//                    new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
//        }
//        savePhoto();
        // CH 10-5
        Intent it =new Intent(Intent.ACTION_GET_CONTENT);
        it.setType("image/*");
        startActivityForResult(it, 101);
    }

    private void savePhoto()
    {
        imgUri = getContentResolver().insert(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new ContentValues());
        Intent it = new Intent("android.media.action.IMAGE_CAPTURE");
        it.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        startActivityForResult(it, 100);
    }

    private void showImg()
    {
        int iw, ih, vw, vh;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        try {
            BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(imgUri), null, options);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(this, "讀取照片資訊時發生錯誤", Toast.LENGTH_LONG).show();
            return;
        }
        iw = options.outWidth;
        ih = options.outHeight;
        vw = imv.getWidth();
        vh = imv.getHeight();

        int scaleFactor = Math.min(iw / vw, ih / vh);// 計算縮小比率

        options.inJustDecodeBounds = false;
        //options.inSampleSize = scaleFactor;// 設定縮小比例

        Bitmap bmp = null;
        try {
            bmp = BitmapFactory.decodeStream(
                    getContentResolver().openInputStream(imgUri), null, options);
        } catch (FileNotFoundException e) {
            //e.printStackTrace();
            Toast.makeText(this, "無法讀取照片", Toast.LENGTH_LONG).show();
        }
        imv.setImageBitmap(bmp);

        new AlertDialog.Builder(this)
                .setTitle("圖檔資訊")
                .setMessage("圖檔URI：" + imgUri.toString() +
                "\n原始尺寸：" + iw + "x" + ih +
                "\n載入尺寸：" + bmp.getWidth() + "x" + bmp.getHeight() +
                "\n顯示尺寸：" + vw + "x" + vh)
                .setNeutralButton("關閉", null)
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            switch (requestCode)
            {
                case 100:
                    // CH 10-1
//                    Bundle bundle =data.getExtras();
//                    Bitmap bmp = (Bitmap) bundle.get("data");
//                    imv.setImageBitmap(bmp);
                    // CH 10-2
//                    Bitmap bmp = null;
//                    try {
//                        bmp = BitmapFactory.decodeStream(
//                                getContentResolver().openInputStream(imgUri), null, null);
//
//                    } catch (FileNotFoundException e) {
//                        //e.printStackTrace();
//                        Toast.makeText(this, "無法讀取照片", Toast.LENGTH_LONG).show();
//                    }
//                    imv.setImageBitmap(bmp);
                    // CH 10-3
                    showImg();
                    break;
                case 101:
                    imgUri = data.getData();
                    showImg();
                    break;
            }

        }
        else
        {
            Toast.makeText(this, requestCode == 100 ? "沒有拍到照片" : "沒有選取照片", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 200)
        {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                savePhoto();
            }
            else
            {
                Toast.makeText(this, "程式需要寫入權限才能運作", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
