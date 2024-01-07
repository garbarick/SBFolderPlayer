package ru.net.serbis.folder.player.notification;

import android.content.*;
import ru.net.serbis.folder.player.*;

public class NotificationProgress extends BaseNotification
{
    public NotificationProgress(Context context, int textId)
    {
        super(context, R.string.progress);
        setContentText(context.getResources().getString(textId));
    }

    public void setProgress(int progress)
    {
        setContentTitle(progress + " %");
        setProgress(100, progress, false);
        update();
    }

    public static NotificationProgress get(Context context, int stringId)
    {
        return new NotificationProgress(context, stringId);
    }
}
