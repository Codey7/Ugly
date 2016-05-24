package com.codey.ugly.core;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codey.ugly.utils.ToastUtils;

/**
 * Created by Mr.Codey on 2016/4/26.
 */
public abstract class BaseFragment extends Fragment
{
    private View view;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        view=inflater.inflate(getLayout(),container,false);
        initView(this.view,savedInstanceState);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState)
    {
        super.onActivityCreated(savedInstanceState);
    }

    public abstract int getLayout();
    public abstract void initView(View view,Bundle savedInstanceState);

    public void showToast(String msg, int duration) {
        if (msg == null) return;
        if (duration == Toast.LENGTH_SHORT || duration == Toast.LENGTH_LONG) {
            ToastUtils.show(getActivity(), msg, duration);
        } else {
            ToastUtils.show(getActivity(), msg, ToastUtils.LENGTH_SHORT);
        }
    }
}
