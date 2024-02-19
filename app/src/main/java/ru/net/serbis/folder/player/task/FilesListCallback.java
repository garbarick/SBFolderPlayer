package ru.net.serbis.folder.player.task;

import java.util.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.util.*;

public class FilesListCallback implements TaskCallback<List<String>>
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
    public void onResult(List<String> result, TaskError error)
    {
        try
        {
            if (error != null)
            {
                UITool.get().toast(activity, error);
            }
            else
            {
                activity.setFilesList(result);
            }
        }
        finally
        {
            activity.finishFileListLoading();
        }
    }
}
