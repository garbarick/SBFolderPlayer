package ru.net.serbis.folder.player;

import android.app.*;
import android.content.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.connection.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.data.param.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.handler.*;
import ru.net.serbis.folder.player.util.*;

public class App extends Application
{
    private ExtConnection shareConnection = new ShareConnection(this);
    private PlayerConnection playerConnection = new PlayerConnection(this);
    private boolean progress;
    private Main main;

    @Override
    public void onCreate()
    {
        super.onCreate();

        initFolderType();
        initParams();
        shareConnection.bind();
        playerConnection.bind();

        Context context = getApplicationContext();
        ShareTools.get().set(context);
        TempFiles.get().set(context);

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
        playerConnection.unBind();
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

    public void setMain(Main main)
    {
        this.main = main;
    }

    public void initMain()
    {
        main.init();
    }
}
