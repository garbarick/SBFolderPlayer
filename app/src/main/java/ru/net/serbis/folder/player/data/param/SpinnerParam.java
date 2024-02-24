package ru.net.serbis.folder.player.data.param;

import android.content.*;
import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;

public abstract class SpinnerParam<T> extends Param<T, Spinner>
{
    protected T[] values;

    public SpinnerParam(int nameId, T defaultValue, T[] values)
    {
        super(nameId, defaultValue);
        this.values = values;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_spinner;
    }

    @Override
    public void initViewValue(View parent)
    {
        Spinner view = getViewValue(parent);
        ArrayAdapter<T> adapter = new ArrayAdapter<T>(view.getContext(), android.R.layout.simple_spinner_item, this.values);  
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        view.setAdapter(adapter);
        setValue(view, getValue());
    }

    @Override
    public void setValue(Spinner view, T value)
    {
        ArrayAdapter<T> adapter = (ArrayAdapter<T>) view.getAdapter();
        view.setSelection(adapter.getPosition(value));
    }

    @Override
    public T getValue(Spinner view)
    {
        return (T) view.getSelectedItem();
    }
}
