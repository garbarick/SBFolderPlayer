package ru.net.serbis.folder.player.service;

import android.app.*;
import android.content.*;
import android.os.*;
import ru.net.serbis.folder.player.notification.*;
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
                        player.playPause();
                        break;
                    case PlayerActions.ACTION_PREVIOUS:
                        player.playPrevious();
                        break;
                    case PlayerActions.ACTION_NEXT:
                        player.playNext();
                        break;
                    case PlayerActions.ACTION_SKIP_LEFT:
                        player.skipLeft();
                        break;
                    case PlayerActions.ACTION_SKIP_RIGHT:
                        player.skipRight();
                        break;
                    case PlayerActions.ACTION_CLOSE:
                        playerNotification.cancel();
                        break;
                    case PlayerActions.ACTION_INIT:
                        cancelNotifications();
                        initNotification(context);
                        sendBroadcast(new Intent(PlayerActions.INIT_MAIN));
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
    private Player player;
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

    public Player getPlayer()
    {
        return player;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        instance = this;
        messenger = new Messenger(new IncomingHandler());
        context = getApplicationContext();
        player = new Player(context);
        sendBroadcast(new Intent(PlayerActions.READY));
    }

    private void initNotification(Context context)
    {
        playerNotification = new PlayerNotification(context);
    }

    private void cancelNotifications()
    {
        if (playerNotification != null)
        {
            playerNotification.cancel();
        }
        NotificationManager manager = SysTool.get().getService(context, Context.NOTIFICATION_SERVICE);
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
        player.cancel();
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
}
