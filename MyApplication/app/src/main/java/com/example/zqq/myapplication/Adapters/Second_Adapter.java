package com.example.zqq.myapplication.Adapters;
/*
    由MyrecyviewAdapter稍加修改的一个RecyclerView的专用适配器
 */

import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.support.v7.widget.*;
import android.support.v7.widget.RecyclerView.OnScrollListener;
import android.util.Log;
import android.view.*;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.classify_Fragments.Fragment_First;
import com.shuyu.gsyvideoplayer.utils.ListVideoUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

import static android.support.v7.widget.RecyclerView.*;

public class Second_Adapter extends RecyclerView.Adapter<MyViewHolder> {
    private ArrayList<HashMap<String, Object>> lists;
    private Context context;
    private List<Object> alllist;
    ListVideoUtil listVideoUtil;
    MyViewHolder holder = null;
    Fragment_First f;
    private List<String> data = new ArrayList<>();

    private List<Integer> counttype = new ArrayList<>();

    int[] layout = {R.layout.home_item_layout};//,R.layout.infodetail_of_item_title_three_buttons, R.layout.paid_item};
    private OnItemClickListener mListener;

    public void setListVideoUtil(final ListVideoUtil listVideoUtil) {
        this.listVideoUtil=listVideoUtil;
    }


    /**
     * 点击事件的接口
     */
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);//点击

        void onItemLongClickListener(View view, int position);//长按
    }

    private OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    /**
     * 构造函数
     *
     * @param
     * @param
     */


    public Second_Adapter(Context context, ArrayList<HashMap<String, Object>> lists, Fragment_First f,ListVideoUtil l) {
        this.context = context;
        this.lists = lists;
        this.f=f;
        this.listVideoUtil=l;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        //从getitemviewtype中获得了布局
        view = LayoutInflater.from(context).inflate(layout[viewType], parent, false);
        MyViewHolder holder = new MyViewHolder(context, view,f);
        return holder;
        //由position也就是位置硬加载布局
    }


    @Override
    public int getItemViewType(int positon) {
        return (int) lists.get(positon).get("layout");
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数

        holder.itemView.setLayoutParams(params);//把params设置给item布局

        //通过返回的viewtype获得硬性布局
        switch ((int) lists.get(position).get("layout")) {

            case 0:
                 holder.nickname.setText(lists.get(position).get("nickname").toString());
                holder.plays_t.setText(lists.get(position).get("view_number").toString());
                holder.likes_t.setText(lists.get(position).get("like_number").toString());
                //    holder.infoDetails_title_tv.setText(lists.get(position).get("name").toString());
                //    holder.infoDetails_relativelayout.setTag(lists.get(position).get("vdoPhotourl").toString());
                //设置内容背景图
                holder.setListVideoUtil(listVideoUtil);
                holder.onBind(position, lists.get(position)/*.get("vid_url").toString()*/, holder, this);
                if (mListener != null) {//如果设置了监听那么它就不为空，然后回调相应的方法
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                            mListener.onItemClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                        }
                    });
                    holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                        @Override
                        public boolean onLongClick(View v) {
                            int pos = holder.getLayoutPosition();//得到当前点击item的位置pos
                            mListener.onItemLongClickListener(holder.itemView, pos);//把事件交给我们实现的接口那里处理
                            return true;
                        }
                    });
                }
                break;


        }


    }


    @Override
    public int getItemCount() {
        Log.e("listsSize",":"+lists.size());
        return lists.size();
    }

}//myrecleradapter


