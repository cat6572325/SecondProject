package com.example.zqq.myapplication;
/*
 *
 */
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.FragmentAdapter;
import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.classify_Fragments.Fragment_First;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Second;
import com.example.zqq.myapplication.classify_Fragments.Fragment_third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home_Fragment extends Fragment implements View.OnClickListener{
    Fragment_First fragment_first=new Fragment_First();
    Fragment_Second fragment_second=new Fragment_Second();
    Fragment_third fragment_third=new Fragment_third();
RecyclerView home_rec;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    RelativeLayout btn;
    View v;
    PopupWindow mPopWindow;
    Button btntest;
    public ArrayList<HashMap<String, Object>> lists = new ArrayList<HashMap<String, Object>>(), lists2 = new ArrayList<HashMap<String, Object>>();
private Second_Adapter second_adapter;
    public static Home_Fragment newInstance(String param1) {
        Home_Fragment fragment = new Home_Fragment();
        Bundle args = new Bundle();
        args.putString("agrs1", param1);
        fragment.setArguments(args);
        return fragment;
    }

    public Home_Fragment() {

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
        TextView tv = (TextView)v.findViewById(R.id.tv_location);
      //  tv.setText(agrs1);
        initView(v);
        return v;
    }
private void initView(View view)
{
    mTabLayout=(TabLayout)view.findViewById(R.id.tab_layout);
    mViewPager=(ViewPager)view.findViewById(R.id.view_pager);
    btn= (RelativeLayout) view.findViewById(R.id.Click_Add_a_data);
    //初始化TabLayout的title数据集
    List<String> titles = new ArrayList<>();
    titles.add("推荐");
    titles.add("最热");
    titles.add("最新");

    //初始化TabLayout的title
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
    //初始化ViewPager的数据集
    List<Fragment> fragments = new ArrayList<>();

    fragments.add(fragment_first);
    fragments.add(fragment_second);
    fragments.add(fragment_third);

    //创建ViewPager的adapter
    FragmentAdapter adapter = new FragmentAdapter(getActivity().getSupportFragmentManager(), fragments, titles);
    mViewPager.setAdapter(adapter);
    //千万别忘了，关联TabLayout与ViewPager
    //同时也要覆写PagerAdapter的getPageTitle方法，否则Tab没有title
    mTabLayout.setupWithViewPager(mViewPager);
    mTabLayout.setTabsFromPagerAdapter(adapter);
    btn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           // onClick(v);
         /*  //添加一条视频
          Toast.makeText(getContext(),"yes",Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String,Object>> Datalist=new ArrayList<>();
            HashMap<String ,Object> map=new HashMap<>();
            map.put("title","FirstVideos");
            map.put("layout",0);

            map.put("tag",String.valueOf(System.currentTimeMillis()));
            Datalist.add(map);
            fragment_first.AddData(Datalist);
            */
            //显示popwindow
            showPopupWindow();
        }
    });
}
public void addview(View view)
{

}

    private void showPopupWindow() {
        //设置contentView
        View contentView = LayoutInflater.from(getContext())
                .inflate(R.layout.classify_popwindow_layout, null);
        mPopWindow = new PopupWindow(contentView//设置上下文布局
                , ViewGroup.LayoutParams.MATCH_PARENT//设置为布局宽度，也可以直接指定
                , ActionMenuView.LayoutParams.WRAP_CONTENT//设置高度，同上
                , true);//不知道
        mPopWindow.setContentView(contentView);
        //设置各个控件的点击响应
        btntest=(Button)contentView.findViewById(R.id.testutton);
        btntest.setOnClickListener(this);
      //  TextView tv1 = (TextView)contentView.findViewById(R.id.pop_computer);
      //  TextView tv2 = (TextView)contentView.findViewById(R.id.pop_financial);
      //  TextView tv3 = (TextView)contentView.findViewById(R.id.pop_manage);
      //  tv1.setOnClickListener(this);
      //  tv2.setOnClickListener(this);
       // tv3.setOnClickListener(this);
        //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.home_pager, null);
      //  mPopWindow.showAtLocation(rootview, Gravity.VERTICAL_GRAVITY_MASK, 0, 0);//设置相对于布局的哪里显示
        mPopWindow.setAnimationStyle(R.style.AnimationPreview);

        mPopWindow.showAsDropDown(mTabLayout,0,10);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.testutton:{
                Toast.makeText(getContext(),"你点击了一个按钮，添加了一个视频,那么，再见",Toast.LENGTH_SHORT).show();
                ArrayList<HashMap<String,Object>> Datalist=new ArrayList<>();
                HashMap<String ,Object> map=new HashMap<>();
                map.put("title","FirstVideos");
                map.put("layout",0);
                map.put("tag",String.valueOf(System.currentTimeMillis()));
                Datalist.add(map);
                fragment_first.AddData(Datalist);
                mPopWindow.dismiss();
            }
            break;

        }
    }

}