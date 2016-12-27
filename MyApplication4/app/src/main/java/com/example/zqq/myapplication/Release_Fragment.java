package com.example.zqq.myapplication;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by zqq on 16-12-26.
 */
public class Release_Fragment extends Fragment {
    public static Release_Fragment newInstance(String param1) {
        Release_Fragment fragment = new Release_Fragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_pager_layout, container, false);
        Bundle bundle = getArguments();
        String agrs1 = bundle.getString("agrs1");
        return view;
    }
}
