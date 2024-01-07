package ru.net.serbis.folder.player.activity;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.adapter.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.dialog.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.listener.*;
import ru.net.serbis.folder.player.notification.*;
import ru.net.serbis.folder.player.util.*;

public class Main extends Activity implements AdapterView.OnItemClickListener, Player.PlayerListener
{
    private LinearLayout main;
    private NotificationProgress notification;
    private ProgressBar bar;
    private ListView list;
    private MediaFilesAdapter adapter;
    private SeekBar seek;
    private TextView trackSeek;
    private TextView trackLength;
    private ImageButton playPause;
    private Player player;
    private ButtonsListener buttons;

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        setContentView(R.layout.folder_music_player);

        SysTool.get().initPermissions(this);

        main = UITool.get().findView(this, R.id.main);
        bar = UITool.get().findView(this, R.id.progress);

        seek = UITool.get().findView(this, R.id.seek);
        seek.setOnSeekBarChangeListener(new SeekBarListener(this));

        trackSeek = UITool.get().findView(this, R.id.track_seek);
        trackLength = UITool.get().findView(this, R.id.track_lenght);

        playPause = UITool.get().findView(this, R.id.play_pause);

        initPlayer();
        buttons = new ButtonsListener(this);
        initList();
    }

    public Player getPlayer()
    {
        return player;
    }

    public void enable(boolean enable)
    {
        UITool.get().setEnableAll(main, enable);
    }

    public void initNotification(int stringId)
    {
        notification = NotificationProgress.get(this, stringId);
    }

    private void initList()
    {
        list = UITool.get().findView(this, R.id.list);
        list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        adapter = new MediaFilesAdapter(this);
        list.setAdapter(adapter);

        initItemsList();
        list.setOnItemClickListener(this);
    }

    private void initItemsList()
    {
        adapter.addAll(player.getFiles());
        if (adapter.getCount() == 0)
        {
            buttons.refreshFilesList();
        }
        else
        {
            setPosition(player.getPosition(), false);
        }
    }
    
    private void setPosition(final int position, boolean smooth)
    {
        list.setItemChecked(position, true);
        if (smooth)
        {
            list.smoothScrollToPosition(position);
        }
        else
        {
            list.post(
                new Runnable()
                {
                    public void run()
                    {
                        list.setSelection(position);
                    }
                }
            );
        }
    }

    private void initPlayer()
    {
        App app = (App) getApplicationContext();
        player = app.getPlayer();
        player.setListener(this);
    }

    public void progress(int progress)
    {
        notification.setProgress(progress);
        bar.setProgress(progress);
    }

    public void finishFileListLiading()
    {
        notification.cancel();
        enable(true);
    }

    public void setFilesList(Set<String> result)
    {
        int countOld = adapter.getCount();
        int countNew = result.size();

        adapter.clear();
        adapter.addAll(result);
        adapter.notifyDataSetChanged();

        if (countOld != countNew)
        {
            player.setPosition(0);
        }

        setPosition(player.getPosition(), false);
        player.setFiles(result);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        player.play(position);
    }

    @Override
    public void onBackPressed()
    {
        player.clearListener(this);
        super.onBackPressed();
    }

    @Override
    public void playerPlay()
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    playPause.setImageResource(android.R.drawable.ic_media_pause);
                }
            }
        );
    }

    @Override
    public void playerPause()
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    playPause.setImageResource(android.R.drawable.ic_media_play);
                }
            }
        );
    }

    @Override
    public void playerDuration(final int value)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    seek.setMax(value);
                    trackLength.setText(UITool.get().formatTime(value));
                }
            }
        );
    }

    @Override
    public void playerProgress(final int value)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    seek.setProgress(value);
                    trackSeek.setText(UITool.get().formatTime(value));
                }
            }
        );
    }

    @Override
    public void setPosition(final int position)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    setPosition(position, true);
                }
            }
        );
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        ParamsDialog dialog = buttons.getDialog();
        if (dialog != null && Activity.RESULT_OK == resultCode)
        {
            ShareFolders folders = new ShareFolders();
            if (folders.isMineResult(requestCode, data))
            {
                String path = new ShareFolders().getPath(data);
                dialog.updateValue(Params.MUSIC_SHARE_FOLDER, path);
            }
        }
    }

    @Override
    public void startFileLoading()
    {
        enable(false);
    }

    @Override
    public void fileLoadProgress(int progress)
    {
        bar.setProgress(progress);
    }

    @Override
    public void finishFileLoading()
    {
        enable(true);
    }
}
