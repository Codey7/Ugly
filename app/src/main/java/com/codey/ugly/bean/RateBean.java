package com.codey.ugly.bean;

/**
 * Created by Mr.Codey on 2016/4/5.
 */
public class RateBean
{
    private String headimgurl;
    private String username;
    private String pubtime;
    private String details;
    private String rateimgurl;
    private float score;


    public RateBean(float score, String pubtime, String details, String rateimgurl)
    {
        this.score = score;
        this.pubtime = pubtime;
        this.details = details;
        this.rateimgurl = rateimgurl;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getHeadimgurl()
    {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl)
    {
        this.headimgurl = headimgurl;
    }

    public String getPubtime()
    {
        return pubtime;
    }

    public void setPubtime(String pubtime)
    {
        this.pubtime = pubtime;
    }

    public float getScore()
    {
        return score;
    }

    public void setScore(float score)
    {
        this.score = score;
    }

    public String getDetails()
    {
        return details;
    }

    public void setDetails(String details)
    {
        this.details = details;
    }

    public String getRateimgurl()
    {
        return rateimgurl;
    }

    public void setRateimgurl(String rateimgurl)
    {
        this.rateimgurl = rateimgurl;
    }
}
