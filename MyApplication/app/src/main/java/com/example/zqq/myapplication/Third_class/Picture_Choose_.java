package com.example.zqq.myapplication.Third_class;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.MainActivity;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.R;

import org.json.JSONObject;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOError;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by zqq on 17-1-17.
 */

public class Picture_Choose_ extends AppCompatActivity {

    ArrayList<HashMap<String, Object>> maps = new ArrayList<>();
    ArrayList<String> images=new ArrayList<>();
    RecyclerView picture_choose_rv;
    Mine_Recycler_Adapter myAdapter;
    AlertDialog.Builder dialog = null;
    Spinner choose_fold;

    private List<String> data_list;//选择文件夹用的集
    private ArrayAdapter<String> arr_adapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_choose);
        initView();
    }
    private void initView() {

        picture_choose_rv = (RecyclerView) this.findViewById(R.id.picture_choose_rv);
        choose_fold=(Spinner)this.findViewById(R.id.picture_choose_item_sp);
        //数据 TODO 选择文件夹的部分
        data_list = new ArrayList<String>();
        data_list.add("文件夹A");
        data_list.add("文件夹B");
        data_list.add("保密");
        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        choose_fold.setAdapter(arr_adapter);
        // 监听回车键
        initView();
        choose_fold.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                String path=view.findViewById(R.id.picture_choose_item_img).getTag().toString();

                Toast.makeText(Picture_Choose_.this,""+position,Toast.LENGTH_SHORT).show();
                Intent intent=getIntent();
                intent.putExtra("imagepath",path);
                Picture_Choose_.this.setResult(0,intent);
                onBackPressed();
                //TODO 选择一张图片后
            }
            @Override
            public void onItemLongClickListener(View view, int position) {
                String path=view.findViewById(R.id.picture_choose_item_img).getTag().toString();
                Prompt(path);

            }
        });
    }
    private void Prompt(String path)
    {
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.choose_img_dialog,
                (ViewGroup) findViewById(R.id.dialog));
        dialog = new AlertDialog.Builder(this).setTitle("此图片在本地地址如下").setView(layout);
        TextView pathData= (TextView) layout.findViewById(R.id.choose_img_dialog_t);
        pathData.setText(path);
        ImageView close=(ImageView)layout.findViewById(R.id.choose_img_dialog_img);

        dialog.show();

    }
}