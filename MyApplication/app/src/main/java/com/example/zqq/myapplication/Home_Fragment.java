package com.example.zqq.myapplication;
/*
 *
 */
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class Home_Fragment extends Fragment {
    Fragment_First fragment_first=new Fragment_First();
    Fragment_Second fragment_second=new Fragment_Second();
    Fragment_third fragment_third=new Fragment_third();
RecyclerView home_rec;
    TabLayout mTabLayout;
    ViewPager mViewPager;
    Button btn;
    View v;
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
    btn=(Button)view.findViewById(R.id.Click_Add_a_data);
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
            Toast.makeText(getContext(),"yes",Toast.LENGTH_LONG).show();
            ArrayList<HashMap<String,Object>> Datalist=new ArrayList<>();
            HashMap<String ,Object> map=new HashMap<>();
            map.put("title","FirstVideos");
            map.put("layout",0);

            map.put("tag",String.valueOf(System.currentTimeMillis()));
            Datalist.add(map);
            fragment_first.AddData(Datalist);
        }
    });
}

public void onClick(View v)
{

}


}