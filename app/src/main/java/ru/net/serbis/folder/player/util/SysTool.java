package ru.net.serbis.folder.player.util;

import android.app.*;
import android.content.pm.*;
import java.io.*;

public class SysTool extends Util
{
    private static final SysTool instance = new SysTool();

    public static SysTool get()
    {
        return instance;
    }

    public <T> T getService(String name)
    {
        return (T) context.getSystemService(name);
    }

    public String errorToText(Throwable error)
    {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public void initPermissions(Activity context) 
    {
        try
        {
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            for (String permission : packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions)
            {
                if (isGrantedPermission(permission))
                {
                    continue;
                }
                context.requestPermissions(new String[]{permission}, 200);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }

    private boolean isGrantedPermission(String permission)
    {
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
