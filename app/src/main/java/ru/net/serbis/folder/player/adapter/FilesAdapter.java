package ru.net.serbis.folder.player.adapter;

import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.dialog.*;
import ru.net.serbis.folder.player.util.*;

public class FilesAdapter extends ArrayAdapter<File> implements AdapterView.OnItemClickListener
{
    private File folder = Environment.getExternalStorageDirectory();
    private FileChooser chooser;
    private boolean onlyFolder;
    private boolean onlyFile;

    public FilesAdapter(Context context, FileChooser chooser, boolean onlyFolder, boolean onlyFile)
    {
        super(context, android.R.layout.simple_list_item_activated_1);
        this.chooser = chooser;
        this.onlyFolder = onlyFolder;
        this.onlyFile = onlyFile;
    }

    public File getSelected()
    {
        if (onlyFolder)
        {
            return folder;
        }
        int selected = chooser.getList().getCheckedItemPosition();
        if (selected > -1)
        {
            File file = getItem(selected);
            if (onlyFile && file.isFile())
            {
                return file;
            }
            return file;
        }
        return null;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent)
    {
        view = LayoutInflater.from(getContext()).inflate(R.layout.resource_img, parent, false);
        TextView text = UITool.get().findView(view, R.id.name);
        ImageView img = UITool.get().findView(view, R.id.img);
        File file = getItem(position);
        if (file.equals(folder))
        {
            text.setText(file.getPath() + "/..");
            img.setImageResource(R.drawable.open_dir);
        }
        else
        {
            text.setText(file.getName());
            if (file.isDirectory())
            {
                img.setImageResource(R.drawable.dir);
            }
            else
            {
                img.setImageResource(R.drawable.file);
            }
        }
        return view;
    }

    public void initFiles()
    {
        setNotifyOnChange(false);
        clear();

        File[] files = getFiles();
        List<File> result = getFiles(files);
        if (folder.getParentFile() != null)
        {
            result.add(0, folder);
        }

        addAll(result);
        setNotifyOnChange(true);
        notifyDataSetChanged();
        chooser.getList().setItemChecked(0, true);
	}

    private File[] getFiles()
    {
        return folder.listFiles(
            new FileFilter()
            {
                public boolean accept(File file)
                {
                    return onlyFolder && file.isDirectory() || !onlyFolder;
                }
            }
        );
	}

    private List<File> getFiles(File[] files)
    {
        List<File> result = new ArrayList<File>();
        if (files != null)
        {
            result.addAll(Arrays.asList(files));
        }
        Collections.sort(
            result,
            new Comparator<File>()
            {
                public int compare(File f1, File f2)
                {
                    if (f1.isDirectory() && f2.isFile())
                    {
                        return -1;
                    }
                    else if (f1.isFile() && f2.isDirectory())
                    {
                        return 1;
                    }
                    else
                    {
                        return f1.compareTo(f2);
                    }
                }
            }
        );
        return result;
	}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id)
    {
        File file = getItem(position);
        if (file.isDirectory())
        {
            if (file.equals(folder))
            {
                File parent = folder.getParentFile();
                if (parent != null)
                {
                    folder = parent;
                }
                else
                {
                    return;
                }
            }
            else
            {
                folder = file;
            }
            initFiles();
        }
	}
}
