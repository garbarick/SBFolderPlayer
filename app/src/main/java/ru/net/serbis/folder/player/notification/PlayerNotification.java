package ru.net.serbis.folder.player.notification;

import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import java.io.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.receiver.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.folder.player.util.*;

public class PlayerNotification extends BaseNotification implements Player.PlayerListener
{
    private RemoteViews views;
    private RemoteViews bigViews;
    private int progress;
    private int duration;

    public PlayerNotification(Context context)
    {
        super(context, R.string.player);
        update();
    }

    @Override
    protected void init()
    {
        super.init();

        views = new RemoteViews(context.getPackageName(), R.layout.notification_player);
        bigViews = new RemoteViews(context.getPackageName(), R.layout.notification_big_player);
        
        setHeader();
        setButtons();

        Player.get().setListener(this);
        setTrack(views, Player.get().getPosition());
        setTrack(bigViews, Player.get().getPosition());
        setPriority(Notification.PRIORITY_MAX);
    }

    private void setHeader()
    {
        setHeader(views);
        setHeader(bigViews);
    }

    private void setHeader(RemoteViews views)
    {
        views.setImageViewResource(R.id.icon, R.drawable.app);
        views.setTextViewText(R.id.app_name, context.getResources().getString(R.string.app));
    }

    private void setButtons()
    {
        setAction(bigViews, R.id.previous, PlayerActions.PREVIOUS);
        setAction(bigViews, R.id.skip_left, PlayerActions.SKIP_LEFT);
        setAction(views, R.id.play_pause, PlayerActions.PLAY_PAUSE);
        setAction(bigViews, R.id.play_pause, PlayerActions.PLAY_PAUSE);
        setAction(bigViews, R.id.skip_right, PlayerActions.SKIP_RIGHT);
        setAction(bigViews, R.id.next, PlayerActions.NEXT);
        setAction(views, R.id.close, PlayerActions.CLOSE);
        setAction(bigViews, R.id.close, PlayerActions.CLOSE);

        if (old)
        {
            setContent(views);
        }
        else
        {
            setCustomContentView(views);
            setCustomBigContentView(bigViews);
        }
    }

    private void setAction(RemoteViews views, int buttonId, String action)
    {
        views.setOnClickPendingIntent(buttonId, PlayerReceiver.getPending(context, action));
    }

    @Override
    public void playerProgress(int progress)
    {
        this.progress = progress;
        bigViews.setProgressBar(R.id.progress, duration, progress, false);
        bigViews.setTextViewText(R.id.track_seek, UITool.get().formatTime(progress));
        update();
    }

    @Override
    public void playerDuration(int duration)
    {
        this.duration = duration;
        bigViews.setProgressBar(R.id.progress, duration, progress, false);
        bigViews.setTextViewText(R.id.track_lenght, UITool.get().formatTime(duration));
        update();
    }

    @Override
    public void playerPlay()
    {
        setPlayImage(views, false);
        setPlayImage(bigViews, false);
        update();
    }

    @Override
    public void playerPause()
    {
        setPlayImage(views, true);
        setPlayImage(bigViews, true);
        update();
    }

    private void setPlayImage(RemoteViews views, boolean play)
    {
        int imageId = play ?
            android.R.drawable.ic_media_play :
            android.R.drawable.ic_media_pause;
        views.setImageViewResource(R.id.play_pause, imageId);
    }

    @Override
    public void setPosition(int position)
    {
        setTrack(views, position);
        setTrack(bigViews, position);
        update();
    }
    
    private void setTrack(RemoteViews views, int position)
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
    public Notification build()
    {
        Notification result = super.build();
        if (old)
        {
            result.bigContentView = bigViews;
        }
        return result;
    }
    
    @Override
    public void cancel()
    {
        Player.get().clearListener(this);
        super.cancel();
    }
}
