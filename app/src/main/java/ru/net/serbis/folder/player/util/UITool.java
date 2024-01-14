package ru.net.serbis.folder.player.util;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.data.*;

public class UITool
{
    private static final UITool instance = new UITool();

    public static UITool get()
    {
        return instance;
    }

    public <T> T findView(View view, int id)
    {
        return (T) view.findViewById(id);
    }

    public <T> T findView(Activity view, int id)
    {
        return (T) view.findViewById(id);
    }
    
    public <T> T findView(AlertDialog view, int id)
    {
        return (T) view.findViewById(id);
    }

    public String getEditText(Activity activity, int id)
    {
        EditText text = findView(activity, id);
        return text.getText().toString();
    }

    public void hide(Activity activity, int id)
    {
        View view = findView(activity, id);
        view.setVisibility(View.GONE);
    }

    public void show(Activity activity, int id)
    {
        View view = findView(activity, id);
        view.setVisibility(View.VISIBLE);
    }

    public void enable(Activity activity, int id)
    {
        View view = findView(activity, id);
        enable(view);
    }

    public void enable(View view)
    {
        view.setEnabled(true);
    }

    public void disable(Activity activity, int id)
    {
        View view = findView(activity, id);
        disable(view);
    }

    public void disable(View view)
    {
        view.setEnabled(false);
    }

    public boolean isEnabled(Activity activity, int id)
    {
        View view = findView(activity, id);
        return isEnabled(view);
    }

    public boolean isEnabled(View view)
    {
        return view.isEnabled();
    }

    public void disableAll(View view)
    {
        setEnableAll(view, false);
    }

    public void enableAll(View view)
    {
        setEnableAll(view, true);
    }

    public void setEnableAll(View view, boolean enable)
    {
        view.setEnabled(enable);
        if (view instanceof ViewGroup)
        {
            ViewGroup group = (ViewGroup) view;
            for (int i = 0; i < group.getChildCount(); i ++)
            {
                View child = group.getChildAt(i);
                setEnableAll(child, enable);
            }
        }
    }

    public void toast(final Context context, final String text)
    {
        runOnUiThread(
            new Runnable()
            {
                public void run()
                {
                    Toast.makeText(context, text, Toast.LENGTH_LONG).show();
                }
            }
        );
    }
    
    public void toast(Context context, int code, String text)
    {
        toast(context, code + ": " + text);
    }

    public void toast(Context context, TaskError error)
    {
        if (error == null)
        {
            return;
        }
        toast(context, error.getCode(), error.getMessage());
    }
    
    public void notImplementedYet(Context context)
    {
        toast(context, context.getResources().getString(R.string.error_not_implemented));
    }

    public int getPercent(long max, long cur)
    {
        Double percent = 100.0 / max * cur;
        return percent.intValue();
    }

    public void initButtons(Activity context, View.OnClickListener tool, int ... ids)
    {
        for (int id : ids)
        {
            View button = findView(context, id);
            button.setOnClickListener(tool);
        }
    }
    
    public void setProgress(Context context, boolean progress)
    {
        App app = (App) context.getApplicationContext();
        app.setProgress(progress);
    }
    
    public boolean isProgress(Context context)
    {
        App app = (App) context.getApplicationContext();
        return app.isProgress();
    }

    public <T> T getBundle(Bundle bundle, String key, T defaultValue)
    {
        if (bundle == null)
        {
            return defaultValue;
        }
        T value = (T) bundle.get(key);
        if (value == null)
        {
            return defaultValue;
        }
        return value;
    }

    public String formatTime(int millisec)
    {
        long second = (millisec / 1000) % 60;
        long minute = (millisec / (1000 * 60)) % 60;
        long hour = (millisec / (1000 * 60 * 60)) % 24;
        return String.format("%02d:%02d:%02d", hour, minute, second);
    }

    public String getNum(int position, int count)
    {
        if (count == 0)
        {
            return "";
        }
        int length = String.valueOf(count).length();
        return String.format("%0" + length + "d", position + 1);
    }

    public void runOnUiThread(Runnable run)
    {
        new Handler(Looper.getMainLooper()).post(run);
    }
}
