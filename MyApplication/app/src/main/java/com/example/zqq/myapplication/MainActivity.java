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
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.Users.User;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

public class MainActivity extends FragmentActivity implements View.OnClickListener{

    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0:
                    //注册
                  //  Button btn=progressDialog.getButton(0);
                   // btn.setText("成功");
                   // progressDialog.cancel();
                    progressDialog.cancel();

                    break;
                case 1:
                    //登录
                    try {
                        user = new User();
                        JSONObject JS = (JSONObject) msg.obj;
                        user.settoken(JS.getString("token"));
                      //  Button btn1=progressDialog.getButton(0);
                        //btn1.setText("成功");

                        progressDialog.cancel();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                case 2:
                    //上传成功

                    uplayout.setVisibility(View.INVISIBLE);
                    break;
            }
        }
    };

                    User user;
   AlertDialog.Builder dialog=null;
    // 初始化顶部栏显示
    private ImageView titleLeftImv;
    private TextView titleTv;
    // 定义4个Fragment对象
    private Home_Fragment fg1;
    private Flop_Fragment_ fg2;
    private Release_Fragment fg3;
    private Home_Fragment fg4;
    private Mine_Fragment_ fg5;
    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private RelativeLayout thirdLayout;
    private RelativeLayout fourthLayout,mine_l;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage,mine_img;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText,mine_txt;
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
        mine_l=(RelativeLayout)findViewById(R.id.mine_layout);
        mine_img=(ImageView)findViewById(R.id.mine_image);
        mine_txt=(TextView)findViewById(R.id.mine_text);
        uplayout=(LinearLayout)findViewById(R.id.uploading);

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
// thirdImage.setImageResource(R.drawable.XXXX);
             //   thirdText.setTextColor(0x99ff0000);
              //  thirdLayout.setBackgroundColor(gray);
               thirdImage.setBackgroundResource(R.mipmap.release_selected);
                    user=new User();
                    if (user.phone!=null) {
                        startActivityForResult(new Intent(MainActivity.this, Round_Video_.class),0);
                    }
                    else {
                       // startActivityForResult(new Intent(MainActivity.this, Register_.class), 0);
                        //finish();
                        registerpop();
                    }
                break;
            case 3:
// fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(0x99ff0000);
             //   fourthLayout.setBackgroundColor(gray);
                fourthImage.setBackgroundResource(R.mipmap.messages_selected);
                if (fg4 == null) {
                    fg4 = new Home_Fragment();
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
    public void HideUploadIng(View view)
    {
        uplayout.setVisibility(View.INVISIBLE);
    }
    PopupWindow mPopWindow;
    public void showADialog()
    {
        progressDialog=new ProgressDialog(this);
        progressDialog.setIcon(android.R.mipmap.sym_def_app_icon);
        progressDialog.setTitle("正在处理数据。。。");
        progressDialog.setMessage("请稍后。。");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);//设置进度条对话框//样式（水平，旋转）

        //进度最大值
        progressDialog.setMax(100);
        progressDialog.setButton("暂停",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                //删除消息队列
                mHandler.removeMessages(0);

            }
        });

        progressDialog.setButton2("取消",new DialogInterface.OnClickListener() {

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
private void registerpop()
{
    LayoutInflater inflater = getLayoutInflater();
   final View layout = inflater.inflate(R.layout.popupwindow_register_,
     (ViewGroup) findViewById(R.id.dialog));
    final   EditText editText,editText1;
    editText=(EditText)layout.findViewById(R.id.phone_register);
    editText1=(EditText)layout.findViewById(R.id.password_register);

    dialog= new AlertDialog.Builder(this).setTitle("注册").setView(layout)

    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {

            if (editText.getText().toString()==null & editText1.getText().toString()==null)
            {
                Toast.makeText(MainActivity.this,"缺少注册或登录元素",Toast.LENGTH_SHORT).show();
            }else {
                showADialog();
                HashMap<String, Object> map = new HashMap<>();
                map.put("handler", mHandler);
                map.put("context", MainActivity.this);
                RequestBody formBody = new FormBody.Builder()
                        .add("phone", editText.getText().toString())
                        .add("password", editText1.getText().toString())
                        .build();
                if (editText.getHint().equals("输入帐号")) {
                    map.put("what", 1);
                    MyAsyncTask myAsycTask = new MyAsyncTask(map, formBody);
                    login.setText("请稍等..");
                    myAsycTask.execute("http://192.168.1.109:3333/login");
                } else {
                    map.put("what", 0);
                    login.setText("请稍等..");
                    MyAsyncTask myAsycTask = new MyAsyncTask(map, formBody);

                    myAsycTask.execute("http://192.168.1.109:3333/reg");
                }
            }
        }
    }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    });
    login=(Button)layout.findViewById(R.id.changeto);
    login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

              editText.setHint("输入帐号");
            editText1.setHint("输入密码");
            dialog.setTitle("登录");

        }
    });

             dialog.show();


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


            Post_Http post_http = new Post_Http(map);
            post_http.Post_Http(params[0], formBody);

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
        switch (resultCode)
        {
            case 0:
                user=new User();
                if (user.wait_UpLoad!=null)
                {
                    uplayout.setVisibility(View.VISIBLE);//显示正在上传中
                    setChioceItem(0); // 初始化页面加载时显示第一个选项卡
                    UpLoad_Video_AsyncTask upLoad_video_asyncTask=new UpLoad_Video_AsyncTask();
                    upLoad_video_asyncTask.execute(user.wait_UpLoad);//启动异步线程
                }
                break;
            case 1:

                break;
        }
    }
    //TODO 上传 VVVVVVVVVVVVVVVVV
    private void upLoad(HashMap<String,Object> map)
    {
       // HashMap<String ,Object> map=new HashMap<>();
        map.put("handler",mHandler);
        map.put("context",MainActivity.this);
      //  map.put("path",file_with.GetFile().getPath());
      //  map.put("videourl","http://192.168.1.109:3333/video/push/:videoId?token=${token}");
       // map.put("title","");
        Post_Http post_http=new Post_Http(map);
        post_http.loadvideopng();//启动上传三步骤

    }
     class UpLoad_Video_AsyncTask extends AsyncTask<HashMap<String,Object>,Void,String>
    {
        HashMap<String,Object> map;


        @Override
        protected String doInBackground(HashMap<String, Object>... params) {
            this.map=params[0];//获得视频数据
            upLoad(map);
            return null;
        }
    } //TODO 上传 AAAAAAAAAAAAAAAA

}