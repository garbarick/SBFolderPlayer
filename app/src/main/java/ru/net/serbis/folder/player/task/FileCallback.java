package ru.net.serbis.folder.player.task;

import android.content.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.bean.*;

public class FileCallback implements TaskCallback<String>
{
    private Context context;
    private Player player;
    private String path;

    public FileCallback(Context context, Player player, String path)
    {
        this.context = context;
        this.player = player;
        this.path = path;
    }

    @Override
    public void progress(int progress)
    {
        player.fileLoadProgress(progress);
    }

    @Override
    public void onResult(String result, TaskError error)
    {
        try
        {
            if (error != null)
            {
                UITool.get().toast(error);
            }
            else
            {
                result = TempFiles.get().addFile(path, result);
                player.play(result);
            }
        }
        finally
        {
            player.finishFileLoading();
        }
    }
}
