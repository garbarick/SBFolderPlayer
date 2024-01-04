package ru.net.serbis.folder.player.util;

import android.content.*;
import android.media.*;
import java.util.*;

public class Player extends TimerTask implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener
{
    public interface PlayerListener
    {
        void playerProgress(int currentPosition);
        
        void playerDuration(int duration);

        void playerPlay();

        void playerPause();
        
        void complete();
    }

    private List<PlayerListener> listeners = new ArrayList<PlayerListener>();
    private MediaPlayer player;
    private String lastPath = "";
    private Timer timer = new Timer();
    private Context context;

    public Player(Context context)
    {
        this.context = context;
        timer.schedule(this, 0, 1000);
    }

    public void setListener(PlayerListener listener)
    {
        this.listeners.add(listener);
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
    }

    public void pause()
    {
        if (isPlaying())
        {
            player.pause();
            playerPause();
        }
    }

    public void play(String path)
    {
        if (!lastPath.equals(path))
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
        lastPath = "";
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

    @Override
    public void run()
    {
        playerProgress();
    }

    private void playerPause()
    {
        if (player == null)
        {
            return;
        }
        for (PlayerListener listener : listeners)
        {
            listener.playerPause();
        }
    }

    private void playerPlay()
    {
        if (player == null)
        {
            return;
        }
        for (PlayerListener listener : listeners)
        {
            listener.playerPlay();
        }
    }
    
    private void playerDuration()
    {
        if (player == null)
        {
            return;
        }
        for (PlayerListener listener : listeners)
        {
            listener.playerDuration(player.getDuration());
        }
    }

    private void playerProgress()
    {
        if (player == null)
        {
            return;
        }
        for (PlayerListener listener : listeners)
        {
            listener.playerProgress(player.getCurrentPosition());
        }
    }

    @Override
    public boolean cancel()
    {
        timer.cancel();
        timer.purge();
        listeners.clear();
        stop();
        return super.cancel();
    }

    @Override
    public void onCompletion(MediaPlayer player)
    {
        playerPause();
        for (PlayerListener listener : listeners)
        {
            listener.complete();
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
}
