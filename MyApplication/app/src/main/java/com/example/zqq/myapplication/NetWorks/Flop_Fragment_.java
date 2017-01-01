package com.example.zqq.myapplication.NetWorks;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.zqq.myapplication.R;

public class Flop_Fragment_ extends Fragment implements View.OnTouchListener {
    View v;
    Button touch1,touch2;
    ImageView touch3;
   // private int _xDelta;
   // private int _yDelta;
    private float x,y,top1=0,bottom1=0;//保存控件原始位置
    private float width,height;//屏幕宽高
    int screenWidth;
    int screenHeight;
    int lastX;
    int lastY;
    FrameLayout frameLayout1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.flop_fragment_layout, container, false);
initView(v);
        return v;
    }
    private void initView(View  view)
    {
        touch1=(Button)view.findViewById(R.id.touch1);
        touch2=(Button)view.findViewById(R.id.touch2);
        touch3=(ImageView) view.findViewById(R.id.touch3);
        frameLayout1=(FrameLayout)view.findViewById(R.id.framelayout_1);
       // RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
       //         150, 50);
       // layoutParams.leftMargin = 50;
       // layoutParams.topMargin = 50;
       // layoutParams.bottomMargin = -250;
       // layoutParams.rightMargin = -250;
     //   touch3.setLayoutParams(layoutParams);
        x=touch3.getX();
        y=touch3.getY();
        top1=(int)touch3.getTop();
        bottom1=touch3.getBottom();
        WindowManager wm = (WindowManager) getContext()
               .getSystemService(Context.WINDOW_SERVICE);

         width= wm.getDefaultDisplay().getWidth();
        int height = wm.getDefaultDisplay().getHeight();
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        screenHeight = dm.heightPixels - 50;

     //   Log.e(String.valueOf(width),"sss");
        touch3.setOnTouchListener(this);



    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int action=event.getAction();
        Log.i("@@@@@@", "Touch:"+action);
        //Toast.makeText(DraftTest.this, "λ�ã�"+x+","+y, Toast.LENGTH_SHORT).show();
        switch(action){
            case MotionEvent.ACTION_DOWN:
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                break;
            /**
             * layout(l,t,r,b)
             * l  Left position, relative to parent
             t  Top position, relative to parent
             r  Right position, relative to parent
             b  Bottom position, relative to parent
             * */
            case MotionEvent.ACTION_MOVE:
                int dx =(int)event.getRawX() - lastX;
                int dy =(int)event.getRawY() - lastY;

                int left = v.getLeft() + dx;
                int top = v.getTop() + dy;
                int right = v.getRight() + dx;
                int bottom = v.getBottom() + dy;
                if(left < 0){
                    left = 0;
                    right = left + v.getWidth();
                }
                if(right > screenWidth){
                    right = screenWidth;
                    left = right - v.getWidth();
                }
                if(top < 0){
                    top = 0;
                    bottom = top + v.getHeight();
                }
                if(bottom > screenHeight){
                    bottom = screenHeight;
                    top = bottom - v.getHeight();
                }
                v.layout(left, top, right, bottom);
                Log.i("@@@@@@", "position��" + left +", " + top + ", " + right + ", " + bottom);
                lastX = (int) event.getRawX();
                lastY = (int) event.getRawY();
                Log.e("x",String.valueOf(lastX));
                Log.e("y",String.valueOf(lastY));
                break;
            case MotionEvent.ACTION_UP:
                if (lastX>(width-50))
                {//右边
                    v.layout((int)x,(int)top1,(int)y,(int)bottom1);

                    frameLayout1.invalidate();
                }else
                {
                    if (lastX<(width+50))
                    {//左边

                    }
                }
                break;
        }
        return false;

}
}
