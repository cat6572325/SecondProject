package com.example.zqq.myapplication;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaMetadataRetriever;
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

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


public class Video_Data_ extends Fragment{
    View view;
    ImageView sure_Upload,video_edit_img,video_edit_back;
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
        video_edit_img=(ImageView)view.findViewById(R.id.video_edit_img);

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
    public void setbitmap(String file)
    {
        Bitmap bitmap=getBitmap(file);
        if (bitmap!=null)
        {
            Drawable drawable = new BitmapDrawable(bitmap);

            video_edit_img.setBackground(drawable);//设置截图
        }
    }
    private Bitmap getBitmap(String file)
    {
        String str=file.substring(0,file.lastIndexOf(".")-1);
        File f=new File(str+((int)(1+Math.random()*(10-1+1)))+".png");
        //增加随机符和后缀
        Log.e("file",file);
        //获得视频截图后缩小
        Bitmap bitmap=resizeImage(getVideoThumb(file),140,145);

        bitmap2File(bitmap,f);//保存文件


        return bitmap;
    }
    /**
     * Bitmap保存成File
     *
     *
     *
     *
     */
    public static String bitmap2File(Bitmap bitmap,File f) {
        //File f = new File(Environment.getExternalStorageDirectory() + name + ".jpg");
        if (f.exists()) f.delete();
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (IOException e) {
            return null;
        }
        return f.getAbsolutePath();
    }
    /*
        *
        * @param path 视频文件的路径
        * @return Bitmap 返回获取的Bitmap
        */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        Log.e("file",path);
        media.setDataSource(path);
        return media.getFrameAtTime();
    }
    //使用Bitmap加Matrix来缩放
    public static Bitmap resizeImage(Bitmap bitmap, int w, int h)
    {
        Bitmap BitmapOrg = bitmap;
        int width = BitmapOrg.getWidth();
        int height = BitmapOrg.getHeight();
        int newWidth = w;
        int newHeight = h;

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);
        // if you want to rotate the Bitmap
        // matrix.postRotate(45);
        Bitmap resizedBitmap = Bitmap.createBitmap(BitmapOrg, 0, 0, width,
                height, matrix, true);
        return resizedBitmap;
    }

}
