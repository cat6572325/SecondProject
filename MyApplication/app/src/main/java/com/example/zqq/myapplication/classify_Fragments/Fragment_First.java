package com.example.zqq.myapplication.classify_Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.Home_Fragment;
import com.example.zqq.myapplication.Llisteners.SampleListener;
import com.example.zqq.myapplication.MainActivity;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.Users.User;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;
import com.squareup.okhttp.RequestBody;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;
import okhttp3.FormBody;

/**
 * Created by zqq on 16-12-27.
 */
public class Fragment_First extends Fragment {
    public Handler mHandler=new Handler()
    {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            JSONArray jsonArray;
            Bundle bundle=msg.getData();

            JSONObject jsonObject,jsonObject1,jsonObject2,jsonObject3,jsonObject4;
            switch (msg.what) {
                case 0:
                    // [{"_id":"5865dca652823516a2390753"
                    // ,"poster":{
                    // "_id":"58667a34569c4e1e6b185227"
                    // ,"nickname":"暂无昵称"
                    // ,"thumbnail":null
                    // ,"head_pic":null
                    // ,"follows":[]
                    // ,"pub_videos":[]
                    // }
                    // ,"title":"444"
                    // ,"video_url":{
                    // "_id":"5865dd0952823516a2390754"
                    // ,"vid_url":"192.168.1.109:3333/public/videos/1482807253367.mp4"
                    // }
                    // ,"cover":{
                    // "_id":"5865dc2f52823516a2390752"
                    // ,"cover_url":"192.168.1.109:3333/public/covers/004.png"
                    // }
                    // ,"channel":"逗比"
                    // ,"view_number":10
                    // ,"like_number":1
                    // ,"comment_number":13
                    // ,"__v":0
                    // ,"create_time":"2016-12-30T04:03:50.628Z"
                    // ,"comments":[]
                    // }]
                    /*
                    视频json: [{"_id":"5865de9952823516a2390756","poster":null,"title":"555","video_url":{"_id":"5865deb752823516a2390757","vid_url":"192.168.1.109:3333/public/videos/1482807248048.mp4"},"cover":{"_id":"5865de7952823516a2390755","cover_url":"192.168.1.109:3333/public/covers/testphoto.jpg"},"channel":"热门","view_number":5,"like_number":1,"comment_number":0,"__v":0,"create_time":"2016-12-30T04:12:09.495Z","comments":[]}]
                     */
                    try {
                        User user=new User();
                        String json=(String)msg.obj;
                    Log.e("视频json",json);
                        jsonArray=new JSONArray(json);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            Log.e("jsonArrayLength",":"+jsonArray.length());
                            jsonObject= jsonArray.getJSONObject(i);//获取第i个视频
                            if (jsonObject.optJSONObject("video_url")==null)
                            {

                            }else {
                                jsonObject1 = jsonObject.getJSONObject("video_url");
                                //   jsonObject2=jsonObject.getJSONObject("poster");
                                jsonObject3 = jsonObject.getJSONObject("cover");

                                HashMap<String, Object> map = new HashMap<>();
                                map.put("vid_url", jsonObject1.getString("vid_url"));
                                map.put("title", "FirstVideos");
                                map.put("layout", 0);
                                map.put("_id",jsonObject.optString("_id"));
                                map.put("nickname", jsonObject.getString("title"));
                                map.put("comment_number", jsonObject.getString("comment_number"));
                                map.put("like_number", jsonObject.getString("like_number"));
                                map.put("view_number", jsonObject.getString("view_number"));
                                map.put("head_pic",jsonObject.optString("head_pic","null"));
                                map.put("tag", String.valueOf(System.currentTimeMillis()));
                                if (user.all_video==null) {
                                    user.all_video = new ArrayList<>();
                                }
                                user.all_video.add(map);
                            }
                        }
                        AddData(user.all_video);
                        mWaveSwipeRefreshLayout.setRefreshing(false);
                     } catch (JSONException e) {
                        Log.e("转换json的时候",e.toString());
                        //获取失败的时候
                        if (countItems==0)
                        {//等于0还出错表示没有这页，也就不继续获取了
                            Log.e("目前未加载数为 ",countItems+"个");
                          //  countItems=5;
                        }else {
                          //  countItems--;//减去1
                          //  getVideos();
                        }

                    }finally {
                        mWaveSwipeRefreshLayout.setRefreshing(false);

                    }
                    break;
                case 1:
                    //临时
                    HashMap<String,Object> map=new HashMap<>();
                    map.put("vid_url","baobab.wdjcdn.com/14564977406580.mp4");
                    map.put("title","FirstVideos");
                    map.put("layout",0);
                    map.put("view_number","null");
                    map.put("like_number","null");
                    map.put("nickname","null");
                    map.put("head_pic","null");
                    map.put("tag",String.valueOf(System.currentTimeMillis()));
                 ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
                    maps.add(map);
            AddData(maps);

            break;
                case 2:
                    //添加&删除喜欢
                   //if (bundle.getString("?").equals("成功")) {
                       User user=new User();
                       if (user.favorites==null)

                           user.favorites=new ArrayList<>();

                           jsonObject=(JSONObject)msg.obj;
                           jsonArray=jsonObject.optJSONArray("favorites");
                           Log.e("j",jsonArray.toString());
                       user=new User();
                           for (int i = 0; i <jsonArray.length() ; i++) {
                               try {
                                   for (int y = 0; y < user.all_video.size(); y++) {
                                       if (jsonArray.getString(i).equals(user.all_video.get(y).get("_id").toString()))
                                       {
                                           if (user.Likes==null)
                                               user.Likes=new ArrayList<>();

                                           user.Likes.add(user.all_video.get(y));
                                       }
                                   }
                               } catch (JSONException e) {
                                   e.printStackTrace();
                               }
                           }
                      Log.e("添加喜欢",jsonObject.toString());

                  // }


                    break;
                case 3:
                    //加载图片
                    break;
            }
        }
    };
    PopupWindow mPopWindow;
    int countItems=1;//定义全局的每次刷新将获得多少个视频
    private Set<MyAsycTask> AsyncTasks;
    int lastVisibleItem;
    int firstVisibleItem;
    LinearLayoutManager linearLayoutManager;
    RecyclerView home_rec;
    View v;
    public static ArrayList<String> urls=new ArrayList<>();//截图，头像等图片的url集合
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>();
    private Second_Adapter second_adapter;
    //第三方刷新控件
    public WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
    public ListVideoUtil listVideoUtil;
    Get_Http_AsycTask get_http_asycTask;
    FrameLayout videoFullContainer;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
             getActivity().getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
             getActivity(). getWindow().setEnterTransition(new Explode());
             getActivity().  getWindow().setExitTransition(new Explode());
         }*/

         v = inflater.inflate(R.layout.home_fragments_layout, container, false);
       //   Bundle bundle = getArguments();
        // String agrs1 = bundle.getString("agrs1");

        //  tv.setText(agrs1);
        initView(v);
        return v;
    }



    private void initView(View view) {
         videoFullContainer=(FrameLayout) view.findViewById(R.id.video_full_container);
        // 设置一个exit transition
         listVideoUtil = new ListVideoUtil(getContext());
        listVideoUtil.setFullViewContainer(videoFullContainer);
        listVideoUtil.setHideActionBar(true);
        home_rec = (RecyclerView) view.findViewById(R.id.fragments_First_rv);
        mWaveSwipeRefreshLayout = (WaveSwipeRefreshLayout) v.findViewById(R.id.main_swipe);
        mWaveSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.RED);
        //mWaveSwipeRefreshLayout.setWaveColor(Color.argb(100,255,0,0));
        mWaveSwipeRefreshLayout.setWaveColor(0xffff0000);
        second_adapter = new Second_Adapter(getActivity(), lists,this,listVideoUtil);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);
        home_rec.setLayoutManager(gridLayoutManager);
        home_rec.setAdapter(second_adapter);
        //条目点击事件
        if (isNetworkAvailable(getActivity()))
        {
            Toast.makeText(getContext(), "当前有可用网络！", Toast.LENGTH_LONG).show();
        }
        else
        {
            Toast.makeText(getContext(), "当前没有可用网络！", Toast.LENGTH_LONG).show();
        }


        second_adapter.setOnClickListener(new Second_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();


            }
            @Override
            public void onItemLongClickListener(View view, int position) {
            }
        });
        second_adapter.setOnLikeListener(new Second_Adapter.OnLikeClickListener() {
            @Override
            public void OnLikeClickListener(View view, int position) {
                //添加至我喜欢的&删除这个我喜欢的
                User user=new User();
                if (user.id!=null) {
                    okhttp3.RequestBody formBody = new FormBody.Builder()
                            .build();
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("handler", mHandler);
                    map.put("what", 2);
                    map.put("vid", lists.get(position).get("_id"));
                    map.put("context", getContext());
                    map.put("body", formBody);
                    map.put("method", "post");
                    addOrclerLike(map);
                    ImageView img_like=(ImageView)view.findViewById(R.id.like_img);
                    if (img_like.getTag().equals("没有点过"))
                    {
                        img_like.setImageResource(R.mipmap.likes_red);
                        img_like.setTag("点过");
                    }else
                    {
                        img_like.setImageResource(R.mipmap.likes);
                        img_like.setTag("没有点过");
                    }
                }else
                {
                    Toast.makeText(getContext(),"未登录",Toast.LENGTH_SHORT).show();
                }
            }
        });

        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
             getVideos();

            }
        });
        home_rec.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                linearLayoutManager=(LinearLayoutManager)home_rec.getLayoutManager();
                firstVisibleItem   = linearLayoutManager.findFirstVisibleItemPosition();
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                Debuger.printfLog("firstVisibleItem " + firstVisibleItem +" lastVisibleItem " + lastVisibleItem);
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 /*&& listVideoUtil.getPlayTAG().equals(RecyclerItemViewHolder.TAG)*/)
                {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //如果是小窗口就不需要处理
                        if (!listVideoUtil.isSmall() && !listVideoUtil.isFull()) {
                            //小窗口
                            int size = CommonUtil.dip2px(getActivity(), 150);
                            //actionbar为true才不会掉下面去
                            listVideoUtil.showSmallVideo(new Point(size, size), true, true);

                        }
                    } else {
                        if (listVideoUtil.isSmall()) {
                            listVideoUtil.smallVideoToNormal();
                        }
                    }
                }else
                {
                    User user=new User();//假如这样频繁的申明和消除实例会给系统造成负担，就得换一种方式了
                    if (user.get_http==null) {
                        user.get_http = new Get_Http_AsycTask();
                        user.get_http.initlrucache();
                    }
                        HashMap<String,Object> map=new HashMap<String, Object>();
                        map.put("?","Fragment_First");
                        map.put("recyclerview",home_rec);
                    map.put("handler",mHandler);
                        user.get_http.loadurl(
                                linearLayoutManager.findFirstVisibleItemPosition()
                                , linearLayoutManager.findLastVisibleItemPosition()
                                , map);

                }
            }
        });

        //小窗口关闭被点击的时候回调处理回复页面
        listVideoUtil.setVideoAllCallBack(new SampleListener() {
            @Override
            public void onPrepared(String url, Object... objects) {
                super.onPrepared(url, objects);
                Debuger.printfLog("Duration " + listVideoUtil.getDuration() + " CurrentPosition " + listVideoUtil.getCurrentPositionWhenPlaying());
            }

            @Override
            public void onQuitSmallWidget(String url, Object... objects) {
                super.onQuitSmallWidget(url, objects);
                linearLayoutManager = (LinearLayoutManager) home_rec.getLayoutManager();

                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 /*&& listVideoUtil.getPlayTAG().equals(ListVideoAdapter.TAG)*/)
                {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        listVideoUtil.releaseVideoPlayer();
                        second_adapter.notifyDataSetChanged();
                    }
                }


            }
        });

    }

    public boolean isNetworkAvailable(Activity activity)
    {
        Context context = activity.getApplicationContext();
        // 获取手机所有连接管理对象（包括对wi-fi,net等连接的管理）
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        if (connectivityManager == null)
        {
            return false;
        }
        else
        {
            // 获取NetworkInfo对象
            NetworkInfo[] networkInfo = connectivityManager.getAllNetworkInfo();

            if (networkInfo != null && networkInfo.length > 0)
            {
                for (int i = 0; i < networkInfo.length; i++)
                {
                    System.out.println(i + "===状态===" + networkInfo[i].getState());
                    System.out.println(i + "===类型===" + networkInfo[i].getTypeName());
                    // 判断当前网络状态是否为连接状态
                    if (networkInfo[i].getState() == NetworkInfo.State.CONNECTED)
                    {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        AsyncTasks=new HashSet<>();
        if (lists.size()<2)
        getVideos();



    }


    public void deleteItem(int position)
    {//用于试用操作链表的临时方法
        second_adapter.notifyItemRemoved(position);
    }
    public void addOrclerLike(final HashMap<String,Object> map)
    {//TODO 添加一个喜欢&删除一个喜欢
        User user=new User();
        if (AsyncTasks!=null)
            for (MyAsycTask mTask : AsyncTasks)
            {//取消所有正在进行的异步
                mTask.cancel(false);
            }

        MyAsycTask myAsycTask=new MyAsycTask(map);
       AsyncTasks.add(myAsycTask);
        myAsycTask.execute("http://tp.newteo.com/user/favorite?vid="
                +map.get("vid").toString()+"&token="+user.token);

    }
    public void getVideos()
    {//TODO 调用Asynstask启动线程加载数据
        HashMap<String,Object> map=new HashMap<>();
        map.put("handler",mHandler);
        map.put("context",getContext());
        map.put("method","get");
        map.put("what",0);
        if (AsyncTasks!=null)
        for (MyAsycTask mTask : AsyncTasks)
        {//取消所有正在进行的异步
            mTask.cancel(false);
        }
        MyAsycTask myAsycTask=new MyAsycTask(map);
     int total=second_adapter.getItemCount()/5;
        //通过总数处以6来获得应该从哪一页开始获取视频
        Log.e("获取第",""+total+"页,"+countItems+"个视频");
        AsyncTasks.add(myAsycTask);
        myAsycTask.execute("http://tp.newteo.com/video/?per=3&page=1");//获取最多点击数的三个视频

        //启动线程联网后就返回
        countItems++;
    }

    private void getmustnewvideo()
    {
        HashMap<String,Object> map=new HashMap<>();
        map.put("handler",mHandler);
        map.put("context",getContext());
        map.put("method","get");
        map.put("what",0);
        if (AsyncTasks!=null)
            for (MyAsycTask mTask : AsyncTasks)
            {//取消所有正在进行的异步
                mTask.cancel(false);
            }
        MyAsycTask myAsycTask=new MyAsycTask(map);
        AsyncTasks.add(myAsycTask);
        myAsycTask.execute("http://tp.newteo.com/video/sort/new?channel="+"=5&page="+countItems);//获取最多点击数的三个视频

    }
    public void AddData(ArrayList<HashMap<String,Object>> Datalist)
    {

      //  HashMap<String ,Object> map=new HashMap<>();
       // map.put("title",Datalist.get(0).get("title").toString());
       // lists.add(map);
        for (int i = 0; i <Datalist.size() ; i++) {
            Log.e("DatalistSize",":"+Datalist.size());
            //将视频条目添加到集合类
            Datalist.get(i).put("VideoList",listVideoUtil);
            if (lists.size()!=2)//上传视频后会马上清空lists然后事先加上
            lists.clear();
            HashMap<String,Object> map1=new HashMap<>();
            map1.put("layout",1);
            map1.put("text","大家都在搜");
            lists.add(map1);
            lists.addAll(Datalist);
            listVideoUtil = new ListVideoUtil(getContext());
            // second_adapter.setListVideoUtil(listVideoUtil);
            listVideoUtil.setFullViewContainer(videoFullContainer);
            listVideoUtil.setHideStatusBar(true);

        }
        second_adapter.notifyDataSetChanged();
    }

    public void AddoneData(final HashMap<String,Object> map1)
    {

        HashMap<String,Object> map=new HashMap<>();
        User user=new User();

        map.put("vid_url", map1.get("path"));
        map.put("title", "FirstVideos");
        map.put("layout", 0);

        map.put("nickname", map1.get("title"));
        map.put("comment_number", 0);
        map.put("like_number", 0);
        map.put("view_number", 0);
        map.put("head_pic","null");
        map.put("tag", String.valueOf(System.currentTimeMillis()));
      //  mList.add(2,"插入");
        lists.add(3,map);
        second_adapter.notifyItemInserted(3);
        second_adapter.notifyItemRangeChanged(2,lists.size()-3);
        user.wait_UpLoad.clear();
        user.wait_UpLoad=null;
    }
    public void getnewlist()
    {

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
            if (map.get("method").toString().equals("get")) {
                get_http_asycTask = new Get_Http_AsycTask();
                get_http_asycTask.gethttp(params[0], map);//get方法链接http
            }
            if (map.get("method").toString().equals("post"))
            {
                Post_Http post_http=new Post_Http(map);
                post_http.Post_Http(params[0],(okhttp3.RequestBody) map.get("body"));
            }
            return null;


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }

}
