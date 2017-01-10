package com.example.zqq.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.Users.User;

import java.io.IOError;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by zqq on 17-1-5.
 */

public class History_ extends AppCompatActivity {
    RecyclerView History_RecyclerView;
   private AlertDialog dialog = null;

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
            map.put("text","目前没有任何播放历史哦");
            maps.add(map);
            mAdapter.notifyDataSetChanged();
        }else
        {
            for (int i = 0; i < user.historys.size(); i++) {
                HashMap<String,Object> map=new HashMap<>();
                map.put("layout",2);
                map.put("text","目前没有任何播放记录哦\n");
                map.put("context",History_.this);
                maps.add(map);
            }
            mAdapter.notifyDataSetChanged();
        }
    }
    public void Historyback(View view)
    {
        onBackPressed();
    }
    public void HistoryClear()
    {
        User.historys.clear();
        maps.clear();
        mAdapter.notifyDataSetChanged();
    }
    public void clerAll(View view)
    {
        Button sure,cancel;
        User user=new User();
        if (user.historys!=null) {
            dialog = new AlertDialog.Builder(History_.this).create();
            dialog.show();
            dialog.getWindow().setContentView(R.layout.history_delete_dialog);
            dialog.getWindow()
                    .findViewById(R.id.sure)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            HistoryClear();
                            dialog.dismiss();
                        }
                    });
        }else
        {
            Toast.makeText(History_.this,"没有任何可清除的",Toast.LENGTH_SHORT).show();
        }
    }

}
