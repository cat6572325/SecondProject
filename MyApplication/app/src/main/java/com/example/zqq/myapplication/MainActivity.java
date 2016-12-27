package com.example.zqq.myapplication;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity; // 注意这里我们导入的V4的包，不要导成app的包了
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    // 初始化顶部栏显示
    private ImageView titleLeftImv;
    private TextView titleTv;
    // 定义4个Fragment对象
    private Home_Fragment fg1;
    private Home_Fragment fg2;
    private Home_Fragment fg3;
    private Home_Fragment fg4;
    private Home_Fragment fg5;
    // 帧布局对象，用来存放Fragment对象
    private FrameLayout frameLayout;
    // 定义每个选项中的相关控件
    private RelativeLayout firstLayout;
    private RelativeLayout secondLayout;
    private RelativeLayout thirdLayout;
    private RelativeLayout fourthLayout,mine_l;
    private ImageView firstImage;
    private ImageView secondImage;
    private ImageView thirdImage;
    private ImageView fourthImage,mine_img;
    private TextView firstText;
    private TextView secondText;
    private TextView thirdText;
    private TextView fourthText,mine_txt;
    // 定义几个颜色
    private int whirt = 0xFFFFFFFF;
    private int gray = 0xFF7597B3;
    private int dark = 0xff000000;
    // 定义FragmentManager对象管理器
    private FragmentManager fragmentManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initView(); // 初始化界面控件
        setChioceItem(0); // 初始化页面加载时显示第一个选项卡
    }
    /**
     * 初始化页面
     */
    private void initView() {

// 初始化底部导航栏的控件
        firstImage = (ImageView) findViewById(R.id.first_image);
        secondImage = (ImageView) findViewById(R.id.second_image);
        thirdImage = (ImageView) findViewById(R.id.third_image);
        fourthImage = (ImageView) findViewById(R.id.fourth_image);
        firstText = (TextView) findViewById(R.id.first_text);
        secondText = (TextView) findViewById(R.id.second_text);
        thirdText = (TextView) findViewById(R.id.third_text);
        fourthText = (TextView) findViewById(R.id.fourth_text);
        firstLayout = (RelativeLayout) findViewById(R.id.first_layout);
        secondLayout = (RelativeLayout) findViewById(R.id.second_layout);
        thirdLayout = (RelativeLayout) findViewById(R.id.third_layout);
        fourthLayout = (RelativeLayout) findViewById(R.id.fourth_layout);
        mine_l=(RelativeLayout)findViewById(R.id.mine_layout);
        mine_img=(ImageView)findViewById(R.id.mine_image);
        mine_txt=(TextView)findViewById(R.id.mine_text);

        firstLayout.setOnClickListener(MainActivity.this);
        secondLayout.setOnClickListener(MainActivity.this);
        thirdLayout.setOnClickListener(MainActivity.this);
        fourthLayout.setOnClickListener(MainActivity.this);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.first_layout:
                setChioceItem(0);
                break;
            case R.id.second_layout:
                setChioceItem(1);
                break;
            case R.id.third_layout:
                setChioceItem(2);
                break;
            case R.id.fourth_layout:
                setChioceItem(3);
                break;
            case R.id.mine_layout:
                setChioceItem(4);
                break;
            default:
                break;
        }
    }
    /**
     * 设置点击选项卡的事件处理
     *
     * @param index 选项卡的标号：0, 1, 2, 3
     */
    private void setChioceItem(int index) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        clearChioce(); // 清空, 重置选项, 隐藏所有Fragment
        hideFragments(fragmentTransaction);
        switch (index) {
            case 0:
// firstImage.setImageResource(R.drawable.XXXX); 需要的话自行修改
                firstText.setTextColor(0x99ff0000);
                //firstLayout.setBackgroundColor(gray);
                firstImage.setBackgroundResource(R.mipmap.home_selected);
// 如果fg1为空，则创建一个并添加到界面上
                if (fg1 == null) {
                    fg1 = new Home_Fragment();
                    fragmentTransaction.add(R.id.content, fg1);
                } else {
// 如果不为空，则直接将它显示出来
                    fragmentTransaction.show(fg1);
                }
                break;
            case 1:
// secondImage.setImageResource(R.drawable.XXXX);
                secondText.setTextColor(0x99ff0000);
               // secondLayout.setBackgroundColor(gray);
                secondImage.setBackgroundResource(R.mipmap.thumb_selected);
                if (fg2 == null) {
                    fg2 = new Home_Fragment();
                    fragmentTransaction.add(R.id.content, fg2);
                } else {
                    fragmentTransaction.show(fg2);
                }
                break;
            case 2:
// thirdImage.setImageResource(R.drawable.XXXX);
                thirdText.setTextColor(0x99ff0000);
              //  thirdLayout.setBackgroundColor(gray);
                thirdImage.setBackgroundResource(R.mipmap.release_selected);
                if (fg3 == null) {
                    fg3 = new Home_Fragment();
                    fragmentTransaction.add(R.id.content, fg3);
                } else {
                    fragmentTransaction.show(fg3);
                }
                break;
            case 3:
// fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(0x99ff0000);
             //   fourthLayout.setBackgroundColor(gray);
                fourthImage.setBackgroundResource(R.mipmap.messages_selected);
                if (fg4 == null) {
                    fg4 = new Home_Fragment();
                    fragmentTransaction.add(R.id.content, fg4);
                } else {
                    fragmentTransaction.show(fg4);
                }
                break;
            case 4:
// fourthImage.setImageResource(R.drawable.XXXX);
                fourthText.setTextColor(0x99ff0000);
                //   fourthLayout.setBackgroundColor(gray);
                fourthImage.setBackgroundResource(R.mipmap.mine_selected);
                if (fg5 == null) {
                    fg5 = new Home_Fragment();
                    fragmentTransaction.add(R.id.content, fg5);
                } else {
                    fragmentTransaction.show(fg5);
                }
                break;
        }


        fragmentTransaction.commit(); // 提交
    }
    /**
     * 当选中其中一个选项卡时，其他选项卡重置为默认
     */
    private void clearChioce() {
// firstImage.setImageResource(R.drawable.XXX);
        firstText.setTextColor(gray);
        firstImage.setBackgroundResource(R.mipmap.home);
       // firstLayout.setBackgroundColor(whirt);
// secondImage.setImageResource(R.drawable.XXX);
        secondText.setTextColor(gray);
        secondImage.setBackgroundResource(R.mipmap.thumb);
       // secondLayout.setBackgroundColor(whirt);
// thirdImage.setImageResource(R.drawable.XXX);
        thirdText.setTextColor(gray);
        thirdImage.setBackgroundResource(R.mipmap.release);
       // thirdLayout.setBackgroundColor(whirt);
// fourthImage.setImageResource(R.drawable.XXX);
        fourthText.setTextColor(gray);
        fourthImage.setBackgroundResource(R.mipmap.messages);
       // fourthLayout.setBackgroundColor(whirt);
        mine_txt.setTextColor(0x99aaaaaa);
        mine_img.setBackgroundResource(R.mipmap.mine);
    }
    /**
     * 隐藏Fragment
     *
     * @param fragmentTransaction
     */
    private void hideFragments(FragmentTransaction fragmentTransaction) {
        if (fg1 != null) {
            fragmentTransaction.hide(fg1);
        }
        if (fg2 != null) {
            fragmentTransaction.hide(fg2);
        }
        if (fg3 != null) {
            fragmentTransaction.hide(fg3);
        }
        if (fg4 != null) {
            fragmentTransaction.hide(fg4);
        }
        if (fg5 != null) {
            fragmentTransaction.hide(fg5);
        }
    }
}