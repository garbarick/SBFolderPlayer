package ru.net.serbis.folder.player.receiver;

import android.content.*;
import ru.net.serbis.folder.player.listener.*;

public class ScreenOnReceiver extends BroadcastReceiver
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        switch (intent.getAction())
        {
            case Intent.ACTION_SCREEN_ON:
            case Intent.ACTION_DREAMING_STOPPED:
                WidgetListener.get().update(true);
                break;
        }
    }
}
