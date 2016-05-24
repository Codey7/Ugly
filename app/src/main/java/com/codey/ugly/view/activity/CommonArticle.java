package com.codey.ugly.view.activity;

import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.codey.ugly.R;
import com.codey.ugly.core.BaseBackActivity;

/**
 * Created by Mr.Codey on 2016/4/2.
 */
public class CommonArticle extends BaseBackActivity
{
    private WebView mwvArticle;
    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        super.initViews(savedInstanceState);
        Log.i("init","done");
        mwvArticle= (WebView) findViewById(R.id.wv_article);
        WebSettings mWebSettings=mwvArticle.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mwvArticle.loadUrl("file:///android_asset/html/common_sense.html");
    }
    @Override
    protected int getLayoutId()
    {
        return R.layout.article_details;
    }

    @Override
    public String getBackTitle()
    {
        return "常识详情";
    }
}
