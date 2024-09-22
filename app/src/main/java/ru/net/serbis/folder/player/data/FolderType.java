package ru.net.serbis.folder.player.data;

import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.utils.*;

import ru.net.serbis.folder.player.R;

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

    public void initName()
    {
        name = Strings.get().get(nameId);
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

    public static void initNames()
    {
        for (FolderType item : FolderType.class.getEnumConstants())
        {
            item.initName();
        }
    }
}
