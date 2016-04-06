package com.codey.ugly.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.codey.ugly.R;
import com.codey.ugly.bean.CommonBean;
import com.codey.ugly.bean.RateBean;
import com.umeng.comm.core.beans.CommUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mr.Codey on 2016/4/5.
 */
public class OtherRateAdapter extends BaseAdapter
{
    private Context context;
    private ArrayList<RateBean> mList = null;
    private CommUser user;
    private ViewHolder holder = null;

    public OtherRateAdapter(Context context, ArrayList<RateBean> mList, CommUser user)
    {
        this.context = context;
        this.mList = mList;
        this.user = user;
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
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        if (convertView == null)
        {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.other_rate_list, parent, false);
            holder.mivHeadimg = (ImageView) convertView.findViewById(R.id.iv_head);
            holder.mtvUsername = (TextView) convertView.findViewById(R.id.tv_user_head);
            holder.mtvPubtime = (TextView) convertView.findViewById(R.id.tv_pub_time);
            holder.mtvDetails = (TextView) convertView.findViewById(R.id.tv_rate_detail);
            holder.mtvRateimg = (ImageView) convertView.findViewById(R.id.iv_rate_img);
            holder.bar = (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.mtvScore = (TextView) convertView.findViewById(R.id.tv_rate_score);
            holder.mbtCommit = (Button) convertView.findViewById(R.id.bt_rate_commit);
            convertView.setTag(holder);
        } else
        {
            convertView.getTag();
        }
        Glide.with(context)
                .load(user.iconUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .crossFade(500)
                .into(holder.mivHeadimg);
        holder.mtvUsername.setText(user.name);
        holder.mtvPubtime.setText(mList.get(position).getPubtime());
        Glide.with(context)
                .load(mList.get(position).getRateimgurl())
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .crossFade(500)
                .into(holder.mtvRateimg);
        holder.mtvScore.setText(String.valueOf(mList.get(position).getScore()));
        holder.mtvDetails.setText(mList.get(position).getDetails());
        holder.bar.setRating(0f);
        holder.mbtCommit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                float score =holder.bar.getRating();
                holder.mtvScore.setText(String.valueOf(score));
                holder.mbtCommit.setClickable(false);
            }
        });
        return convertView;
    }

    public static class ViewHolder
    {
        ImageView mivHeadimg;
        TextView mtvUsername;
        TextView mtvPubtime;
        TextView mtvDetails;
        ImageView mtvRateimg;
        TextView mtvScore;
        RatingBar bar;
        Button mbtCommit;
    }
}
