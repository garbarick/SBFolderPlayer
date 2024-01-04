package ru.net.serbis.folder.player.listener;

import android.widget.*;
import ru.net.serbis.folder.player.activity.*;

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener
{
    private Main activity;

    public SeekBarListener(Main activity)
    {
        this.activity = activity;
    }

    @Override
    public void onProgressChanged(SeekBar seek, int progress, boolean byUser)
    {
        if (byUser)
        {
            activity.getPlayer().seek(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seek)
    {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seek)
    {
    }
}
