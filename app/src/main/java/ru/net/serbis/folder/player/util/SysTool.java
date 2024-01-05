package ru.net.serbis.folder.player.util;

import android.app.*;
import android.content.*;
import android.content.pm.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.folder.player.data.*;

public class SysTool
{
    private static final SysTool instance = new SysTool();

    public static SysTool get()
    {
        return instance;
    }

    public <T> T getService(Context context, String name)
    {
        return (T) context.getSystemService(name);
    }

    public void setClipBoard(Context context, int labelId, String text)
    {
        ClipboardManager clipboard = getService(context, Context.CLIPBOARD_SERVICE);
        String label = context.getResources().getString(labelId);
        ClipData clip = ClipData.newPlainText(label, text);
        clipboard.setPrimaryClip(clip);
    }
    
    public String errorToText(Throwable error)
    {
        StringWriter writer = new StringWriter();
        error.printStackTrace(new PrintWriter(writer));
        return writer.toString();
    }

    public SharedPreferences getPreferences(Context context)
    {
        return context.getSharedPreferences(Constants.APP, Context.MODE_PRIVATE);
    }

    public SharedPreferences.Editor getPreferencesEditor(Context context)
    {
        return getPreferences(context).edit();
    }

    public Set<String> getSet(Collection data)
    {
        Set<String> result = new TreeSet<String>();
        for (Object item : data)
        {
            result.add(item.toString());
        }
        return result;
    }

    public void initPermissions(Activity context) 
    {
        try
        {
            String packageName = context.getPackageName();
            PackageManager packageManager = context.getPackageManager();
            for (String permission : packageManager.getPackageInfo(packageName, PackageManager.GET_PERMISSIONS).requestedPermissions)
            {
                if (isGrantedPermission(context, permission))
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

    private boolean isGrantedPermission(Context context, String permission)
    {
        return context.checkCallingOrSelfPermission(permission) == PackageManager.PERMISSION_GRANTED;
    }
}
