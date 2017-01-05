package com.example.zqq.myapplication.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.zqq.myapplication.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;


public class Mine_Recycler_Adapter extends RecyclerView.Adapter<Recycler_Holder>  {

    Recycler_Holder holder;
    private OnItemClickListener mListener;
    Context context;
    ArrayList<HashMap<String,Object>>  maps;
    int[] layout = {R.layout.mine_recyclerview_item_layout,R.layout.pop_recycler_item_layout,R.layout.history_recyclerview_item_layout,R.layout.nothing_item_layout};
    public Mine_Recycler_Adapter(ArrayList<HashMap<String,Object>> maps)
    {
        this.maps=maps;
    }

    @Override
    public Recycler_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        context=null;
        context=(Context)maps.get(0).get("context");
        view = LayoutInflater.from(context).inflate(layout[viewType], parent, false);
      holder = new Recycler_Holder(view);

        return holder;

    }
    public interface OnItemClickListener {
        void onItemClickListener(View view, int position);//点击

        void onItemLongClickListener(View view, int position);//长按
    }
    public void setOnClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(final Recycler_Holder holder, int position) {
        ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();//得到item的LayoutParams布局参数

        holder.itemView.setLayoutParams(params);//把params设置给item布局

        switch (getItemViewType(position))
        {

            case 0:
                //TODO 我的信息面板
                holder.title.setText(maps.get(position).get("title").toString());
                holder.item_icon.setBackgroundResource((int)maps.get(position).get("icon_id"));

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
            case 1:
                //TODO 下拉选项表格
                holder.popwindow_recycler_item_b.setText(maps.get(position).get("title").toString());
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
            case 2:
                //历史列表

                break;
            case 3:
                //无内容item

                break;
        }

    }


    @Override
    public int getItemCount() {
        return maps.size();
    }

    @Override
    public int getItemViewType(int position) {
        return (int)maps.get(position).get("layout");
    }
}
