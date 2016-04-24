package com.guo.course.courseinteraction;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

public class SettingActivity extends AppCompatActivity {

    private TextView activity_setting_tv_updateapp;
    private CheckBox activity_setting_cb_update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_setting);

        InitView();
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("设置");
        actionBar.setDisplayHomeAsUpEnabled(true);

        activity_setting_tv_updateapp = (TextView) findViewById(R.id.activity_setting_tv_updateapp);
        activity_setting_cb_update = (CheckBox) findViewById(R.id.activity_setting_cb_update);
        activity_setting_cb_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (activity_setting_cb_update.isChecked() == true){
                    activity_setting_tv_updateapp.setText("自动检测打开");
                }else{
                    activity_setting_tv_updateapp.setText("自动检测关闭");
                }
            }
        });

        findViewById(R.id.activity_setting_btn_savesetting).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:将设置的自动更新保存到SharePerference
                Toast.makeText(SettingActivity.this, "设置保存成功", Toast.LENGTH_SHORT).show();
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
