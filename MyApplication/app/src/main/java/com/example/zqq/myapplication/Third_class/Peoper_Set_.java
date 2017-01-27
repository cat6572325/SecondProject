package com.example.zqq.myapplication.Third_class;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Mine_Fragment_;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.Users.User;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.RequestBody;

import static java.security.AccessController.getContext;

/**
 * Created by zqq on 17-1-16.
 */

public class Peoper_Set_ extends AppCompatActivity {

    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            JSONObject JS = null;

            switch (msg.what) {

                case 0:
                    Toast.makeText(Peoper_Set_.this, "修改失败", Toast.LENGTH_SHORT).show();
                    break;

                default:
                    break;
            }
        }
    };
    String peoper_set_nickname;
    int count;
    EditText nickname_e;
    Spinner spinner;
    TextView right_nickname;
    private List<String> data_list;
    private ArrayAdapter<String> arr_adapter;
    String issexChande;//进来获得原本值

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.peoper_set_layout);
        nickname_e = (EditText) findViewById(R.id.peoper_set_nickname_e);
        spinner = (Spinner) findViewById(R.id.sex_choose);
        right_nickname=(TextView)findViewById(R.id.right_nickname);
        //数据
        data_list = new ArrayList<String>();
        data_list.add("男");
        data_list.add("女");
        data_list.add("保密");

        //适配器
        arr_adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data_list);
        //设置样式
        arr_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //加载适配器
        spinner.setAdapter(arr_adapter);
        // 监听回车键
        initView();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                User user = new User();
                HashMap<String, Object> map = new HashMap<>();
                map.put("handler", mHandler);
                map.put("context", Peoper_Set_.this);
                map.put("what", 0);
                map.put("method", "post");
                TextView textView;
                String sex = data_list.get(position);
                String sexIndex = "0";
                if (sex.equals("男")) {
                    sexIndex = "1";
                    if (!issexChande.equals(sex)) {
                        textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(0xffff0000);
                    }else
                    {

                    }
                }
                if (sex.equals("女")) {
                    sexIndex = "2";
                    if (!issexChande.equals(sex)) {
                        textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(0xffff0000);
                    }
                }
                if (sex.equals("保密")) {
                    sexIndex = "0";
                    if (!issexChande.equals(sex)) {
                        textView = (TextView) view.findViewById(android.R.id.text1);
                        textView.setTextColor(0xffff0000);
                    }

                }
                RequestBody formBody = new FormBody.Builder()
                        .add("sex", sexIndex)
                        .build();
                MyAsyncTask myAsyncTask = new MyAsyncTask(map, formBody);
                myAsyncTask.execute("http://tp.newteo.com/user/change?token=" + user.token);
                //TODO 改变性别
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        nickname_e.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            /**
             *
             * @param v 被监听的对象
             * @param actionId  动作标识符,如果值等于EditorInfo.IME_NULL，则回车键被按下。
             * @param event    如果由输入键触发，这是事件；否则，这是空的(比如非输入键触发是空的)。
             * @return 返回你的动作
             */
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e("多行监听", actionId + "\t" + KeyEvent.KEYCODE_ENTER);
                if (actionId == EditorInfo.IME_NULL) {//发送改变昵称的http
                    if (nickname_e.getText().toString() == null) {

                    } else {
                        User user = new User();
                        right_nickname.setText(nickname_e.getText().toString());
                        right_nickname.setTextColor(0xff000000);
                        nickname_e.setVisibility(View.INVISIBLE);
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("handler", mHandler);
                        map.put("context", Peoper_Set_.this);
                        RequestBody formBody = new FormBody.Builder()
                                .add("nickname", nickname_e.getText().toString())
                                .build();
                        map.put("what", 0);
                        map.put("method", "post");
                        MyAsyncTask myAsyncTask = new MyAsyncTask(map, formBody);
                        myAsyncTask.execute("http://tp.newteo.com/user/change?token=" + user.token);
                        //TODO 改变昵称


                    }

                }

                return event != null && (event.getKeyCode() == KeyEvent.KEYCODE_ENTER);


            }
        });
    }

    public void peoper_set_headImg(View view)
    {
        startActivityForResult(new Intent(Peoper_Set_.this,Picture_Choose_.class),0);
    }
    public void peoper_set_back(View view) {
        finish();
    }

    public void peoper_set_sure(View view) {

    }

    public void peoper_set_loginout(View view) {

    }

    public void peoper_set_nickname(View view) {
        nickname_e.setVisibility(View.VISIBLE);
        nickname_e.clearFocus(); //失去焦点
        nickname_e.requestFocus();//获取焦点弹出软键盘


    }

    public void peoper_set_sex(View view) {

    }

    public void peoper_set_data(View view) {

    }
//////
private void initView()
{
    User user=new User();
    issexChande=user.sex;
}
    class Mytextwatcher implements TextWatcher {
        public void afterTextChanged(Editable s) {


        }

        @Override
        public void beforeTextChanged
                (CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged
                (CharSequence s, int start, int before, int count) {
            int a = getCurrentFocus().getId();
            //当前焦点
            switch (a) {
                case R.id.peoper_set_nickname_e:
                    count = s.length();
                    if (s.length() != 11) {

                    }

                    break;
            }
        }////////if input
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
                Log.e("AsyncTask", params[0]);
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
        switch (resultCode)
        {
            case 0:
                //头像返回地址
                String path=data.getDataString();
                Bundle bundle = data.getExtras();
                Log.e("头像返回地址",path);
                break;
        }
    }
}
