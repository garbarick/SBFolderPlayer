package ru.net.serbis.folder.player.util;

import android.content.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.folder.player.data.*;

public class TempFiles extends Util
{
    private static final TempFiles instance = new TempFiles();

    private Map<String, String> files;

    public static TempFiles get()
    {
        return instance;
    }

    @Override
    public void set(Context context)
    {
        super.set(context);
        String data = Preferences.get().getString(Constants.TEMP_FILES, "[]");
        files = new JsonTools().parseMap(data);
        clearFailed();
    }

    public String addFile(String original, String temp)
    {
        File dir = new File(Params.TEMP_FOLDER.getValue(context));
        dir.mkdirs();
        File tempFile = new File(temp);
        File appFile = new File(dir, new Date().getTime() + "." + IOTool.get().getExt(temp));
        IOTool.get().moveFileQuietly(tempFile, appFile, Params.BUFFER_SIZE.getValue(context));
        temp = appFile.getAbsolutePath();
        
        files.put(original, temp);
        removeFirst(Params.TEMP_FILES_COUNT.getValue(context));

        Preferences.get().setString(Constants.TEMP_FILES, new JsonTools().toJson(files));

        return temp;
    }

    public boolean has(String original)
    {
        return files.containsKey(original);
    }

    public String get(String original)
    {
        return files.get(original);
    }

    private void removeFirst(int count)
    {
        if (files.size() < count)
        {
            return;
        }
        Iterator<Map.Entry<String, String>> iterator = files.entrySet().iterator();
        Map.Entry<String, String> first = iterator.next();
        iterator.remove();
        String path = first.getValue();
        new File(path).delete();
    }

    private void clearFailed()
    {
        Iterator<Map.Entry<String, String>> iterator = files.entrySet().iterator();
        while (iterator.hasNext())
        {
            Map.Entry<String, String> entry = iterator.next();
            File temp = new File(entry.getValue());
            if (!temp.exists())
            {
                iterator.remove();
            }
        }
    }
}
