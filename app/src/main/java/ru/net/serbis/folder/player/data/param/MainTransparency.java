package ru.net.serbis.folder.player.data.param;

import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;
import ru.net.serbis.utils.param.*;

public class MainTransparency extends SeekBarParam
{
    public MainTransparency()
    {
        super(R.string.transparency, 0, 255, 0, false);
    }

    @Override
    public void saveValue(Integer value)
    {
        super.saveValue(value);
        HelpTool.get().setColorTransparent(context, value);
    }
}
