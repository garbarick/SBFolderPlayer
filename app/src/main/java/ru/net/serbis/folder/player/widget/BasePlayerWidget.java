package ru.net.serbis.folder.player.widget;

import android.appwidget.*;
import android.content.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.listener.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.folder.player.view.*;

public abstract class BasePlayerWidget extends AppWidgetProvider
{
    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] ids)
    {
        super.onUpdate(context, manager, ids);
        for (int id : ids)
        {
            get().add(id, this);
            onUpdate(context, manager, id);
        }
    }

    public void onUpdate(Context context, AppWidgetManager manager, int id)
    {
        RemoteViews views = getRemoteView(context, manager, id);
        if (views == null)
        {
            return;
        }

        views.setOnClickPendingIntent(R.id.layout, PlayerView.get().getMain());
        PlayerView.get().setHeader(views);
        PlayerView.get().setTrack(views, Player.get().getPosition());
        PlayerView.get().setPlayImage(views, get().isPlay());

        initView(views);

        manager.updateAppWidget(id, views);
    }

    protected RemoteViews getRemoteView(Context context, AppWidgetManager manager, int id)
    {
        AppWidgetProviderInfo info = manager.getAppWidgetInfo(id);
        if (info == null)
        {
            return null;
        }
        int layoutId = info.initialLayout;
        return new RemoteViews(context.getPackageName(), layoutId);
    }

    protected abstract void initView(RemoteViews views)

    @Override
    public void onDeleted(Context context, int[] ids)
    {
        for (int id : ids)
        {
            get().delete(id);
        }
        super.onDeleted(context, ids);
    }

    protected WidgetListener get()
    {
        return WidgetListener.get();
    }

    public abstract boolean isProgressSupport()
}
