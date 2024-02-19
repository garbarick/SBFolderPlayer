package ru.net.serbis.folder.player.activity;

import android.app.*;
import android.content.*;
import android.content.res.*;
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
import ru.net.serbis.folder.player.receiver.*;
import ru.net.serbis.folder.player.service.*;
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
    private ButtonsListener buttons;
    private BroadcastReceiver receiver = new BroadcastReceiver()
    {
        @Override
        public void onReceive(Context context, Intent intent)
        {
            String action = intent.getAction();
            switch (action)
            {
                case PlayerActions.READY:
                    PlayerReceiver.sendAction(Main.this, PlayerActions.INIT);
                    break;
                case PlayerActions.INIT_MAIN:
                    init();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle state)
    {
        super.onCreate(state);
        SysTool.get().initPermissions(this);
        initUi();
        enable(false);
        registerReceiver();
        PlayerReceiver.sendAction(this, PlayerActions.INIT);
    }

    private void registerReceiver()
    {
        IntentFilter filter = new IntentFilter();
        filter.addAction(PlayerActions.READY);
        filter.addAction(PlayerActions.INIT_MAIN);
        registerReceiver(receiver, filter);
    }

    private void initUi()
    {
        setContentView(getLayout());

        main = UITool.get().findView(this, R.id.main);
        bar = UITool.get().findView(this, R.id.progress);

        seek = UITool.get().findView(this, R.id.seek);
        seek.setOnSeekBarChangeListener(new SeekBarListener());

        trackSeek = UITool.get().findView(this, R.id.track_seek);
        trackLength = UITool.get().findView(this, R.id.track_lenght);

        playPause = UITool.get().findView(this, R.id.play_pause);

        buttons = new ButtonsListener(this);
        initList();
    }

    private void init()
    {
        unregisterReceiver(receiver);
        initItemsList();
        enable(true);
    }

    private int getLayout()
    {
        if (Configuration.ORIENTATION_LANDSCAPE == getResources().getConfiguration().orientation)
        {
            return R.layout.main_landscape;
        }
        return R.layout.main_portrait;
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
        list.setOnItemClickListener(this);
    }

    private void initItemsList()
    {
        adapter.addAll(Player.get().getFiles());
        if (adapter.getCount() == 0)
        {
            buttons.refreshFilesList();
        }
        else
        {
            setPosition(Player.get().getPosition(), false);
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

    public void progress(int progress)
    {
        notification.setProgress(progress);
        bar.setProgress(progress);
    }

    public void finishFileListLoading()
    {
        notification.cancel();
        enable(true);
    }

    public void setFilesList(List<String> result)
    {
        int countOld = adapter.getCount();
        int countNew = result.size();

        adapter.clear();
        adapter.addAll(result);
        adapter.notifyDataSetChanged();

        if (countOld != countNew)
        {
            Player.get().setPosition(0);
        }

        setPosition(Player.get().getPosition(), false);
        Player.get().setFiles(result);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        Player.get().play(position);
    }

    @Override
    public void onBackPressed()
    {
        Player.get().clearListener(this);
        super.onBackPressed();
    }

    @Override
    protected void onResume()
    {
        Player.get().setListener(this);
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Player.get().clearListener(this);
        super.onPause();
    }

    @Override
    public void playerPlay()
    {
        playPause.setImageResource(android.R.drawable.ic_media_pause);
    }

    @Override
    public void playerPause()
    {
        playPause.setImageResource(android.R.drawable.ic_media_play);
    }

    @Override
    public void playerDuration(int value)
    {
        seek.setMax(value);
        trackLength.setText(UITool.get().formatTime(value));
    }

    @Override
    public void playerProgress(int value)
    {
        seek.setProgress(value);
        trackSeek.setText(UITool.get().formatTime(value));
    }

    @Override
    public void setPosition(int position)
    {
        setPosition(position, true);
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
