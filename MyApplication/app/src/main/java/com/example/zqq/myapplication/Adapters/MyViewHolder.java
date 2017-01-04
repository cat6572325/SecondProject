package com.example.zqq.myapplication.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.classify_Fragments.Fragment_First;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

class MyViewHolder extends RecyclerView.ViewHolder {

    //Home_Fragment
    public final static String TAG = "RecyclerView2List";
    FrameLayout frameLayout;
    ImageView btn;
    ImageView imageView;
    TextView plays_t,likes_t,comments_t;
    TextView nickname;
    ListVideoUtil listVideoUtil;
    RelativeLayout wannaHideOfLayout;

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
        imageView=new ImageView(context);
        this.fragment_first=fragment_first;
    }

    public void onBind(final int position, final String url, final MyViewHolder myViewHolder, final Second_Adapter second_adapter) {

        //增加封面
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         imageView.setImageResource(R.mipmap.ic_launcher);
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
                fragment_first.listVideoUtil.startPlay("http://"+url);
            }
        });
    }



    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil=listVideoUtil;
    }//添加视频集合

}