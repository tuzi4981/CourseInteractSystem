package com.guo.course.courseinteraction.LoginAndPost;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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
            db.update(LoginDatabaseHelpter.Login_TABLE_NAME, cv, "account = ?", args);
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
        Cursor c = db.rawQuery("SELECT * FROM " + LoginDatabaseHelpter.Login_TABLE_NAME, null);
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
     * query all questions, return cursor
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
            qes.setQes_id(c.getInt(c.getColumnIndex("qes_id")));
            qes.setQes_content(c.getString(c.getColumnIndex("qes_content")));
            qes.setQes_choose(c.getString(c.getColumnIndex("qes_choose")));
            qes.setQes_type(c.getInt(c.getColumnIndex("qes_type")));
            qes.setQes_teacher(c.getString(c.getColumnIndex("qes_teacher")));
            qes.setQes_class(c.getInt(c.getColumnIndex("qes_class")));
            questions.add(qes);
        }
        c.close();
        return questions;
    }

    /**
     * 根据所属班级查询到属于这个班级的所有题目
     * 用于学生查询属于该学生可以答题的题目
     * @param qes_class
     * @return
     */
    public List<question> queryQuestionsFromClass(int qes_class){
        ArrayList<question> questions = new ArrayList<question>();
        Cursor c = getQuestionQueryCursor();
        while (c.moveToNext())
        {
            question qes = new question();
            if (qes_class == c.getInt(c.getColumnIndex("qes_class"))){
                qes.setQes_id(c.getInt(c.getColumnIndex("qes_id")));
                qes.setQes_title(c.getString(c.getColumnIndex("qes_title")));
                qes.setQes_content(c.getString(c.getColumnIndex("qes_content")));
                qes.setQes_choose(c.getString(c.getColumnIndex("qes_choose")));
                qes.setQes_type(c.getInt(c.getColumnIndex("qes_type")));
                qes.setQes_teacher(c.getString(c.getColumnIndex("qes_teacher")));
                qes.setQes_class(c.getInt(c.getColumnIndex("qes_class")));
                questions.add(qes);
            }
        }
        c.close();
        return questions;
    }

    /**
     * 根据给定的question的id查询特定的一条question
     * @param qes_id
     * @return
     */
    public question queryQuestionFromid(int qes_id){
        question qes = new question();
        Cursor c = getQuestionQueryCursor();
        while (c.moveToNext())
        {
            int temp_qesid = c.getInt(c.getColumnIndex("qes_id"));
            if (temp_qesid == qes_id){
                qes.setQes_id(c.getInt(c.getColumnIndex("qes_id")));
                qes.setQes_title(c.getString(c.getColumnIndex("qes_title")));
                qes.setQes_content(c.getString(c.getColumnIndex("qes_content")));
                qes.setQes_choose(c.getString(c.getColumnIndex("qes_choose")));
                qes.setQes_type(c.getInt(c.getColumnIndex("qes_type")));
                qes.setQes_class(c.getInt(c.getColumnIndex("qes_class")));
                return qes;
            }
        }
        return null;
    }

    /**
     * 插入一条数据到question表
     * 用于教师提交问题
     * @param qes
     */
    public void addQuestion(question qes){
        db.beginTransaction(); // 开始事务
        try {
            db.execSQL("INSERT INTO " + LoginDatabaseHelpter.Question_TABLE_NAME
                    +"(qes_title, qes_content, qes_choose, qes_type, qes_class, qes_teacher)" + " VALUES(?, ?, ?, ?, ?, ?)",
                    new Object[]{qes.getQes_title(), qes.getQes_content(), qes.getQes_choose(), qes.getQes_type(), qes.getQes_class(), qes.getQes_teacher()});

            db.setTransactionSuccessful(); // 设置事务成功完成
        } finally {
            db.endTransaction(); // 结束事务
        }
    }

    /**
     * query all answer, return cursor
     *
     * @return Cursor
     */
    public Cursor getAnswerQueryCursor()
    {
        Cursor c = db.rawQuery("SELECT * FROM " + LoginDatabaseHelpter.Answer_TABLE_NAME,
                null);
        return c;
    }

    /**
     * 返回所有的answer
     * @return
     */
    public List<answer> queryAllAnswer(){
        ArrayList<answer> answers = new ArrayList<answer>();
        Cursor c = getQuestionQueryCursor();
        while (c.moveToNext())
        {
            answer ans = new answer();
            ans.setAccount(c.getString(c.getColumnIndex("account")));
            ans.setQes_id(c.getInt(c.getColumnIndex("qes_id")));
            ans.setAns_answer(c.getString(c.getColumnIndex("ans_time")));
            ans.setAns_class(c.getInt(c.getColumnIndex("ans_class")));
            ans.setAns_time(c.getString(c.getColumnIndex("ans_answer")));
            answers.add(ans);
        }
        c.close();
        return answers;
    }

    /**
     * 根据account和qesid返回一条具体的answer
     * @param account
     * @param qesid
     * @return
     */
    public answer queryAnswerFromaccount_and_qesid(String account, int qesid){
        answer ans = new answer();
        Cursor c = getAnswerQueryCursor();
        while (c.moveToNext())
        {
            String temp_account = c.getString(c.getColumnIndex("account"));
            int temp_qesid = c.getInt(c.getColumnIndex("qes_id"));
            if (temp_account.equals(account) && temp_qesid == qesid){
                ans.setQes_id(c.getInt(c.getColumnIndex("qes_id")));
                ans.setAccount(c.getString(c.getColumnIndex("account")));
                ans.setAns_answer(c.getString(c.getColumnIndex("ans_answer")));
                ans.setAns_class(c.getInt(c.getColumnIndex("ans_class")));
                ans.setAns_time(c.getString(c.getColumnIndex("ans_time")));
                return ans;
            }
        }
        return null;
    }

    /**
     * 根据所属班级查询到属于这个班级学生提交的所有题目答案
     * 用于教师查询属于该班级学生提交的答案
     * @param ans_class
     * @return
     */
    public List<answer> queryAnswersFromClass(int ans_class){
        ArrayList<answer> answers = new ArrayList<answer>();
        Cursor c = getAnswerQueryCursor();
        while (c.moveToNext())
        {
            answer ans = new answer();
            if (ans_class == c.getInt(c.getColumnIndex("ans_class"))){
                ans.setAccount(c.getString(c.getColumnIndex("account")));
                ans.setQes_id(c.getInt(c.getColumnIndex("qes_id")));
                ans.setAns_time(c.getString(c.getColumnIndex("ans_time")));
                ans.setAns_class(c.getInt(c.getColumnIndex("ans_class")));
                ans.setAns_answer(c.getString(c.getColumnIndex("ans_answer")));
                answers.add(ans);
            }
        }
        c.close();
        return answers;
    }

    /**
     * 一次添加一条记录到答题表
     *
     * @param ans
     */
    public void addAnswer(answer ans) {
        // 采用事务处理，确保数据完整性
        db.beginTransaction(); // 开始事务
        try {
            db.execSQL("INSERT INTO " + LoginDatabaseHelpter.Answer_TABLE_NAME
                    + " VALUES(?, ?, ?, ?, ?)", new Object[]{ans.getAccount(), ans.getQes_id(), ans.getAns_time(), ans.getAns_class(), ans.getAns_answer()});

            db.setTransactionSuccessful(); // 设置事务成功完成
        } finally {
            db.endTransaction(); // 结束事务
        }
    }
}
