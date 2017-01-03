package com.example.zqq.myapplication;

import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

public class Video_Data_ extends Fragment{
    View view;
    ImageView sure_Upload;
    EditText editText;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.viddeo_data_layout, container, false);

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        sure_Upload=(ImageView)view.findViewById(R.id.sure_Upload);
        editText=(EditText)view.findViewById(R.id.viddeo_title);
        sure_Upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Round_Video_ mainActivity= (Round_Video_) getActivity();
                Message msg=new Message();
                msg.obj=editText.getText().toString();
                msg.what=0;
                mainActivity.mHandler.sendMessage(msg);
            }
        });
    }
}
