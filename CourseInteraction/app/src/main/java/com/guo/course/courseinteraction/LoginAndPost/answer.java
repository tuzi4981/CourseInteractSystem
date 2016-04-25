package com.guo.course.courseinteraction.LoginAndPost;

/**
 * 作者： 郭明亮 on 2016/4/25 002520:06
 */
public class answer {
    private String account;
    private String qes_id;
    private String ans_time;
    private int ans_class;
    private String ans_answer;

    public answer() {
    }

    public answer(String account, String ans_answer, int ans_class, String ans_time, String qes_id) {
        this.account = account;
        this.ans_answer = ans_answer;
        this.ans_class = ans_class;
        this.ans_time = ans_time;
        this.qes_id = qes_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAns_answer() {
        return ans_answer;
    }

    public void setAns_answer(String ans_answer) {
        this.ans_answer = ans_answer;
    }

    public int getAns_class() {
        return ans_class;
    }

    public void setAns_class(int ans_class) {
        this.ans_class = ans_class;
    }

    public String getAns_time() {
        return ans_time;
    }

    public void setAns_time(String ans_time) {
        this.ans_time = ans_time;
    }

    public String getQes_id() {
        return qes_id;
    }

    public void setQes_id(String qes_id) {
        this.qes_id = qes_id;
    }
}
