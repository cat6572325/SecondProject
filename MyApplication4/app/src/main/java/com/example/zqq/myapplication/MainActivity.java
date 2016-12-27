package com.example.zqq.myapplication;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity  implements BottomNavigationBar.OnTabSelectedListener {
        private ArrayList<Fragment> fragments;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
                super.onCreate(savedInstanceState);
                setContentView(R.layout.activity_main);
                BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) findViewById(R.id.bottom_navigation_bar);

                bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);

                bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);

                bottomNavigationBar.addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Home").setActiveColor(R.color.Bottom_nagavi_Red).setInActiveColor(R.color.gray))
                        .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Books"))//.setActiveColor(R.color.Bottom_nagavi_Red))
                        .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Music"))//.setActiveColor(R.color.Bottom_nagavi_Red))
                        .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Movies & TV"))//.setActiveColor(R.color.Bottom_nagavi_Red))//.setInActiveColor(R.color.gray))
                        .addItem(new BottomNavigationItem(R.mipmap.ic_launcher, "Games"))//.setActiveColor(R.color.Bottom_nagavi_Red))//.setInActiveColor(R.color.gray))
                        .setFirstSelectedPosition(0)
                        .initialise();

                fragments = getFragments();
                setDefaultFragment();
                bottomNavigationBar.setTabSelectedListener(this);
        }

        /**
         * 设置默认的
         */
        private void setDefaultFragment() {
                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction transaction = fm.beginTransaction();
                transaction.replace(R.id.layFrame, Home_Fragment.newInstance("Home"));
                transaction.commit();
        }

        private ArrayList<Fragment> getFragments() {
                ArrayList<Fragment> fragments = new ArrayList<>();
                fragments.add(Home_Fragment.newInstance("Home"));
                fragments.add(Release_Fragment.newInstance("Books"));
                fragments.add(Thumb_Fragment.newInstance("Music"));
                fragments.add(Messages_Fragment.newInstance("Movies & TV"));
                fragments.add(Mine_Fragment.newInstance("Games"));
                return fragments;
        }

        @Override
        public void onTabSelected(int position) {
                if (fragments != null) {
                        if (position < fragments.size()) {
                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                Fragment fragment = fragments.get(position);
                                if (fragment.isAdded()) {
                                        ft.replace(R.id.layFrame, fragment);
                                } else {
                                        ft.add(R.id.layFrame, fragment);
                                }
                                ft.commitAllowingStateLoss();
                        }
                }

        }

        @Override
        public void onTabUnselected(int position) {
                if (fragments != null) {
                        if (position < fragments.size()) {
                                FragmentManager fm = getSupportFragmentManager();
                                FragmentTransaction ft = fm.beginTransaction();
                                Fragment fragment = fragments.get(position);
                                ft.remove(fragment);
                                ft.commitAllowingStateLoss();
                        }
                }
        }

        @Override
        public void onTabReselected(int position) {

        }
}