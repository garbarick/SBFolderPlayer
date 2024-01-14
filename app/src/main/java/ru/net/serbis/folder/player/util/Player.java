package ru.net.serbis.folder.player.util;

import android.content.*;
import android.media.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.extension.share.*;
import ru.net.serbis.folder.player.notification.*;
import ru.net.serbis.folder.player.task.*;

public class Player extends Util implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener
{
    private static final Player instance = new Player();

    public interface PlayerListener
    {
        void playerProgress(int currentPosition);
        void playerDuration(int duration);
        void playerPlay();
        void playerPause();
        void setPosition(int position);

        void startFileLoading();
        void fileLoadProgress(int progress);
        void finishFileLoading();
    }

    private List<PlayerListener> listeners = Collections.synchronizedList(new ArrayList<PlayerListener>());
    private MediaPlayer player;
    private String lastPath;
    private Timer timer;
    private List<String> files = Collections.synchronizedList(new ArrayList<String>());
    private int position;
    private NotificationProgress notification;

    public static Player get()
    {
        return instance;
    }

    @Override
    public void set(Context context)
    {
        super.set(context);
        init();
    }

    private void init()
    {
        SharedPreferences preferences = SysTool.get().getPreferences(context);
        files.addAll(new TreeSet<String>(preferences.getStringSet(Constants.MEDIA_FILES, Collections.<String>emptySet())));
        position = preferences.getInt(Constants.LAST_MEDIA_FILE, 0);
    }

    public List<String> getFiles()
    {
        return files;
    }

    public void setFiles(Set<String> files)
    {
        this.files.clear();
        this.files.addAll(files);
        saveFiles(files);
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.setPosition(position);
                    }
                }
            }
        );
    }

    private void saveFiles(Set<String> files)
    {
        SharedPreferences.Editor editor = SysTool.get().getPreferencesEditor(context);
        editor.putStringSet(Constants.MEDIA_FILES, files);
        editor.commit();
    }

    public int getPosition()
    {
        return position;
    }

    public int getPosition(int delta)
    {
        int position = this.position + delta;
        if (position < files.size())
        {
            return position;
        }
        return -1;
    }

    public void setPosition(final int position)
    {
        this.position = position;
        savePisition(position);
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.setPosition(position);
                    }
                }
            }
        );
    }

    private void savePisition(int position)
    {
        SharedPreferences.Editor editor = SysTool.get().getPreferencesEditor(context);
        editor.putInt(Constants.LAST_MEDIA_FILE, position);
        editor.commit();
    }

    public void setListener(PlayerListener listener)
    {
        listeners.add(listener);
        stopTimer();
        startTimer();
        if (player == null)
        {
            return;
        }
        playerDuration();
        playerProgress();
        if (isPlaying())
        {
            playerPlay();
        }
        else
        {
            playerPause();
        }
    }

    public void clearListener(PlayerListener listener)
    {
        listeners.remove(listener);
        stopTimer();
        if (listeners.isEmpty())
        {
            return;
        }
        startTimer();
    }

    public boolean pause()
    {
        if (isPlaying())
        {
            player.pause();
            playerPause();
            return true;
        }
        return false;
    }

    public void play(String path)
    {
        if (lastPath == null || !lastPath.equals(path))
        {
            stop();
            if (path == null)
            {
                return;
            }
            lastPath = path;
            player = new MediaPlayer();
            player.setOnErrorListener(this);
            player.setOnCompletionListener(this);
            setTrack();
        }
        if (player == null)
        {
            return;
        }
        if (player.isPlaying())
        {
            return;
        }
        player.start();
        playerPlay();
        playerDuration();
    }

    private void setTrack()
    {
        try
        {
            player.setDataSource(lastPath);
            player.prepare();
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }

    public void stop()
    {
        if (player == null)
        {
            return;
        }
        player.stop();
        player.release();
        player = null;
        lastPath = null;
        playerPause();
    }

    public boolean isPlaying()
    {
        if (player == null)
        {
            return false;
        }
        return player.isPlaying();
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra)
    {
        stop();
        return false;
    }

    private void playerPause()
    {
        if (player == null)
        {
            return;
        }
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.playerPause();
                    }
                }
            }
        );
    }

    private void playerPlay()
    {
        if (player == null)
        {
            return;
        }
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.playerPlay();
                    }
                }
            }
        );
    }

    private void playerDuration()
    {
        if (player == null)
        {
            return;
        }
        try
        {
            int duration = player.getDuration();
            playerDuration(duration);
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }

    private void playerDuration(final int duration)
    {
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.playerDuration(duration);
                    }
                }
            }
        );
    }

    private void playerProgress()
    {
        if (player == null)
        {
            return;
        }
        try
        {
            int position = player.getCurrentPosition();
            playerProgress(position);
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }

    private void playerProgress(final int position)
    {
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.playerProgress(position);
                    }
                }
            }
        );
    }

    public void cancel()
    {
        timer.cancel();
        listeners.clear();
        stop();
    }

    @Override
    public void onCompletion(MediaPlayer player)
    {
        playerPause();
        int position = getPosition(1);
        if (position == -1 && files.size() > 0)
        {
            position = 0;
        }
        if (position == -1)
        {
            pause();
        }
        else
        {
            play(position);
        }
    }

    public void skipLeft()
    {
        if (player == null)
        {
            return;
        }
        int time = player.getCurrentPosition() - 10000;
        if (time < 0)
        {
            time = 0;
        }
        player.seekTo(time);
    }

    public void skipRight()
    {
        if (player == null)
        {
            return;
        }
        int time = player.getCurrentPosition() + 10000;
        if (time > player.getDuration())
        {
            time = player.getDuration();
        }
        player.seekTo(time);
    }

    public void seek(int time)
    {
        if (player == null)
        {
            return;
        }
        player.seekTo(time);
    }

    public void playNext()
    {
        int position = getPosition(1);
        if (position > -1)
        {
            play(position);
        }
    }

    public void playPrevious()
    {
        int position = getPosition(-1);
        if (position > -1)
        {
            play(position);
        }
    }

    public void play(int position)
    {
        setPosition(position);
        pause();
        playPause();
    }

    public void playPause()
    {
        if (pause())
        {
            return;
        }
        if (files.size() == 0)
        {
            return;
        }
        String path = files.get(position);
        if (ShareTools.get().isSharePath(path))
        {
            if (TempFiles.get().has(path))
            {
                play(TempFiles.get().get(path));
            }
            else
            {
                notification = NotificationProgress.get(context, R.string.folder_music_player_loading_files);
                startFileLoading();
                ShareTools.get().getFile(new FileCallback(context, this, path), path);
            }
        }
        else
        {
            play(path);
        }
    }

    public void startFileLoading()
    {
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    for (PlayerListener listener : listeners)
                    {
                        listener.startFileLoading();
                    }
                }
            }
        );
    }

    public void fileLoadProgress(final int progress)
    {
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    notification.setProgress(progress);
                    for (PlayerListener listener : listeners)
                    {
                        listener.fileLoadProgress(progress);
                    }
                }
            }
        );
    }

    public void finishFileLoading()
    {
        UITool.get().runOnUiThread(
            new Runnable()
            {
                @Override
                public void run()
                {
                    notification.cancel();
                    for (PlayerListener listener : listeners)
                    {
                        listener.finishFileLoading();
                    }
                }
            }
        );
    }

    synchronized
    private void startTimer()
    {
        timer = new Timer();
        timer.schedule(
            new TimerTask()
            {
                @Override
                public void run()
                {
                    playerProgress();
                }
            }, 0, 1000);
    }

    synchronized
    private void stopTimer()
    {
        if (timer == null)
        {
            return;
        }
        timer.cancel();
        timer = null;
    }
}
