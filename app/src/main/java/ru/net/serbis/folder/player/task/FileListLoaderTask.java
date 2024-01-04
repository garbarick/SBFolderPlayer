package ru.net.serbis.folder.player.task;

import android.content.*;
import android.os.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.util.*;

public class FileListLoaderTask extends AsyncTask<Object, Integer, Set<String>>
{
    private Context context;
    private TaskCallback<Set<String>> callback;
    private TaskError error;
    private int count;
    private int current;

    public FileListLoaderTask(Context context, TaskCallback<Set<String>> callback)
    {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Set<String> doInBackground(Object ... params)
    {
        try
        {
            UITool.get().setProgress(context, true);
            String dir = (String) params[0];
            publishProgress(0);
            return loadLocalFiles(dir);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            error = new TaskError(Constants.ERROR_LOAD_FILE_LIST, e.getMessage());
            return null;
        }
        finally
        {
            publishProgress(0);
        }
    }

    private Set<String> loadLocalFiles(String dir)
    {
        return loadLocalFiles(new File(dir));
    }

    private Set<String> loadLocalFiles(File dir)
    {
        final Set<String> result = new TreeSet<String>();
        File[] files = dir.listFiles(new FileFilter()
            {
                public boolean accept(File file)
                {
                    if (file.isDirectory())
                    {
                        result.addAll(loadLocalFiles(file));
                        return false;
                    }
                    if (IOTool.get().checkExt(file))
                    {
                        return true;
                    }
                    return false;
                }
            }
        );
        if (files != null)
        {
            count += files.length;
            for (File file : files)
            {
                result.add(file.getAbsolutePath());
                publishProgress(UITool.get().getPercent(count, ++current));
            }
        }
        return result;
    }

    @Override
    protected void onProgressUpdate(Integer[] progress)
    {
        callback.progress(progress[0]);
    }

    @Override
    protected void onPostExecute(Set<String> result)
    {
        UITool.get().setProgress(context, false);
        callback.onResult(result, error);
    }
}
