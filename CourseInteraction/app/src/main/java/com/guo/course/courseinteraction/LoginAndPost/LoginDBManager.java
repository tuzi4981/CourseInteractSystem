package com.guo.course.courseinteraction.LoginAndPost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.guo.course.courseinteraction.SignCalendar.DatabaseHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者： 郭明亮 on 2016/4/17 001719:19
 */
public class LoginDBManager {
    private LoginDatabaseHelpter db_helper;
    private SQLiteDatabase db;

    public LoginDBManager(Context context){
        db_helper = new LoginDatabaseHelpter(context);
        db = db_helper.getWritableDatabase();
    }

    /**
     * 一次添加多个用户信息到数据库
     *
     * @param users
     */
    public void addPersons(List<user> users)
    {
        // 采用事务处理，确保数据完整性
        db.beginTransaction(); // 开始事务
        try
        {
            for (user person : users)
            {
                db.execSQL("INSERT INTO " + LoginDatabaseHelpter.Login_TABLE_NAME
                        + " VALUES(?, ?, ?, ?, ?, ?)", new Object[] { person.account, person.password, person.user_name, person.user_class, person.email, person.user_type});
            }
            db.setTransactionSuccessful(); // 设置事务成功完成
        }
        finally
        {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * 一次添加一个用户信息到数据库
     *
     * @param person
     */
    public void addPerson(user person) {
        // 采用事务处理，确保数据完整性
        db.beginTransaction(); // 开始事务
        try {
            db.execSQL("INSERT INTO " + LoginDatabaseHelpter.Login_TABLE_NAME
                    + " VALUES(?, ?, ?, ?, ?, ?)", new Object[]{person.account, person.password, person.user_name, person.user_class, person.email, person.user_type});

            db.setTransactionSuccessful(); // 设置事务成功完成
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * 修改某个用户的信息
     *
     * @param person
     */
    public void updatePerson(user person) {
        // 采用事务处理，确保数据完整性
        db.beginTransaction(); // 开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("user_name", person.user_name);
            cv.put("user_class", person.user_class);
            cv.put("email", person.email);
            String[] args = {person.account};
            db.update(LoginDatabaseHelpter.Login_TABLE_NAME, cv, "account = ?", args);
            db.setTransactionSuccessful(); // 设置事务成功完成
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * 修改某个用户的密码
     *
     * @param person
     */
    public void updatePersonPwd(user person) {
        // 采用事务处理，确保数据完整性
        db.beginTransaction(); // 开始事务
        try {
            ContentValues cv = new ContentValues();
            cv.put("password", person.password);
            String[] args = {person.account};
            db.update(LoginDatabaseHelpter.Login_TABLE_NAME,cv,"account = ?",args);
            db.setTransactionSuccessful(); // 设置事务成功完成
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * query all persons, return list
     *
     * @return List<Person>
     */
    public List<user> query()
    {
        ArrayList<user> users = new ArrayList<user>();
        Cursor c = getQueryCursor();
        while (c.moveToNext())
        {
            user person = new user();
            person.account = c.getString(c.getColumnIndex("account"));
            person.password = c.getString(c.getColumnIndex("password"));
            person.user_name = c.getString(c.getColumnIndex("user_name"));
            person.user_class = c.getInt(c.getColumnIndex("user_class"));
            person.email = c.getString(c.getColumnIndex("email"));
            person.user_type = c.getInt(c.getColumnIndex("user_type"));
            users.add(person);
        }
        c.close();
        return users;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor getQueryCursor()
    {
        Cursor c = db.rawQuery("SELECT * FROM " + LoginDatabaseHelpter.Login_TABLE_NAME,
                null);
        return c;
    }

    /**
     * 根据账号返回个人信息
     * @param account
     * @return
     */
    public user queryFromAccount(String account){
        user person = new user();
        Cursor c = getQueryCursor();
        while (c.moveToNext())
        {
            person.account = c.getString(c.getColumnIndex("account"));
            if (person.account.equals(account)){
                person.password = c.getString(c.getColumnIndex("password"));
                person.user_name = c.getString(c.getColumnIndex("user_name"));
                person.user_class = c.getInt(c.getColumnIndex("user_class"));
                person.email = c.getString(c.getColumnIndex("email"));
                person.user_type = c.getInt(c.getColumnIndex("user_type"));
                return person;
            }
        }
        return null;
    }

    /**
     * query all persons, return cursor
     *
     * @return Cursor
     */
    public Cursor getQuestionQueryCursor()
    {
        Cursor c = db.rawQuery("SELECT * FROM " + LoginDatabaseHelpter.Question_TABLE_NAME,
                null);
        return c;
    }

    /**
     * 返回所有的question
     * @return
     */
    public List<question> queryAllQuestion(){
        ArrayList<question> questions = new ArrayList<question>();
        Cursor c = getQuestionQueryCursor();
        while (c.moveToNext())
        {
            question qes = new question();
            qes.setQes_id(c.getString(c.getColumnIndex("qes_id")));
            qes.setQes_content(c.getString(c.getColumnIndex("qes_content")));
            qes.setQes_choose(c.getString(c.getColumnIndex("qes_choose")));
            qes.setQes_type(c.getInt(c.getColumnIndex("qes_type")));
            questions.add(qes);
        }
        c.close();
        return questions;
    }

}
