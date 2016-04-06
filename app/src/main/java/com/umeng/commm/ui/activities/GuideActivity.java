package com.umeng.commm.ui.activities;

import android.content.DialogInterface;

import com.umeng.comm.ui.imagepicker.activities.GuideBaseActivity;
import com.umeng.commm.ui.fragments.RecommendTopicFragment;
import com.umeng.commm.ui.fragments.RecommendUserFragment;

/**
 * Created by wangfei on 16/1/25.
 */
public class GuideActivity extends GuideBaseActivity{
    @Override
    protected void showTopicFragment() {
        RecommendTopicFragment topicRecommendDialog = RecommendTopicFragment.newRecommendTopicFragment();
        topicRecommendDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                showRecommendUserFragment();
            }

        });
        addFragment(mContainer, topicRecommendDialog);
    }

    @Override
    protected void showRecommendUserFragment() {
        setFragmentContainerId(mContainer);
        RecommendUserFragment recommendUserFragment = new RecommendUserFragment();
        replaceFragment(mContainer, recommendUserFragment);
    }
}
