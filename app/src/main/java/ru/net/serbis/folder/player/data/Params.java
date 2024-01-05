package ru.net.serbis.folder.player.data;

import ru.net.serbis.folder.player.*;
import ru.net.serbis.folder.player.data.param.*;
import ru.net.serbis.folder.player.util.*;

public interface Params
{
    FileParam MUSIC_LOCAL_FOLDER = new FileParam(R.string.music_local_folder, IOTool.get().getMusicFolderPath(), true, false);
    ShareDirParam MUSIC_SHARE_FOLDER = new ShareDirParam(R.string.music_share_folder, null);
    NumberParam BUFFER_SIZE = new NumberParam(R.string.buffer_size, 10240);
    TempFolderParam TEMP_FOLDER = new TempFolderParam(R.string.temp_folder);
    NumberParam TEMP_FILES_COUNT = new NumberParam(R.string.temp_files_count, 10);
    FolderTypeParam MUSIC_FOLDER_TYPE = new FolderTypeParam(
        R.string.music_folder_type,
        new Param[][]{
            {MUSIC_LOCAL_FOLDER},
            {MUSIC_SHARE_FOLDER, BUFFER_SIZE, TEMP_FOLDER, TEMP_FILES_COUNT}
        });
    Param[] PARAMS = new Param[]{
        MUSIC_LOCAL_FOLDER,
        MUSIC_SHARE_FOLDER,
        MUSIC_FOLDER_TYPE,
        BUFFER_SIZE,
        TEMP_FOLDER,
        TEMP_FILES_COUNT
    };
}
