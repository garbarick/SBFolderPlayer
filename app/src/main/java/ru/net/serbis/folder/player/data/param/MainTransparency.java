package ru.net.serbis.folder.player.data.param;

import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;

public class MainTransparency extends SeekBarParam
{
    public MainTransparency()
    {
        super(R.string.transparency, 255, 0);
    }

    @Override
    public void saveValue(Integer value)
    {
        super.saveValue(value);
        UITool.get().setColorTransparent(context, value);
    }
}
