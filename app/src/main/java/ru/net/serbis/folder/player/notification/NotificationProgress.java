package ru.net.serbis.folder.player.notification;

import android.app.*;
import android.content.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;

public class NotificationProgress extends BaseNotification
{
    private RemoteViews views;

    public NotificationProgress(Context context, int textId)
    {
        super(context, R.string.progress, textId);
    }

    @Override
    protected void init()
    {
        super.init();
        views = new RemoteViews(context.getPackageName(), R.layout.progress);
        views.setImageViewResource(R.id.icon, R.drawable.app);
        views.setTextViewText(R.id.text, context.getResources().getString(textId));
        setContent(views);
    }

    public void setProgress(int progress)
    {
        views.setTextViewText(R.id.title, progress + " %");
        views.setProgressBar(R.id.progress, 100, progress, false);
        update();
    }

    @Override
    public Notification build()
    {
        Notification result = super.build();
        if (old)
        {
            result.bigContentView = views;
        }
        return result;
    }

    public static NotificationProgress get(Context context, int stringId)
    {
        return new NotificationProgress(context, stringId);
    }
}
