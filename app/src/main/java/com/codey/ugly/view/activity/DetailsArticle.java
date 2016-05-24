package com.codey.ugly.view.activity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.codey.ugly.R;
import com.codey.ugly.core.BaseBackActivity;


/**
 * Created by Mr.Codey on 2016/4/2.
 */
public class DetailsArticle extends BaseBackActivity
{
    private WebView mwvArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setWebview();
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.article_details;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        super.initViews(savedInstanceState);
        mwvArticle= (WebView) findViewById(R.id.wv_article);
    }

    @Override
    public String getBackTitle()
    {
        return "攻略详情";
    }

    public void setWebview()
    {
        WebSettings mWebSettings=mwvArticle.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mwvArticle.loadUrl("file:///android_asset/html/guide_detail_close.html");
        //根据url加载不同的文章
        /*Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        mwvArticle.loadUrl(url);*/
    }
}
