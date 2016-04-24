package com.guo.course.courseinteraction;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.user;

public class PersonalActivity extends AppCompatActivity {

    private AlertDialog dialog_editpersonal;
    private AlertDialog dialog_editpwd;
    private TextView activity_personal_tv_useraccount;
    private TextView activity_personal_tv_username;
    private TextView activity_personal_tv_usertype;
    private TextView activity_personal_tv_userclass;
    private TextView activity_personal_tv_usermail;

    private TextView dialog_editpersonal_tv_useraccount;
    private EditText dialog_editpersonal_et_username;
    private TextView dialog_editpersonal_tv_useremail;
    private TextView dialog_editpersonal_tv_userclass;
    private TextView dialog_editpersonal_tv_usertype;

    private EditText dialog_editpwd_et_prepwd;
    private EditText dialog_editpwd_et_newpwd;
    private EditText dialog_editpwd_et_newpwd_confirm;

    //-------------数据库---------------------
    LoginDBManager loginDBManager;
    //-----------------------------------------

    //-------------从其他activity传过来的值------
    private String account;
    //-----------------------------------------

    //-------------个人信息---------------------
    private String user_name;
    private String password;
    private int user_type;
    private int user_class;
    private String email;

    //----------------------------------------



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_personal);
        //获取从其他的activity传过来的值
        getExtraInstanceFromOtherActivity();
        //通过账号从数据库中查到个人信息
        queryInfoFromDB(account);
        InitView();
    }

    private void getExtraInstanceFromOtherActivity() {
        account = getIntent().getStringExtra("account");
    }

    private void queryInfoFromDB(String account) {
        loginDBManager = new LoginDBManager(this);
        user person = loginDBManager.queryFromAccount(account);
        password = person.password;
        user_name = person.user_name;
        user_type = person.user_type;
        user_class = person.user_class;
        email = person.email;
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("个人信息");
        actionBar.setDisplayHomeAsUpEnabled(true);

        activity_personal_tv_useraccount = (TextView) findViewById(R.id.activity_personal_tv_useraccount);
        activity_personal_tv_username = (TextView) findViewById(R.id.activity_personal_tv_username);
        activity_personal_tv_usertype = (TextView) findViewById(R.id.activity_personal_tv_usertype);
        activity_personal_tv_userclass = (TextView) findViewById(R.id.activity_personal_tv_userclass);
        activity_personal_tv_usermail = (TextView) findViewById(R.id.activity_personal_tv_usermail);

        show_personInfo();

        //"编辑信息"按钮
        findViewById(R.id.personal_btn_editpersonal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到编辑个人信息界面
                show_editpersonal_dialog();
            }
        });

        //"修改密码"按钮
        findViewById(R.id.personal_btn_editpwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到修改密码界面
                show_editpwd_dialog();
            }
        });
    }

    private void show_personInfo(){
        activity_personal_tv_useraccount.setText(account);
        activity_personal_tv_username.setText(user_name);
        if (user_type == 0)
        {
            activity_personal_tv_usertype.setText("学生");
        }else {
            activity_personal_tv_usertype.setText("教师");
        }
        activity_personal_tv_userclass.setText(user_class + "班");
        activity_personal_tv_usermail.setText(email);
    }

    private void show_editpwd_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
        View view = View.inflate(PersonalActivity.this, R.layout.dialog_editpwd, null);

        dialog_editpwd_et_prepwd = (EditText) view.findViewById(R.id.dialog_editpwd_et_prepwd);
        dialog_editpwd_et_newpwd = (EditText) view.findViewById(R.id.dialog_editpwd_et_newpwd);
        dialog_editpwd_et_newpwd_confirm = (EditText) view.findViewById(R.id.dialog_editpwd_et_newpwd_confirm);



        //修改密码对话框上的"返回"按钮
        view.findViewById(R.id.dialog_editpwd_btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_editpwd.dismiss();
            }
        });

        //修改密码对话框上的"提交"按钮
        view.findViewById(R.id.dialog_editpwd_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String prepwd = dialog_editpwd_et_prepwd.getText().toString();
                String newpwd = dialog_editpwd_et_newpwd.getText().toString();
                String newpwd_confirm = dialog_editpwd_et_newpwd_confirm.getText().toString();

                queryInfoFromDB(account);

                Log.i("prepwd", prepwd);
                Log.i("password", password);
                if (!prepwd.equals(password)){
                    Toast.makeText(PersonalActivity.this, "原密码不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!newpwd.equals(newpwd_confirm)){
                    Toast.makeText(PersonalActivity.this, "两次输入的密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                user person = new user();
                person.account = account;
                person.password = newpwd;
                loginDBManager.updatePersonPwd(person);
                dialog_editpwd.dismiss();
            }
        });
        dialog_editpwd = builder.create();
        dialog_editpwd.setView(view);
        dialog_editpwd.show();
    }

    private void show_editpersonal_dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(PersonalActivity.this);
        View view = View.inflate(PersonalActivity.this, R.layout.dialog_editpersonal, null);

        dialog_editpersonal_tv_useraccount = (TextView) view.findViewById(R.id.dialog_editpersonal_tv_useraccount);
        dialog_editpersonal_tv_useraccount.setText(account);

        dialog_editpersonal_et_username = (EditText) view.findViewById(R.id.dialog_editpersonal_et_username);
        dialog_editpersonal_et_username.setText(user_name);

        dialog_editpersonal_tv_useremail = (TextView) view.findViewById(R.id.dialog_editpersonal_tv_useremail);
        dialog_editpersonal_tv_useremail.setText(email);

        dialog_editpersonal_tv_userclass = (TextView) view.findViewById(R.id.dialog_editpersonal_tv_userclass);
        dialog_editpersonal_tv_userclass.setText(user_class+"");

        dialog_editpersonal_tv_usertype = (TextView) view.findViewById(R.id.dialog_editpersonal_tv_usertype);

        if (user_type == 0){
            dialog_editpersonal_tv_usertype.setText("学生");
        }else{
            dialog_editpersonal_tv_usertype.setText("教师");
        }

        //编辑个人信息对话框上的"返回"按钮
        view.findViewById(R.id.dialog_editpersonal_btn_exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_editpersonal.dismiss();
            }
        });

        //编辑个人信息对话框上的"提交"按钮
        view.findViewById(R.id.dialog_editpersonal_btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //处理编辑个人信息逻辑
                //1. 在返回到个人信息界面时，要更新界面上的值
                //2. 要更新数据库中的值
                //3. 判断所填信息是否是有效的，无效则提示且停留在对话框
                String username = dialog_editpersonal_et_username.getText().toString();
                String userclass = dialog_editpersonal_tv_userclass.getText().toString();
                String usermail = dialog_editpersonal_tv_useremail.getText().toString();
//                if (!usermail.contains("@"))
//                {
//                    Toast.makeText(PersonalActivity.this, "邮箱格式不正确", Toast.LENGTH_SHORT).show();
//                    return;
//                }
                user person = new user();
                person.account = account;
                person.user_name = username;
                person.user_class = Integer.parseInt(userclass);
                person.email = usermail;
                person.user_type = user_type;
                loginDBManager.updatePerson(person);
                queryInfoFromDB(account);
                show_personInfo();
                dialog_editpersonal.dismiss();
            }
        });

        dialog_editpersonal = builder.create();
        dialog_editpersonal.setView(view);
        dialog_editpersonal.show();
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
