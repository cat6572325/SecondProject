package com.example.zqq.myapplication.classify_Fragments;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.R;

import java.util.HashMap;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

/**
 * Created by zqq on 16-12-27.
 */
public class Fragment_Second  extends Fragment {
    public Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String json=(String)msg.obj;
                    Log.e("视频json",json);
                    break;
            }
        }
    };
    private View v;
    //第三方刷新控件
    public WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    Get_Http_AsycTask get_http_asycTask=new Get_Http_AsycTask();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.h_second, container, false);
        //   Bundle bundle = getArguments();
        // String agrs1 = bundle.getString("agrs1");
        //  tv.setText(agrs1);
        return v;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser)
        {

        }
    }
    private void getVideos()
    {//TODO 调用Asynstask启动线程加载数据
        HashMap<String,Object> map=new HashMap<>();
        map.put("handler",mHandler);
        MyAsycTask myAsycTask=new MyAsycTask(map);
        myAsycTask.execute("http://192.168.1.109:3333/video/?per=1&page=1");
        //启动线程联网后就返回
    }
    class MyAsycTask extends AsyncTask<String,Void,String>
    {//TODO 异步
        HashMap<String,Object> map;
        public MyAsycTask(HashMap<String,Object> map)
        {
            this.map=map;

        }

        @Override
        protected String doInBackground(String... params) {
            get_http_asycTask.gethttp(params[0],map);//get方法链接http
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }
    private void initView()
    {
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.second_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.RED);
        //mWaveSwipeRefreshLayout.setWaveColor(Color.argb(100,255,0,0));
        mWaveSwipeRefreshLayout.setWaveColor(0xffff0000);
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {


            }
        });
    }
}
