package com.example.zqq.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by zqq on 16-12-30.
 */
public class Release_Fragment extends Fragment {
    View v;
    public Release_Fragment()
    {

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.home_pager, container, false);
        //   Bundle bundle = getArguments();
        // String agrs1 = bundle.getString("agrs1");
          return v;
    }
    public static Release_Fragment newInstance(String param1) {
        Release_Fragment fragment = new Release_Fragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }




}
