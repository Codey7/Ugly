package com.codey.ugly.view.navmenu;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.codey.ugly.R;
import com.codey.ugly.core.BaseBackActivity;

/**
 * Created by Mr.Codey on 2016/4/26.
 */
public class PointMall extends BaseBackActivity
{
    private WebView mwvArticle;
    @Override
    public String getBackTitle()
    {
        return "积分商城";
    }

    @Override
    protected int getLayoutId()
    {
        return R.layout.point_mall;
    }

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        super.initViews(savedInstanceState);
        mwvArticle= (WebView) findViewById(R.id.wv_article);
        setWebview();
    }
    public void setWebview()
    {
        WebSettings mWebSettings=mwvArticle.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mwvArticle.loadUrl("file:///android_asset/exchange.html");
        //根据url加载不同的文章
        /*Intent intent=getIntent();
        String url=intent.getStringExtra("url");
        mwvArticle.loadUrl(url);*/
    }
}
