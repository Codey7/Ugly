package com.codey.ugly.core;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.codey.ugly.R;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/23.
 */
public class BasePersonInfo extends BaseAppCompatActivity
{
    @Override
    protected int getLayoutId()
    {
        return 0;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {

    }
    public void setTextView(TextView textView)
    {
        if (textView.isSelected() == true)
        {
            textView.setSelected(false);
            textView.setTextColor(getResources().getColor(R.color.black));
        }
        else
        {
            textView.setSelected(true);
            textView.setTextColor(getResources().getColor(R.color.umeng_comm_white_color));
        }
    }
    public ArrayList getCheckText(TextView[] tvs)
    {
        ArrayList<String> mList=new ArrayList<>();
        for (TextView  tv: tvs)
        {
            if (tv.isSelected()==true)
            {
                mList.add(tv.getText().toString().trim());
            }
        }
        return mList;
    }
}
