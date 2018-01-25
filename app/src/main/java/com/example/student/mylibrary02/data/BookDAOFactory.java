package com.example.student.mylibrary02.data;

import android.content.Context;

/**
 * Created by Student on 2018/1/23.
 */

public class BookDAOFactory {
    public static BookDAO getDAOInstance(Context context, DBType dbType)
    {
        switch(dbType)
        {
            case MEMORY:
                return new BookCaseDAO();
            case FILE:
                return new BookFileDAO(context);
            case DB:
                return new BookDAODBImpl(context);
            case CLOUD:
                return new BookCloudDAOImpl(context);
        }
        return null;
    }
}
