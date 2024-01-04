package ru.net.serbis.folder.player.data.param;

import android.content.*;

public class TempFolderParam extends FileParam
{
    public TempFolderParam(int nameId)
    {
        super(nameId, null, true, false);
    }

    @Override
    public void initName(Context context)
    {
        super.initName(context);
        defaultValue = context.getFilesDir().getAbsolutePath();
    }
}
