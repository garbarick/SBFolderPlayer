package ru.net.serbis.folder.player.task;

import java.util.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.data.*;

public class FilesListCallback implements TaskCallback<Set<String>>
{
    private Main activity;

    public FilesListCallback(Main activity)
    {
        this.activity = activity;
    }

    @Override
    public void progress(int progress)
    {
        activity.progress(progress);
    }

    @Override
    public void onResult(Set<String> result, TaskError error)
    {
        try
        {
            if (error != null)
            {
                activity.setError(error);
            }
            else
            {
                activity.setFilesList(result);
            }
        }
        finally
        {
            activity.finishResult();
        }
    }
}
