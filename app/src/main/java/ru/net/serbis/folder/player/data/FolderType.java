package ru.net.serbis.folder.player.data;

import android.content.*;
import java.util.*;
import ru.net.serbis.folder.player.*;

public enum FolderType
{
    LOCAL(R.string.local),
    SHARE(R.string.share);

    private int nameId;
    private String name;

    private static final Map<String, FolderType> VALUES = new HashMap<String, FolderType>();

    public FolderType(int nameId)
    {
        this.nameId = nameId;
    }

    public void initName(Context context)
    {
        name = context.getResources().getString(nameId);
        VALUES.put(name, this);
    }

    @Override
    public String toString()
    {
        return name;
    }

    public static FolderType get(String name)
    {
        return VALUES.get(name);
    }
}
