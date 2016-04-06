package com.umeng.commm.ui.fragments;

import com.umeng.comm.core.beans.FeedItem;
import com.umeng.comm.core.beans.Topic;
import com.umeng.comm.ui.imagepicker.presenter.impl.RecommendTopicFeedPresenter;
import com.umeng.comm.ui.imagepicker.util.Filter;

import java.util.Iterator;
import java.util.List;

/**
 * Created by wangfei on 15/12/2.
 */
public class RecommendTopicFeedFragment extends TopicFeedFragment{
    public static RecommendTopicFeedFragment newTopicFeedFrmg(final Topic topic) {
        RecommendTopicFeedFragment topicFeedFragment = new RecommendTopicFeedFragment();
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
    protected RecommendTopicFeedPresenter createPresenters() {
        RecommendTopicFeedPresenter presenter = new RecommendTopicFeedPresenter(this);
        presenter.setId(mTopic.id);
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
