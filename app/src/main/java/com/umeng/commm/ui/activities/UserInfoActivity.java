/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2015 Umeng, Inc
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.umeng.commm.ui.activities;

import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;

import com.umeng.comm.core.beans.BaseBean;
import com.umeng.comm.core.beans.CommUser;
import com.umeng.comm.core.listeners.Listeners.LoginOnViewClickListener;
import com.umeng.comm.core.utils.ResFinder;
import com.umeng.comm.ui.imagepicker.activities.AlbumActivity;
import com.umeng.comm.ui.imagepicker.activities.UserInfoBaseActivity;
import com.umeng.comm.ui.imagepicker.fragments.FansFragment;
import com.umeng.comm.ui.imagepicker.fragments.FollowedUserFragment;
import com.umeng.comm.ui.imagepicker.util.ViewFinder;

import com.umeng.commm.ui.fragments.PostedFeedsFragment;
import com.umeng.commm.ui.fragments.PostedFeedsFragment.OnDeleteListener;


/**
 * 用户个人信息页面, 包含已发布的消息、已关注的话题、已关注的人三个fragment, 以及用户的头像、个人基本信息等.
 */
public final class UserInfoActivity extends UserInfoBaseActivity {

    /**
     * 已发送Feed的Fragment
     */
    private PostedFeedsFragment mPostedFragment = null;

    /**
     * 关注的好友Fragment
     */
    private FollowedUserFragment mFolloweredUserFragment;

    /**
     * 粉丝Fragment
     */
    private FansFragment mFansFragment;





    @Override
    public void initFragment() {

        mPostedFragment = PostedFeedsFragment.newInstance(mUser);
        mPostedFragment.setOnAnimationResultListener(mListener);
        // 视图查找器
        mViewFinder = new ViewFinder(getWindow().getDecorView());

        mPostedFragment.setCurrentUser(mUser);
        mPostedFragment.setOnDeleteListener(new OnDeleteListener() {

            @Override
            public void onDelete(BaseBean item) {
                mPresenter.decreaseFeedCount(1);
            }
        });
        addFragment(ResFinder.getId("umeng_comm_user_info_fragment_container"),
                mPostedFragment);
    }

    @Override
    public int GetLayout() {
        return ResFinder.getLayout("umeng_comm_user_info_layout");
    }


    @Override
    protected void ListenerSet() {
        mFollowToggleButton.setOnClickListener(new LoginOnViewClickListener() {

            @Override
            protected void doAfterLogin(View v) {
                mFollowToggleButton.setClickable(false);
                // true为选中状态为已关注，此时显示文本为“取消关注”；false代表未关注，此时显示文本为“关注”
                if (!mUser.isFollowed) {
                    mPresenter.followUser(mResultListener);
                } else {
                    mPresenter.cancelFollowUser(mResultListener);
                }
            }
        });
                mAlbumTextView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                jumpToActivityWithUid(AlbumActivity.class);
            }
        });
                mTopicTextView.setOnClickListener(new OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        jumpToActivityWithUid(FollowedTopicActivity.class);
                    }
                });
    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == ResFinder.getId("umeng_comm_posted_layout")) {// 已发消息
            if (mCurrentFragment instanceof PostedFeedsFragment) { // 已经处于当前页面，判断是否需要滚动到起始位置
                mPostedFragment.executeScrollToTop();
            } else {
                showFragment(mPostedFragment);
            }
            moveTabCurosr(0);
        } else if (id == ResFinder.getId("umeng_comm_follow_user_layout")) {// 关注用户
            if (mFolloweredUserFragment == null) {
                mFolloweredUserFragment = FollowedUserFragment.newInstance(mUser.id);
                mFolloweredUserFragment.setOnAnimationResultListener(mListener);
                mFolloweredUserFragment.setOnResultListener(mFollowListener);
            }
            if (mCurrentFragment instanceof FollowedUserFragment
                    && !(mCurrentFragment instanceof FansFragment)) {
                mFolloweredUserFragment.executeScrollTop();
            } else {
                showFragment(mFolloweredUserFragment);
            }
            moveTabCurosr(1);
        } else if (id == ResFinder.getId("umeng_comm_my_fans_layout")) { // 我的粉丝
            if (mFansFragment == null) {
                mFansFragment = FansFragment.newFansFragment(mUser.id);
                mFansFragment.setOnAnimationResultListener(mListener);
                mFansFragment.setOnResultListener(mFansListener);
            }
            if (mCurrentFragment instanceof FansFragment) {
                mFansFragment.executeScrollTop();
            } else {
                showFragment(mFansFragment);
            }
            moveTabCurosr(2);
        } else if (id == ResFinder.getId("umeng_comm_title_back_btn")) { // 返回
            this.finish();
        }
        changeSelectedText();
    }







    /**
     * 修改文本颜色 </br>
     */
    protected void changeSelectedText() {
        if ((mCurrentFragment instanceof PostedFeedsFragment)) {
            mFansCountTextView.setTextColor(Color.BLACK);
            changeTextColor(mSelectedColor, Color.BLACK, Color.BLACK);
        } else if ((mCurrentFragment instanceof FansFragment)) {
            changeTextColor(Color.BLACK, Color.BLACK, mSelectedColor);
        } else if ((mCurrentFragment instanceof FollowedUserFragment)) {
            changeTextColor(Color.BLACK, mSelectedColor, Color.BLACK);
        }
    }






    @Override
    protected void ReceiverComplete(CommUser user ,boolean follow) {
        if (mFolloweredUserFragment != null) {
            mFolloweredUserFragment.updateFollowedState(user.id, follow);
        }
        if (mFansFragment != null) {
            mFansFragment.updateFollowedState(user.id, follow);
        }
    }


}
