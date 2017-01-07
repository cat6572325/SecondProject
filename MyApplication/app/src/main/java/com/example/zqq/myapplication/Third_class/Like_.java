package com.example.zqq.myapplication.Third_class;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.Users.User;
import com.example.zqq.myapplication.classify_Fragments.Fragment_First;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by zqq on 17-1-5.
 */

public class Like_ extends AppCompatActivity {
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            JSONObject JS = null;
            switch (msg.what) {
                case 0:
                    Log.e("likes",msg.obj.toString());
                    break;
            }
        }
    };

    RecyclerView History_RecyclerView;
    public ArrayList<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
    private Mine_Recycler_Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //预先设置允许改变的窗口状态，需在 setContentView 之前调用，否则设置标题时抛运行时错误。
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.c_like);
        initView();
    }

    private void initView()
    {

        History_RecyclerView=(RecyclerView)this.findViewById(R.id.History_recyclerView);
        mAdapter = new Mine_Recycler_Adapter( maps);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(Like_.this, 1);

        History_RecyclerView.setLayoutManager(gridLayoutManager);

        History_RecyclerView.setAdapter(mAdapter);
        //条目点击事件
        mAdapter.setOnClickListener(new Mine_Recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {


            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });

        //loadData();
    }


    private void loadData()
    {
        User user=new User();
        if (user.Likes==null) {
            HashMap<String, Object> map = new HashMap<>();
            map.put("handler",mHandler);
            map.put("what",0);
            map.put("context", Like_.this);
            new MyAsyncTask(map).execute("http://copytp.herokuapp.com/user/favorite/get?&token=" + user.token);
        }else {
            HashMap<String, Object> map = new HashMap<>();
            map.put("layout", 3);
            map.put("context", Like_.this);
            map.put("text", "目前没有任何粉丝哦");
            map.put("button", "0");
            maps.add(map);
        }
        mAdapter.notifyDataSetChanged();




    }
    public void back(View view)
    {
        onBackPressed();
    }

    private class MyAsyncTask extends AsyncTask<String, Void, String[]> {
        HashMap<String ,Object> map;

        public MyAsyncTask(HashMap<String,Object> map)
        {
            this.map=map;
        }
        @Override
        protected String[] doInBackground(String... params) {


            Get_Http_AsycTask get_http_asycTask=new Get_Http_AsycTask();
            get_http_asycTask.gethttp(params[0],map);
            return new String[0];
        }

        @Override protected void onPostExecute(String[] result) {

            super.onPostExecute(result);
        }
    }
    private void checkLike()
    {

    }
}
