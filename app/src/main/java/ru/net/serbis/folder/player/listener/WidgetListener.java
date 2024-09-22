package ru.net.serbis.folder.player.listener;

import android.appwidget.*;
import android.content.*;
import java.util.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.folder.player.widget.*;
import ru.net.serbis.utils.*;

public class WidgetListener extends Util implements Player.PlayerListener
{
    private static final WidgetListener instance = new WidgetListener();
    private Map<Integer, BasePlayerWidget> widgets = Collections.synchronizedMap(new HashMap<Integer, BasePlayerWidget>());
    private int duration, progress;
    private boolean play;

    public int getDuration()
    {
        return duration;
    }

    public int getProgress()
    {
        return progress;
    }

    public boolean isPlay()
    {
        return play;
    }

    public static WidgetListener get()
    {
        return instance;
    }

    public void add(int id, BasePlayerWidget item)
    {
        widgets.put(id, item);
        if (!widgets.isEmpty() && !Player.get().isRegistered(this))
        {
            Player.get().setListener(this);
        }
    }

    public void delete(int id)
    {
        widgets.remove(id);
        if (widgets.isEmpty())
        {
            Player.get().clearListener(this);
        }
    }

    private AppWidgetManager getManager()
    {
        return AppWidgetManager.getInstance(context);
    }

    public void update(boolean checkSupport)
    {
        for (Map.Entry<Integer, BasePlayerWidget> entry : widgets.entrySet())
        {
            int id = entry.getKey();
            BasePlayerWidget item = entry.getValue();
            if (checkSupport && !item.isProgressSupport())
            {
                continue;
            }
            item.onUpdate(context, getManager(), id);
        }
    }

    @Override
    public void playerProgress(int progress)
    {
        this.progress = progress;
        update(true);
    }

    @Override
    public void playerDuration(int duration)
    {
        this.duration = duration;
        update(true);
    }

    @Override
    public void playerPlay()
    {
        play= true;
        update(false);
    }

    @Override
    public void playerPause()
    {
        play = false;
        update(false);
    }

    @Override
    public void setPosition(int position)
    {
        update(false);
    }

    @Override
    public void startFileLoading()
    {
    }

    @Override
    public void fileLoadProgress(int progress)
    {
    }

    @Override
    public void finishFileLoading()
    {
    }

    @Override
    public void set(Context context)
    {
        super.set(context);
        initWidgets(
            BigPlayerWidget.class,
            SmallPlayerWidget.class);
    }

    private void initWidgets(Class ... classes)
    {
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        for (Class classItem : classes)
        {
            ComponentName name = new ComponentName(context, classItem.getName());
            int[] ids = manager.getAppWidgetIds(name);
            Intent updateIntent = new Intent();
            updateIntent.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);
            updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
            context.sendBroadcast(updateIntent);
        }
    }
}
