package ca.synx.mississaugatransit.models;

import ca.synx.mississaugatransit.interfaces.IListItem;

public class ListItem implements IListItem {
    private String mText;
    private int mImageResource;

    public ListItem(String text, int imageResource) {
        this.mText = text;
        this.mImageResource = imageResource;
    }

    public String getText() {
        return mText;
    }

    public int getImageResource() {
        return mImageResource;
    }
}