package ru.net.serbis.folder.player.receiver;

import android.app.*;
import android.content.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.folder.player.task.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.bean.*;

public class PlayerReceiver extends BroadcastReceiver implements TaskCallback<Boolean>
{
    private Context context;

    @Override
    public void onReceive(Context context, Intent intent)
    {
        this.context = context;
        PlayerTools tools = PlayerTools.get();
        String action = intent.getAction();
        switch (action)
        {
            case PlayerActions.PLAY_PAUSE:
                tools.sendAction(PlayerActions.ACTION_PLAY_PAUSE, this);
                break;
            case PlayerActions.PREVIOUS:
                tools.sendAction(PlayerActions.ACTION_PREVIOUS, this);
                break;
            case PlayerActions.NEXT:
                tools.sendAction(PlayerActions.ACTION_NEXT, this);
                break;
            case PlayerActions.SKIP_LEFT:
                tools.sendAction(PlayerActions.ACTION_SKIP_LEFT, this);
                break;
            case PlayerActions.SKIP_RIGHT:
                tools.sendAction(PlayerActions.ACTION_SKIP_RIGHT, this);
                break;
            case PlayerActions.INIT:
                tools.sendAction(PlayerActions.ACTION_INIT, this);
                break;
            case PlayerActions.NOTIFY:
                tools.sendAction(PlayerActions.ACTION_NOTIFY, this);
                break;
        }
    }

    @Override
    public void progress(int progress)
    {
    }

    @Override
    public void onResult(Boolean result, TaskError error)
    {
        UITool.get().toast(error);
    }

    public static void sendAction(Context context, String action)
    {
        context.sendBroadcast(getIntent(context, action));
    }

    public static Intent getIntent(Context context, String action)
    {
        Intent intent = new Intent(context, PlayerReceiver.class);
        intent.setAction(action);
        return intent;
    }

    public static PendingIntent getPending(Context context, String action)
    {
        return PendingIntent.getBroadcast(context, 0, getIntent(context, action), 0);
    }
}
