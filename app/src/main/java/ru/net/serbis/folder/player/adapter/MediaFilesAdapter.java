package ru.net.serbis.folder.player.adapter;

import android.content.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.util.*;

public class MediaFilesAdapter extends ArrayAdapter<String>
{
    public MediaFilesAdapter(Context context)
    {
        super(context, 0);
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        if (view == null)
        {
            view = LayoutInflater.from(getContext()).inflate(R.layout.media_file, parent, false);
        }
        String path = getItem(position);

        TextView numView = UITool.get().findView(view, R.id.num);
        numView.setText(UITool.get().getNum(position, getCount()));
        
        TextView nameView = UITool.get().findView(view, R.id.name);
        nameView.setText(new File(path).getName());

        TextView parhView = UITool.get().findView(view, R.id.path);
        parhView.setText(path);

        return view;
    }

    public String getChecked(ListView list)
    {
        int position = list.getCheckedItemPosition();
        if (position > -1)
        {
            return getItem(position);
        }
        return null;
    }
}
