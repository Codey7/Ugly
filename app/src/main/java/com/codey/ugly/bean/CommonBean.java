package com.codey.ugly.bean;

/**
 * Created by Mr.Codey on 2016/4/1.
 */
public class CommonBean
{
    private String url;
    private String title;

    private int thumbnum;
    private int bookmarknum;

    public CommonBean(String url, String title, int thumbnum, int bookmarknum)
    {
        this.url = url;
        this.title = title;
        this.thumbnum = thumbnum;
        this.bookmarknum = bookmarknum;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
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

    public int getThumbnum()
    {
        return thumbnum;
    }

    public void setThumbnum(int thumbnum)
    {
        this.thumbnum = thumbnum;
    }

    public int getBookmarknum()
    {
        return bookmarknum;
    }

    public void setBookmarknum(int bookmarknum)
    {
        this.bookmarknum = bookmarknum;
    }
}
