package com.guo.course.courseinteraction;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.answer;
import com.guo.course.courseinteraction.LoginAndPost.question;

public class QuestionDetailForTeacherActivity extends AppCompatActivity {

    private String account;
    private int qesid;

    private String student_account;
    private String qestitle;
    private String qescontent;
    private String qeschoose;
    private int qestype;
    private int qesclass;
    private String qesanswer;

    private String choose_a;
    private String choose_b;
    private String choose_c;
    private String choose_d;

    private String student_answer;
    //-------------数据库----------
    private LoginDBManager loginDBManager;
    //----------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_question_detail_for_teacher);
        account = getIntent().getStringExtra("account");
        qesid = getIntent().getIntExtra("qesid", 0);
        Log.i("QuesDetailForStudent", "qesid=" + qesid);

        loginDBManager = new LoginDBManager(this);

        getDataFromDB();

        InitView();

    }

    private void getDataFromDB() {
        //根据qesid得到question的类型
        question qes = loginDBManager.queryQuestionFromid(qesid);
        qestitle = qes.getQes_title();
        qescontent = qes.getQes_content();
        qeschoose = qes.getQes_choose();
        qestype = qes.getQes_type();
        qesclass = qes.getQes_class();

        answer ans = loginDBManager.queryAnswerFromaccount_and_qesid(account, qesid);
        student_answer = ans.getAns_answer();
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("查看提交答案");
        actionBar.setDisplayHomeAsUpEnabled(true);

        TextView activity_questiondetailforteacher_tv_title = (TextView) findViewById(R.id.activity_questiondetailforteacher_tv_title);
        activity_questiondetailforteacher_tv_title.setText(qestitle);
        TextView activity_questiondetailforteacher_tv_qescontent = (TextView) findViewById(R.id.activity_questiondetailforteacher_tv_qescontent);
        activity_questiondetailforteacher_tv_qescontent.setText("题目内容："+qescontent);
        LinearLayout activity_questiondetailforteacher_ll_answer_choose = (LinearLayout) findViewById(R.id.activity_questiondetailforteacher_ll_answer_choose);
        if (qestype == 0){
            activity_questiondetailforteacher_ll_answer_choose.setVisibility(View.VISIBLE);
            RadioButton activity_questiondetailforteacher_rb_A = (RadioButton) findViewById(R.id.activity_questiondetailforteacher_rb_A);
            RadioButton activity_questiondetailforteacher_rb_B = (RadioButton) findViewById(R.id.activity_questiondetailforteacher_rb_B);
            RadioButton activity_questiondetailforteacher_rb_C = (RadioButton) findViewById(R.id.activity_questiondetailforteacher_rb_C);
            RadioButton activity_questiondetailforteacher_rb_D = (RadioButton) findViewById(R.id.activity_questiondetailforteacher_rb_D);
            String[] split = qeschoose.split("#");
            choose_a = split[0];
            choose_b = split[1];
            choose_c = split[2];
            choose_d = split[3];
            activity_questiondetailforteacher_rb_A.setText(choose_a);
            activity_questiondetailforteacher_rb_B.setText(choose_b);
            activity_questiondetailforteacher_rb_C.setText(choose_c);
            activity_questiondetailforteacher_rb_D.setText(choose_d);
        }else if (qestype == 1){
            activity_questiondetailforteacher_ll_answer_choose.setVisibility(View.GONE);
        }
        TextView activity_questiondetailforteacher_tv_answer = (TextView) findViewById(R.id.activity_questiondetailforteacher_tv_answer);
        activity_questiondetailforteacher_tv_answer.setText(student_answer);
    }
}
