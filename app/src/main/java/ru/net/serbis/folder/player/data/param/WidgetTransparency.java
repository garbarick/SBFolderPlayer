package ru.net.serbis.folder.player.data.param;

import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.listener.*;
import ru.net.serbis.utils.param.*;

public class WidgetTransparency extends SeekBarParam
{
    public WidgetTransparency()
    {
        super(R.string.widget_transparency, 0, 255, 0, false);
    }

    @Override
    public void saveValue(Integer value)
    {
        super.saveValue(value);
        WidgetListener.get().update(false);
    }
}
