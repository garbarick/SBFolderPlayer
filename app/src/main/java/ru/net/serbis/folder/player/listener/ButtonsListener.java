package ru.net.serbis.folder.player.listener;

import android.view.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.dialog.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.task.*;
import ru.net.serbis.folder.player.util.*;

public class ButtonsListener implements View.OnClickListener
{
    private Main activity;
    private ParamsDialog dialog;

    public ButtonsListener(Main activity)
    {
        this.activity = activity;
        UITool.get().initButtons(activity, this, R.id.settings, R.id.refresh, R.id.previous, R.id.skip_left, R.id.play_pause, R.id.skip_right, R.id.next);
    }

    public ParamsDialog getDialog()
    {
        return dialog;
    }

    @Override
    public void onClick(View view)
    {
        switch (view.getId())
        {
            case R.id.settings:
                dialog = new ParamsDialog(activity, R.string.settings, Params.PARAMS);
                break;
            case R.id.refresh:
                refreshFilesList();
                break;
            case R.id.previous:
                previousFile();
                break;
            case R.id.skip_left:
                skipLeft();
                break;
            case R.id.play_pause:
                activity.getPlayer().playPause();
                break;
            case R.id.skip_right:
                skipRight();
                break;
            case R.id.next:
                nextFile();
                break;
        }
    }

    public void refreshFilesList()
    {
        FolderType type = Params.MUSIC_FOLDER_TYPE.getValue(activity);
        switch (type)
        {
            case LOCAL:
                refreshLocalFilesList();
                break;
            case SHARE:
                refreshShareFilesList();
                break;
            default:
                UITool.get().toast(activity, activity.getResources().getString(R.string.error_unknown_folder_type) + " " + type);
                return;
        }
    }

    private void refreshLocalFilesList()
    {
        String dir = Params.MUSIC_LOCAL_FOLDER.getValue(activity);
        activity.enable(false);
        activity.initNotification(R.string.folder_music_player_loading_files);
        new FileListLoaderTask(activity, new FilesListCallback(activity)).execute(dir);
    }

    private void refreshShareFilesList()
    {
        String dir = Params.MUSIC_SHARE_FOLDER.getValue(activity);
        activity.enable(false);
        activity.initNotification(R.string.folder_music_player_loading_files);
        ShareTools.get().getFileList(new FilesListCallback(activity), dir);
    }

    private void previousFile()
    {
        activity.getPlayer().playPrevious();
    }

    private void skipLeft()
    {
        activity.getPlayer().skipLeft();
    }

    private void skipRight()
    {
        activity.getPlayer().skipRight();
    }

    private void nextFile()
    {
        activity.getPlayer().playNext();
    }
}
