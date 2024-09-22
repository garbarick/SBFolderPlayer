package ru.net.serbis.folder.player.util;

import android.app.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.os.*;
import android.view.*;

public class HelpTool
{
    private static final HelpTool instance = new HelpTool();
    private Handler hadler = new Handler(Looper.getMainLooper());

    public static HelpTool get()
    {
        return instance;
    }

    public int getPercent(long max, long cur)
    {
        Double percent = 100.0 / max * cur;
        return percent.intValue();
    }

    public String formatTime(int millisec)
    {
        long second = (millisec / 1000) % 60;
        long minute = (millisec / (1000 * 60)) % 60;
        long hour = (millisec / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getNum(int position, int count)
    {
        if (count == 0)
        {
            return "";
        }
        int length = String.valueOf(count).length();
        return String.format("%0" + length + "d", position + 1);
    }

    public void runOnUiThread(Runnable run)
    {
        hadler.post(run);
    }

    public void setColorTransparent(Activity context, int transparent)
    {
        Window window = context.getWindow();
        int color = getColorTransparent(transparent);
        window.setBackgroundDrawable(new ColorDrawable(color));
    }

    public int getColorTransparent(int transparent)
    {
        return Color.argb(255 - transparent, 50, 50, 50);
    }
}
