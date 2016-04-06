package com.codey.ugly.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codey.ugly.R;
import com.codey.ugly.bean.CommonBean;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/1.
 */
public class CommonAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<CommonBean> mList = null;

    public CommonAdapter(Context context, ArrayList<CommonBean> mList)
    {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public int getCount()
    {
        if (mList==null)
        {
            return 0;
        }
        return mList.size();
    }

    @Override
    public Object getItem(int position)
    {
        if (mList==null)
        {
            return null;
        }
        return mList.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        ViewHolder holder=null;
        if (convertView==null)
        {
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.common_list,parent,false);
            holder.imageView= (ImageView) convertView.findViewById(R.id.iv_common);
            holder.mtvTitle= (TextView) convertView.findViewById(R.id.tv_common_title);
            holder.mtvThumb= (TextView) convertView.findViewById(R.id.tv_thumb_up);
            holder.mtvBookmark= (TextView) convertView.findViewById(R.id.tv_bookmark);
            convertView.setTag(holder);
        }
        else
        {
            convertView.getTag();
        }
        Log.i("commmmmmmmmmtag", mList.get(position).getUrl());
        Glide.with(context)
                .load(mList.get(position).getUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .crossFade(1000)
                .into(holder.imageView);
        Drawable drawable=context.getResources().getDrawable(R.drawable.ic_thumb_up_black_18dp);
        drawable.setBounds(0,0,25,25);
        holder.mtvThumb.setCompoundDrawables(drawable, null, null, null);
        Drawable drawable2=context.getResources().getDrawable(R.drawable.ic_bookmark_border_black_18dp);
        drawable2.setBounds(0, 0, 25, 25);
        holder.mtvBookmark.setCompoundDrawables(drawable2,null,null,null);
        holder.mtvTitle.setText(mList.get(position).getTitle());
        holder.mtvThumb.setText(String.valueOf(mList.get(position).getThumbnum()));
        holder.mtvBookmark.setText(String.valueOf(mList.get(position).getBookmarknum()));
        return convertView;
    }
    public static class ViewHolder
    {
        ImageView imageView;
        TextView mtvTitle;
        TextView mtvThumb;
        TextView mtvBookmark;
    }
}
