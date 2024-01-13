package ru.net.serbis.folder.player.receiver;

import android.content.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.folder.player.task.*;
import ru.net.serbis.folder.player.util.*;

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
            case PlayerActions.CLOSE:
                tools.sendAction(PlayerActions.ACTION_CLOSE, this);
                break;
            case PlayerActions.INIT:
                tools.sendAction(PlayerActions.ACTION_INIT, this);
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
        UITool.get().toast(context, error);
    }
}
