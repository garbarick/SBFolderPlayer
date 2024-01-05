package ru.net.serbis.folder.player.data.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.data.*;

public class FolderTypeParam extends SpinnerParam<FolderType> implements AdapterView.OnItemSelectedListener
{
    protected Param[][] params;
    
    public FolderTypeParam(int nameId, Param[][] params)
    {
        super(nameId, FolderType.LOCAL, FolderType.class.getEnumConstants());
        this.params = params;
    }

    @Override
    public String typeToString(FolderType value)
    {
        return value.name();
    }

    @Override
    public FolderType stringToType(String value)
    {
        return FolderType.valueOf(value);
    }

    @Override
    public void initViewValue(View parent)
    {
        super.initViewValue(parent);
        Spinner spinner = getViewValue(parent);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> spinner, View view, int position, long id)
    {
        for (int i = 0; i < params.length; i++)
        {
            int visible = i == position ? View.VISIBLE : View.GONE;
            for (Param param : params[i])
            {
                adapter.getView(param).setVisibility(visible);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> p1)
    {
    }
}
