package com.example.zqq.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.Third_class.Fans_;
import com.example.zqq.myapplication.Third_class.Follow_;
import com.example.zqq.myapplication.Third_class.Like_;
import com.example.zqq.myapplication.Users.User;

import java.io.IOError;
import java.util.ArrayList;
import java.util.HashMap;

import com.example.zqq.myapplication.Utils.*;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.RequestBody;

/**
 * Created by zqq on 17-1-4.
 */
public class Mine_Fragment_ extends Fragment {


    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            JSONObject JS = null;

            switch (msg.what) {

                case 0:
                    //注册
                    progressDialog.cancel();

                    break;
                case 1:
                    //登录
                    JS = (JSONObject) msg.obj;

                    if (bundle.getString("?").equals("成功")) {
                        try {
                            user = new User();
                            user.settoken(JS.getString("token"));
                            getmydatas();
                            //发送获取个人信息请求
                        } catch (JSONException e) {
                            Log.e("在登录成功时", e.toString());
                            progressDialog.setMessage("错误：" + JS.toString());
                        }
                    }
                    if (bundle.getString("?").equals("失败")) {
                        try {
                            Toast.makeText(getContext(),JS.getString("error"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("登录失败",e.toString());
                        }
                    }


                    break;
                case 3:
                    //TODO 获取个人信息，只有登陆成功才自动调用
                    try {
                        JSONObject jsonObject=new JSONObject((String)msg.obj);
                        user=new User();
                        user.setnickname(jsonObject.getString("nickname"));
                        user.setheadurl(jsonObject.getString("head_pic"));
                        user.setmyid(jsonObject.optString("_id","null"));
                      nickname.setText(user.nickname);
                        nickname_l.setVisibility(View.VISIBLE);
                        if (user.lrucache1 ==null)
                            user.lrucache1 =new UpLoad_LruCache();
                        HashMap<String,Object> map=new HashMap<>();
                        map.put("context",getContext());
                        head_img.setTag(user.headurl);
                        user.lrucache1.loadImageViewurl(user.headurl,head_img,map);
                        Log.e("个人信息",jsonObject.toString());
                        progressDialog.cancel();

                    } catch (JSONException e) {
                        Log.e("获取个人信息成功时",e.toString());
                        progressDialog.setMessage("错误：" + JS.toString());

                    }
                    break;

            }
        }
    };
                    View v;
    RecyclerView mine_recycler;
    User user;
    ProgressDialog progressDialog;
    Mine_Recycler_Adapter mAdapter;
    RelativeLayout re1,re2,re3;
    Button login;
    LinearLayout nickname_l,head_line;
    TextView nickname,text;
    ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
    ImageView  head_img;
    AlertDialog.Builder dialog = null;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater
            , @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.mine_layout, container, false);

        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView();
    }

    private void initView()
    {
        mine_recycler=(RecyclerView)v.findViewById(R.id.mine_recyclerview);
        re1=(RelativeLayout)v.findViewById(R.id.re1);
        re2=(RelativeLayout)v.findViewById(R.id.re2);
        re3=(RelativeLayout)v.findViewById(R.id.re3);
        head_img=(ImageView)v.findViewById(R.id.drawer_headerImageView);
        text=(TextView)v.findViewById(R.id.nickname_Mine);
        nickname=(TextView)v.findViewById(R.id.mine_nickname);
        nickname_l=(LinearLayout)v.findViewById(R.id.nickname_sex);
        head_line=(LinearLayout)v.findViewById(R.id.head_line);
head_line.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        User user=new User();
        if (user.id==null)
        {
            registerpop();
        }
    }
});
        re1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        re2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //关注
                startActivity(new Intent(getContext(), Follow_.class));
            }
        });
        re3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //粉丝
                startActivity(new Intent(getContext(), Fans_.class));

            }
        });
        mAdapter=new Mine_Recycler_Adapter(maps);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        mine_recycler.setAdapter(mAdapter);
        //设置Item增加、移除动画
        mine_recycler.setItemAnimator(new DefaultItemAnimator());
//添加分割线
        mine_recycler.setLayoutManager(gridLayoutManager);

        Invatation();


        //条目点击事件
        mAdapter.setOnClickListener(new Mine_Recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
                TextView txt=(TextView)view.findViewById(R.id.item_title);
                if (txt!=null) {
                    if (txt.getText().toString().equals("我的订阅")) {

                    }
                    if (txt.getText().toString().equals("我喜欢的")) {
                        startActivity(new Intent(getContext(),Like_.class));

                    }
                    if (txt.getText().toString().equals("播放历史")) {
                        startActivity(new Intent(getActivity(),History_.class));

                    }
                    if (txt.getText().toString().equals("兴趣选择")) {

                    }
                    if (txt.getText().toString().equals("反馈")) {

                    }
                    if (txt.getText().toString().equals("版本检测")) {

                    }
                    if (txt.getText().toString().equals("预留")) {

                    }
                }
                switch (position)
                {
                    case 1:
                        //我的订阅
                        break;

                    case 2:
                        //喜欢的
                        break;
                    case 3:
                        //播放历史
                        break;
                    case 5:
                        //兴趣选择
                        break;

                    case 6:
                        //反馈
                        break;

                    case 7:
                        //版本检测
                        break;

                    case 8:
                        //预留
                        break;
                }
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        User user=new User();
        if (user.nickname!=null)
        {
            nickname.setText(user.nickname);
            nickname_l.setVisibility(View.VISIBLE);
            if (user.lrucache1 ==null)
                user.lrucache1 =new UpLoad_LruCache();
            HashMap<String,Object> map=new HashMap<>();
            map.put("context",getContext());
            head_img.setTag(user.headurl);
            user.lrucache1.loadImageViewurl(user.headurl,head_img,map);
        }

    }


    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser)
        {

        }

    }

    private void Invatation()
{
    String[] titles={"我的订阅","我喜欢的","播放历史","兴趣选择","反馈","版本检测","预留"};
    int[] icons={R.mipmap.subscription
    ,R.mipmap.liked
    ,R.mipmap.history
    ,R.mipmap.select
    ,R.mipmap.feedback
    ,R.mipmap.mobile

    };
    for (int i = 0; i <6 ; i++) {
        if (i==3 | i==0)
    {

        HashMap<String,Object> nothink=new HashMap<>();
        nothink.put("layout",5);
        nothink.put("context",getContext());
        nothink.put("title",titles[i]);
        nothink.put("icon_id",icons[i]);
        maps.add(nothink);
    }else
        {
            HashMap<String,Object> nothink=new HashMap<>();
            nothink.put("layout",4);
            nothink.put("context",getContext());
            nothink.put("title",titles[i]);
            nothink.put("icon_id",icons[i]);
            maps.add(nothink);
        }

        HashMap<String,Object> map=new HashMap<>();
        map.put("layout",0);
        map.put("context",getContext());
        map.put("title",titles[i]);
        map.put("icon_id",icons[i]);
        maps.add(map);
    }

    mAdapter.notifyDataSetChanged();

}
    public void showADialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setIcon(android.R.mipmap.sym_def_app_icon);
        progressDialog.setTitle("正在处理数据。。。");
        progressDialog.setMessage("请稍后。。");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置进度条对话框//样式（水平，旋转）

        //进度最大值
        progressDialog.setMax(100);
        progressDialog.setButton("暂停", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除消息队列
                mHandler.removeMessages(0);

            }
        });

        progressDialog.setButton2("取消", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除消息队列
                mHandler.removeMessages(0);
                //恢复进度条初始值

            }
        });

        //显示
        progressDialog.show();
        //必须设置到show之后


    }
    private void registerpop() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View layout = inflater.inflate(R.layout.popupwindow_register_,
                (ViewGroup) getActivity().findViewById(R.id.dialog));
        final EditText editText, editText1;
        editText = (EditText) layout.findViewById(R.id.phone_register);
        editText1 = (EditText) layout.findViewById(R.id.password_register);

        dialog = new AlertDialog.Builder(getContext()).setTitle("注册").setView(layout)

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (editText.getText().toString() == null & editText1.getText().toString() == null) {
                            Toast.makeText(getContext(), "缺少注册或登录元素", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                showADialog();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("handler", mHandler);
                                map.put("context", getContext());
                                map.put("method", "post");
                                RequestBody formBody = new FormBody.Builder()
                                        .add("phone", editText.getText().toString())
                                        .add("password", editText1.getText().toString())
                                        .build();
                                if (editText.getHint().equals("输入帐号")) {
                                    map.put("what", 1);
                                    map.put("method", "post");
                                    MyAsyncTask myAsycTask = new MyAsyncTask(map, formBody);
                                    login.setText("请稍等..");
                                    myAsycTask.execute("http://tp.newteo.com/login");


                                }else{
                                    map.put("what", 0);
                                    login.setText("请稍等..");
                                    MyAsyncTask myAsycTask = new MyAsyncTask(map, formBody);

                                    myAsycTask.execute("http://tp.newteo.com/reg");
                                }
                            }catch(IOError e)
                            {

                                Log.e("启动线程",e.toString());
                            }
                        }
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        login = (Button) layout.findViewById(R.id.changeto);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (login.getText().toString().equals("点击更换登录")) {
                    editText.setHint("输入帐号");
                    editText1.setHint("输入密码");
                    dialog.setTitle("登录");
                    login.setText("点击更换注册");
                }else
                {
                    editText.setHint("输入注册帐号");
                    editText1.setHint("输入注册密码");
                    dialog.setTitle("登录");
                    login.setText("点击更换登录");

                }

            }
        });

        dialog.show();
    }
    private void getmydatas() {
        user = new User();
        HashMap<String, Object> map = new HashMap<>();
        map.put("handler", mHandler);
        map.put("context", getContext());
        RequestBody formBody = new FormBody.Builder()
                .build();
        map.put("what", 3);
        map.put("method", "get");
        MyAsyncTask myAsycTask = new MyAsyncTask(map, formBody);
        myAsycTask.execute("http://tp.newteo.com/user/info?token=" + user.token);
    }
    class MyAsyncTask extends AsyncTask<String, Void, String> {//TODO 异步
        HashMap<String, Object> map;
        RequestBody formBody;

        public MyAsyncTask(HashMap<String, Object> map, RequestBody formBody) {
            this.map = map;
            this.formBody = formBody;

        }

        @Override
        protected String doInBackground(String... params) {
            if (map.get("method").toString().equals("post")) {
                Post_Http post_http = new Post_Http(map);
                post_http.Post_Http(params[0], formBody);
                Log.e("AsyncTask",params[0]);
            }
            if (map.get("method").toString().equals("get")) {//获取各种东西
                //自定义what
                Get_Http_AsycTask get_http_asycTask = new Get_Http_AsycTask();
                get_http_asycTask.gethttp(params[0], map);
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

}
