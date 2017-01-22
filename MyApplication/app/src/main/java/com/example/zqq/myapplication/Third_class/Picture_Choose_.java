package com.example.zqq.myapplication.Third_class;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.R;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by zqq on 17-1-17.
 */

public class Picture_Choose_ extends AppCompatActivity {

    ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    RecyclerView picture_choose_rv;
    Mine_Recycler_Adapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_choose);
        initView();
    }
    private void initView() {

        picture_choose_rv = (RecyclerView) this.findViewById(R.id.picture_choose_rv);
        myAdapter = new Mine_Recycler_Adapter(maps);
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, null, null, null,null);
//图片路径
        String  _data=null;
        while(cursor.moveToNext()){
        images.add(cursor.getString(cursor.getColumnIndex("_data")));

        }  Log.e("path",images.get(0));

        for (int i = 0; i < images.size(); i++) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("layout",7);
            map.put("image",images.get(i));
            map.put("context",Picture_Choose_.this);
            maps.add(map);
        }
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(Picture_Choose_.this, 4);
        picture_choose_rv.setAdapter(myAdapter);
        //设置Item增加、移除动画
        picture_choose_rv.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        picture_choose_rv.setLayoutManager(gridLayoutManager);

        //条目点击事件
        myAdapter.setOnClickListener(new Mine_Recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(Picture_Choose_.this,""+position,Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        });
    }
}