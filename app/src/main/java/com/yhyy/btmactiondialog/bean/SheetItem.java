package com.yhyy.btmactiondialog.bean;

import android.graphics.drawable.Drawable;

import com.yhyy.btmactiondialog.inter.OnSheetItemClickListener;

public class SheetItem {
    Drawable drawableLeft;
    String name;
    Integer color;
    OnSheetItemClickListener onSheetItemClickListener;

    public SheetItem(String name, Integer color, OnSheetItemClickListener onSheetItemClickListener) {
        this.name = name;
        this.color = color;
        this.onSheetItemClickListener = onSheetItemClickListener;
    }

    public SheetItem(Drawable drawableLeft, String name, Integer color, OnSheetItemClickListener onSheetItemClickListener) {
        this.drawableLeft = drawableLeft;
        this.name = name;
        this.color = color;
        this.onSheetItemClickListener = onSheetItemClickListener;
    }

    public Drawable getDrawableLeft() {
        return drawableLeft;
    }

    public void setDrawableLeft(Drawable drawableLeft) {
        this.drawableLeft = drawableLeft;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getColor() {
        return color;
    }

    public void setColor(Integer color) {
        this.color = color;
    }

    public OnSheetItemClickListener getOnSheetItemClickListener() {
        return onSheetItemClickListener;
    }

    public void setOnSheetItemClickListener(OnSheetItemClickListener onSheetItemClickListener) {
        this.onSheetItemClickListener = onSheetItemClickListener;
    }
}
