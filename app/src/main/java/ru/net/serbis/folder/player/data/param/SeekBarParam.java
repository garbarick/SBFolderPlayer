package ru.net.serbis.folder.player.data.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;

public class SeekBarParam extends NumberParam<SeekBar>
{
    private int max;

    public SeekBarParam(int nameId, int max, int defaultValue)
    {
        super(nameId, defaultValue);
        this.max = max;
    }

    @Override
    public int getLayoutId()
    {
        return R.layout.param_seekbar;
    }

    @Override
    public void initViewValue(View parent)
    {
        SeekBar view = getViewValue(parent);
        view.setMax(max);
        setValue(view, getValue());
    }

    @Override
    public void setValue(SeekBar view, Integer value)
    {
        view.setProgress(value);
    }

    @Override
    public Integer getValue(SeekBar view)
    {
        return view.getProgress();
    }
}
