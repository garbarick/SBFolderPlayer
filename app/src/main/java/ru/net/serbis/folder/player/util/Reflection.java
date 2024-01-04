package ru.net.serbis.folder.player.util;

import java.lang.reflect.*;
import java.util.*;

public class Reflection
{
    private static final Reflection instance = new Reflection();

    public static Reflection get()
    {
        return instance;
    }
    
    public <T> List<T> getParams(Class holder, Class<T> type)
    {
        List<T> result = new ArrayList<T>();
        for (Field field : holder.getFields())
        {
            if (type.isAssignableFrom(field.getType()))
            {
                T param = getValue(field);
                result.add(param);
            }
        }
        return result;
    }
    
    public <T> T getValue(Field field)
    {
        try
        {
            return (T) field.get(null);
        }
        catch (Exception e)
        {
            Log.error(this, e);
            return null;
        }
    }
}
