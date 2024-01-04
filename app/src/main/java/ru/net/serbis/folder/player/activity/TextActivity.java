package ru.net.serbis.folder.player.activity;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.folder.player.util.*;

public class TextActivity extends Activity implements View.OnClickListener
{
    protected EditText edit;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        edit = UITool.get().findView(this, R.id.text);
        edit.setKeyListener(null);
        edit.setTextIsSelectable(true);

        initTitle();
        UITool.get().initButtons(this, this, R.id.ok, R.id.cancel);
    }

    protected void initTitle()
    {
        Intent intent = getIntent();
        if (intent.hasExtra(Constants.TITLE))
        {
            String title = intent.getStringExtra(Constants.TITLE);
            setTitle(title);
        }
    }

    @Override
    public void onClick(View view)
    {
        switch(view.getId())
        {
            case R.id.ok:
                onOk();
                break;

            case R.id.cancel:
                onCancel();
                break;
        }
    }

    protected void onOk()
    {
        finish();
    }

    protected void onCancel()
    {
        finish();
    }
}
