package ru.net.serbis.folder.player.notification;

import android.app.*;
import android.content.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;

public class NotificationProgress extends Notification.Builder
{
    private int id = new Random(new Date().getTime()).nextInt();
    private NotificationManager manager;

    public NotificationProgress(Context context, int textId)
    {
        super(context, context.getResources().getString(R.string.progress));
        setSmallIcon(R.drawable.app);
        setContentText(context.getResources().getString(textId));
        String channelId = context.getResources().getString(R.string.progress);
        NotificationChannel channel = new NotificationChannel(channelId, channelId, NotificationManager.IMPORTANCE_LOW);
        manager = SysTool.get().getService(context, Context.NOTIFICATION_SERVICE);
        manager.createNotificationChannel(channel);
    }

    public void setProgress(int progress)
    {
        setContentTitle(progress + " %");
        setProgress(100, progress, false);
        manager.notify(id, build());
    }

    public void cancel()
    {
        manager.cancel(id);
    }
}
