package ru.net.serbis.folder.player.widget;

import android.widget.*;
import ru.net.serbis.folder.player.view.*;

public class SmallPlayerWidget extends BasePlayerWidget
{
    @Override
    protected void initView(RemoteViews views)
    {
        PlayerView.get().setSmallViewBackground(views); 
        PlayerView.get().setSmallViewActions(views);    
    }

    @Override
    public boolean isProgressSupport()
    {
        return false;
    }
}
