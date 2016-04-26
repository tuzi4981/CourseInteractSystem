package com.guo.course.courseinteraction;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.guo.course.courseinteraction.LoginAndPost.LoginDBManager;
import com.guo.course.courseinteraction.LoginAndPost.question;
import com.guo.course.courseinteraction.LoginAndPost.user;

import java.util.ArrayList;
import java.util.List;

/**
 * 学生答题界面
 */
public class AnswerForStudentActivity extends AppCompatActivity {

    private ListView activity_answerforstudent_lv_question;
    private List<question> list_questions = new ArrayList<question>();

    private String account;

    //-------------数据库-------------
    private LoginDBManager loginDBManager;
    //-------------------------------

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//去掉信息栏
        setContentView(R.layout.activity_answer_for_student);

        account = getIntent().getStringExtra("account");

        Log.i("AnswerForStudent", "account="+account);

        loginDBManager = new LoginDBManager(this);

        init_list_questions();

        InitView();
    }

    private void init_list_questions() {
        //从数据库中查到属于该学生所属班级的所有题目，更新list_questions
        user person = loginDBManager.queryFromAccount(account);
        Log.i("AnswerForStudent", "person.user_class="+person.user_class);
        list_questions = loginDBManager.queryQuestionsFromClass(person.user_class);
        Log.i("AnswerForStudent", "list_question.size()="+list_questions.size());
    }

    private void InitView() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("答题");
        actionBar.setDisplayHomeAsUpEnabled(true);

        activity_answerforstudent_lv_question = (ListView) findViewById(R.id.activity_answerforstudent_lv_question);
        activity_answerforstudent_lv_question.setAdapter(new ListViewAdapter(list_questions));
        activity_answerforstudent_lv_question.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                question qes = list_questions.get(position);
                int qesid = qes.getQes_id();
                //得到了题目的id之后跳转到下个界面，显示该id对应题目的所有信息
                enterToQuestionDetailforStudent(qesid);
            }
        });
    }

    private void enterToQuestionDetailforStudent(int qesid) {
        Intent intent = new Intent(AnswerForStudentActivity.this, QuestionDetailForStudentActivity.class);
        intent.putExtra("qesid", qesid);
        Log.i("AnswerForStudent", "qesid=" + qesid);
        intent.putExtra("account", account);
        startActivity(intent);
        finish();
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

    public class ListViewAdapter extends BaseAdapter{
        View[] itemViews;

        public ListViewAdapter(List<question> questions) {

            itemViews = new View[questions.size()];

            for(int i=0;i<questions.size();i++){
                question getQes=(question)questions.get(i);    //获取第i个对象
                //调用makeItemView，实例化一个Item
                itemViews[i]=makeItemView(
                        getQes.getQes_id(), getQes.getQes_title(),getQes.getQes_teacher()
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
        private View makeItemView(int qes_id, String qes_title, String qes_teacher) {
            LayoutInflater inflater = (LayoutInflater) AnswerForStudentActivity.this
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            // 使用View的对象itemView与R.layout.item关联
            View itemView = inflater.inflate(R.layout.listitem_answerforstudent, null);

            // 通过findViewById()方法实例R.layout.item内各组件
            TextView listitem_answerforstudent_qesid = (TextView) itemView.findViewById(R.id.listitem_answerforstudent_qesid);
            listitem_answerforstudent_qesid.setText(qes_id+"");
            TextView listitem_answerforstudent_qestitle = (TextView) itemView.findViewById(R.id.listitem_answerforstudent_qestitle);
            listitem_answerforstudent_qestitle.setText(qes_title);
            TextView listitem_answerforstudent_qesteacher = (TextView) itemView.findViewById(R.id.listitem_answerforstudent_qesteacher);
            listitem_answerforstudent_qesteacher.setText(qes_teacher);

            return itemView;
        }
    }
}
