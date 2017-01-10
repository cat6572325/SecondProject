package com.example.zqq.myapplication.Adapters;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Customs.Custom_shape_img;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.Users.User;
import com.example.zqq.myapplication.classify_Fragments.Fragment_First;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.FormBody;
import okhttp3.RequestBody;

class MyViewHolder extends RecyclerView.ViewHolder {
    private Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
        }
    };

    //Home_Fragment
    public final static String TAG = "RecyclerView2List";
    FrameLayout frameLayout;
    ImageView btn;
    LinearLayout comment_img,like_img;
    ImageView imageView,like_icon;
    Custom_shape_img head_img1=null;
    TextView plays_t,likes_t,comments_t,search_t,home_tile;
    TextView nickname;
    ListVideoUtil listVideoUtil;
    RelativeLayout wannaHideOfLayout;

    LinearLayout more_l;

    Fragment_First fragment_first;
    public MyViewHolder(Context context,View itemView,Fragment_First fragment_first) {
        super(itemView);
        frameLayout=(FrameLayout)itemView.findViewById(R.id.list_item_container);
        btn=(ImageView)itemView.findViewById(R.id.list_item_btn);//点击开始播放
        wannaHideOfLayout=(RelativeLayout)itemView.findViewById(R.id.framelayout_above_layout);
        plays_t=(TextView)itemView.findViewById(R.id.plays_t);
        likes_t=(TextView)itemView.findViewById(R.id.likes_t);
        comments_t=(TextView)itemView.findViewById(R.id.comments_t);
        nickname=(TextView) itemView.findViewById(R.id.nickname);
        search_t=(TextView)itemView.findViewById(R.id.search_t);
        more_l=(LinearLayout)itemView.findViewById(R.id.home_more);
        like_img=(LinearLayout)itemView.findViewById(R.id.like_l);
        head_img1=(Custom_shape_img)itemView.findViewById(R.id.head_img1);
        comment_img=(LinearLayout)itemView.findViewById(R.id.comment_l);
        home_tile=(TextView)itemView.findViewById(R.id.title_home);
        like_icon=(ImageView)itemView.findViewById(R.id.like_img);

        imageView=new ImageView(context);
        this.fragment_first=fragment_first;
    }

    public void onBind(final int position, final HashMap<String,Object> map, final MyViewHolder myViewHolder, final Second_Adapter second_adapter) {
        //增加封面
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        if (position==0|position==2|position==3|position==6|position==8) {
            imageView.setImageResource(R.drawable.a);
        }else
        {
            if (position==1|position==4|position==5|position==7)
            imageView.setImageResource(R.drawable.b);
            else
                imageView.setImageResource(R.drawable.c);
        }


       fragment_first.listVideoUtil.addVideoPlayer(position, imageView, TAG
                , myViewHolder.frameLayout, btn);

        myViewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    wannaHideOfLayout.setVisibility(View.INVISIBLE);
                    //   getRecyclerBaseAdapter().notifyDataSetChanged();

                    second_adapter.notifyDataSetChanged();
                    //listVideoUtil.setLoop(true);
                    fragment_first.listVideoUtil.setPlayPositionAndTag(position, TAG);
                    // final String url = url;//"http://baobab.wdjcdn.com/14564977406580.mp4";
                    //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                    fragment_first.listVideoUtil.startPlay(map.get("vid_url").toString());

                    User user = new User();
                    if (user.historys == null)
                        user.historys = new ArrayList<HashMap<String, Object>>();
                    user.historys.add(map);//播放历史

                MyAsynctask myAsynctask=new MyAsynctask(map);
                myAsynctask.execute("s");

            }
        });
    }

private class MyAsynctask extends AsyncTask<String,Void,Void>
{
    HashMap<String,Object> map;
    public MyAsynctask(HashMap<String,Object> map)
    {
        this.map=map;
    }

    @Override
    protected Void doInBackground(String... params) {
        RequestBody formBody = new FormBody.Builder()
                .build();
        User user=new User();
        if (map.get("_id")!=null) {
            map.put("what", 0);
            map.put("handler", mHandler);
            Post_Http post_http = new Post_Http(map);
            post_http.Post_Http("http://tp.newteo.com/user/history/?vid=" + map.get("_id").toString() + "&token=" + user.token, formBody);
        }
        return null;
    }
}

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil=listVideoUtil;
    }//添加视频集合

}