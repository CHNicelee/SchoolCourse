package csu.edu.ice.schoolcourse.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ice on 2018/5/23.
 */

public class DBHelper extends SQLiteOpenHelper {

    private static final String name = "student.db";
    private static final int version = 3;
    private String CREATE_STUDENT_TABLE = "create table student(" +
            "id integer primary key autoincrement," +
            "username varchar(20) unique," +
            "password varchar(20)" +
            ")";

    private String CREATE_NEWS_TABLE = "create table news(" +
            "id integer primary key autoincrement," +
            "title varchar(100)," +
            "date varchar(30)," +
            "category varchar(10)," +
            "author_name varchar(40)," +
            "thumbnail_pic_s varchar(300)," +
            "thumbnail_pic_s02 varchar(300)," +
            "thumbnail_pic_s03 varchar(300)," +
            "url varchar(200));";

    public DBHelper(Context context) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STUDENT_TABLE);
        db.execSQL(CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(CREATE_NEWS_TABLE);
    }


}
