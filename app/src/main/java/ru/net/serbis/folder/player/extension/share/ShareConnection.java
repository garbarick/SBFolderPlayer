package ru.net.serbis.folder.player.extension.share;

import android.app.*;
import ru.net.serbis.folder.player.connection.*;

public class ShareConnection extends ExtConnection
{
    public ShareConnection(Application app)
    {
        super(app);
    }

    @Override
    protected String packageName()
    {
        return Share.PACKAGE;
    }

    @Override
    protected String serviceName()
    {
        return Share.SERVICE;
    }
}
