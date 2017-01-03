package com.example.zqq.myapplication.Users;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zqq on 16-12-30.
 */
public class User {
    public static String nickname=null,password=null,phone=null,headurl=null,token=null,id=null;
    public static HashMap<String,Object> wait_UpLoad=null;
    public static ArrayList<HashMap<String,Object>> all_video=null;
    public void settoken(String token)
    {
        this.token=token;
    }
    public void setmyid(String token)
    {
        this.id=token;
    }
    public void setnickname(String token)
    {
        this.nickname=token;
    }
    public void setphone(String token)
    {
        this.phone=token;
    }
    public void setpassword(String token)
    {
        this.password=token;
    }
    public void setheadurl(String token)
    {
        this.headurl=token;
    }
}
