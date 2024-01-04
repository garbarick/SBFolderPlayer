package ru.net.serbis.folder.player.activity;

import android.content.*;
import android.os.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.util.*;

public class ExceptionReport extends TextActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (intent.hasExtra(Constants.THROWABLE))
        {
            Throwable error = (Throwable) intent.getSerializableExtra(Constants.THROWABLE);
            edit.setText(SysTool.get().errorToText(error));
        }
    }

    @Override
    protected void onOk()
    {
        Intent intent = new Intent(this, Main.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        super.onOk();
    }
}
