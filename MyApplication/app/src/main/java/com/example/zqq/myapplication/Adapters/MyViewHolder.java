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
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

class MyViewHolder extends RecyclerView.ViewHolder {

    //Home_Fragment
    public final static String TAG = "RecyclerView2List";
    FrameLayout frameLayout;
    ImageView btn;
    ImageView imageView;
    ListVideoUtil listVideoUtil;

    public MyViewHolder(Context context,View itemView) {
        super(itemView);
        frameLayout=(FrameLayout)itemView.findViewById(R.id.list_item_container);
        btn=(ImageView)itemView.findViewById(R.id.list_item_btn);//点击开始播放
        imageView=new ImageView(context);

    }

    public void onBind(final int position, String str, final MyViewHolder myViewHolder, final Second_Adapter second_adapter) {

        //增加封面
         imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
         imageView.setImageResource(R.mipmap.ic_launcher);
        listVideoUtil.addVideoPlayer(position, imageView, TAG
                , myViewHolder.frameLayout, btn);

        myViewHolder.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //   getRecyclerBaseAdapter().notifyDataSetChanged();
                second_adapter.notifyDataSetChanged();
                //listVideoUtil.setLoop(true);
                listVideoUtil.setPlayPositionAndTag(position, TAG);
                final String url = "http://baobab.wdjcdn.com/14564977406580.mp4";
                //listVideoUtil.setCachePath(new File(FileUtils.getPath()));
                listVideoUtil.startPlay(url);
            }
        });
    }

    public void setListVideoUtil(ListVideoUtil listVideoUtil) {
        this.listVideoUtil=listVideoUtil;
    }//添加视频集合

}