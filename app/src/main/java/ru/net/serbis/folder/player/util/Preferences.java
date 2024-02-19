package ru.net.serbis.folder.player.util;

import android.content.*;
import java.util.*;
import ru.net.serbis.folder.player.data.*;

public class Preferences extends Util
{
    private static final Preferences instance = new Preferences();

    public static Preferences get()
    {
        return instance;
    }

    private SharedPreferences getPreferences()
    {
        return context.getSharedPreferences(Constants.APP, Context.MODE_PRIVATE);
    }

    private SharedPreferences.Editor getPreferencesEditor()
    {
        return getPreferences().edit();
    }

    public void setString(String name, String value)
    {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putString(name, value);
        editor.commit();
    }

    public String getString(String name, String defaultValue)
    {
        return getPreferences().getString(name, defaultValue);
    }

    public void setInt(String name, int value)
    {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putInt(name, value);
        editor.commit();
    }

    public int getInt(String name)
    {
        return getPreferences().getInt(name, 0);
    }

    public void setList(String name, List<String> values)
    {
        SharedPreferences.Editor editor = getPreferencesEditor();
        editor.putStringSet(name, new HashSet<String>(values));
        editor.commit();
    }

    public List<String> getList(String name)
    {
        return new ArrayList<String>(
            new TreeSet<String>(
                getPreferences().getStringSet(name, Collections.<String>emptySet())));
    }
}
