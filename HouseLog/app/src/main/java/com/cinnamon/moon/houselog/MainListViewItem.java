package com.cinnamon.moon.houselog;

import android.graphics.drawable.Drawable;

/**
 * Created by Cinnmon on 2017. 6. 7..
 */

public class MainListViewItem {
    private Drawable iconDrawable;
    private String titleStr;
    private String descStr;
    private boolean value;

    public void setIcon(Drawable icon) {
        iconDrawable = icon;
    }

    public void setTitle(String title) {
        titleStr = title;
    }

    public void setDesc(String desc) {
        descStr = desc;
    }

    public void setValue(boolean value){this.value = value;}

    public Drawable getIcon() {
        return this.iconDrawable;
    }

    public String getTitle() {
        return this.titleStr;
    }

    public String getDesc() {
        return this.descStr;
    }

    public boolean getValue(){return this.value;}
}