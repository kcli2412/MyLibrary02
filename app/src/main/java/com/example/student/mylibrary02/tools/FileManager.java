package com.example.student.mylibrary02.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Student on 2018/1/31.
 */

public class FileManager {

    public static void saveBitmap(Context context, Bitmap bitmap, String fileName) throws IOException
    {
        File file = new File(context.getExternalFilesDir("Images"), fileName);
        OutputStream outputStream  = new FileOutputStream(file);

        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, outputStream);

        outputStream.flush();
        outputStream.close();
    }

    public static Bitmap scaleBitmap(Context context, Bitmap bitmap, String imageName, ImageView imv)
    {
        Bitmap newBitmap = null;

        int iw, ih, vw, vh;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;

        File file = new File(context.getExternalFilesDir("Images"), imageName);
        try {
            InputStream is = new FileInputStream(file);
            BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        iw = options.outWidth;
        ih = options.outHeight;
        vw = imv.getWidth();
        vh = imv.getHeight();

        int scaleFactor = Math.min(iw/vw, ih/vh);

        options.inJustDecodeBounds = false;
        options.inSampleSize = scaleFactor;

        try {
            InputStream is = new FileInputStream(file);
            newBitmap = BitmapFactory.decodeStream(is, null, options);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return newBitmap;
    }

    public static Bitmap loadBitmap(Context context, String imageName)
    {
        File file = new File(context.getExternalFilesDir("Images"), imageName);
        try {
            InputStream is = new FileInputStream(file);
            return getFitImage(is);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
    public static Bitmap getFitImage(InputStream is)
    {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = false;
        byte[] bytes = new byte[0];
        try {
            bytes = readStream(is);
            //BitmapFactory.decodeStream(inputStream, null, options);
            Log.d("BMP", "byte length:" + bytes.length);
            Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length, options);
            System.gc();
            // Log.d("BMP", "Size:" + bmp.getByteCount());
            return bmp;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static byte[] readStream(InputStream inStream) throws Exception
    {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        outStream.close();
        inStream.close();
        return outStream.toByteArray();
    }

    public static boolean saveFile(Context context)
    {
        String filename = "myfile.txt";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    public static boolean loadFile()
    {
        return false;
    }

}
