package com.example.student.mylibrary02.tools;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import static android.content.ContentValues.TAG;

/**
 * Created by Student on 2018/1/31.
 */

public class FileManager {

    public static Bitmap loadBitmap(Context context, String imageName)
    {
        File file = new File(context.getExternalFilesDir("PHOTO"), imageName);
        try {
            InputStream is = new FileInputStream(file);
            Bitmap bitmap = getFitImage(is);
            return bitmap;
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

    // 得到存檔的路徑
    public static File getOutputMediaFile(String imageName)
    {
        String state = Environment.getExternalStorageState();
        if (! Environment.MEDIA_MOUNTED.equals(state)) {
            Log.w(TAG, "getOutputMediaFile: Environment storage not writable");
            return null;
        }

        File mediaStorageDir = new File(
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "mylibrary02");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                return null;
            }
        }

        // Create a media file name
        //String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String timeStamp = imageName;
        File mediaFile;

        String mImageName = timeStamp +".jpg";
        mediaFile = new File(mediaStorageDir.getPath() + File.separator + mImageName);
        Log.d(TAG, "mediaStorageDir.getPath(): " + mediaStorageDir.getPath() + ", mImageName: "  + mImageName);
        return mediaFile;
    }

    public static File saveBitmapToFile(Bitmap bitmap, String imageName)
    {
        File pictureFile = getOutputMediaFile(imageName);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return null;
        }

        try {
            FileOutputStream fos = new FileOutputStream(pictureFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
            fos.close();
        } catch (FileNotFoundException e) {
            Log.d(TAG, "File not found: " + e.getMessage());
        } catch (IOException e) {
            Log.d(TAG, "Error accessing file: " + e.getMessage());
        }
        return pictureFile;
    }

    public static Bitmap loadBitmapFromFile(String pathName)
    {
        File pictureFile = getOutputMediaFile(pathName);
        if (pictureFile == null) {
            Log.d(TAG, "Error creating media file, check storage permissions: ");
            return null;
        }

        BitmapFactory.Options options = new BitmapFactory.Options();
        //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
        Log.d(TAG, "loadBitmapFromFile: " + pictureFile.getPath());
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(pictureFile.getPath(), options);
        }
        catch (Exception e)
        {
            Log.d(TAG, "loadBitmapFromFile: e" + e.getMessage());
        }

        return bitmap;
    }
}
