package com.lhy.xposed.mhzs.bean;

import cn.bmob.v3.BmobObject;

public class Feedback extends BmobObject {
    private String detail;
    private String contact;
    private String device;
    private String framework;
    private String version;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getDevice() {
        return device;
    }

    public void setDevice(String device) {
        this.device = device;
    }

    public String getFramework() {
        return framework;
    }

    public void setFramework(String framework) {
        this.framework = framework;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return "Feedback{" +
                "detail='" + detail + '\'' +
                ", contact='" + contact + '\'' +
                ", device='" + device + '\'' +
                ", framework='" + framework + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
