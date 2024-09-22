package ru.net.serbis.folder.player;

import android.app.*;
import android.content.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.connection.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.listener.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.folder.player.view.*;
import ru.net.serbis.utils.*;

public class App extends Application
{
    private ExtConnection shareConnection = new ShareConnection(this);
    private PlayerConnection playerConnection = new PlayerConnection(this);

    @Override
    public void onCreate()
    {
        super.onCreate();

        shareConnection.bind();
        playerConnection.bind();

        Context context = getApplicationContext();
        Strings.get().set(context);
        FolderType.initNames();
        UITool.get().set(context);
        SysTool.get().set(context);
        Preferences.get().set(context);
        Preferences.get().setApp(Constants.APP);
        ShareTools.get().set(context);
        TempFiles.get().set(context);
        Player.get().set(context);
        PlayerTools.get().set(context);
        WidgetListener.get().set(context);
        PlayerView.get().set(context);
        ExceptionHandler.get().set(context);
        ExceptionHandler.get().setErrorActivity(ExceptionReport.class);
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        shareConnection.unBind();
        playerConnection.unBind();
    }

    public ExtConnection getShareConnection()
    {
        shareConnection.bind();
        return shareConnection;
	}

    public PlayerConnection getPlayerConnection()
    {
        playerConnection.bind();
        return playerConnection;
	}
}
