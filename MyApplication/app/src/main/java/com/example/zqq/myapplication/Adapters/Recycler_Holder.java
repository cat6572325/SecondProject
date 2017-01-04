package com.example.zqq.myapplication.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.zqq.myapplication.R;

import java.util.HashMap;

/**
 * Created by zqq on 17-1-4.
 */
public class Recycler_Holder extends RecyclerView.ViewHolder {
    ImageView item_icon;
    TextView title;
    RelativeLayout relativeLayout;
    public Recycler_Holder(View itemView) {
        super(itemView);
        item_icon=(ImageView)itemView.findViewById(R.id.left_icon);
        title=(TextView)itemView.findViewById(R.id.item_title);
        relativeLayout=(RelativeLayout)itemView.findViewById(R.id.recy_item_rela);

    }
    public void setViewAndClick(int position, HashMap<String,Object> map)
    {

    }


}
