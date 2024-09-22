package ru.net.serbis.folder.player.data.param;

import android.app.*;
import ru.net.serbis.utils.param.*;

public class TempFolderParam extends FileParam
{
    public TempFolderParam(int nameId)
    {
        super(nameId, null, true, false);
    }

    @Override
    public void setContext(Activity context)
    {
        super.setContext(context);
        value = context.getFilesDir().getAbsolutePath();
    }
}
