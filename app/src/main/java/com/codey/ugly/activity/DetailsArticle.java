package com.codey.ugly.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.codey.ugly.R;


/**
 * Created by Mr.Codey on 2016/4/2.
 */
public class DetailsArticle extends Activity
{
    private TextView mtvBack;
    private WebView mwvArticle;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_details);
        init();
    }
    private void init()
    {
        mtvBack= (TextView) findViewById(R.id.tv_back);
        mtvBack.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
        mwvArticle= (WebView) findViewById(R.id.wv_article);
        WebSettings mWebSettings=mwvArticle.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        mwvArticle.loadUrl("file:///android_asset/html/guide_detail_close.html");
    }
}
