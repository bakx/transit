package ca.synx.mississaugatransit.models;

import ca.synx.mississaugatransit.interfaces.IDrawerItem;

public class DrawerItem implements IDrawerItem {
    private String mText;
    private int mImageResource;

    public DrawerItem(String text, int imageResource) {
        this.mText = text;
        this.mImageResource = imageResource;
    }

    public String getDrawerItemText() {
        return mText;
    }

    public int getDrawerItemImageResource() {
        return mImageResource;
    }
}