package csu.edu.ice.schoolcourse.utils;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by ice on 2018/3/18.
 */
public class MyGson {


    private static final String TAG = "MyGson";

    public <T> T fromString(Object json, Class<T> clazz) throws InvocationTargetException, IllegalAccessException, JSONException {

        return fromJsonObject(json, clazz);

    }


    public <T> List<T> fromJsonArray(Object array, Class<T> clazz) {
        log("fromJsonArray:" + array.toString());
        try {
            JSONArray jsonArray = new JSONArray(array.toString());
            List<T> list = new ArrayList<>(jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                if (jsonArray.get(i).getClass() == JSONObject.class) {
                    list.add(fromJsonObject(jsonArray.get(i), clazz));
                } else {
                    list.add((T) jsonArray.get(i));
                }
            }
            return list;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();

    }

    public <T> T fromJsonObject(Object json, Class<T> clazz) {
        log("fromJsonObject:" + json.toString());
        try {

            JSONObject jsonObj = new JSONObject(json.toString());
            T t = clazz.newInstance();

            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                String fieldName = field.getName();
                if (field.isAnnotationPresent(SerializeName.class)) {
                    SerializeName serializeName = field.getAnnotation(SerializeName.class);
                    String value = serializeName.value();//序列化的字段
                    fieldName = value;
                }
                //如果json里面有对应的key  那么进行赋值操作
                if (jsonObj.has(fieldName)) {

                    if (jsonObj.get(fieldName).getClass() == JSONArray.class) {
                        //是一个数组
                        setFiledValue(t, field, clazz, fromJsonArray(jsonObj.get(fieldName), getClassFromArrayType(field)));
                    } else if (jsonObj.get(fieldName).getClass() == JSONObject.class) {
                        //是一个对象
                        setFiledValue(t, field, clazz, fromJsonObject(jsonObj.get(fieldName), field.getType()));
                    } else {
                        setFiledValue(t, field, clazz, jsonObj.get(fieldName));
                    }

                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void setFiledValue(Object obj, Field field, Class clazz, Object ojb) {

        //属性无法直接访问
        if (!field.isAccessible()) {
            field.setAccessible(true);
        }
        //属性可以直接访问   直接赋值
        try {
            field.set(obj, ojb);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    private Class getClassFromArrayType(Field field) {
        String type = field.getType().getName();
        log(field.getName() + "的类型是" + type);

        if (type.contains("Integer")) {
            return Integer.class;
        } else if (type.contains("Double")) {
            return Double.class;
        } else if (type.contains("Float")) {
            return Float.class;
        } else if (type.contains("Character")) {
            return Character.class;
        } else if (type.contains("Short")) {
            return Short.class;
        } else if (type.contains("Boolean")) {
            return Boolean.class;
        } else if (type.contains("Byte")) {
            return Byte.class;
        }
        return Integer.class;
    }


    private void log(String s) {
        Log.d(TAG, "log: "+s);
    }


    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface SerializeName {
        String value();
    }
}



