package com.guo.course.courseinteraction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.answer;
import com.guo.course.courseinteraction.LoginAndPost.question;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuestionDetailForStudentActivity extends AppCompatActivity {

    private static final int QUESTION_CHOOSE = 0;
    private static final int QUESTION_TEXT = 1;
    private String account;
    private String qesid;
    private String qestitle;
    private String qescontent;
    private String qeschoose;
    private int qestype;
    private int qesclass;
    private String qesanswer;

    //-------------数据库----------
    private LoginDBManager loginDBManager;
    //----------------------------

    private TextView activity_questiondetailforstudent_tv_title;
    private TextView activity_questiondetailforstudent_tv_qescontent;
    private LinearLayout activity_questiondetailforstudent_ll_answer_text;
    private LinearLayout activity_questiondetailforstudent_ll_answer_choose;
    private EditText activity_questiondetailforstudent_et_answer_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail_for_student);
        account = getIntent().getStringExtra("account");
        qesid = getIntent().getStringExtra("qesid");

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
    }

    private void InitView() {
        activity_questiondetailforstudent_tv_title = (TextView) findViewById(R.id.activity_questiondetailforstudent_tv_title);
        activity_questiondetailforstudent_tv_qescontent = (TextView) findViewById(R.id.activity_questiondetailforstudent_tv_qescontent);
        activity_questiondetailforstudent_ll_answer_text = (LinearLayout) findViewById(R.id.activity_questiondetailforstudent_ll_answer_text);
        activity_questiondetailforstudent_et_answer_text = (EditText) findViewById(R.id.activity_questiondetailforstudent_et_answer_text);
        activity_questiondetailforstudent_ll_answer_choose = (LinearLayout) findViewById(R.id.activity_questiondetailforstudent_ll_answer_choose);
        RadioButton activity_questiondetailforstudent_rb_A = (RadioButton) findViewById(R.id.activity_questiondetailforstudent_rb_A);
        RadioButton activity_questiondetailforstudent_rb_B = (RadioButton) findViewById(R.id.activity_questiondetailforstudent_rb_B);
        RadioButton activity_questiondetailforstudent_rb_C = (RadioButton) findViewById(R.id.activity_questiondetailforstudent_rb_C);
        RadioButton activity_questiondetailforstudent_rb_D = (RadioButton) findViewById(R.id.activity_questiondetailforstudent_rb_D);

        activity_questiondetailforstudent_tv_title.setText(qestitle);
        activity_questiondetailforstudent_tv_qescontent.setText(qescontent);
        if (qestype == QUESTION_CHOOSE){
            activity_questiondetailforstudent_ll_answer_text.setVisibility(View.GONE);
            activity_questiondetailforstudent_ll_answer_choose.setVisibility(View.VISIBLE);
            String[] split = qeschoose.split("#");
            activity_questiondetailforstudent_rb_A.setText(split[0]);
            activity_questiondetailforstudent_rb_B.setText(split[1]);
            activity_questiondetailforstudent_rb_C.setText(split[2]);
            activity_questiondetailforstudent_rb_D.setText(split[3]);
        }else if (qestype == QUESTION_TEXT){
            activity_questiondetailforstudent_ll_answer_text.setVisibility(View.VISIBLE);
            activity_questiondetailforstudent_ll_answer_choose.setVisibility(View.GONE);
        }

        RadioGroup activity_questiondetailforstudent_rg_choose = (RadioGroup) findViewById(R.id.activity_questiondetailforstudent_rg_choose);
        activity_questiondetailforstudent_rg_choose.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case 0:
                        qesanswer = "A";
                        break;
                    case 1:
                        qesanswer = "B";
                        break;
                    case 2:
                        qesanswer = "C";
                        break;
                    case 3:
                        qesanswer = "D";
                        break;
                }
            }
        });
        //"确认提交"按钮
        findViewById(R.id.activity_questiondetailforstudent_btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //提交学生填写的答案到答题表
                if (qestype == QUESTION_CHOOSE) {

                } else if (qestype == QUESTION_TEXT) {
                    qesanswer = activity_questiondetailforstudent_et_answer_text.getText().toString().trim();
                }
                answer ans = new answer();
                ans.setAccount(account);
                ans.setQes_id(qesid);

                SimpleDateFormat formatter = new SimpleDateFormat    ("yyyy-MM-dd");
                Date curDate =   new Date(System.currentTimeMillis());//获取当前时间
                String str = formatter.format(curDate);

                ans.setAns_time(str);
                ans.setAns_class(qesclass);
                ans.setAns_answer(qesanswer);
                loginDBManager.addAnswer(ans);
            }
        });

    }
}
