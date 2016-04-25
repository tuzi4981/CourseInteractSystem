package com.guo.course.courseinteraction;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class AnswerForTeacherActivity extends AppCompatActivity {

    private int qes_type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_answer_for_teacher);
        qes_type = getIntent().getIntExtra("qes_type", -1);
        InitView();
    }
    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("发布题目");
        actionBar.setDisplayHomeAsUpEnabled(true);

        LinearLayout activity_answerforteacher_ll_chooser = (LinearLayout) findViewById(R.id.activity_answerforteacher_ll_chooser);
        if (qes_type == 0){
            activity_answerforteacher_ll_chooser.setVisibility(View.VISIBLE);
        }else if (qes_type == 1){
            activity_answerforteacher_ll_chooser.setVisibility(View.GONE);
        }

        //"提交题目"按钮
        findViewById(R.id.activity_answerforteacher_btn_commit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
}
