package com.codey.ugly.view.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/26.
 */
public class BaseListAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<?> mList = null;

    public BaseListAdapter(Context context, ArrayList<?> mList)
    {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount()
    {
        if (mList == null)
        {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (mList == null)
        {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        return null;
    }
    public void valTag(Context context, ImageView imageView, Object tag, int placeholder)
    {
        /*imageView.setTag(tag);
        imageView.setImageResource(placeholder);*/
       /* if (imageView.getTag()!=null&&imageView.getTag().equals(tag))
        {*/
            Glide.with(context)
                    .load(tag)
                    .centerCrop()
                    .placeholder(placeholder)
                    .crossFade(500)
                    .into(imageView);
        /*}*/
    }
}
