package ru.net.serbis.folder.player.data.param;

import android.app.*;
import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.adapter.*;
import ru.net.serbis.folder.player.util.*;

public abstract class Param<T, V extends View>
{
    protected String name;
    private int nameId;
    protected T defaultValue;
    protected Activity context;
    protected ParamsAdapter adapter;

    public Param(int nameId, T defaultValue)
    {
        this.nameId = nameId;
        this.defaultValue = defaultValue;
    }

    public abstract int getLayoutId();

    public void initName(Context context)
    {
        name = context.getResources().getString(nameId);
    }

    public void setAdapter(ParamsAdapter adapter)
    {
        this.adapter = adapter;
    }

    public void initNameView(View parent)
    {
        TextView view = UITool.get().findView(parent, R.id.name);
        view.setText(name);
    }

    public abstract void initViewValue(View parent);

    public V getViewValue(View parent)
    {
        return UITool.get().findView(parent, R.id.value);
    }

    public void saveValue(T value)
    {
        String data = value == null ? null : typeToString(value);
        Preferences.get().setString(name, data);
    }

    public abstract String typeToString(T value);

    public T getValue()
    {
        return stringToType(Preferences.get().getString(name, typeToString(defaultValue)));
    }

    public abstract T stringToType(String value);

    public void saveViewValue(V view)
    {
        T value = getValue(view);
        saveValue(value);
    }

    public abstract void setValue(V view, T value);
    public abstract T getValue(V view);

    public void setContext(Activity context)
    {
        this.context = context;
    }
}
