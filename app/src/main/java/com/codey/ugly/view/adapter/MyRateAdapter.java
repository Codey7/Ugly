package com.codey.ugly.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codey.ugly.R;
import com.codey.ugly.bean.RateBean;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/5/3.
 */
public class MyRateAdapter extends BaseListAdapter
{
    private Context context;
    private ArrayList<RateBean> mList = null;
    private ViewHolder holder = null;
    public MyRateAdapter(Context context, ArrayList<RateBean> mList)
    {
        super(context, mList);
        this.context=context;
        this.mList=mList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.my_rate_list, parent, false);
            holder.mtvTitle = (TextView) convertView.findViewById(R.id.tv_rate_title);
            holder.mtvRateimg = (ImageView) convertView.findViewById(R.id.iv_rate_img);
            holder.mtvScore = (TextView) convertView.findViewById(R.id.tv_rate_score);
            convertView.setTag(holder);
        } else
        {
            convertView.getTag();
        }
        valTag(context,holder.mtvRateimg,mList.get(position).getRateimgurl(),R.drawable.placeholder);
        holder.mtvScore.setText(String.valueOf(mList.get(position).getScore()));
        holder.mtvTitle.setText(mList.get(position).getDetails());
        return convertView;
    }
    public static class ViewHolder
    {
        TextView mtvTitle;
        ImageView mtvRateimg;
        TextView mtvScore;
    }
}
