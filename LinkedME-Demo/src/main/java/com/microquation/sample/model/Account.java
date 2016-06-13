package com.microquation.sample.model;

import java.io.Serializable;

public class Account implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 7619935180458969539L;
    public static final String SITE_ID_APPS = "1";
    public static final String SITE_ID_FEACTURES = "2";
    public static final String SITE_ID_DEMO = "3";
    public static final String SITE_ID_INTROCTION = "4";

    private String siteId;
    private int iconResourceId;
    private String name;

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public int getIconResourceId() {
        return iconResourceId;
    }

    public void setIconResourceId(int iconResourceId) {
        this.iconResourceId = iconResourceId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
