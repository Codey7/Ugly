package com.codey.ugly.navmenu;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codey.ugly.R;

/**
 * Created by Mr.Codey on 2016/4/5.
 */
public class MyRate extends Fragment
{
    private View view ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.my_rate,container,false);
        return view;
    }
}
