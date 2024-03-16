package ru.net.serbis.folder.player.view;

import android.app.*;
import android.content.*;
import android.widget.*;
import java.io.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.receiver.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.folder.player.util.*;

public class PlayerView extends Util
{
    private static final PlayerView instance = new PlayerView();

    public static PlayerView get()
    {
        return instance;
    }

    public void setAction(RemoteViews views, int buttonId, String action)
    {
        views.setOnClickPendingIntent(buttonId, PlayerReceiver.getPending(context, action));
    }

    public void setBigViewActions(RemoteViews views)
    {
        setAction(views, R.id.previous, PlayerActions.PREVIOUS);
        setAction(views, R.id.skip_left, PlayerActions.SKIP_LEFT);
        setAction(views, R.id.play_pause, PlayerActions.PLAY_PAUSE);
        setAction(views, R.id.skip_right, PlayerActions.SKIP_RIGHT);
        setAction(views, R.id.next, PlayerActions.NEXT);
    }

    public void setSmallViewActions(RemoteViews views)
    {
        setAction(views, R.id.previous, PlayerActions.PREVIOUS);
        setAction(views, R.id.play_pause, PlayerActions.PLAY_PAUSE);
        setAction(views, R.id.next, PlayerActions.NEXT);
    }

    public void setHeader(RemoteViews views)
    {
        views.setImageViewResource(R.id.icon, R.drawable.app);
        views.setTextViewText(R.id.app_name, context.getResources().getString(R.string.app));
    }

    public void setTrack(RemoteViews views, int position)
    {
        int count = Player.get().getFiles().size();
        views.setTextViewText(R.id.num, UITool.get().getNum(position, count));
        views.setTextViewText(R.id.count, String.valueOf(count));
        if (count > 0)
        {
            String path = count > 0 ? Player.get().getFiles().get(position) : null;
            views.setTextViewText(R.id.path, path);
            views.setTextViewText(R.id.name, getTrackName(path));
        }
    }

    private String getTrackName(String path)
    {
        if (path == null)
        {
            return null;
        }
        return new File(path).getName();
    }

    public void setPlayImage(RemoteViews views, boolean play)
    {
        int imageId = play ?
            android.R.drawable.ic_media_pause :
            android.R.drawable.ic_media_play;
        views.setImageViewResource(R.id.play_pause, imageId);
    }

    public void progress(RemoteViews views, int progress, int duration)
    {
        views.setProgressBar(R.id.progress, duration, progress, false);
    }

    public void trackSeek(RemoteViews views, int progress)
    {
        views.setTextViewText(R.id.track_seek, UITool.get().formatTime(progress));
    }

    public void trackLength(RemoteViews views, int duration)
    {
        views.setTextViewText(R.id.track_lenght, UITool.get().formatTime(duration));
    }
    
    public PendingIntent getMain()
    {
        return PendingIntent.getActivity(context, 0, new Intent(context, Main.class), PendingIntent.FLAG_UPDATE_CURRENT);
    }

    public void setBackground(RemoteViews views, int ... ids)
    {
        int color = UITool.get().getColorTransparent(Params.WIDGET_TRANSPARENCY.getValue());
        for (int id : ids)
        {
            views.setInt(id, "setBackgroundColor", color);
        }
    }

    public void setBigViewBackground(RemoteViews views)
    {
        setBackground(
            views,
            R.id.layout,
            R.id.previous,
            R.id.skip_left,
            R.id.play_pause,
            R.id.skip_right,
            R.id.next);
    }

    public void setSmallViewBackground(RemoteViews views)
    {
        setBackground(
            views,
            R.id.layout,
            R.id.previous,
            R.id.play_pause,
            R.id.next);
    }
}
