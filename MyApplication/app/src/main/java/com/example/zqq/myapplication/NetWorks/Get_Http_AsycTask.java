package com.example.zqq.myapplication.NetWorks;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zqq.myapplication.classify_Fragments.Fragment_First;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Second;
import com.example.zqq.myapplication.classify_Fragments.Fragment_third;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
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
public class Get_Http_AsycTask extends Thread{

            private static final OkHttpClient client = new OkHttpClient.Builder()
            //设置超时，不设置可能会报异常
            .connectTimeout(3000, TimeUnit.MINUTES)
            .readTimeout(3000, TimeUnit.MINUTES)
            .writeTimeout(3000, TimeUnit.MINUTES)
            .build();
    Handler handler;
    LruCache<String,Bitmap> lruCache;//
    Message msg=null;
    Bundle bundle=null;
    Context context;
    ImageView headimg;
    String imgurl;
    public Get_Http_AsycTask()
    {//TODO 构造

    }
    public void initlrucache()
    {//TODO 初始化一个缓冲区
        int maxMemory=(int)Runtime.getRuntime().maxMemory();
        int cacheSize=maxMemory/4;//指定使用可用内存4分之1
        lruCache=new LruCache<String,Bitmap>(cacheSize)
        {
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //在每次存入缓存时调用，返回一个bitmap正确大小
                return value.getByteCount();
            }
        };//初始化缓存区
    }

    public void loadurl(int start,int end,final HashMap<String,Object> map)
    {
        String url1 = null;
        handler=(Handler)map.get("handler");
        RecyclerView recyclerView=(RecyclerView)map.get("recyclerview");
        for (int i=start;i<end;i++) {
            //加载多条
            if (map.get("?").equals("Fragment_First")) {
                if (i>Fragment_First.urls.size() | Fragment_First.urls.size()==0)
                {//如果是这个fragment又没有这张图片则加载它
                    addimg();
                }else {
                    url1 = Fragment_First.urls.get(i);
                }
            }
            if (map.get("?").equals("InfoDetailsFragment")) {
                if (i>Fragment_Second.urls.size()| Fragment_First.urls.size()==0)
                {//如果是这个fragment又没有这张图片则加载它
                    addimg();
                }else {
                    url1 = Fragment_Second.urls.get(i);
                }
            }
            if (map.get("?").equals("AgendaFragment")) {
                if (i>Fragment_third.urls.size()| Fragment_First.urls.size()==0)
                {//如果是这个fragment又没有这张图片则加载它
                    addimg();
                }else {
                    url1 = Fragment_third.urls.get(i);
                }
            }
            if (url1!=null) {
                this.headimg = (ImageView) recyclerView.findViewWithTag(url1);
                //通过这个recyclerview找寻这个tag
                this.imgurl = url1;
            }

        }
    }
    private void addimg()
    {

        new Thread() {
            @Override
            public void run() {
                super.run();
                Bitmap bitmap = null;
                try {
                    URL url = new URL(imgurl);

                    HttpURLConnection conn = (HttpURLConnection) url
                            .openConnection();
                    conn.setDoInput(true);
                    conn.connect();
                    InputStream is = new BufferedInputStream(conn.getInputStream());
                    bitmap = BitmapFactory.decodeStream(is);
                    is.close();
                    conn.disconnect();

                } catch (IOException e) {
                    Log.e("联网或图片加载失败", e.toString());
                }
                if (bitmap != null) {
                    Message msg = Message.obtain();
                    msg.obj = bitmap;
                    msg.what = 3;

                }
            }
        }.start();
    }
    public void GetGridViewImg(final HashMap<String,Object> map)
    {
        headimg=(ImageView)map.get("image");
        Bitmap bitmap=(Bitmap)lruCache.get(map.get("path").toString());
        if (bitmap!=null)
        {
            headimg.setImageBitmap(bitmap);
        }else {
            new Thread() {
                @Override
                public void run() {
                    super.run();
                  Bitmap  bitmap1 = null;
                    if (bitmap1 != null) {
                        try {
                            FileInputStream fis=new FileInputStream(map.get("path").toString());
                            bitmap1=BitmapFactory.decodeStream(fis);

                        Message msg = new Message();
                        msg.obj = bitmap1;
                        msg.what = 0;
                        handler.sendMessage(msg);

                        } catch (FileNotFoundException e) {
                        Log.e("获取gridview图片时",e.toString());
                    }
                    }
                }
            }.start();
        }
    }
    public void gethttp(String url, final HashMap<String,Object> map)
    {
        //构造上传请求，类似web表单
      //  RequestBody requestBody = new MultipartBody.Builder().setType(MultipartBody.FORM)
               // .addFormDataPart("hello", "android")
               // .addFormDataPart("photofile", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                //.addPart(Headers.of("photofile", "form-data; name=\"another\";filename=\"another.dex\""), RequestBody.create(MediaType.parse("application/octet-stream"), file))
       //         .build();

        context =(Context)map.get("context");
        handler=(Handler)map.get("handler");

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
                   msg=new Message();
                    msg.obj=str;
                    msg.what=(Integer)map.get("what");
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
                handler=(Handler)map.get("handler");
                msg=new Message();

                msg.what=1;
                handler.sendMessage(msg);


                Log.e("错误",e.toString());

            }

        });

    }
}
