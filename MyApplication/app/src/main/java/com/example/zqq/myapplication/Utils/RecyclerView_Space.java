package com.example.zqq.myapplication.Utils;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class RecyclerView_Space extends RecyclerView.ItemDecoration{

    private int space,count;
    //间距（int）

    private int spanCount;
    private int spacing;
    private boolean includeEdge;

    public RecyclerView_Space(int spanCount, int spacing, boolean includeEdge) {
        this.spanCount = spanCount;
        this.spacing = spacing;
        this.includeEdge = includeEdge;
    }


  /*   @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {

       int pos = parent.getChildLayoutPosition(view);
        if(parent.getChildPosition(view) != 0)
            outRect.top = space;
        // 设置左右间距
        outRect.set(space / 2, 0, space / 2, 0);

// 从第二行开始 top = mSpace
        if (pos >= count) {
            outRect.top = space;
        } else {
            outRect.top = 0;
        }
    }*/
}