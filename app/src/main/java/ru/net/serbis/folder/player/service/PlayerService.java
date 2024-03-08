package ru.net.serbis.folder.player.service;

import android.app.*;
import android.content.*;
import android.media.*;
import android.os.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.notification.*;
import ru.net.serbis.folder.player.receiver.*;
import ru.net.serbis.folder.player.util.*;

public class PlayerService extends Service
{
    private class IncomingHandler extends Handler
    {
        @Override
        public void handleMessage(Message msg)
        {
            try
            {
                boolean result = true;
                switch (msg.what)
                {
                    case PlayerActions.ACTION_PLAY_PAUSE:
                        Player.get().playPause();
                        break;
                    case PlayerActions.ACTION_PREVIOUS:
                        Player.get().playPrevious();
                        break;
                    case PlayerActions.ACTION_NEXT:
                        Player.get().playNext();
                        break;
                    case PlayerActions.ACTION_SKIP_LEFT:
                        Player.get().skipLeft();
                        break;
                    case PlayerActions.ACTION_SKIP_RIGHT:
                        Player.get().skipRight();
                        break;
                    case PlayerActions.ACTION_INIT:
                        cancelNotifications();
                        initNotification(context);
                        registerMediaButtons();
                        sendBroadcast(new Intent(PlayerActions.INIT_MAIN));
                        break;
                    case PlayerActions.ACTION_NOTIFY:
                        cancelNotifications();
                        initNotification(context);
                        registerMediaButtons();
                        break;
                    default:
                        result = false;
                }
                if (result)
                {
                    sendResult();
                }
            }
            catch (Exception e)
            {
                Log.error(this, e);
                sendError(e);
            }
        }
    }

    private static PlayerService instance;

    private Messenger messenger;
    private Context context;
    private PlayerNotification playerNotification;

    public static PlayerService get()
    {
        return instance;
    }

    @Override
    public IBinder onBind(Intent intent)
    {
        return messenger.getBinder();
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        messenger = new Messenger(new IncomingHandler());
        context = getApplicationContext();
        sendBroadcast(new Intent(PlayerActions.READY));
    }

    private void initNotification(Context context)
    {
        if (Params.NOTIFICATION_PLAYER.getValue())
        {
            playerNotification = new PlayerNotification(context);
        }
    }

    private void cancelNotifications()
    {
        if (playerNotification != null)
        {
            playerNotification.cancel();
        }
        NotificationManager manager = SysTool.get().getService(Context.NOTIFICATION_SERVICE);
        manager.cancelAll();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId)
    {
        return START_STICKY;
    }

    @Override
    public void onDestroy()
    {
        cancelNotifications();
        Player.get().cancel();
    }

    private void sendResult(Bundle data)
    {
        Message msg = Message.obtain();
        msg.setData(data);
        try
        {
            messenger.send(msg);
        }
        catch (RemoteException e)
        {
            Log.error(this, e);
        }
	}

    private void sendError(Exception error)
    {
        Bundle data = new Bundle();
        data.putInt(PlayerActions.ERROR_CODE, PlayerActions.PLAYER_ERROR);
        data.putString(PlayerActions.ERROR, error.getMessage());
        sendResult(data);
    }

    private void sendResult()
    {
        Bundle data = new Bundle();
        data.putBoolean(PlayerActions.RESULT, true);
        sendResult(data);
    }

    private void registerMediaButtons()
    {
        AudioManager manager = SysTool.get().getService(Context.AUDIO_SERVICE);
        ComponentName name = new ComponentName(context.getPackageName(), MediaButtonReceiver.class.getName());
        manager.unregisterMediaButtonEventReceiver(name);
        manager.registerMediaButtonEventReceiver(name);
    }
}
