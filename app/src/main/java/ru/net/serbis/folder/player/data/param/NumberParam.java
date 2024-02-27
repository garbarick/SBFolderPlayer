package ru.net.serbis.folder.player.data.param;

import android.view.*;
import ru.net.serbis.folder.player.util.*;

public abstract class NumberParam<V extends View> extends Param<Integer, V>
{
    public NumberParam(int nameId, Integer defaultValue)
    {
        super(nameId, defaultValue);
    }

    @Override
    public String typeToString(Integer value)
    {
        return value.toString();
    }

    @Override
    public Integer stringToType(String value)
    {
        try
        {
            return Integer.valueOf(value);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            return defaultValue;
        }
    }
}
