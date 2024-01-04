package ru.net.serbis.folder.player.task;

import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.util.*;

public class FileCallback implements TaskCallback<String>
{
    private Main activity;
    private String path;

    public FileCallback(Main activity, String path)
    {
        this.activity = activity;
        this.path = path;
    }

    @Override
    public void progress(int progress)
    {
        activity.progress(progress);
    }

    @Override
    public void onResult(String result, TaskError error)
    {
        try
        {
            if (error != null)
            {
                activity.setError(error);
            }
            else
            {
                result = TempFiles.get().addFile(path, result);
                activity.setFilePath(result);
            }
        }
        finally
        {
            activity.finishResult();
        }
    }
}
