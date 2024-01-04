package ru.net.serbis.folder.player.data.param;

import android.view.*;

public abstract class TextParam<V extends View> extends Param<String, V>
{
    public TextParam(int nameId, String defaultValue)
    {
        super(nameId, defaultValue);
    }

    @Override
    public String typeToString(String value)
    {
        return value;
    }

    @Override
    public String stringToType(String value)
    {
        return value;
    }
}
