package csu.edu.ice.schoolcourse.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import csu.edu.ice.schoolcourse.bean.Student;

/**
 * Created by ice on 2018/5/23.
 */

public class StudentDao {
    DBHelper dbHelper;
    public StudentDao(Context context){
        dbHelper = new DBHelper(context);
    }

    public int insertStudent(Student student) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username",student.getUsername());
        int id = (int) db.insert("student",null,contentValues);
        db.close();
        return id;
    }

    public Student queryStudent(String username){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from student where username=?", new String[]{username});
        Student student=null;
        if(cursor.moveToNext()){
            student = new Student();
            student.setId(cursor.getInt(0));
            student.setUsername(cursor.getString(1));
            student.setPassword(cursor.getString(2));
        }
        cursor.close();
        db.close();
        return student;
    }

}
