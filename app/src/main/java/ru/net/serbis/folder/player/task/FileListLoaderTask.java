package ru.net.serbis.folder.player.task;

import android.content.*;
import android.os.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.bean.*;

public class FileListLoaderTask extends AsyncTask<Object, Integer, List<String>>
{
    private Context context;
    private TaskCallback<List<String>> callback;
    private TaskError error;
    private int count;
    private int current;

    public FileListLoaderTask(Context context, TaskCallback<List<String>> callback)
    {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected List<String> doInBackground(Object ... params)
    {
        try
        {
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

    private List<String> loadLocalFiles(String dir)
    {
        List<String> result = loadLocalFiles(new File(dir));
        Collections.sort(result);
        return result;
    }

    private List<String> loadLocalFiles(File dir)
    {
        final List<String> result = new ArrayList<String>();
        File[] files = dir.listFiles(new FileFilter()
            {
                public boolean accept(File file)
                {
                    if (file.isDirectory())
                    {
                        result.addAll(loadLocalFiles(file));
                        return false;
                    }
                    if (FilesTool.get().checkExt(file))
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
                publishProgress(HelpTool.get().getPercent(count, ++current));
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
    protected void onPostExecute(List<String> result)
    {
        UITool.get().setProgress(false);
        callback.onResult(result, error);
    }
}
