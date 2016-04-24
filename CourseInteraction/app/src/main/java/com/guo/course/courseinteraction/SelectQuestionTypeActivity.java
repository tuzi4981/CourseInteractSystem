package com.guo.course.courseinteraction;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

public class SelectQuestionTypeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_select_question_type);

        InitView();
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("选择题型");
        actionBar.setDisplayHomeAsUpEnabled(true);

        //"选择题"按钮
        findViewById(R.id.activity_select_question_type_btn_choose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectQuestionTypeActivity.this, AnswerForTeacherActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //"简答题"按钮
        findViewById(R.id.activity_select_question_type_btn_shortanswer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectQuestionTypeActivity.this, AnswerForTeacherActivity.class);
                startActivity(intent);
                finish();
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
