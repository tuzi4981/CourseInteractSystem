package com.guo.course.courseinteraction;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.user;

public class MainActivity extends AppCompatActivity {
    private AlertDialog dialog_teacher_select;

    //--------------flag----------------------
    private static final int STUDENT = 0;
    private static final int TEACHER = 1;
    //----------------------------------------

    //-------------从其他activity传过来的值------
    private String account;
    //-----------------------------------------

    //--------------数据库-------------------
    LoginDBManager loginDBManager;
    //-------------------------------------

    //-------------个人信息---------------------
    private String user_name;
    private int user_type = 1;
    private int user_class;
    private String email;
    //----------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_main);

        //获取从其他的activity传过来的值
        getExtraInstanceFromOtherActivity();
        //根据账号从数据库中查到个人信息
        queryInfoFromDB();
        InitView();
    }

    private void getExtraInstanceFromOtherActivity() {
        account = getIntent().getStringExtra("account");
    }

    private void queryInfoFromDB() {
        loginDBManager = new LoginDBManager(this);
        user person = loginDBManager.queryFromAccount(account);
        user_name = person.user_name;
        user_type = person.user_type;
        user_class = person.user_class;
        email = person.email;
    }

    private void InitView() {
        //"个人"按钮
        findViewById(R.id.main_iv_self).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
            }
        });

        //"签到"按钮
        findViewById(R.id.main_iv_qiandao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignCalendarActivity.class);
                startActivity(intent);
            }
        });

        //"答题"按钮
        findViewById(R.id.main_iv_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_type == STUDENT) {
                    Intent intent = new Intent(MainActivity.this, AnswerForStudentActivity.class);
                    intent.putExtra("account", account);
                    startActivity(intent);
                } else if (user_type == TEACHER) {
                    show_select_dialog();
                }
            }
        });

        //"设置"按钮
        findViewById(R.id.main_iv_setting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SettingActivity.class);
                startActivity(intent);
            }
        });

        //"关于"按钮
        findViewById(R.id.main_iv_author).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AuthorActivity.class);
                startActivity(intent);
            }
        });

        //"退出"按钮
        findViewById(R.id.main_iv_exitlogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                Toast.makeText(MainActivity.this, "账号注销成功", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void show_select_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        View view = View.inflate(MainActivity.this, R.layout.dialog_teacher_select, null);

        //"发布题目"按钮
        view.findViewById(R.id.dialog_teacher_select_btn_subject).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectQuestionTypeActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
                dialog_teacher_select.dismiss();
            }
        });

        //"查看答案"按钮
        view.findViewById(R.id.dialog_teacher_select_btn_query).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QueryAnswerActivity.class);
                intent.putExtra("account", account);
                startActivity(intent);
                dialog_teacher_select.dismiss();
            }
        });

        dialog_teacher_select = builder.create();
        dialog_teacher_select.setView(view);
        dialog_teacher_select.show();
    }
}
