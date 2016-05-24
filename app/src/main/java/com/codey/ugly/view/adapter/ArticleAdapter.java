package com.codey.ugly.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codey.ugly.R;
import com.codey.ugly.bean.Article;


import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/1.
 */
public class ArticleAdapter extends BaseListAdapter
{

    private Context context;
    private ArrayList<Article> mList = null;

    public ArticleAdapter(Context context, ArrayList list)
    {
        super(context,list);
        this.context = context;
        this.mList = list;
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
        valTag(context,holder.imageView,mList.get(position).getUrl(),R.drawable.placeholder);
        return convertView;
    }

    public static class ViewHolder
    {
        ImageView imageView;
        TextView textView;
    }
}
