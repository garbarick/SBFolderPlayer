package ru.net.serbis.folder.player.view;

import android.content.*;
import android.util.*;
import android.widget.*;

public class ImageButtonExt extends ImageButton
{
    protected int imageResourceId;

    public ImageButtonExt(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public int getImageResourceId()
    {
        return imageResourceId;
    }

    @Override
    public void setImageResource(int imageResourceId)
    {
        if (this.imageResourceId == imageResourceId)
        {
            return;
        }
        super.setImageResource(imageResourceId);
        this.imageResourceId = imageResourceId;
    }
}
