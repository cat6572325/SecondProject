package com.example.zqq.myapplication.classify_Fragments;

import android.graphics.Point;
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
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.Llisteners.SampleListener;
import com.example.zqq.myapplication.NetWorks.Get_Http_AsycTask;
import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.Users.User;
import com.shuyu.gsyvideoplayer.GSYVideoPlayer;
import com.shuyu.gsyvideoplayer.utils.CommonUtil;
import com.shuyu.gsyvideoplayer.utils.Debuger;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import jp.co.recruit_lifestyle.android.widget.WaveSwipeRefreshLayout;

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
            JSONObject jsonObject,jsonObject1;
            switch (msg.what) {
                case 0:
                    try {
                        User user=new User();
                        String json=(String)msg.obj;
                    Log.e("视频json",json);
                        jsonArray=new JSONArray(json);
                        for (int i = 0; i <jsonArray.length() ; i++) {
                            jsonObject= jsonArray.getJSONObject(i);//获取第i个视频
                            jsonObject1=jsonObject.getJSONObject("video_url");
                            HashMap<String,Object> map=new HashMap<>();
                            map.put("vid_url",jsonObject1.getString("vid_url"));
                            map.put("title","FirstVideos");
                            map.put("layout",0);
                            map.put("tag",String.valueOf(System.currentTimeMillis()));
                            if (user.all_video==null)
                                user.all_video=new ArrayList<>();
                            user.all_video.add(map);
                        }
                        AddData(user.all_video);


                     } catch (JSONException e) {
                        Log.e("转换json的时候",e.toString());
                    }
                    break;
            }
        }
    };
    int lastVisibleItem;
    int firstVisibleItem;
    LinearLayoutManager linearLayoutManager;
    RecyclerView home_rec;
    View v;
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>();
    private Second_Adapter second_adapter;
    //第三方刷新控件
    WaveSwipeRefreshLayout mWaveSwipeRefreshLayout;
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

        second_adapter = new Second_Adapter(getActivity(), lists,this,listVideoUtil);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 1);

        home_rec.setLayoutManager(gridLayoutManager);

        home_rec.setAdapter(second_adapter);
        //条目点击事件
        second_adapter.setOnClickListener(new Second_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });
        mWaveSwipeRefreshLayout.setOnRefreshListener(new WaveSwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Do work to refresh the list here.
                new Task().execute();
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
                if (listVideoUtil.getPlayPosition() >= 0 /*&& listVideoUtil.getPlayTAG().equals(RecyclerItemViewHolder.TAG)*/) {
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
                //大于0说明有播放,//对应的播放列表TAG
                if (listVideoUtil.getPlayPosition() >= 0 /*&& listVideoUtil.getPlayTAG().equals(ListVideoAdapter.TAG)*/) {
                    //当前播放的位置
                    int position = listVideoUtil.getPlayPosition();
                    //不可视的是时候
                    if ((position < firstVisibleItem || position > lastVisibleItem)) {
                        //释放掉视频
                        listVideoUtil.releaseVideoPlayer();
                        second_adapter.notifyDataSetChanged();
                    }
                }

            }
        });

    }
        private class Task extends AsyncTask<Void, Void, String[]> {
               @Override
            protected String[] doInBackground(Void... params) {
                return new String[0];
            }

            @Override protected void onPostExecute(String[] result) {
                // Call setRefreshing(false) when the list has been refreshed.
                mWaveSwipeRefreshLayout.setRefreshing(false);
                getVideos();
                super.onPostExecute(result);
            }
        }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }

    private void getVideos()
    {//TODO 调用Asynstask启动线程加载数据
        HashMap<String,Object> map=new HashMap<>();
        map.put("handler",mHandler);
        MyAsycTask myAsycTask=new MyAsycTask(map);

     int total=second_adapter.getItemCount()/6;
        if (total<1)
            total=1;
        myAsycTask.execute("http://192.168.1.109:3333/video/?per=1&page="+(total+1));
        //启动线程联网后就返回
    }
    public void AddData(ArrayList<HashMap<String,Object>> Datalist)
    {

      //  HashMap<String ,Object> map=new HashMap<>();
       // map.put("title",Datalist.get(0).get("title").toString());
       // lists.add(map);
        for (int i = 0; i <Datalist.size() ; i++) {
            //将视频条目添加到集合类
            Datalist.get(i).put("VideoList",listVideoUtil);
            lists.addAll(Datalist);

            listVideoUtil = new ListVideoUtil(getContext());
            // second_adapter.setListVideoUtil(listVideoUtil);
            listVideoUtil.setFullViewContainer(videoFullContainer);
            listVideoUtil.setHideStatusBar(true);

        }
        second_adapter.notifyDataSetChanged();
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
            get_http_asycTask=new Get_Http_AsycTask();
            get_http_asycTask.gethttp(params[0],map);//get方法链接http
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

        }

    }

}
