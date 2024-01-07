package ru.net.serbis.folder.player;

import android.app.*;
import android.content.*;
import ru.net.serbis.folder.player.connection.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.data.param.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.handler.*;
import ru.net.serbis.folder.player.notification.*;
import ru.net.serbis.folder.player.util.*;

public class App extends Application
{
    private ExtConnection shareConnection = new ShareConnection(this);
    private boolean progress;
    private Player player;
    private PlayerNotification playerNotification;

    @Override
    public void onCreate()
    {
        super.onCreate();

        initFolderType();
        initParams();
        shareConnection.bind();

        Context context = getApplicationContext();
        player = new Player(context);
        ShareTools.get().set(context);
        TempFiles.get().set(context);
        playerNotification = new PlayerNotification(context);

        Thread.setDefaultUncaughtExceptionHandler(new ExceptionHandler(getApplicationContext()));
    }

    private void initFolderType()
    {
        for (FolderType item : FolderType.class.getEnumConstants())
        {
            item.initName(this);
        }
    }

    private void initParams()
    {
        for (Param param : Reflection.get().getParams(Params.class, Param.class))
        {
            param.initName(this);
        }
    }

    @Override
    public void onTerminate()
    {
        super.onTerminate();
        shareConnection.unBind();
        closePlayerNotification();
        player.cancel();
    }

    public ExtConnection getShareConnection()
    {
        shareConnection.bind();
        return shareConnection;
	}

    public void setProgress(boolean progress)
    {
        this.progress = progress;
    }

    public boolean isProgress()
    {
        return progress;
    }
    
    public Player getPlayer()
    {
        return player;
    }

    public void closePlayerNotification()
    {
        playerNotification.cancel();
    }
}
