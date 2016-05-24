package com.codey.ugly.bean;

/**
 * Created by Mr.Codey on 2016/4/1.
 */
public class Article
{
    private int contentId;
    private String title;
    private String url;
    private String detailsUrl;
    private int thumbNum;//阅读量
    private int bookmarkNum;//收藏量

    public Article(int contentId, String title, String url,String detailsUrl)
    {
        this.contentId=contentId;
        this.title = title;
        this.url = url;
        this.detailsUrl=detailsUrl;
    }
    public String getDetailsUrl()
    {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl)
    {
        this.detailsUrl = detailsUrl;
    }

    public int getContentId()
    {
        return contentId;
    }

    public void setContentId(int contentId)
    {
        this.contentId = contentId;
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

}
