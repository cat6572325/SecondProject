package com.example.zqq.myapplication.classify_Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.zqq.myapplication.R;

import java.util.ArrayList;

/**
 * Created by zqq on 16-12-27.
 */
public class Fragment_third  extends Fragment {
    private View v;

    public static ArrayList<String> urls=new ArrayList<>();//截图，头像等图片的url集合
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.h_third, container, false);
        //   Bundle bundle = getArguments();
        // String agrs1 = bundle.getString("agrs1");

        //  tv.setText(agrs1);
        return v;
    }

}
