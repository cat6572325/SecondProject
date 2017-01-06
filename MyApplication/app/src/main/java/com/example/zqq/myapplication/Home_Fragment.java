package com.example.zqq.myapplication;
/*
 *
 */
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zqq.myapplication.Adapters.FragmentAdapter;
import com.example.zqq.myapplication.Adapters.Mine_Recycler_Adapter;
import com.example.zqq.myapplication.Adapters.Second_Adapter;
import com.example.zqq.myapplication.Utils.RecyclerView_Space;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Fifth;
import com.example.zqq.myapplication.classify_Fragments.Fragment_First;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Fourth;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Second;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Seventh;
import com.example.zqq.myapplication.classify_Fragments.Fragment_Sixth;
import com.example.zqq.myapplication.classify_Fragments.Fragment_third;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Home_Fragment extends Fragment implements View.OnClickListener{
    Fragment_First fragment_first=new Fragment_First();
    Fragment_Second fragment_second=new Fragment_Second();
    Fragment_third fragment_third=new Fragment_third();
    Fragment_Fourth fragment_fourth=new Fragment_Fourth();
    Fragment_Fifth fragment_fifth=new Fragment_Fifth();
    Fragment_Sixth fragment_sixth=new Fragment_Sixth();
    Fragment_Seventh fragment_seventh=new Fragment_Seventh();
    TabLayout mTabLayout;
    ViewPager mViewPager;
    RelativeLayout btn;
    RecyclerView popupwindow_recyclerView;
    Mine_Recycler_Adapter mAdapter=null;
    ArrayList<HashMap<String,Object>> maps=new ArrayList<>(),popwindow_recyclers=new ArrayList<>();
    View v;
    PopupWindow mPopWindow;
    Button btntest;

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
    titles.add("最爱");
    titles.add("最好");
    titles.add("作死");
    titles.add("动物");
    titles.add("昆虫");
    titles.add("运动");

    //初始化TabLayout的title
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(0)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(1)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(2)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(3)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(4)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(5)));
    mTabLayout.addTab(mTabLayout.newTab().setText(titles.get(6)));
    //初始化ViewPager的数据集
    List<Fragment> fragments = new ArrayList<>();

    fragments.add(fragment_first);
    fragments.add(fragment_second);
    fragments.add(fragment_third);
    fragments.add(fragment_fourth);
    fragments.add(fragment_fifth);
    fragments.add(fragment_sixth);
    fragments.add(fragment_seventh);

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
          showPopupWindow(v);
        }
    });
}


    public void showPopupWindow(final View v) {
        //TODO 下拉选项表格
        //设置contentView
        View contentView = LayoutInflater.from(getContext())
                .inflate(R.layout.classify_popwindow_layout, null);
        mPopWindow = new PopupWindow(contentView//设置上下文布局
                , ViewGroup.LayoutParams.MATCH_PARENT//设置为布局宽度，也可以直接指定
                , ActionMenuView.LayoutParams.WRAP_CONTENT//设置高度，同上
                , true);//不知道
        mPopWindow.setContentView(contentView);
        popupwindow_recyclerView=(RecyclerView)contentView.findViewById(R.id.popupwind_recyclerView);
        //通过布局获取recyclerview实例
        mAdapter = new Mine_Recycler_Adapter(popwindow_recyclers);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 5);
        //设置布局为一行5列的样式
        popupwindow_recyclerView.setLayoutManager(gridLayoutManager);
        int spanCount = 5; // 3 columns
        int spacing = 50; // 50px
        boolean includeEdge = false;
        popupwindow_recyclerView.addItemDecoration(new RecyclerView_Space(spanCount, spacing, includeEdge));

        for (int i = 0; i <20 ; i++) {
            HashMap<String,Object> map=new HashMap<>();
            map.put("title","第"+i+"个");
            map.put("layout",1);
            map.put("context",getContext());
            popwindow_recyclers.add(map);
        }


        //绑定布局管理器
        popupwindow_recyclerView.setAdapter(mAdapter);
        //条目点击事件
        mAdapter.setOnClickListener(new Mine_Recycler_Adapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(View view, int position) {
                Toast.makeText(getActivity(), position + "========Click:", Toast.LENGTH_SHORT).show();
                fragment_first.deleteItem(position);//删除一个item
            }

            @Override
            public void onItemLongClickListener(View view, int position) {

            }
        });

         //显示PopupWindow
        View rootview = LayoutInflater.from(getContext()).inflate(R.layout.home_pager, null);
        //  mPopWindow.showAtLocation(rootview, Gravity.VERTICAL_GRAVITY_MASK, 0, 0);//设置相对于布局的哪里显示
        // mPopWindow.setAnimationStyle(R.style.AnimationPreview);
        // 如果不设置PopupWindow的背景，无论是点击外部区域还是Back键都无法dismiss弹框。不知道是什么原因
        mPopWindow.setBackgroundDrawable(new BitmapDrawable());
// 设置点击窗口外边窗口消失
        mPopWindow.setOutsideTouchable(true);
// 设置此参数获得焦点，否则无法点击
        mPopWindow.setFocusable(true);
// 设置弹出框的显示位置
        mPopWindow.showAsDropDown(v,0,0);

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
     //   btntest.setOnClickListener(this);
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

    public void getVideos() {
       // fragment_first.mWaveSwipeRefreshLayout.setRefreshing(true);
        fragment_first.getVideos();
    }
}