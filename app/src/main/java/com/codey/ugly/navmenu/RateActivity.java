package com.codey.ugly.navmenu;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.codey.ugly.R;
import com.codey.ugly.adapter.MyFragmentAdapter;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/5.
 */
public class RateActivity extends FragmentActivity
{
    private TextView mtvBack;
    private ViewPager mViewpager;
    private ArrayList<Fragment> fragments;
    private TabLayout mTablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rate_main);
        mtvBack= (TextView) findViewById(R.id.tv_back);
        mtvBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        mTablayout= (TabLayout) findViewById(R.id.tab);
        mViewpager= (ViewPager) findViewById(R.id.rate_pager);
        setRate();
    }

    private void setRate()
    {
        fragments=new ArrayList<>();
        Rate rate=new Rate();
        MyRate myRate=new MyRate();
        fragments.add(rate);
        fragments.add(myRate);
        String[] pageName={"为TA打分","我的分数"};
        MyFragmentAdapter adapter=new MyFragmentAdapter(getSupportFragmentManager(),fragments,this,pageName);
        mViewpager.setAdapter(adapter);
        mViewpager.setCurrentItem(0);
        mViewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener()
        {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels)
            {

            }

            @Override
            public void onPageSelected(int position)
            {

            }

            @Override
            public void onPageScrollStateChanged(int state)
            {

            }
        });
        mTablayout.setupWithViewPager(mViewpager);
        mTablayout.setTabMode(TabLayout.MODE_FIXED);
    }
}
