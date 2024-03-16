package ru.net.serbis.folder.player.widget;

import android.widget.*;
import ru.net.serbis.folder.player.view.*;

public class BigPlayerWidget extends BasePlayerWidget
{
    @Override
    protected void initView(RemoteViews views)
    {
        PlayerView.get().setBigViewBackground(views);
        PlayerView.get().progress(views, get().getProgress(), get().getDuration());
        PlayerView.get().trackLength(views, get().getDuration());
        PlayerView.get().trackSeek(views, get().getProgress());
        PlayerView.get().setBigViewActions(views);
    }

    @Override
    public boolean isProgressSupport()
    {
        return true;
    }
}
