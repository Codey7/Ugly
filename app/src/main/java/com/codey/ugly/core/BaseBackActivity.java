package com.codey.ugly.core;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.codey.ugly.R;

/**
 * Created by Mr.Codey on 2016/4/26.
 */
public abstract class BaseBackActivity extends BaseAppCompatActivity
{
    private TextView textView;

    @Override
    protected void initViews(Bundle savedInstanceState)
    {
        textView= (TextView) findViewById(R.id.tv_back);
        textView.setText(getBackTitle());
        textView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        });
    }
    public abstract String getBackTitle();
}
