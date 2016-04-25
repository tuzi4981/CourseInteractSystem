package com.guo.course.courseinteraction.LoginAndPost;

/**
 * 作者： 郭明亮 on 2016/4/24 002421:17
 */
public class question {
    private String qes_id;
    private String qes_title;
    private String qes_content;
    private String qes_choose;
    private int qes_type;
    private int qes_class;
    private String qes_teacher;

    public question() {
    }

    public question(String qes_id, String qes_content, String qes_choose, int qes_type) {
        this.qes_id = qes_id;
        this.qes_content = qes_content;
        this.qes_choose = qes_choose;
        this.qes_type = qes_type;
    }
    public String getQes_id() {
        return qes_id;
    }

    public void setQes_id(String qes_id) {
        this.qes_id = qes_id;
    }

    public String getQes_title() {
        return qes_title;
    }

    public void setQes_title(String qes_title) {
        this.qes_title = qes_title;
    }

    public String getQes_content() {
        return qes_content;
    }

    public void setQes_content(String qes_content) {
        this.qes_content = qes_content;
    }

    public String getQes_choose() {
        return qes_choose;
    }

    public void setQes_choose(String qes_choose) {
        this.qes_choose = qes_choose;
    }

    public int getQes_type() {
        return qes_type;
    }

    public void setQes_type(int qes_type) {
        this.qes_type = qes_type;
    }

    public int getQes_class() {
        return qes_class;
    }

    public void setQes_class(int qes_class) {
        this.qes_class = qes_class;
    }

    public String getQes_teacher() {
        return qes_teacher;
    }

    public void setQes_teacher(String qes_teacher) {
        this.qes_teacher = qes_teacher;
    }
}
