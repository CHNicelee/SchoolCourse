package csu.edu.ice.schoolcourse.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import csu.edu.ice.schoolcourse.bean.News;

/**
 * Created by ice on 2018/6/6.
 */

public class NewsDao {
    DBHelper dbHelper;

    public NewsDao(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insertNewsList(List<News> newsList){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (News news : newsList) {
            putValues(contentValues,news,News.class);
            db.insert("news",null,contentValues);
        }
        db.close();
    }

    public void deleteNewsByCategoryAndDate(String category,String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("news","category=? and date like ?",new String[]{category,date+"%"});
        db.close();
    }


    public List<News> getNewsList(String category, String date){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

//        Cursor cursor = db.query("news", null, "category=? and date like ?", , null, null, null);
        Cursor cursor = db.rawQuery("select * from news where category=? and date like ?", new String[]{category, date + "%"});
        List<News> newsList = new ArrayList<>();
        while (cursor.moveToNext()){
            News news = new News();
            String title = cursor.getString(1);
            String author_name = cursor.getString(4);
            String thumbnail_s = cursor.getString(5);
            String thumbnail_s1 = cursor.getString(6);
            String thumbnail_s2 = cursor.getString(7);
            String url = cursor.getString(8);
            news.setTitle(title);
            news.setCategory(category);
            news.setDate(cursor.getString(2));
            news.setAuthorName(author_name);
            news.setThumbnail_pic_s(thumbnail_s);
            news.setThumbnail_pic_s02(thumbnail_s1);
            news.setThumbnail_pic_s03(thumbnail_s2);
            news.setUrl(url);
            newsList.add(news);
        }
        return newsList;
    }


    public void putValues(ContentValues contentValues,Object object,Class clazz){
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if(!field.isAccessible()){
                field.setAccessible(true);
            }

            String key = field.getName();
            if(key.equals("id"))continue;
            if(field.isAnnotationPresent(MyGson.SerializeName.class)){
                MyGson.SerializeName serializeName = field.getAnnotation(MyGson.SerializeName.class);
                key = serializeName.value();
            }

            try {
                Object objValue = field.get(object);

                try {
                    Method getter = clazz.getDeclaredMethod("get" + (char) (key.charAt(0) - 'a' + 'A') + key.substring(1));
                    objValue = getter.invoke(object);
                }catch (Exception e){
                }

                if(field.getType()==String.class){
                    String value = (String)objValue;
                    contentValues.put(key,value);
                }else if(field.getType()==Integer.class){
                    Integer value = (Integer)objValue;
                    contentValues.put(key,value);
                }else if(field.getType() == int.class){
                    int value = (int) objValue;
                    contentValues.put(key,value);
                }else if(field.getType() == double.class){
                    double value = (double) objValue;
                    contentValues.put(key,value);
                }

            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }

        }

    }

}
