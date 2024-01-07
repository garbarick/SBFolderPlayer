package ru.net.serbis.folder.player.receiver;

import android.content.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;

public class PlayerReceiver extends BroadcastReceiver 
{
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (UITool.get().isProgress(context))
        {
            return;
        }
        App app = (App) context.getApplicationContext();
        Player player = app.getPlayer();
        String action = intent.getAction();
        switch (action)
        {
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
                app.closePlayerNotification();
                break;
        }
    }
}
