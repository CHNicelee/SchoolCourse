package csu.edu.ice.schoolcourse.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ice on 2018/5/23.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String name = "student.db";
    private static final int version = 1;
    private String CREATE_TABLE = "create table student(" +
            "id integer primary key autoincrement," +
            "username varchar(20) unique," +
            "password varchar(20)" +
            ")";
    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


}
