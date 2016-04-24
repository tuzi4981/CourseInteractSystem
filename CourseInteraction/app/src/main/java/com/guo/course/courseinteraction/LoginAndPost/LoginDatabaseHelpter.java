package com.guo.course.courseinteraction.LoginAndPost;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者： 郭明亮 on 2016/4/17 001718:28
 */
public class LoginDatabaseHelpter extends SQLiteOpenHelper {
    // 数据库版本号
    private static final int DATABASE_VERSION = 1;
    // 数据库名
    private static final String DATABASE_NAME = "login.db";

    // 数据表名
    public static final String Login_TABLE_NAME = "UserTable";


    public LoginDatabaseHelpter(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public LoginDatabaseHelpter(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        // 数据库实际被创建是在getWritableDatabase()或getReadableDatabase()方法调用时
        // CursorFactory设置为null,使用系统默认的工厂类
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sBuffer = new StringBuffer();
        sBuffer.append("CREATE TABLE " + Login_TABLE_NAME + " (account varchar(30) primary key,password varchar(30),user_name varchar(30),user_class int,email varchar(30),user_type int)");
        // 执行创建表的SQL语句
        db.execSQL(sBuffer.toString());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Login_TABLE_NAME);
        onCreate(db);
    }
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        super.onOpen(db);
        // 每次打开数据库之后首先被执行
    }
}
