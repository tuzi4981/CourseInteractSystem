package com.guo.course.courseinteraction.LoginAndPost;

/**
 * 作者： 郭明亮 on 2016/4/17 001719:25
 */
public class user {
    public String account;
    public String password;
    public String user_name;
    public int user_class;
    public String email;
    public int user_type;

    public user(){}

    public user(String account, String password, String user_name, int user_class, String email, int user_type){
        this.account = account;
        this.password = password;
        this.user_name = user_name;
        this.user_class = user_class;
        this.email = email;
        this.user_type = user_type;
    }

    public String getAccount(){return account;}

    public String getPassword(){return password;}

    public String getUser_name(){return user_name;}

    public int getUser_class(){return user_class;}

    public String getEmail(){return email;}

    public int getUser_type(){return user_type;}

    public void setAccount(String account){this.account = account;}

    public void setPassword(String password){this.password = password;}

    public void setUser_name(String user_name){this.user_name = user_name;}

    public void setUser_class(int user_class){this.user_class = user_class;}

    public void setEmail(String email){this.email = email;}

    public void setUser_type(int user_type){this.user_type = user_type;}
}
