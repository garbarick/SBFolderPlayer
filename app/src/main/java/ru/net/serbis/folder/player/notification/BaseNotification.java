package ru.net.serbis.folder.player.notification;

import android.app.*;
import android.content.*;
import android.os.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;

public class BaseNotification extends Notification.Builder
{
    protected int id = new Random(new Date().getTime()).nextInt();
    protected Context context;
    protected int channelStringId;
    protected NotificationManager manager;

    public BaseNotification(Context context, int channelStringId)
    {
        super(context);

        this.context = context;
        this.channelStringId = channelStringId;

        init();
    }

    protected void init()
    {
        setSmallIcon(R.drawable.app);
        setOngoing(true);
        setAutoCancel(true);
        manager = SysTool.get().getService(context, Context.NOTIFICATION_SERVICE);
        initChannel();
    }

    protected void initChannel()
    {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            String channelId = context.getResources().getString(channelStringId);
            setChannelId(channelId);
            NotificationChannel channel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_LOW);
            manager.createNotificationChannel(channel);
        }
    }

    public void update()
    {
        manager.notify(id, build());
    }

    public void cancel()
    {
        manager.cancel(id);
    }
}
