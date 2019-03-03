package com.lhy.xposed.mhzs.bean;

import com.google.gson.annotations.SerializedName;

/**
 * VideoInfoBean
 * 麻花影视 2.5.0之前
 */
@Deprecated
public class VideoInfoBean {
    private String code;
    @SerializedName(value = "data")
    private DataBean dataBean;
    private String description;
    private String imgAddr;
    private boolean success;
    private String videoAddr;

    public DataBean getDataBean() {
        return this.dataBean;
    }

    public void setDataBean(DataBean dataBean) {
        this.dataBean = dataBean;
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String str) {
        this.code = str;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String str) {
        this.description = str;
    }

    public String getImgAddr() {
        return this.imgAddr;
    }

    public void setImgAddr(String str) {
        this.imgAddr = str;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean z) {
        this.success = z;
    }

    public String getVideoAddr() {
        return this.videoAddr;
    }

    public void setVideoAddr(String str) {
        this.videoAddr = str;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("VideoInfo{dataBean=");
        stringBuilder.append(this.dataBean);
        stringBuilder.append(", code='");
        stringBuilder.append(this.code);
        stringBuilder.append('\'');
        stringBuilder.append(", description='");
        stringBuilder.append(this.description);
        stringBuilder.append('\'');
        stringBuilder.append(", imgAddr='");
        stringBuilder.append(this.imgAddr);
        stringBuilder.append('\'');
        stringBuilder.append(", success=");
        stringBuilder.append(this.success);
        stringBuilder.append(", videoAddr='");
        stringBuilder.append(this.videoAddr);
        stringBuilder.append('\'');
        stringBuilder.append('}');
        return stringBuilder.toString();
    }
}