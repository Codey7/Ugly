package com.umeng.commm.ui.fragments;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.ui.imagepicker.presenter.impl.LatestTopicFeedPresenter;
import com.umeng.comm.ui.imagepicker.util.Filter;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wangfei on 15/12/2.
 */
public class LastestTopicFeedFragment extends TopicFeedFragment{
    public static LastestTopicFeedFragment newTopicFeedFrmg(final Topic topic) {
        LastestTopicFeedFragment topicFeedFragment = new LastestTopicFeedFragment();
        topicFeedFragment.mTopic = topic;
        topicFeedFragment.mFeedFilter = new Filter<FeedItem>() {

            @Override
            public List<FeedItem> doFilte(List<FeedItem> newItems) {
                if (newItems == null || newItems.size() == 0) {
                    return newItems;
                }
                Iterator<FeedItem> iterator = newItems.iterator();
                while (iterator.hasNext()) {
                    List<Topic> topics = iterator.next().topics;
                    if (!topics.contains(topic)) {
                        iterator.remove();
                    }
                }
                return newItems;
            }
        };
        return topicFeedFragment;
    }
    @Override
    protected LatestTopicFeedPresenter createPresenters() {
        LatestTopicFeedPresenter presenter = new LatestTopicFeedPresenter(this);
        presenter.setId(mTopic.id);
        presenter.setIsShowTopFeeds(false);
        return presenter;
    }
    @Override
    protected void initWidgets() {
        super.initWidgets();

    }
    @Override
    protected void postFeedComplete(FeedItem feedItem) {

    }
}
