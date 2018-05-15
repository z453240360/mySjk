package com.dd.mylibrary.bean;

/**
 * Created by Administrator on 2018/3/12 0012.
 */

public class TextBean {
    public TextBean() {
    }

    public TextBean(int colours, int size, String name) {
        this.colours = colours;
        this.size = size;
        this.name = name;
    }

    public int getColours() {
        return colours;
    }

    public float getSize() {
        return size;
    }

    public String getName() {
        return name;
    }

    private int colours;

    public int getBackgroundColours() {
        return backgroundColours;
    }

    public void setBackgroundColours(int backgroundColours) {
        this.backgroundColours = backgroundColours;
    }

    private int backgroundColours;
    private float size;
    private String name;
    private boolean isImpression;

    public boolean isImpression() {
        return isImpression;
    }

    public void setImpression(boolean impression) {
        isImpression = impression;
    }


    public void setColours(int colours) {
        this.colours = colours;
    }

    public void setSize(float size) {
        this.size = size;
    }

    public void setName(String name) {
        this.name = name;
    }
}
