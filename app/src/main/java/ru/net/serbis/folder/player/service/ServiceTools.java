package ru.net.serbis.folder.player.service;

import android.content.*;
import android.os.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.connection.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.task.*;
import ru.net.serbis.folder.player.util.*;

public abstract class ServiceTools
{
    protected Context context;
    protected App app;

    public void set(Context context)
    {
        this.context = context;
        app = (App) context.getApplicationContext();
    }

    protected void sendServiceAction(int action, Handler reply)
    {
        sendServiceAction(action, new HashMap<String, String>(), reply);
    }

    protected void sendServiceAction(int action, Map<String, String> request, Handler reply)
    {
        UITool.get().setProgress(context, true);
        ExtConnection connection = getConnection();
        if (!connection.isBound())
        {
            return;
        }
        Message msg = Message.obtain(null, action, 0, 0);
        Bundle data = new Bundle();
        for (Map.Entry<String, String> entry : request.entrySet())
        {
            data.putString(entry.getKey(), entry.getValue());
        }
        msg.setData(data);
        msg.replyTo = new Messenger(reply);
        try
        {
            connection.getService().send(msg);
        }
        catch (RemoteException e)
        {
            Log.error(this, e);
        }
    }

    protected abstract ExtConnection getConnection()

    protected <T> void progress(TaskCallback<T> callback, int progress)
    {
        try
        {
            callback.progress(progress);
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
    }

    protected <T> void onResult(TaskCallback<T> callback, T result, TaskError error)
    {
        try
        {
            callback.onResult(result, error);
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        finally
        {
            UITool.get().setProgress(context, false);
        }
    }

    protected <T> boolean checkResult(TaskCallback<T> callback, Message msg, String keyResult)
    {
        if (msg.getData().containsKey(keyResult))
        {
            T result = (T) msg.getData().get(keyResult);
            onResult(callback, result, null);
            return true;
        }
        return false;
    }

    protected <T> boolean checkError(TaskCallback<T> callback, Message msg, String keyError, String keyErrorCode)
    {
        if (msg.getData().containsKey(keyError) &&
            msg.getData().containsKey(keyErrorCode))
        {
            int errorCode = msg.getData().getInt(keyErrorCode);
            String error = msg.getData().getString(keyError);
            onResult(callback, null, new TaskError(errorCode, error));
            return true;
        }
        return false;
    }

    protected <T> boolean checkProgress(TaskCallback<T> callback, Message msg, String keyProgress)
    {
        if (msg.getData().containsKey(keyProgress))
        {
            int progress = msg.getData().getInt(keyProgress);
            progress(callback, progress);
            return true;
        }
        return false;
    }
}
