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
import ru.net.serbis.folder.player.task.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.folder.player.view.*;

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
    private ImageButtonExt playPause;
    private Player player;
    private ButtonsListener buttons;

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        setContentView(R.layout.folder_music_player);

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
        notification = new NotificationProgress(this, stringId);
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
        SharedPreferences preferences = SysTool.get().getPreferences(this);
        Set<String> files = new TreeSet<String>(preferences.getStringSet(Constants.MEDIA_FILES, Collections.<String>emptySet()));
        adapter.addAll(files);
        if (adapter.getCount() == 0)
        {
            buttons.refreshFilesList();
        }
        else
        {
            initPosition();
        }
    }

    private void initPosition()
    {
        SharedPreferences preferences = SysTool.get().getPreferences(this);
        int position = preferences.getInt(Constants.LAST_MEDIA_FILE, 0);
        setPosition(position, false);
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

    public int getPosition(int delta)
    {
        int position = list.getCheckedItemPosition() + delta;
        if (position < adapter.getCount())
        {
            return position;
        }
        return -1;
    }

    public void progress(int progress)
    {
        notification.setProgress(progress);
        bar.setProgress(progress);
    }

    public void setError(TaskError error)
    {
        UITool.get().toast(this, error);
    }

    public void finishResult()
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
            savePosition(0);
        }

        initPosition();
        saveItemsList(result);
    }

    private void saveItemsList(Set<String> items)
    {
        SharedPreferences.Editor editor = SysTool.get().getPreferencesEditor(this);
        editor.putStringSet(Constants.MEDIA_FILES, items);
        editor.commit();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        play(position);
    }

    private void savePosition(int position)
    {
        SharedPreferences.Editor editor = SysTool.get().getPreferencesEditor(this);
        editor.putInt(Constants.LAST_MEDIA_FILE, position);
        editor.commit();
    }

    @Override
    public void onBackPressed()
    {
        player.clearListener(this);
        super.onBackPressed();
    }

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
                    trackLength.setText(formatTime(value));
                }
            }
        );
    }

    public void playerProgress(final int value)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    seek.setProgress(value);
                    trackSeek.setText(formatTime(value));
                }
            }
        );
    }

    private String formatTime(int millisec)
    {
        long second = (millisec / 1000) % 60;
        long minute = (millisec / (1000 * 60)) % 60;
        long hour = (millisec / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    private boolean pause()
    {
        if (player.isPlaying())
        {
            player.pause();
            return true;
        }
        return false;
    }

    public void playPause()
    {
        if (pause())
        {
            return;
        }
        String path = adapter.getChecked(list);
        if (ShareTools.get().isSharePath(path))
        {
            if (TempFiles.get().has(path))
            {
                setFilePath(TempFiles.get().get(path));
            }
            else
            {
                enable(false);
                initNotification(R.string.folder_music_player_loading_files);
                ShareTools.get().getFile(new FileCallback(this, path), path);
            }
        }
        else
        {
            setFilePath(path);
        }
    }

    public void setFilePath(String path)
    {
        player.play(path);
    }

    public void play(int position)
    {
        savePosition(position);
        setPosition(position, true);
        pause();
        playPause();
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
    public void complete()
    {
        int position = getPosition(1);
        if (position == -1 && adapter.getCount() > 0)
        {
            position = 0;
        }
        if (position == -1 )
        {
            pause();
        }
        else
        {
            play(position);
        }
    }
}
