package ru.net.serbis.folder.player.data.param;

import android.view.*;
import android.widget.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.dialog.*;

public class FileParam extends TextViewParam
{
    private boolean onlyFolder;
    private boolean onlyFile;

    public FileParam(int nameId, String defaultValue, boolean onlyFolder, boolean onlyFile)
    {
        super(nameId, defaultValue);
        this.onlyFolder = onlyFolder;
        this.onlyFile = onlyFile;
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
                    new FileChooser(context, R.string.choose_dir, onlyFolder, onlyFile)
                    {
                        @Override
                        public void onChoose(String path)
                        {
                            view.setText(path);
                        }
                    };
                }
            }
        );
    }
}
