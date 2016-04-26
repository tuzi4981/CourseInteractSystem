package com.guo.course.courseinteraction;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.question;
import com.guo.course.courseinteraction.LoginAndPost.user;

public class AnswerForTeacherActivity extends AppCompatActivity {

    private int qes_type;
    private String account;
    private EditText activity_answerforteacher_et_title;
    private EditText activity_answerforteacher_et_content;
    private EditText activity_answerforteacher_et_a;
    private EditText activity_answerforteacher_et_b;
    private EditText activity_answerforteacher_et_c;
    private EditText activity_answerforteacher_et_d;

    //--------------数据库------------
    private LoginDBManager loginDBManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_answer_for_teacher);
        qes_type = getIntent().getIntExtra("qes_type", -1);
        account = getIntent().getStringExtra("account");
        loginDBManager = new LoginDBManager(this);
        InitView();
    }
    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("发布题目");
        actionBar.setDisplayHomeAsUpEnabled(true);

        activity_answerforteacher_et_title = (EditText) findViewById(R.id.activity_answerforteacher_et_title);
        activity_answerforteacher_et_content = (EditText) findViewById(R.id.activity_answerforteacher_et_content);
        activity_answerforteacher_et_a = (EditText) findViewById(R.id.activity_answerforteacher_et_a);
        activity_answerforteacher_et_b = (EditText) findViewById(R.id.activity_answerforteacher_et_b);
        activity_answerforteacher_et_c = (EditText) findViewById(R.id.activity_answerforteacher_et_c);
        activity_answerforteacher_et_d = (EditText) findViewById(R.id.activity_answerforteacher_et_d);

        LinearLayout activity_answerforteacher_ll_chooser = (LinearLayout) findViewById(R.id.activity_answerforteacher_ll_chooser);
        if (qes_type == 0){
            activity_answerforteacher_ll_chooser.setVisibility(View.VISIBLE);
        }else if (qes_type == 1){
            activity_answerforteacher_ll_chooser.setVisibility(View.GONE);
        }else if(qes_type == -1){
            activity_answerforteacher_ll_chooser.setVisibility(View.GONE);
            Toast.makeText(AnswerForTeacherActivity.this, "题目类型解析出错，请返回主页面", Toast.LENGTH_LONG).show();
        }

        //"提交题目"按钮
        findViewById(R.id.activity_answerforteacher_btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String qestitle = activity_answerforteacher_et_title.getText().toString().trim();
                String qescontent = activity_answerforteacher_et_content.getText().toString();
                String qeschoose = null;
                if (qes_type == 0) {
                    qeschoose = activity_answerforteacher_et_a.getText().toString() + "#" +
                            activity_answerforteacher_et_b.getText().toString() + "#" +
                            activity_answerforteacher_et_c.getText().toString() + "#" +
                            activity_answerforteacher_et_d.getText().toString();
                }
                user person = loginDBManager.queryFromAccount(account);
                int qesclass = person.getUser_class();
                String teacher = person.getUser_name();
                addQuestionToDB(qestitle, qescontent, qeschoose, qes_type, qesclass, teacher);
                Log.i("AnswerForTeacher", "qestitle=" + qestitle + "qescontent=" + qescontent + "qeschoose=" + qeschoose + "qesclass=" + qesclass + "teacher=" + teacher);
                finish();
                Toast.makeText(AnswerForTeacherActivity.this, "题目提交成功", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void addQuestionToDB(String title, String content, String choose, int qestype, int qesclass, String teacher) {
        question qes = new question();
        qes.setQes_title(title);
        qes.setQes_content(content);
        qes.setQes_choose(choose);
        qes.setQes_type(qestype);
        qes.setQes_class(qesclass);
        qes.setQes_teacher(teacher);
        loginDBManager.addQuestion(qes);
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
}
