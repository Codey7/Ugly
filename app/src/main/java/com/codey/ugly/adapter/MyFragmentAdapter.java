package com.codey.ugly.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/3/30.
 */
public class MyFragmentAdapter extends FragmentPagerAdapter
{
    private String[] pageName;
    private Context context;
    private ArrayList<Fragment> fragments;
    public MyFragmentAdapter(FragmentManager fm,ArrayList<Fragment> fragments,Context context,String[] pageName)
    {
        super(fm);
        this.pageName=pageName;
        this.fragments=fragments;
        this.context=context;

    }
    public MyFragmentAdapter(FragmentManager fm)
    {
        super(fm);
    }

    @Override
    public Fragment getItem(int position)
    {
        return fragments.get(position);
    }

    @Override
    public int getCount()
    {
        return pageName.length;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        return pageName[position];
    }
}
