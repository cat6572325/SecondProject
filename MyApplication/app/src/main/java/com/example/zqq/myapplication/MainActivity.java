package com.example.zqq.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity; // 注意这里我们导入的V4的包，不要导成app的包了
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.NetWorks.Flop_Fragment_;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.Users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOError;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends FragmentActivity implements View.OnClickListener {

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            JSONObject JS=null;

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
                            Toast.makeText(MainActivity.this,JS.getString("error"),Toast.LENGTH_LONG).show();
                        } catch (JSONException e) {
                            Log.e("登录失败",e.toString());
                        }
                    }


                        break;
                case 2:
                    //上传成功
                    if (bundle.getString("?").equals("失败"))
                    {
                        uplayout.setVisibility(View.INVISIBLE);
                        user=new User();
                        user.wait_UpLoad.clear();
                        Toast.makeText(MainActivity.this,"上传失败，或许你需要重新录制并且上传",Toast.LENGTH_SHORT).show();
                    }

                    if (bundle.getString("?").equals("封面上传成功"))
                    {
                        uploading_t.setText("封面上传完成");
                        progress_t.setText(100+"%");
                    }
                    if (bundle.getString("?").equals("其他数据上传成功"))
                    {
                        uploading_t.setText("其他数据上传完成");
                        progress_t.setText(100+"%");
                    }
                    if (bundle.getString("?").equals("进度"))
                    {
                        progress_t.setText(msg.arg1+"%");
                    }

                    if (bundle.getString("?").equals("完成"))
                    {
                        Toast.makeText(MainActivity.this,"上传结束",Toast.LENGTH_SHORT).show();
                        uplayout.setVisibility(View.INVISIBLE);
                        upload_finish.setVisibility(View.VISIBLE);
                        user=new User();
                        fg1.ShowAdd(user.wait_UpLoad);
                        //fg1.getVideos();
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

                        Log.e("个人信息",jsonObject.toString());
                        progressDialog.cancel();
                        startActivityForResult(new Intent(MainActivity.this,Round_Video_.class),0);

                    } catch (JSONException e) {
                        Log.e("获取个人信息成功时",e.toString());

                        progressDialog.setMessage("错误：" + JS.toString());

                    }
                    break;

            }
        }
    };

    User user;
    AlertDialog.Builder dialog = null;

    // 初始化顶部栏显示
    private ImageView titleLeftImv;
    private TextView titleTv;
    // 定义4个Fragment对象
    private Home_Fragment fg1;
    private Flop_Fragment_ fg2;
    private Release_Fragment fg3;
    private h_Message_Fragment fg4;
    private Mine_Fragment_ fg5;
    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private RelativeLayout thirdLayout;
    private RelativeLayout fourthLayout, mine_l;
    LinearLayout upload_finish;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage, mine_img;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText, mine_txt,uploading_t,progress_t;
    private LinearLayout uplayout;

    // 定义几个颜色
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int dark = 0xff000000;
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    Button login;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initView(); // 初始化界面控件
        setChioceItem(0); // 初始化页面加载时显示第一个选项卡

    }

    /**
     * 初始化页面
     */
    private void initView() {
// 初始化底部导航栏的控件

        firstImage = (ImageView) findViewById(R.id.first_image);
        secondImage = (ImageView) findViewById(R.id.second_image);
        thirdImage = (ImageView) findViewById(R.id.third_image);
        fourthImage = (ImageView) findViewById(R.id.fourth_image);
        firstText = (TextView) findViewById(R.id.first_text);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        fourthText = (TextView) findViewById(R.id.fourth_text);
        firstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        secondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        thirdLayout = (RelativeLayout) findViewById(R.id.third_layout);
        fourthLayout = (RelativeLayout) findViewById(R.id.fourth_layout);
        mine_l = (RelativeLayout) findViewById(R.id.mine_layout);
        mine_img = (ImageView) findViewById(R.id.mine_image);
        mine_txt = (TextView) findViewById(R.id.mine_text);
        uplayout = (LinearLayout) findViewById(R.id.uploading);
        uploading_t=(TextView)findViewById(R.id.UpLoading_t);
        progress_t=(TextView)findViewById(R.id.progress_t);
        upload_finish=(LinearLayout)findViewById(R.id.upload_finish);


        firstLayout.setOnClickListener(MainActivity.this);
        secondLayout.setOnClickListener(MainActivity.this);
        thirdLayout.setOnClickListener(MainActivity.this);
        fourthLayout.setOnClickListener(MainActivity.this);
        mine_l.setOnClickListener(MainActivity.this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setChioceItem(0);
                break;
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            case R.id.mine_layout:
                setChioceItem(4);
                break;
            default:
                break;
        }
    }

    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {


        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
// firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
                firstText.setTextColor(0x99ff0000);
                //firstLayout.setBackgroundColor(gray);
                firstImage.setBackgroundResource(R.mipmap.home_selected);
// 如果fg1为空，则创建一个并添加到界面上
                if (fg1 == null) {
                    fg1 = new Home_Fragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:
// secondImage.setImageResource(R.drawable.XXXX);
                secondText.setTextColor(0x99ff0000);
                // secondLayout.setBackgroundColor(gray);
                secondImage.setBackgroundResource(R.mipmap.thumb_selected);
                if (fg2 == null) {
                    fg2 = new Flop_Fragment_();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
                thirdImage.setBackgroundResource(R.mipmap.release_selected);
                user = new User();
                if (user.id != null) {
                    startActivityForResult(new Intent(MainActivity.this, Round_Video_.class), 0);
                } else {
                 registerpop();
                }
                break;
            case 3:
// fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(0x99ff0000);
                //   fourthLayout.setBackgroundColor(gray);
                fourthImage.setBackgroundResource(R.mipmap.messages_selected);
                if (fg4 == null) {
                    fg4 = new h_Message_Fragment();
                    fragmentTransaction.add(R.id.content, fg4);
                } else {

                    fragmentTransaction.show(fg4);
                }
                break;
            case 4:
// fourthImage.setImageResource(R.drawable.XXXX);
                mine_txt.setTextColor(0x99ff0000);
                //   fourthLayout.setBackgroundColor(gray);
                mine_img.setBackgroundResource(R.mipmap.mine_selected);
                if (fg5 == null) {
                    fg5 = new Mine_Fragment_();
                    fragmentTransaction.add(R.id.content, fg5);
                } else {
                    fragmentTransaction.show(fg5);
                }
                break;
        }


        fragmentTransaction.commit(); // 提交
    }

    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        firstText.setTextColor(0x99aaaaaa);
        firstImage.setBackgroundResource(R.mipmap.home);
        // firstLayout.setBackgroundColor(whirt);
// secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(0x99aaaaaa);
        secondImage.setBackgroundResource(R.mipmap.thumb);
        // secondLayout.setBackgroundColor(whirt);
// thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(0x99aaaaaa);
        thirdImage.setBackgroundResource(R.mipmap.release);
        // thirdLayout.setBackgroundColor(whirt);
// fourthImage.setImageResource(R.drawable.XXX);
        fourthText.setTextColor(0x99aaaaaa);
        fourthImage.setBackgroundResource(R.mipmap.messages);
        // fourthLayout.setBackgroundColor(whirt);
        mine_txt.setTextColor(0x99aaaaaa);
        mine_img.setBackgroundResource(R.mipmap.mine);
    }

    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
        if (fg5 != null) {
            fragmentTransaction.hide(fg5);
        }
    }

    public void HideUploadIng(View view) {
        uplayout.setVisibility(View.INVISIBLE);
    }

    PopupWindow mPopWindow;

    public void showADialog() {
        progressDialog = new ProgressDialog(this);
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
        LayoutInflater inflater = getLayoutInflater();
        final View layout = inflater.inflate(R.layout.popupwindow_register_,
                (ViewGroup) findViewById(R.id.dialog));
        final EditText editText, editText1;
        editText = (EditText) layout.findViewById(R.id.phone_register);
        editText1 = (EditText) layout.findViewById(R.id.password_register);

        dialog = new AlertDialog.Builder(this).setTitle("注册").setView(layout)

                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (editText.getText().toString() == null & editText1.getText().toString() == null) {
                            Toast.makeText(MainActivity.this, "缺少注册或登录元素", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                showADialog();
                                HashMap<String, Object> map = new HashMap<>();
                                map.put("handler", mHandler);
                                map.put("context", MainActivity.this);
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
    public void HideuploadFinish(View view)
    {
        upload_finish.setVisibility(View.GONE);
    }

    private void getmydatas() {
        user = new User();
        HashMap<String, Object> map = new HashMap<>();
        map.put("handler", mHandler);
        map.put("context", MainActivity.this);
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case 0:
                user = new User();
                if (user.wait_UpLoad != null) {
                    uplayout.setVisibility(View.VISIBLE);//显示正在上传中
                    setChioceItem(0); // 初始化页面加载时显示第一个选项卡
                    user.wait_UpLoad.put("handler",mHandler);
                    user.wait_UpLoad.put("context",MainActivity.this);
                    user.wait_UpLoad.put("what",2);
                    UpLoad_Video_AsyncTask upLoad_video_asyncTask = new UpLoad_Video_AsyncTask();
                    upLoad_video_asyncTask.execute(user.wait_UpLoad);//启动异步线程
                }
                break;
            case 1:

                break;
        }
    }

    //TODO 上传 VVVVVVVVVVVVVVVVV
    private void upLoad(HashMap<String, Object> map) {
        // HashMap<String ,Object> map=new HashMap<>();
        map.put("handler", mHandler);
        map.put("context", MainActivity.this);
        //  map.put("path",file_with.GetFile().getPath());
        //  map.put("videourl","http://192.168.1.109:3333/video/push/:videoId?token=${token}");
        // map.put("title","");
        Post_Http post_http = new Post_Http(map);
        User user=new User();
        post_http.videodata("http://tp.newteo.com/user/video/detail?token="+user.token);//启动上传三步骤

    }

    class UpLoad_Video_AsyncTask extends AsyncTask<HashMap<String, Object>, Void, String> {
        HashMap<String, Object> map;


        @Override
        protected String doInBackground(HashMap<String, Object>... params) {
            this.map = params[0];//获得视频数据
            upLoad(map);
            return null;
        }
    } //TODO 上传 AAAAAAAAAAAAAAAA

}