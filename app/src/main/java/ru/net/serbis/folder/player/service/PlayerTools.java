package ru.net.serbis.folder.player.service;

import android.os.*;
import ru.net.serbis.folder.player.connection.*;
import ru.net.serbis.folder.player.task.*;

public class PlayerTools extends ServiceTools
{
    private static final PlayerTools instance = new PlayerTools();

    public static PlayerTools get()
    {
        return instance;
    }

    @Override
    protected ExtConnection getConnection()
    {
        return app.getPlayerConnection();
    }

    public void sendAction(int action, final TaskCallback<Boolean> callback)
    {
        sendServiceAction(
            action,
            new Handler(Looper.getMainLooper())
            {
                @Override
                public void handleMessage(Message msg)
                {
                    if (checkResult(callback, msg, PlayerActions.RESULT))
                    {
                        return;
                    }
                    if (checkError(callback, msg, PlayerActions.ERROR, PlayerActions.ERROR_CODE))
                    {
                        return;
                    }
                    if (checkProgress(callback, msg, PlayerActions.PROGRESS))
                    {
                        return;
                    }
                }
            }
        );
	}
}
