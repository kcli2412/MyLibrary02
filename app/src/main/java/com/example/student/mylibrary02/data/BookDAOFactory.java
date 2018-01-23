package com.example.student.mylibrary02.data;

import static com.example.student.mylibrary02.data.DBType.BookCase;

/**
 * Created by Student on 2018/1/23.
 */

public class BookDAOFactory {
    public static BookDAO getDAOInstance(DBType dbType)
    {
        switch(dbType)
        {
            case BookCase:
                return new BookCaseDAO();
        }
        return null;
    }
}
