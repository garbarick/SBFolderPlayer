package ru.net.serbis.folder.player.data;

import java.util.*;

public interface Constants
{
    String APP = "SBFolderPlayer";
    int ERROR_ZIP_DIR = 400;
    int ERROR_FILE_IS_NOT_FOUND = 401;
    int ERROR_ACTIVITIES = 402;
    int ERROR_CLEAR_TRASH = 403;
    int ERROR_LOAD_FILE_LIST = 404;
    String RESOURCE = "RESOURCE";
    String MEDIA_FILES = "MEDIA_FILES";
    String LAST_MEDIA_FILE = "LAST_MEDIA_FILE";
    String TEMP_FILES = "TEMP_FILES";

    List<String> EXTENSIONS = Arrays.asList
    (
        new String[]
        {
            "mp3",
            "m4a",
            "wav",
            "ogg"
        }
	);
}
