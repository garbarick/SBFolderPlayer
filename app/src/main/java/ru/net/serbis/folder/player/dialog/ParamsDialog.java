package ru.net.serbis.folder.player.dialog;

import android.app.*;
import android.content.*;
import android.view.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.adapter.*;
import ru.net.serbis.folder.player.data.param.*;
import ru.net.serbis.folder.player.receiver.*;
import ru.net.serbis.folder.player.service.*;

public class ParamsDialog extends AlertDialog.Builder implements DialogInterface.OnClickListener
{
    private Activity context;
    private ParamsAdapter adapter;

    public ParamsDialog(Activity context, int titleId, Param[] params)
    {
        super(context);
        this.context = context;

        setTitle(titleId);

        View view = LayoutInflater.from(context).inflate(R.layout.params, null, false);
		adapter = new ParamsAdapter(context, view, params);
        setView(view);

        setPositiveButton(android.R.string.ok, this);
        setNeutralButton(R.string.reset, this);
        setNegativeButton(android.R.string.cancel, this);

        show();
    }

    @Override
    public void onClick(DialogInterface dialog, int id)
    {
        switch(id)
        {
            case Dialog.BUTTON_POSITIVE:
                adapter.saveValues();
                PlayerReceiver.sendAction(context, PlayerActions.NOTIFY);
                break;
            case Dialog.BUTTON_NEUTRAL:
                adapter.reset();
                PlayerReceiver.sendAction(context, PlayerActions.NOTIFY);
                break;
        }
    }

    public void updateValue(Param param, Object value)
    {
        adapter.updateValue(param, value);
    }
}
