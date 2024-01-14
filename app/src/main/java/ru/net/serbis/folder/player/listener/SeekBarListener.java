package ru.net.serbis.folder.player.listener;

import android.widget.*;
import ru.net.serbis.folder.player.util.*;

public class SeekBarListener implements SeekBar.OnSeekBarChangeListener
{
    @Override
    public void onProgressChanged(SeekBar seek, int progress, boolean byUser)
    {
        if (byUser)
        {
            Player.get().seek(progress);
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
