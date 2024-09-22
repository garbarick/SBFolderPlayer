package ru.net.serbis.folder.player.util;

import java.util.*;
import org.json.*;
import ru.net.serbis.utils.*;

public class JsonMapTools
{
    public String toJson(Map<String, String> map)
    {
        JSONArray items = new JSONArray();
        try
        {
            Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
            while (iterator.hasNext())
            {
                Map.Entry<String, String> entry = iterator.next();
                JSONObject item = new JSONObject();
                item.put("k", entry.getKey());
                item.put("v", entry.getValue());
                items.put(item);
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return items.toString();
    }

    public Map<String, String> parseMap(String json)
    {
        Map<String, String> result = new LinkedHashMap<String, String>();
        try
        {
            JSONArray items = new JSONArray(json);
            for (int i = 0; i < items.length(); i++)
            {
                JSONObject item = items.getJSONObject(i);
                result.put(getString(item, "k"), getString(item, "v"));
            }
        }
        catch (Exception e)
        {
            Log.error(this, e);
        }
        return result;
    }

    private String getString(JSONObject item, String key) throws Exception
    {
        return item.has(key) ? item.getString(key) : "";
    }
}
