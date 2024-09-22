package ru.net.serbis.folder.player.util;

import android.os.*;
import java.io.*;
import java.util.*;
import ru.net.serbis.folder.player.data.*;
import ru.net.serbis.utils.*;

public class FilesTool
{
    private static final FilesTool instance = new FilesTool();

    public static FilesTool get()
    {
        return instance;
    }

    public String getMusicFolderPath()
    {
        return Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC).getAbsolutePath();
    }
    
    public List<String> findFiles(String fileListPath)
    {
        List<String> result = new ArrayList<String>();
        File file = new File(fileListPath);
        BufferedReader reader = null;
        try
        {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line;
            while ((line = reader.readLine()) != null)
            {
                if (checkExt(line))
                {
                    result.add(line);
                }
            }
        }
        catch (Throwable e)
        {
            Log.error(this, e);
        }
        finally
        {
            IOTool.get().close(reader);
            file.delete();
        }
        Collections.sort(result);
        return result;
	}
    
    public String getExt(String fileName)
    {
        String ext = "";
        int i = fileName.lastIndexOf('.');
        if (i > 0)
        {
            ext = fileName.substring(i + 1).toLowerCase();
        }
        return ext;
    }
    
    public boolean checkExt(String fileName)
    {
        return Constants.EXTENSIONS.contains(getExt(fileName));
	}
    
    public boolean checkExt(File file)
    {
        return checkExt(file.getName());
	}

    public void moveFile(File from, File to, int bufferSize) throws Exception
    {
        IOTool.get().copy(from, to, bufferSize);
        from.delete();
    }

    public boolean moveFileQuietly(File from, File to, int bufferSize)
    {
        try
        {
            moveFile(from, to, bufferSize);
            return true;
        }
        catch (Exception e)
        {
            Log.error(new IOTool(), e);
            return false;
        }
    }
}
