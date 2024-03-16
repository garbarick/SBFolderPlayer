package ru.net.serbis.folder.player.notification;

import android.app.*;
import android.content.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.folder.player.view.*;

public class PlayerNotification extends BaseNotification implements Player.PlayerListener
{
    private RemoteViews smallViews;
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

        setContentIntent(PlayerView.get().getMain());
        smallViews = new RemoteViews(context.getPackageName(), R.layout.widget_small_player);
        bigViews = new RemoteViews(context.getPackageName(), R.layout.widget_big_player);

        setHeader();
        setButtons();

        Player.get().setListener(this);
        PlayerView.get().setTrack(smallViews, Player.get().getPosition());
        PlayerView.get().setTrack(bigViews, Player.get().getPosition());
        setPriority(Notification.PRIORITY_MAX);
    }

    private void setHeader()
    {
        PlayerView.get().setHeader(smallViews);
        PlayerView.get().setHeader(bigViews);
    }

    private void setButtons()
    {
        PlayerView.get().setBigViewActions(bigViews);
        PlayerView.get().setSmallViewActions(smallViews);

        if (old)
        {
            setContent(smallViews);
        }
        else
        {
            setCustomContentView(smallViews);
            setCustomBigContentView(bigViews);
        }
    }

    @Override
    public void playerProgress(int progress)
    {
        this.progress = progress;
        PlayerView.get().progress(bigViews, progress, duration);
        PlayerView.get().trackSeek(bigViews, progress);
        update();
    }

    @Override
    public void playerDuration(int duration)
    {
        this.duration = duration;
        PlayerView.get().progress(bigViews, progress, duration);
        PlayerView.get().trackLength(bigViews, duration);
        update();
    }

    @Override
    public void playerPlay()
    {
        PlayerView.get().setPlayImage(smallViews, true);
        PlayerView.get().setPlayImage(bigViews, true);
        update();
    }

    @Override
    public void playerPause()
    {
        PlayerView.get().setPlayImage(smallViews, false);
        PlayerView.get().setPlayImage(bigViews, false);
        update();
    }

    @Override
    public void setPosition(int position)
    {
        PlayerView.get().setTrack(smallViews, position);
        PlayerView.get().setTrack(bigViews, position);
        update();
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
