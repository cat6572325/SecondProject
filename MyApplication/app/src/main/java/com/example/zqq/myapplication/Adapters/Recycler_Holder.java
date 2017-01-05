package com.example.zqq.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zqq.myapplication.R;

import java.util.HashMap;

/**
 * Created by zqq on 17-1-4.
 */
public class Recycler_Holder extends RecyclerView.ViewHolder {
    ImageView item_icon,History_img;
    TextView title,History_title,History_peop,History_create_time;
    Button popwindow_recycler_item_b;
    RelativeLayout relativeLayout;
    public Recycler_Holder(View itemView) {
        super(itemView);
        item_icon=(ImageView)itemView.findViewById(R.id.left_icon);
        title=(TextView)itemView.findViewById(R.id.item_title);
        relativeLayout=(RelativeLayout)itemView.findViewById(R.id.recy_item_rela);
        popwindow_recycler_item_b=(Button) itemView.findViewById(R.id.popupwind_recyclerView_item_b);


        History_create_time=(TextView)itemView.findViewById(R.id.History_Create_time);
        History_peop=(TextView)itemView.findViewById(R.id.History_peop);
        History_title=(TextView)itemView.findViewById(R.id.History_title);
        History_img=(ImageView)itemView.findViewById(R.id.History_img);

    }
    public void setViewAndClick(int position, HashMap<String,Object> map)
    {

    }


}
