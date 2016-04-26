package com.guo.course.courseinteraction;

import android.content.Context;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.answer;
import com.guo.course.courseinteraction.LoginAndPost.question;
import com.guo.course.courseinteraction.LoginAndPost.user;

import java.util.ArrayList;
import java.util.List;

public class QueryAnswerActivity extends AppCompatActivity {

    private String account;
    private ListView activity_queryanswer_lv_answer;
    private List<answer> list_answers = new ArrayList<answer>();

    //-------------数据库-------------
    private LoginDBManager loginDBManager;
    //-------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_query_answer);
        account = getIntent().getStringExtra("account");
        loginDBManager = new LoginDBManager(this);

        init_list_answers();

        InitView();

    }

    private void init_list_answers() {
        user person = loginDBManager.queryFromAccount(account);
        int ans_class = person.getUser_class();

        list_answers = loginDBManager.queryAnswersFromClass(ans_class);
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("查看提交答案");
        actionBar.setDisplayHomeAsUpEnabled(true);

        activity_queryanswer_lv_answer = (ListView) findViewById(R.id.activity_queryanswer_lv_answer);
        activity_queryanswer_lv_answer.setAdapter(new ListViewAdapter(list_answers));
    }


    public class ListViewAdapter extends BaseAdapter {
        View[] itemViews;

        public ListViewAdapter(List<answer> answers) {

            itemViews = new View[answers.size()];

            for(int i=0;i<answers.size();i++){
                answer getAns=(answer)answers.get(i);    //获取第i个对象
                String temp_account = getAns.getAccount();
                user person = loginDBManager.queryFromAccount(temp_account);
                String studentname = person.getUser_name();
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(
                        getAns.getQes_id(), getAns.getAns_answer(),studentname
                );
            }
        }

        @Override
        public int getCount() {
            return itemViews.length;
        }

        @Override
        public Object getItem(int position) {
            return itemViews[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null)
                return itemViews[position];
            return convertView;
        }

        //绘制Item的函数
        private View makeItemView(int qes_id, String ans_answer, String studentname) {
            LayoutInflater inflater = (LayoutInflater) QueryAnswerActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.listitem_answerforstudent, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView listitem_answerforstudent_qesid = (TextView) itemView.findViewById(R.id.listitem_answerforstudent_qesid);
            listitem_answerforstudent_qesid.setText(qes_id+"");
            TextView listitem_answerforstudent_qestitle = (TextView) itemView.findViewById(R.id.listitem_answerforstudent_qestitle);
            listitem_answerforstudent_qestitle.setText(ans_answer);
            TextView listitem_answerforstudent_qesteacher = (TextView) itemView.findViewById(R.id.listitem_answerforstudent_qesteacher);
            listitem_answerforstudent_qesteacher.setText(studentname);

            return itemView;
        }
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
