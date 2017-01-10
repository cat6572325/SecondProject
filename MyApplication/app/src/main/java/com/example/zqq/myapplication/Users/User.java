package com.example.zqq.myapplication.Users;

import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.Utils.UpLoad_LruCache;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zqq on 16-12-30.
 */
public class User {
    public static String nickname=null,password=null,phone=null,headurl=null,token=null,id=null;
    public static HashMap<String,Object> wait_UpLoad=null;
    public static ArrayList<HashMap<String,Object>> all_video=null,historys=null,Likes=null,Follows=null,favorites=null;
    public static HashMap<String,String> applications=null;
    public  static Get_Http_AsycTask get_http;
    public static UpLoad_LruCache lrucache1;
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
