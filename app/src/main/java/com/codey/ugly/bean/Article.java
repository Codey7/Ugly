package com.codey.ugly.bean;

/**
 * Created by Mr.Codey on 2016/4/1.
 */
public class Article
{
    private String title;
    private String url;
    private int status;

    public Article(String title, String url)
    {
        this.title = title;
        this.url = url;
    }

    public String getTitle()
    {
        return title;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public int getStatus()
    {
        return status;
    }

    public void setStatus(int status)
    {
        this.status = status;
    }
}
