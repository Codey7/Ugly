package com.codey.ugly.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codey.ugly.R;
import com.codey.ugly.bean.Article;


import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/1.
 */
public class ArticleAdapter extends BaseAdapter
{

    private Context context;
    private ArrayList<Article> mList = null;

    public ArticleAdapter(Context context, ArrayList list)
    {
        this.context = context;
        this.mList = list;
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
        ViewHolder holder=null;
        if (convertView==null)
        {
            holder=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.deploy_list,parent,false);
            holder.imageView= (ImageView) convertView.findViewById(R.id.iv_article);
            holder.textView= (TextView) convertView.findViewById(R.id.tv_article_title);
            convertView.setTag(holder);
        }
        else
        {
            holder= (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(mList.get(position).getTitle());
       //Log.i("tag", mList.get(position).getUrl());
        Glide.with(context)
                .load(mList.get(position).getUrl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .crossFade(1000)
                .into(holder.imageView);
        return convertView;
    }

    public static class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }
}
