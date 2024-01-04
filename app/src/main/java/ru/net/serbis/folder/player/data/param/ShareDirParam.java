package ru.net.serbis.folder.player.data.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.extension.share.*;

public class ShareDirParam extends TextViewParam
{
    public ShareDirParam(int nameId, String defaultValue)
    {
        super(nameId, defaultValue);
    }

    @Override
    public void initViewValue(View parent)
    {
        final TextView view = getViewValue(parent);
        setValue(view, getValue(context));
        view.setOnClickListener(
            new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    ShareFolders folders = new ShareFolders();
                    context.startActivityForResult(
                        folders.getFoldersIntent(),
                        folders.getFolderResult());
                }
            }
        );
    }
}
