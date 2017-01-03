package com.example.zqq.myapplication;

import android.annotation.TargetApi;
import android.app.*;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.*;
import android.hardware.*;
import android.hardware.Camera.Size;
import android.media.*;
import android.media.MediaRecorder.*;
import android.media.audiofx.EnvironmentalReverb;
import android.os.*;

import android.provider.MediaStore;
import android.support.v4.app.*;
import android.support.v4.app.FragmentManager;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v7.app.*;
import android.support.v7.app.AlertDialog;
import android.system.ErrnoException;
import android.text.Layout;
import android.util.DisplayMetrics;

import android.util.Log;
import android.util.TimeUtils;
import android.view.*;
import android.view.View.*;
import android.webkit.WebView;
import android.widget.*;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.security.Policy;
import java.util.*;
import java.util.concurrent.TimeUnit;

import android.hardware.Camera;
import android.support.design.widget.*;

import com.example.zqq.myapplication.FileUtils.File_with_;
import com.example.zqq.myapplication.NetWorks.Post_Http;
import com.example.zqq.myapplication.Users.User;
import com.example.zqq.myapplication.Utils.CameraUtils;
import com.okhttplib.HttpInfo;
import com.okhttplib.OkHttpUtil;
import com.okhttplib.OkHttpUtilInterface;
import com.okhttplib.annotation.CacheLevel;
import com.okhttplib.callback.ProgressCallback;
import java.io.IOException;
import java.util.List;


import android.app.Activity;
import android.content.Context;

import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.hardware.Camera.Size;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.view.SurfaceHolder.Callback;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Round_Video_ extends FragmentActivity

{
    public Handler mHandler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            Bundle bundle = msg.getData();
            switch (msg.what) {
                case 0:
                    upload(msg.obj.toString());
                    break;


                case 1:

                    ResetCamera();
                    break;


                case 3:

                    if (bundle != null)
                        if (bundle.getString("?").equals("已保存")) {
                            dialog.cancel();
                            finish();
                        }
                    break;

            }
        }
    };

    Video_Data_ video_data_;
    User user = new User();
   static int message = 0;
    EditText title_e;
    private Button startButton, stopButton, playButton;
    private SurfaceView mSurfaceView;
    private boolean isRecording;
    private MediaRecorder mediaRecorder;
    private Camera camera;
    private SurfaceHolder surfaceHolder1;
    public RelativeLayout HideLayout, bottom_hide_layout;
    static int flag = 0, recordarl = 0, turncamera = 0;
    int count = 0;
    Timer mTimer = new Timer();
    TimeUnit t;
    File file = new File("/sdcard/RoundVideo/RoudVideos" + randomProdoction() + ".3gp");
    File_with_ file_with;
    String COMMA_PATTERN = ",";
    TextView top_time;
    int minute, min;
    Size size;
    task Prog_task;
    ImageView sound, turnC, round_back_img, rounding_time_img, round_delete, round_upload, round_edit;
    AlertDialog.Builder waitdialog;
    AlertDialog dialog;
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);

        setContentView(R.layout.round_layout);
        mSurfaceView = (SurfaceView) findViewById(R.id.camera_surfaceview);
        fragmentManager = getSupportFragmentManager();


        // 声明Surface不维护自己的缓冲区，针对Android3.0以下设备支持
        mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        initView();

        startButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v)

            {////点击开始录像
                if (flag == 0) {

                    if (camera != null)
                       // sound.setVisibility(View.INVISIBLE);
              //      turnC.setVisibility(View.INVISIBLE);

                    if (camera != null) {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    }
                    start();
                    flag = 1;


                } else {//点击结束录像
                    stop();
                    flag = 0;
                }

            }
        });

    }//oncreate()

    private void setDefaultFragment()
    {

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                //firstLayout.setBackgroundColor(gray);
              if (video_data_ == null) {
                  video_data_ = new Video_Data_();
                  fragmentTransaction.add(R.id.Round_Framelayout, video_data_);
                  fragmentTransaction.commit(); // 提交
              }
    }
    public void initView() {
        top_time = (TextView) this.findViewById(R.id.RoundTop_time);
        turnC = (ImageView) this.findViewById(R.id.Round_turn);
        startButton=(Button)this.findViewById(R.id.start_round);
        round_back_img = (ImageView) this.findViewById(R.id.round_back);
        rounding_time_img = (ImageView) this.findViewById(R.id.rounding_time_img);
         round_back_img.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
       mHandler.sendEmptyMessage(1);


    }//initView
    //开始录制前设置不把声音录进去
    public void SetsoundState(View view)
    {
        if (recordarl==1) {
            recordarl = 0;
            //录音
            Toast.makeText(Round_Video_.this,"开启录音",Toast.LENGTH_SHORT).show();
        }else {
            recordarl=1;
            //关闭录音
            Toast.makeText(Round_Video_.this,"关闭录音",Toast.LENGTH_SHORT).show();
        }
    }

    //开始录制前设置不把声音录进去
    private String randomProdoction() {
        int random = (int) Math.random() * 10;
        String str = null;
        Random ran = new Random(System.currentTimeMillis());
        for (int i = 0; i < 20; i++) {

            random = ran.nextInt(10);
            str += String.valueOf(random);
            //数字加一个字母
            switch (random) {
                case 0:
                    str += "a";
                    break;

                case 1:
                    str += "b";
                    break;

                case 2:
                    str += "c";
                    break;

                case 3:
                    str += "e";
                    break;

                case 4:
                    str += "f";
                    break;

                case 5:
                    str += "g";
                    break;

                case 6:
                    str += "h";
                    break;

                case 7:
                    str += "i";
                    break;
            }

        }
        return str;
    }
    private void setWaitdialog()
    {
       // waitdialog=new android.support.v7.app.AlertDialog.Builder(Round_Video_.this,R.style.Dialog);
       // LayoutInflater inflater=getLayoutInflater();
       // final View layout=inflater.inflate(R.layout.login_wait_dialog,null);
      //  TextView textView=(TextView)layout.findViewById(R.id.text_dialog);

     //   textView.setText("加载中..");
      //  waitdialog.setView(layout);
      //  dialog=waitdialog.create();
      //  dialog.show();

    }


    class task extends TimerTask {
        public View v;
        int x, y;
        boolean b;
        int count;

        public task(int count) {
            this.count = count;
        }

        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {

                    min++;
                    if (min == 60) {
                        min = 0;
                        minute++;
                    }
                    if (min > 9) {
                        top_time.setText(minute + ":" + min);
                    } else {
                        top_time.setText(minute + ":0" + min);
                    }
                }

            });
        }

    }
    ;



    protected void start() {
        round_back_img.setClickable(false);
        try {

            if (file_with != null) {//如果先前已经申请过一个文件
                file_with.DeleteFile();
            }
            file_with = new File_with_();
            mediaRecorder = new MediaRecorder();// 创建mediarecorder对象

            if (turncamera == 0) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            }
            camera.setPreviewDisplay(mSurfaceView.getHolder());

            camera.setDisplayOrientation(90);

            camera.setPreviewDisplay(mSurfaceView.getHolder());
            updateCameraParameters();
            camera.unlock();
           // camera.startPreview();
            mediaRecorder.setCamera(camera);
            // 设置录制视频源为Camera(相机)
            if (recordarl == 0) {
                //等于0则设置录音
                mediaRecorder.setVideoSource(Camera.CameraInfo.CAMERA_FACING_BACK);
                //设置音频采集方式
                mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                //设置输出格式
                mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                //设置audio编码方式
            }
            mediaRecorder.setOrientationHint(90);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            //设置最大限时
            //mediaRecorder.setMaxDuration(60*1000);
            //录像旋转90度
            //mediaRecorder.setOrientationHint(90);
            // 设置录制完成后视频的封装格式THREE_GPP为3gp.MPEG_4为mp4
            //mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            // 设置录制的视频编码h263 h264
            mediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
            //设置高质量录制,改变码率
            mediaRecorder.setVideoEncodingBitRate(5 * 1024 * 1024);
            //设置视频录制的分辨率。必须放在设置编码和格式的后面，否则报错
            mediaRecorder.setVideoSize(1080, 720);
            // 设置录制的视频帧率。必须放在设置编码和格式的后面，否则报错
            mediaRecorder.setVideoFrameRate(20);
            // 设置视频文件输出的路径
            mediaRecorder.setOutputFile(file_with.TestFile(file).getPath());
            // 设置捕获视频图像的预览界面
            mediaRecorder.setPreviewDisplay(mSurfaceView.getHolder().getSurface());
            mediaRecorder.setOnErrorListener(new OnErrorListener() {
                @Override
                public void onError(MediaRecorder mr, int what, int extra) {
                    // 发生错误，停止录制
                    mediaRecorder.stop();
                    mediaRecorder.release();
                    mediaRecorder = null;
                    isRecording = false;
                    startButton.setEnabled(true);
                    stopButton.setEnabled(false);
                    playButton.setEnabled(false);
                }
            });
            // 准备、开始
            mediaRecorder.prepare();
            mediaRecorder.start();
            //startButton.setEnabled(false);
            //stopButton.setEnabled(true);
            isRecording = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void stop() {
       setDefaultFragment();
         //解除隐藏
        top_time.setText("录制完成");
        if (isRecording) {
            // 如果正在录制，停止并释放资源
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
            isRecording = false;
            //startButton.setEnabled(true);
            //stopButton.setEnabled(false);
            //	playButton.setEnabled(true);
            if (camera != null) {
                camera.release();
            }

        }
    }

    @Override
    protected void onDestroy() {
        if (isRecording) {
            mediaRecorder.stop();
            mediaRecorder.release();
            mediaRecorder = null;
        }
        if (camera != null) {
            camera.release();
        }
        super.onDestroy();
    }

    public void ResetCamera() {


        //  mSurfaceView.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        try {
            if (camera != null) {
                camera.stopPreview();
                camera.release();
                camera = null;
            }
            if (turncamera == 0) {
                camera = Camera.open(Camera.CameraInfo.CAMERA_FACING_BACK);
            } else {
                camera = Camera.open(CameraInfo.CAMERA_FACING_FRONT);
            }
            camera.setPreviewDisplay(mSurfaceView.getHolder());

            camera.setDisplayOrientation(90);
            camera.setPreviewDisplay(mSurfaceView.getHolder());
            updateCameraParameters();

            camera.startPreview();


            //	Size pictureS = MyCampara.getInstance().getPictureSize(pictureSizes, 800);
            //	mParams.setPictureSize(pictureS.width, pictureS.height);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("初始化摄像头时",e.toString());
        }
    }
    private void updateCameraParameters() {
        if (camera != null) {
            Camera.Parameters p = camera.getParameters();

            long time = new Date().getTime();
            p.setGpsTimestamp(time);

            //以下几行代码获得适合的预览尺寸
          //  Size pictureS = CameraUtils.getInstance().getPreviewSize(p.getSupportedPreviewSizes(), 700);
         Camera.Size sizes=getCloselyPreSize(mSurfaceView.getWidth(),mSurfaceView.getHeight(),p.getSupportedPreviewSizes());
            p.setPreviewSize(sizes.width, sizes.height);
            camera.setParameters(p);

          /*  Size pictureSize = findBestPictureSize(p);
            p.setPictureSize(pictureSize.width, pictureSize.height);

            // Set the preview frame aspect ratio according to the picture size.
            Size size = p.getPictureSize();
         //   PreviewFrameLayout frameLayout = (PreviewFrameLayout) findViewById(R.id.frame_layout);
        //    frameLayout.setAspectRatio((double) size.width / size.height);

            Size previewSize = findBestPreviewSize(p);
         //   p.setPreviewSize(previewSize.width,previewSize.height);

            camera.setParameters(p);

          //  int supportPreviewWidth = previewSize.width;
          //  int supportPreviewHeight = previewSize.height;

            int srcWidth = getScreenWH().widthPixels;
            int srcHeight = getScreenWH().heightPixels;

            int width = Math.min(srcWidth, srcHeight);
          //  int height = width * supportPreviewWidth / supportPreviewHeight ;
*/
           // mSurfaceView.setLayoutParams(new FrameLayout.LayoutParams(height, width));//
        }
    }
    /**
     * 通过对比得到与宽高比最接近的尺寸（如果有相同尺寸，优先选择）
     *
     * @param surfaceWidth
     *            需要被进行对比的原宽
     * @param surfaceHeight
     *            需要被进行对比的原高
     * @param preSizeList
     *            需要对比的预览尺寸列表
     * @return 得到与原宽高比例最接近的尺寸
     */
    protected Camera.Size getCloselyPreSize(int surfaceWidth, int surfaceHeight,
                                            List<Size> preSizeList) {

        int ReqTmpWidth;
        int ReqTmpHeight;
        // 当屏幕为垂直的时候需要把宽高值进行调换，保证宽大于高
     //   if (mIsPortrait) {
            ReqTmpWidth = surfaceHeight;
            ReqTmpHeight = surfaceWidth;
      /*  } else {
            ReqTmpWidth = surfaceWidth;
            ReqTmpHeight = surfaceHeight;
        }*/
        //先查找preview中是否存在与surfaceview相同宽高的尺寸
        for(Camera.Size size : preSizeList){
            if((size.width == ReqTmpWidth) && (size.height == ReqTmpHeight)){
                return size;
            }
        }

        // 得到与传入的宽高比最接近的size
        float reqRatio = ((float) ReqTmpWidth) / ReqTmpHeight;
        float curRatio, deltaRatio;
        float deltaRatioMin = Float.MAX_VALUE;
        Camera.Size retSize = null;
        for (Camera.Size size : preSizeList) {
            curRatio = ((float) size.width) / size.height;
            deltaRatio = Math.abs(reqRatio - curRatio);
            if (deltaRatio < deltaRatioMin) {
                deltaRatioMin = deltaRatio;
                retSize = size;
            }
        }

        return retSize;
    }


    //////////////////sucess Onclick
    public void Turncamera(View v) {
        if (camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
        //先停下当前摄像
        if (turncamera == 0) {
            turncamera = 1;
        } else {
            turncamera = 0;
        }
        ResetCamera();
        // ResetCamera();
        //再重新开始
       /* int cameraCount = 0;
        CameraInfo cameraInfo = new CameraInfo();
        cameraCount = Camera.getNumberOfCameras();//得到摄像头的个数
        for (int i = 0; i < cameraCount; i++) {
            Camera.getCameraInfo(i, cameraInfo);//得到每一个摄像头的信息
            if (turncamera == 0) {
                //现在是后置，变更为前置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        deal(camera);
                        camera.setPreviewDisplay(surfaceHolder1);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    camera.startPreview();//开始预览
                    turncamera = 1;
                    break;
                }
            } else {
                //现在是前置， 变更为后置
                if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {//代表摄像头的方位，CAMERA_FACING_FRONT前置      CAMERA_FACING_BACK后置
                    camera.stopPreview();//停掉原来摄像头的预览
                    camera.release();//释放资源
                    camera = null;//取消原来摄像头
                    camera = Camera.open(i);//打开当前选中的摄像头
                    try {
                        deal(camera);
                        camera.setPreviewDisplay(surfaceHolder1);//通过surfaceview显示取景画面
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    camera.startPreview();//开始预览
                    turncamera = 0;
                    break;
                }
            }
}*/
    }

    public void record(View v) {//是否录音
        if (recordarl == 0) {
            recordarl = 1;
        } else {
            recordarl = 0;
        }
    }

    public void onInvalitorProgress(int counttime) {
        int y = counttime;
    }



    public void upload(String title) {
        //TODO　上传按钮
        //跳转至首页上传
        Intent intent = this.getIntent();

        user=new User();
        HashMap<String,Object> map=new HashMap<>();
        map.put("title",title);
        map.put("path",file_with.GetFile().getPath());
        map.put("videourl","http://192.168.1.109:3333/video/push/:videoId?token=${token}");
        user.wait_UpLoad=map;
        this.setResult(0, intent);//返回页面1
        onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //
        // 按钮保存数据，未上传时按下返回键就会开始联网发送未上传视频的信息，发送保存成功后收到一个handler的message然后直接finish
        //大多需要的参数都在User的对象里了，一旦上传则清空
        //为保证点了开始录制的按钮后没有停止就想退出界面，在这里判断如果camera不为空，则释放
        if (camera != null) {
            camera=null;
        }
        finish();

    }

    public void edit(View v) {
        //TODO 编辑按钮


    }

    //////////////////sucess Onclick
    class task1 extends TimerTask {
        public View v;
        int x, y;
        boolean b;
        int count;

        public task1(int count) {
            this.count = count;

        }

        @Override
        public void run() {

            runOnUiThread(new Runnable() {      // UI thread
                @Override
                public void run() {
                    //  bar.OnProgressChanged(10);
                }
            });
        }
    }

    public class Mytest {
        public int test() {
            return 1000;
        }

    }

    protected DisplayMetrics getScreenWH() {
        DisplayMetrics dMetrics = new DisplayMetrics();
        dMetrics = this.getResources().getDisplayMetrics();
        return dMetrics;
    }


    //TODO 视频处理　　vvvvvvvvvv
    private void exactorMedia(String... data) {
        FileOutputStream videoOutputStream = null;
        FileOutputStream audioOutputStream = null;
        MediaExtractor mediaExtractor = null;
        try {
            //分离的视频文件
            File videoFile = new File(data[1]);
            //分离的音频文件
            File audioFile = new File(data[2]);
            videoOutputStream = new FileOutputStream(videoFile);
            audioOutputStream = new FileOutputStream(audioFile);
            //源文件
            mediaExtractor.setDataSource(data[0]);
            //信道总数
            int trackCount = mediaExtractor.getTrackCount();
            int audioTrackIndex = -1;
            int videoTrackIndex = -1;
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                String mineType = trackFormat.getString(MediaFormat.KEY_MIME);
                //视频信道
                if (mineType.startsWith("video/")) {
                    videoTrackIndex = i;
                }
                //音频信道
                if (mineType.startsWith("audio/")) {
                    audioTrackIndex = i;
                }
            }

            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            //切换到视频信道
            mediaExtractor.selectTrack(videoTrackIndex);
            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }
                //保存视频信道信息
                byte[] buffer = new byte[readSampleCount];
                byteBuffer.get(buffer);
                videoOutputStream.write(buffer);
                byteBuffer.clear();
                mediaExtractor.advance();
            }
            //切换到音频信道
            mediaExtractor.selectTrack(audioTrackIndex);
            while (true) {
                int readSampleCount = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleCount < 0) {
                    break;
                }
                //保存音频信息
                byte[] buffer = new byte[readSampleCount];
                byteBuffer.get(buffer);
                audioOutputStream.write(buffer);
                byteBuffer.clear();
                mediaExtractor.advance();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (IOError e1) {


        } catch (UnknownError e4) {

        } finally {
            mediaExtractor.release();
            try {
                videoOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressWarnings("WrongConstant")
    private void muxerAudio(String fromPath, String toPath) {
        MediaExtractor mediaExtractor = new MediaExtractor();
        int audioIndex = -1;
        try {
            mediaExtractor.setDataSource(fromPath);
            int trackCount = mediaExtractor.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor.getTrackFormat(i);
                if (trackFormat.getString(MediaFormat.KEY_MIME).startsWith("audio/")) {
                    audioIndex = i;
                }
            }
            mediaExtractor.selectTrack(audioIndex);
            MediaFormat trackFormat = mediaExtractor.getTrackFormat(audioIndex);
            MediaMuxer mediaMuxer = new MediaMuxer(toPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            int writeAudioIndex = mediaMuxer.addTrack(trackFormat);
            mediaMuxer.start();
            ByteBuffer byteBuffer = ByteBuffer.allocate(500 * 1024);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();

            long stampTime = 0;
            //获取帧之间的间隔时间
            {
                mediaExtractor.readSampleData(byteBuffer, 0);
                if (mediaExtractor.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC) {
                    mediaExtractor.advance();
                }
                mediaExtractor.readSampleData(byteBuffer, 0);
                long secondTime = mediaExtractor.getSampleTime();
                mediaExtractor.advance();
                mediaExtractor.readSampleData(byteBuffer, 0);
                long thirdTime = mediaExtractor.getSampleTime();
                stampTime = Math.abs(thirdTime - secondTime);
                ///     Log.e("fuck", stampTime + "");
            }

            mediaExtractor.unselectTrack(audioIndex);
            mediaExtractor.selectTrack(audioIndex);
            while (true) {
                int readSampleSize = mediaExtractor.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor.advance();

                bufferInfo.size = readSampleSize;
                bufferInfo.flags = mediaExtractor.getSampleFlags();
                bufferInfo.offset = 0;
                bufferInfo.presentationTimeUs += stampTime;

                mediaMuxer.writeSampleData(writeAudioIndex, byteBuffer, bufferInfo);
            }
            mediaMuxer.stop();
            mediaMuxer.release();
            mediaExtractor.release();
            // Log.e("fuck", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    @SuppressWarnings("WrongConstant")
    private void muxerMedia(String fromPath, String toPath) {
        MediaExtractor mediaExtractor1 = new MediaExtractor();
        int videoIndex = -1;
        try {
            mediaExtractor1.setDataSource(fromPath);
            int trackCount = mediaExtractor1.getTrackCount();
            for (int i = 0; i < trackCount; i++) {
                MediaFormat trackFormat = mediaExtractor1.getTrackFormat(i);
                String mimeType = trackFormat.getString(MediaFormat.KEY_MIME);
                // 取出视频的信号
                if (mimeType.startsWith("video/")) {
                    videoIndex = i;
                }
            }
            //切换道视频信号的信道
            mediaExtractor1.selectTrack(videoIndex);
            MediaFormat trackFormat = mediaExtractor1.getTrackFormat(videoIndex);
            MediaMuxer mediaMuxer = new MediaMuxer(toPath, MediaMuxer.OutputFormat.MUXER_OUTPUT_MPEG_4);
            //追踪此信道
            int trackIndex = mediaMuxer.addTrack(trackFormat);
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024 * 500);
            MediaCodec.BufferInfo bufferInfo = new MediaCodec.BufferInfo();
            mediaMuxer.start();
            long videoSampleTime;
            //获取每帧的之间的时间
            {
                mediaExtractor1.readSampleData(byteBuffer, 0);
                //skip first I frame
                if (mediaExtractor1.getSampleFlags() == MediaExtractor.SAMPLE_FLAG_SYNC)
                    mediaExtractor1.advance();
                mediaExtractor1.readSampleData(byteBuffer, 0);
                long firstVideoPTS = mediaExtractor1.getSampleTime();
                mediaExtractor1.advance();
                mediaExtractor1.readSampleData(byteBuffer, 0);
                long SecondVideoPTS = mediaExtractor1.getSampleTime();
                videoSampleTime = Math.abs(SecondVideoPTS - firstVideoPTS);
                //   Log.d("fuck", "videoSampleTime is " + videoSampleTime);
            }
            //重新切换此信道，不然上面跳过了3帧,造成前面的帧数模糊
            mediaExtractor1.unselectTrack(videoIndex);
            mediaExtractor1.selectTrack(videoIndex);
            while (true) {
                //读取帧之间的数据
                int readSampleSize = mediaExtractor1.readSampleData(byteBuffer, 0);
                if (readSampleSize < 0) {
                    break;
                }
                mediaExtractor1.advance();
                bufferInfo.size = readSampleSize;
                bufferInfo.offset = 0;
                bufferInfo.flags = mediaExtractor1.getSampleFlags();
                bufferInfo.presentationTimeUs += videoSampleTime;
                //写入帧的数据
                mediaMuxer.writeSampleData(trackIndex, byteBuffer, bufferInfo);
            }
            //release
            mediaMuxer.stop();
            mediaExtractor1.release();
            mediaMuxer.release();
            //  Log.e("TAG", "finish");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO 视频处理　　AAAAAAAAAAAAAA

    /**
     * 获取视频文件截图
     *
     * @param path 视频文件的路径
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb(String path) {
        MediaMetadataRetriever media = new MediaMetadataRetriever();
        media.setDataSource(path);
        return media.getFrameAtTime();
    }

    /**
     * 获取视频文件缩略图 API>=8(2.2)
     *
     * @param path 视频文件的路径
     * @param kind 缩略图的分辨率：MINI_KIND、MICRO_KIND、FULL_SCREEN_KIND
     * @return Bitmap 返回获取的Bitmap
     */
    public static Bitmap getVideoThumb2(String path, int kind) {
        return ThumbnailUtils.createVideoThumbnail(path, kind);
    }

    public static Bitmap getVideoThumb2(String path) {
        return getVideoThumb2(path, MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);
    }
}