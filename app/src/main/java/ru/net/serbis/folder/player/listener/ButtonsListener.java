package ru.net.serbis.folder.player.listener;

import android.view.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.activity.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.receiver.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.folder.player.task.*;
import ru.net.serbis.utils.*;
import ru.net.serbis.utils.adapter.*;
import ru.net.serbis.utils.dialog.*;

import ru.net.serbis.folder.player.R;

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
                settings();
                break;
            case R.id.refresh:
                refreshFilesList();
                break;
            case R.id.previous:
                PlayerReceiver.sendAction(activity, PlayerActions.PREVIOUS);
                break;
            case R.id.skip_left:
                PlayerReceiver.sendAction(activity, PlayerActions.SKIP_LEFT);
                break;
            case R.id.play_pause:
                PlayerReceiver.sendAction(activity, PlayerActions.PLAY_PAUSE);
                break;
            case R.id.skip_right:
                PlayerReceiver.sendAction(activity, PlayerActions.SKIP_RIGHT);
                break;
            case R.id.next:
                PlayerReceiver.sendAction(activity, PlayerActions.NEXT);
                break;
        }
    }
    
    private void settings()
    {
        dialog = new ParamsDialog(activity, R.string.settings, Params.PARAMS)
        {
            @Override
            public void ok(ParamsAdapter adapter)
            {
                super.ok(adapter);
                PlayerReceiver.sendAction(activity, PlayerActions.NOTIFY);
            }
            
            @Override
            public void reset(ParamsAdapter adapter)
            {
                super.reset(adapter);
                PlayerReceiver.sendAction(activity, PlayerActions.NOTIFY);
            }
        };
        dialog.show();
    }

    public void refreshFilesList()
    {
        FolderType type = Params.MUSIC_FOLDER_TYPE.getValue();
        switch (type)
        {
            case LOCAL:
                refreshLocalFilesList();
                break;
            case SHARE:
                refreshShareFilesList();
                break;
            default:
                UITool.get().toast(activity.getResources().getString(R.string.error_unknown_folder_type) + " " + type);
                return;
        }
    }

    private void refreshLocalFilesList()
    {
        String dir = Params.MUSIC_LOCAL_FOLDER.getValue();
        activity.enable(false);
        activity.initNotification(R.string.loading_files);
        new FileListLoaderTask(activity, new FilesListCallback(activity)).execute(dir);
    }

    private void refreshShareFilesList()
    {
        String dir = Params.MUSIC_SHARE_FOLDER.getValue();
        activity.enable(false);
        activity.initNotification(R.string.loading_files);
        ShareTools.get().getFileList(new FilesListCallback(activity), dir);
    }
}
