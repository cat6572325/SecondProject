package com.example.zqq.myapplication.NetWorks;

import android.content.*;
import android.graphics.*;
import android.media.*;
import android.os.*;
import android.provider.*;
import android.util.*;
import com.socks.okhttp.plus.*;
import com.socks.okhttp.plus.listener.*;
import com.socks.okhttp.plus.model.*;
import java.io.*;
import java.util.*;
import okhttp3.*;
import org.json.*;
/**
 * key
 * file path
 * url
 * handler
 *
 *
 */
public class Post_Http {

    Handler handler;
    Message msg=null;
    Bundle bundle=null;
HashMap<String,Object> map;

    public Post_Http(HashMap<String,Object> map)
    {
        this.map=map;
    }



    public void pul(final String _id)
    {
        final Message msg=new Message();
        final Bundle bundle=new Bundle();
        final File file = new File(map.get("path").toString());//Environment.getExternalStorageDirectory(), "jiandan02.jpg");
        if (!file.exists()) {
            //  Toast.makeText(con.this, "File not exits！", Toast.LENGTH_SHORT).show();
            return;
        }

        Map<String, String> param = new HashMap<>();
        Pair<String, File> pair = new Pair("null", file);
        OkHttpProxy
                .upload()
                .url(map.get("videourl").toString()+_id)
                .file(pair)
                .setParams(param)
                .setWriteTimeOut(20)
                .start(new UploadListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("错误", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String str=response.body().string();
                        try{
                            JSONObject js=new JSONObject(str);
                            Log.e("video",str);
                            Log.e("完成", String.valueOf(response.isSuccessful()));

                        }catch(JSONException e)
                        {

                        }
                    }


                    @Override
                    public void onSuccess(okhttp3.Response response) {
                        try {

                            String str=response.body().string();

                            JSONObject js=new JSONObject(str);

                            bundle.putString("?", "success");
                            bundle.putString("!",str);
                            msg.what=0;
                            msg.setData(bundle);
                            //  handler.sendMessage(msg);



                        } catch (IOException e) {
                            e.printStackTrace();
                        }catch (JSONException e) {}

                    }

                    @Override
                    public void onFailure(Exception e) {
                        //  tv_response.setText(e.getMessage());
                        Log.e("bbbb",String.valueOf(e));
                        bundle.putString("?","error");
                        bundle.putString("!",e.toString());
                        msg.what=0;
                        msg.setData(bundle);
                        handler.sendMessage(msg);
                    }

                    @Override
                    public void onUIProgress(Progress progress) {
                        int pro = (int) ((progress.getCurrentBytes() + 0.0) / progress.getTotalBytes() * 100);
                        if (pro > 0) {
                            Bundle b=new Bundle();
                            int o=pro;
                            Log.e("vide进度",String.valueOf(pro));


                            msg.arg1=pro;
                            msg.what=0;
                            msg.setData(b);

                            //  handler.sendMessage(msg);
                        }
                        //  KLog.d("pro = " + pro + " getCurrentBytes = " + progress.getCurrentBytes() + " getTotalBytes = " + progress.getTotalBytes());
                    }
                });


    }
    public void videodata(String url)
    {
        OkHttpClient client=new OkHttpClient();
        JSONObject jsonObject = null;
        JSONArray jsonArray = null;
        String str = null;
        Response response = null;

        try {



            JSONObject jsonObject1=new JSONObject();
            jsonObject1.put("introduction","");

            RequestBody formBody = new FormBody.Builder()
                     .add("title", map.get("title").toString())
                    .build();
            Request request = new Request.Builder()
                    .url(url)
                    .post(formBody)
                    .build();
            Log.e("url",url);
            try
            {
                response = client.newCall(request).execute();
            }
            catch (IOException e)
            {}
            //if (!response.isSuccessful())
            str = response.body().string();
            Log.e("发送视频信息时返回了",str);
            jsonObject=new JSONObject(str);

          pul(jsonObject.get("_id").toString());
             msg.what=0;
            msg.setData(bundle);
            handler.sendMessage(msg);





        } catch (IOException e) {
            e.printStackTrace();
            Log.e("videodata",e.toString());


        } catch(JSONException e)
        {
            Log.e("上传视频信息时",e.toString());
        }
        finally {


        }

    }
    public void loadvideopng()
    {
        String path=map.get("path").toString();
        String str=path.substring(0,path.lastIndexOf(".")-1);
        File f=new File(str+((int)(1+Math.random()*(10-1+1)))+".png");
        //	compressImage(getVideoThumb(file.getPath()),f.getPath());
        //改后缀名
        //获得视频截图后缩小
        Bitmap bitmap=resizeImage(getVideoThumb(path),140,145);
        bitmap2File(bitmap,f);



        Map<String, String> param = new HashMap<>();
        // param.put("file","videofile");
        Pair<String, File> pair = new Pair("cover", f);
        OkHttpProxy
                .upload()
                .url("http://192.168.1.109:3333/video/cover")
                .file(pair)
                .setParams(param)
                .setWriteTimeOut(20)

                .start(new UploadListener() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("错误", e.toString());
                    }

                    @Override
                    public void onResponse(Call call, okhttp3.Response response) throws IOException {
                        String str=response.body().string();


                        Log.e("videophonto",str);
                        Log.e("完成", String.valueOf(response.isSuccessful()));
                        HashMap<String,Object> map =new HashMap<String,Object>();
                        handler=(Handler) map.get("handler");
                        //将截图id和url传过去
                        videodata("http://localhost:3333/user/video/detail?scsId=${scsId}&token=${token}");


                    }


                    @Override
                    public void onSuccess(Response response) {
                        Log.e("上传视频截图成功的时候","这个success可以用");
                    }

                    @Override
                    public void onFailure(Exception e) {
                        Log.e("上传视频截图的时候",e.toString());
                    }

                    @Override
                    public void onUIProgress(Progress progress) {
                        int pro = (int) ((progress.getCurrentBytes() + 0.0) / progress.getTotalBytes() * 100);
                        Log.e("上传视频截图进度",String.valueOf(pro));
                    }
                });


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
    /*
    压缩
     */
    private Bitmap decodeSampleBitmapFromStream(Bitmap bitmap)
    {
        ByteArrayOutputStream baos=new ByteArrayOutputStream();
        int options =80;
        bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        while (baos.toByteArray().length/1024>100)
        {
            baos.reset();
            options-=10;
            bitmap.compress(Bitmap.CompressFormat.PNG,100,baos);
        }
        ByteArrayInputStream isBm=new ByteArrayInputStream(baos.toByteArray());
        Bitmap bitmap1=BitmapFactory.decodeStream(isBm,null,null);

        return bitmap1;
    }
}