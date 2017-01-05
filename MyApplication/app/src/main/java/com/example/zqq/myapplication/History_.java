package com.example.zqq.myapplication;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.Users.User;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zqq on 17-1-5.
 */

public class History_ extends AppCompatActivity {
    RecyclerView History_RecyclerView;
    public ArrayList<HashMap<String, Object>> maps = new ArrayList<HashMap<String, Object>>();
    private Mine_Recycler_Adapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.history_layout);
        initView();
    }

    private void initView()
    {
        History_RecyclerView=(RecyclerView)this.findViewById(R.id.History_recyclerView);
        mAdapter = new Mine_Recycler_Adapter( maps);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(History_.this, 1);

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
        loadData();

    }


    private void loadData()
    {
        User user=new User();
        if (user.historys==null)
        {
            HashMap<String,Object> map=new HashMap<>();
            map.put("layout",3);
            map.put("context",History_.this);
            maps.add(map);
            mAdapter.notifyDataSetChanged();

        }else
        {
            for (int i = 0; i < user.historys.size(); i++) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("layout",2);
                map.put("context",History_.this);
                maps.add(map);
            }
            mAdapter.notifyDataSetChanged();



        }
    }

}
