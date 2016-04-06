package com.umeng.commm.ui.adapters.viewholders;

import android.content.Context;
import android.view.View;

import com.umeng.comm.ui.imagepicker.util.ViewFinder;


/**
 * Created by umeng on 12/18/15.
 */
public class CommentMsgViewHolder extends CommentMessageViewHolder {

    public CommentMsgViewHolder(){

    }

    public CommentMsgViewHolder (Context context,View rootView){
        mContext = context;
        itemView = rootView;
        mViewFinder = new ViewFinder(rootView);
        initWidgets();
    }

    @Override
    protected int getItemLayout() {
        return super.getItemLayout();
    }

    @Override
    protected void initWidgets() {
        super.initWidgets();
    }




}
