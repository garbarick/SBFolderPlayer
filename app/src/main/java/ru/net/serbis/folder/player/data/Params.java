package ru.net.serbis.folder.player.data;

import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.data.param.*;
import ru.net.serbis.folder.player.util.*;

public interface Params
{
    FileParam MUSIC_LOCAL_FOLDER = new FileParam(R.string.music_local_folder, IOTool.get().getMusicFolderPath(), true, false);
    ShareDirParam MUSIC_SHARE_FOLDER = new ShareDirParam(R.string.music_share_folder, null);
    EditNumberParam BUFFER_SIZE = new EditNumberParam(R.string.buffer_size, 10240);
    TempFolderParam TEMP_FOLDER = new TempFolderParam(R.string.temp_folder);
    EditNumberParam TEMP_FILES_COUNT = new EditNumberParam(R.string.temp_files_count, 10);
    FolderTypeParam MUSIC_FOLDER_TYPE = new FolderTypeParam(
        R.string.music_folder_type,
        new Param[][]{
            {MUSIC_LOCAL_FOLDER},
            {MUSIC_SHARE_FOLDER, BUFFER_SIZE, TEMP_FOLDER, TEMP_FILES_COUNT}
        });
    EditNumberParam VOLUM_LEVEL = new EditNumberParam(R.string.volum_level, -1);
    BooleanParam NOTIFICATION_PLAYER = new BooleanParam(R.string.notification_player, true);
    MainTransparency TRANSPARENCY = new MainTransparency();
    WidgetTransparency WIDGET_TRANSPARENCY = new WidgetTransparency();
    Param[] PARAMS = new Param[]{
        MUSIC_LOCAL_FOLDER,
        MUSIC_SHARE_FOLDER,
        MUSIC_FOLDER_TYPE,
        BUFFER_SIZE,
        TEMP_FOLDER,
        TEMP_FILES_COUNT,
        NOTIFICATION_PLAYER,
        TRANSPARENCY,
        WIDGET_TRANSPARENCY
    };
}
