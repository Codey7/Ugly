package com.codey.ugly.mainFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codey.ugly.R;

/**
 * Created by Mr.Codey on 2016/3/30.
 * 社区模块
 */
public class Social extends Fragment
{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view=inflater.inflate(R.layout.socialfrag,container,false);
        return view;
    }
}
