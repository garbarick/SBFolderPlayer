package ru.net.serbis.folder.player.connection;

import android.app.*;
import ru.net.serbis.folder.player.service.*;

public class PlayerConnection extends ExtConnection
{
    public PlayerConnection(Application app)
    {
        super(app);
    }

    @Override
    protected String packageName()
    {
        return app.getPackageName();
    }

    @Override
    protected String serviceName()
    {
        return PlayerService.class.getName();
    }
}
