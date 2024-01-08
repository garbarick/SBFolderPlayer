package ru.net.serbis.folder.player.service;

import android.app.*;
import android.content.*;
import android.os.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.notification.*;
import ru.net.serbis.folder.player.util.*;

public class PlayerService extends Service
{
    private static PlayerService instance;

    private Player player;
    private PlayerNotification playerNotification;

    public static PlayerService get()
    {
        return instance;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return null;
    }

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void onCreate()
    {
        instance = this;
        Context context = getApplicationContext();
        player = new Player(context);
    }

    private void initNotification(Context context)
    {
        playerNotification = new PlayerNotification(context);
    }

    private void cancelNotification()
    {
        if (playerNotification != null)
        {
            playerNotification.cancel();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        int result = START_STICKY;
        if (intent == null)
        {
            return result;
        }
        Context context = getApplicationContext();
        if (UITool.get().isProgress(context))
        {
            return result;
        }
        String action = intent.getAction();
        if (action == null)
        {
            return result;
        }
        App app = (App) context;
        switch (action)
        {
            case PlayerActions.INIT_MAIN:
                app.initMain();
                cancelNotification();
                initNotification(context);
                break;
            case PlayerActions.PLAY_PAUSE:
                player.playPause();
                break;
            case PlayerActions.PREVIOUS:
                player.playPrevious();
                break;
            case PlayerActions.NEXT:
                player.playNext();
                break;
            case PlayerActions.SKIP_LEFT:
                player.skipLeft();
                break;
            case PlayerActions.SKIP_RIGHT:
                player.skipRight();
                break;
            case PlayerActions.CLOSE:
                playerNotification.cancel();
                break;
        }
        return result;
    }

    @Override
    public void onDestroy()
    {
        cancelNotification();
        player.cancel();
    }
}
