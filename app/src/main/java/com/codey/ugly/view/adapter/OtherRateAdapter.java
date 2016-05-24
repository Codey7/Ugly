package com.codey.ugly.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.codey.ugly.R;
import com.codey.ugly.bean.RateBean;
import com.umeng.comm.core.beans.CommUser;

import java.util.ArrayList;

/**
 * Created by Mr.Codey on 2016/4/5.
 */
public class OtherRateAdapter extends BaseListAdapter
{
    private Context context;
    private ArrayList<RateBean> mList = null;
    private CommUser user;
    private ViewHolder holder = null;

    public OtherRateAdapter(Context context, ArrayList<RateBean> mList, CommUser user)
    {
        super(context,mList);
        this.context=context;
        this.mList=mList;
        this.user = user;
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
        valTag(context,holder.mivHeadimg,user.iconUrl,R.drawable.placeholder);
        holder.mtvUsername.setText(user.name);
        holder.mtvPubtime.setText(mList.get(position).getPubtime());
        valTag(context,holder.mtvRateimg,mList.get(position).getRateimgurl(),R.drawable.placeholder);
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
