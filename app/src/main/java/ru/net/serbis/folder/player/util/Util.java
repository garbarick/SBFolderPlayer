package ru.net.serbis.folder.player.util;

import android.content.*;
import ru.net.serbis.folder.player.*;

public class Util
{
    protected Context context;
    protected App app;

    public void set(Context context)
    {
        this.context = context;
        app = (App) context.getApplicationContext();
    }
}
