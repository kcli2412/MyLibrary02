package com.example.student.mylibrary02.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Student on 2018/1/24.
 */

public class BookDBHelper extends SQLiteOpenHelper {
    final static String DB_NAME = "book.sqlite";
    final static int VERSION = 1;

    public BookDBHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE `books` ( `_id` INTEGER, `name` TEXT, `isbn` TEXT," +
                " `author` TEXT, `publication_date` TEXT, `press` TEXT," +
                " `category` TEXT, `introduction` TEXT, `pricing` INTEGER," +
                " `score` INTEGER, `bookcase` INTEGER, PRIMARY KEY(`_id`) )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
