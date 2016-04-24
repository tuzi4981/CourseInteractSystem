package com.guo.course.courseinteraction;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.user;
import com.guo.course.courseinteraction.utils.FileService;

import java.util.ArrayList;
import java.util.List;

public class PostActivity extends AppCompatActivity {

    private static final String[] usertype_String = {"学生", "教师"};
    private static final int STUDENT = 0;
    private static final int TEACHER = 1;
    private static final String TAG = "PostActivity";
    private EditText post_et_userid;
    private EditText post_et_pwd;
    private EditText post_et_pwd_confirm;
    private EditText post_et_name;
    private EditText post_et_class;
    private EditText post_et_mail;
    private Spinner post_sp_usertype;
    private Button post_btn_post;
    private int usertype = 0;

    //-------------------文件操作--------------------
    private String filename_userinfo = "userInfo.txt";
    //----------------------------------------------

    //-------------------数据库---------------------
    LoginDBManager loginDBManager;
    //---------------------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_post);

        InitView();

        loginDBManager = new LoginDBManager(this);
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("注册");
        actionBar.setDisplayHomeAsUpEnabled(true);

        post_et_userid = (EditText) findViewById(R.id.post_et_userid);
        post_et_pwd = (EditText) findViewById(R.id.post_et_pwd);
        post_et_pwd_confirm = (EditText) findViewById(R.id.post_et_pwd_confirm);
        post_et_name = (EditText) findViewById(R.id.post_et_name);
        post_et_class = (EditText)findViewById(R.id.post_et_class);
        post_et_mail = (EditText) findViewById(R.id.post_et_mail);

        post_sp_usertype = (Spinner) findViewById(R.id.post_sp_usertype);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, usertype_String);
        post_sp_usertype.setAdapter(adapter);
        post_sp_usertype.setOnItemSelectedListener(new SpinnerSelectedListener());
        post_btn_post = (Button) findViewById(R.id.post_btn_post);

        post_btn_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean ok = checkData();
                if (ok) {
//                    commitData();
                    commitDataToDB();
                    enterLogin();
                }
            }
        });
    }

    //处理actionbar上面的返回按钮
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void enterLogin() {
        Intent intent = new Intent(PostActivity.this, LoginActivity.class);
        startActivity(intent);
        Toast.makeText(PostActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        finish();
    }

    /**
     * 检查注册填入的数据
     */
    private boolean checkData() {

        String userid = post_et_userid.getText().toString().trim();
        String pwd = post_et_pwd.getText().toString();
        String pwd_confirm = post_et_pwd_confirm.getText().toString();
        String user_name = post_et_name.getText().toString();
        String user_class = post_et_class.getText().toString();
        String email = post_et_mail.getText().toString();

        if (userid.isEmpty())
        {
            post_et_userid.setError("账号不能为空");
            return false;
        }
        if (pwd.isEmpty())
        {
            post_et_pwd.setError("密码不能为空");
            return false;
        }
        if (user_name.isEmpty())
        {
            post_et_name.setError("姓名不能为空");
            return false;
        }
        if (email.isEmpty()){
            post_et_mail.setError("邮箱不能为空");
            return false;
        }
//        if (email.contains("@") == false)
//        {
//            post_et_mail.setError("邮箱格式不正确");
//            return false;
//        }
        if (!pwd.equals(pwd_confirm))
        {
            post_et_pwd_confirm.setError("两次密码不一致");
            return false;
        }
        if (user_class.isEmpty())
        {
            post_et_class.setError("班级不能为空");
            return false;
        }
        //从数据库中查找该ID是否被注册过
        if (queryFromDB(userid) == true)
        {
            post_et_userid.setError("该账号已经被注册过");
            return false;
        }
        return true;
    }

//    /**
//     * 提交数据
//     * 将数据存到本地sd卡中
//     */
//    private void commitData() {
//        String userid = post_et_userid.getText().toString();
//        String pwd = post_et_pwd.getText().toString();
//        String pwd_confirm = post_et_pwd_confirm.getText().toString();
//        String name = post_et_name.getText().toString();
//        String user_class = post_et_class.getText().toString();
//        String mail = post_et_mail.getText().toString();
//
//        String strToSD = userid + "##" + pwd + "##" + name + "##" + user_class + "##" + mail + "##" + Integer.toString(usertype);
//        Log.i(TAG, "strToSD ======= " + strToSD);
//
//        FileService fileService = new FileService(getApplicationContext());
//
//        try {
//            //保存到手机的内存上
//            fileService.saveFilePrivate(filename_userinfo,strToSD);
//        } catch (Throwable e) {
//            e.printStackTrace();
//        }
//    }

    /**
     * 保存数据到数据库
     */
    private void commitDataToDB(){
        String userid = post_et_userid.getText().toString();
        String pwd = post_et_pwd.getText().toString();
        String name = post_et_name.getText().toString();
        int user_class = Integer.parseInt(post_et_class.getText().toString());
        String mail = post_et_mail.getText().toString();

        user person = new user();
        //---用户名，密码，姓名，班级，邮箱，用户类型-----
        person.account = userid;
        person.password = pwd;
        person.user_name = name;
        person.user_class = user_class;
        person.email = mail;
        person.user_type = usertype;
        //---------------------------------
        loginDBManager.addPerson(person);
    }

    /**
     * 根据账号查找数据库中是否已经有该用户
     * @param account
     * @return
     */
    private Boolean queryFromDB(String account){
        user person = loginDBManager.queryFromAccount(account);
        if (person != null)
        {
            return true;
        }
        return false;
    }

    class SpinnerSelectedListener implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            switch (position)
            {
                case STUDENT:
                    usertype = STUDENT;
                    break;
                case TEACHER:
                    usertype = TEACHER;
                    break;
            }

        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }
}
