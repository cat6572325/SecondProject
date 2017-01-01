package com.example.zqq.myapplication.NetWorks;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by zqq on 16-12-29.
 */
public class Get_Http_AsycTask  {
    private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(3000, TimeUnit.MINUTES)
            .readTimeout(3000, TimeUnit.MINUTES)
            .writeTimeout(3000, TimeUnit.MINUTES)
            .build();

    Handler handler;
    Message msg=null;
    Bundle bundle=null;
    public Get_Http_AsycTask()
    {//TODO 构造

    }



    public void gethttp(String url, final HashMap<String,Object> map)
    {
        //构造上传请求，类似web表单
      //  RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
               // .addFormDataPart("hello", "android")
               // .addFormDataPart("photofile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                //.addPart(Headers.of("photofile", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
       //         .build();

        //进行包装，使其支持进度回调
         final Request request = new Request
                .Builder()
                .url(url)
                 .get()

               // .post(requestBody)/*  (ProgressHelper.addProgressRequestListener(requestBody, uiProgressRequestListener))*/
                .build();
        //开始请求
        client.newCall(request).enqueue(new Callback() {



            @Override
            public void onResponse(okhttp3.Call p1,okhttp3.Response r2)
            {
                try {
                    final String str=r2.body().string();
                    handler=(Handler)map.get("handler");
                    msg=new Message();
                    msg.obj=str;
                    msg.what=0;
                    handler.sendMessage(msg);

                    Log.e("gethttp",str);
                } catch (IOException e) {
                    Log.e("gethttp",e.toString());
                }

             /*   try
                {
                    //上传头像，目前只在首页面对第一次登录的用户，上传预置头像用的
                     JSONObject jsonObject=new JSONObject(str);
                    Handler handler=(Handler)map.get("handler");
                    Bundle bundle=new Bundle();
                    bundle.putString("?","上传");
                    bundle.putString("url",jsonObject.getString("headprturl"));
                    handler.sendEmptyMessage(9);
                    Log.e("成功", str);
                }
                catch (IOException e)
                {

                    if (context!=null)
                    {
                        final String exception=e.toString();


                    }
                    Log.e("在上传预置头像的时候", e.toString());
                } catch (JSONException e) {
                    Log.e("在上传预置头像的时候", e.toString());
                }*/


            }
            @Override
            public void onFailure(okhttp3.Call c,IOException e)

            {
                Log.e("错误",e.toString());
            }

        });

    }
}
