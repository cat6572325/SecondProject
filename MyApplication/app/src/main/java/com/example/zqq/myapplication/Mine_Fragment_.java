package com.example.zqq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.Third_class.Fans_;
import com.example.zqq.myapplication.Third_class.Follow_;
import com.example.zqq.myapplication.Users.User;
import com.example.zqq.myapplication.Utils.UpLoad_LruCache;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by zqq on 17-1-4.
 */
public class Mine_Fragment_ extends Fragment {
    View v;
    RecyclerView mine_recycler;
    Mine_Recycler_Adapter mAdapter;
    RelativeLayout re1,re2,re3;
    TextView nickname;
    ArrayList<HashMap<String,Object>> maps=new ArrayList<>();
    ImageView  head_img;
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
        nickname=(TextView)v.findViewById(R.id.nickname_Mine);

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

        mine_recycler.setLayoutManager(gridLayoutManager);

        Invatation();
        mine_recycler.setAdapter(mAdapter);
        //条目点击事件
        mAdapter.setOnClickListener(new Mine_Recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
                switch (position)
                {
                    case 0:
                        //我的订阅
                        break;

                    case 1:
                        //喜欢的
                        break;
                    case 2:
                        //播放历史
                        startActivity(new Intent(getActivity(),History_.class));
                        break;
                    case 3:
                        //兴趣选择
                        break;

                    case 4:
                        //反馈
                        break;

                    case 5:
                        //版本检测
                        break;

                    case 6:
                        //预留
                        break;
                }
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
       User user=new User();
        if (user.nickname!=null)
        {
            nickname.setText(user.nickname);
            if (user.lruCache==null)
                user.lruCache=new UpLoad_LruCache();
            HashMap<String,Object> map=new HashMap<>();
            map.put("context",getContext());
            head_img.setTag(user.headurl);
            user.lruCache.loadImageViewurl(user.headurl,head_img,map);
        }
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
    String[] titles={"我的订阅","我喜欢的","播放历史","兴趣选择","反馈","版本检测"};
    int[] icons={R.mipmap.subscription
    ,R.mipmap.liked
    ,R.mipmap.history
    ,R.mipmap.select
    ,R.mipmap.feedback
    ,R.mipmap.mobile};
    for (int i = 0; i <6 ; i++) {
        HashMap<String,Object> map=new HashMap<>();
        map.put("layout",0);
        map.put("context",getContext());
        map.put("title",titles[i]);
        map.put("icon_id",icons[i]);
        maps.add(map);
    }
    mAdapter.notifyDataSetChanged();

}

}
