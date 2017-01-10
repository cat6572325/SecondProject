package com.example.zqq.myapplication.Utils;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ImageView;

import com.example.zqq.myapplication.MainActivity;
import com.example.zqq.myapplication.R;
import com.example.zqq.myapplication.Round_Video_;

public class WatcherListener implements TextWatcher {
    Activity context;
    ImageView img;
    public WatcherListener(Activity activity, ImageView img) {
        this.context = activity;
        this.img=img;
    }
    public void afterTextChanged(Editable s) {


    }
    @Override
    public void beforeTextChanged
            (CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged
            (CharSequence s, int start, int before, int count) {
        int a = context.getCurrentFocus().getId();
        //当前焦点
        switch (a)
        {
            case R.id.viddeo_title:



                if (s.length() !=0) {
                    img.setImageResource(R.mipmap.button_ok);
                    img.setClickable(true);

                }else
                {
                    img.setClickable(false);
                    img.setImageResource(R.mipmap.button);
                break;

        }
    }////////if input


}
}