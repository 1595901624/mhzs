package com.lhy.xposed.mhzs.bean;

import com.google.gson.annotations.SerializedName;

public class DataBean {

    private String m3u8PlayUrl;

    @SerializedName(value = "m3u8Format")
    private M3u8FormatBean m3u8FormatBean;

    public void setM3u8PlayUrl(String m3u8PlayUrl) {
        this.m3u8PlayUrl = m3u8PlayUrl;
    }

    public String getM3u8PlayUrl() {
        return m3u8PlayUrl;
    }

    public void setM3u8Format(M3u8FormatBean m3u8Format) {
        this.m3u8FormatBean = m3u8Format;
    }

    public M3u8FormatBean getM3u8Format() {
        return m3u8FormatBean;
    }

}
