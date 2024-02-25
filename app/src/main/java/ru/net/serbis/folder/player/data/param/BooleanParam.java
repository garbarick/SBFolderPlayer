package ru.net.serbis.folder.player.data.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;

public class BooleanParam extends Param<Boolean, CheckBox>
{
    public BooleanParam(int nameId, boolean defaultValue)
    {
        super(nameId, defaultValue);
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_boolean;
    }

    @Override
    public void initViewValue(View parent)
    {
        CheckBox view = getViewValue(parent);
        setValue(view, getValue());
    }

    @Override
    public String typeToString(Boolean value)
    {
        return value.toString();
    }

    @Override
    public Boolean stringToType(String value)
    {
        return Boolean.parseBoolean(value);
    }

    @Override
    public void setValue(CheckBox view, Boolean value)
    {
        view.setChecked(value);
    }

    @Override
    public Boolean getValue(CheckBox view)
    {
        return view.isChecked();
    }
}
