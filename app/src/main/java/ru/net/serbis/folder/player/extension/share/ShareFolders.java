package ru.net.serbis.folder.player.extension.share;

import android.content.*;

public class ShareFolders
{
    public Intent getFoldersIntent()
    {
        Intent intent = new Intent();
        intent.setClassName(Share.PACKAGE, Share.ACCOUNTS);
        intent.putExtra(Share.SELECT_MODE, true);
        intent.putExtra(Share.ACTION, Share.ACTION_SELECT_ACCOUNT_PATH);
        return intent;
    }

    public int getFolderResult()
    {
        return Share.RESULT_CHOOSE_FOLDER;
    }

    public boolean isMineResult(int requestCode, Intent data)
    {
        return requestCode == Share.RESULT_CHOOSE_FOLDER && data.hasExtra(Share.SELECT_PATH);
    }

    public String getPath(Intent data)
    {
        return data.getStringExtra(Share.SELECT_PATH);
    }
}
