package com.lhy.xposed.mhzs.bean;


import com.google.gson.annotations.SerializedName;

/**
 * M3u8FormatBean
 * 麻花影视 2.5.0之前
 */
@Deprecated
public class M3u8FormatBean {
    @SerializedName(value = "1080P")
    private String _$1080P;
    @SerializedName(value = "360P")
    private String _$360P;
    @SerializedName(value = "480P")
    private String _$480P;
    @SerializedName(value = "720P")
    private String _$720P;
    private String free;

    public String getM3u8Format(int i) {
        if (i == 0) {
            if (get_$360P() != null) {
                return get_$360P();
            }
            return null;
        } else if (i == 1) {
            if (get_$480P() != null) {
                return get_$480P();
            }
            return null;
        } else if (i == 2) {
            if (get_$720P() != null) {
                return get_$720P();
            }
            return null;
        } else if (i == 3) {
            if (get_$1080P() != null) {
                return get_$1080P();
            }
            return null;
        } else if (i != -1 || getFree() == null) {
            return null;
        } else {
            return getFree();
        }
    }

    public String get_$720P() {
        return this._$720P;
    }

    public void set_$720P(String str) {
        this._$720P = str;
    }

    public String getFree() {
        return this.free;
    }

    public void setFree(String str) {
        this.free = str;
    }

    public String get_$480P() {
        return this._$480P;
    }

    public void set_$480P(String str) {
        this._$480P = str;
    }

    public String get_$360P() {
        return this._$360P;
    }

    public void set_$360P(String str) {
        this._$360P = str;
    }

    public String get_$1080P() {
        return this._$1080P;
    }

    public void set_$1080P(String str) {
        this._$1080P = str;
    }
}