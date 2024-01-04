package ru.net.serbis.folder.player.extension.share;

public interface Share
{
    String PACKAGE = "ru.net.serbis.share";
    String SERVICE = PACKAGE + ".service.FilesService";
    String ACCOUNTS = PACKAGE + ".activity.Accounts";
    String SELECT_MODE = PACKAGE + ".SELECT_MODE";
    String ACTION = PACKAGE + ".ACTION";
    String SELECT_PATH = PACKAGE + ".SELECT_PATH";
    String PATH = PACKAGE + ".PATH";
    String FILES_LIST = PACKAGE + ".FILES_LIST";
    String RESULT = PACKAGE + ".RESULT";
    String ERROR = PACKAGE + ".ERROR";
    String ERROR_CODE = PACKAGE + ".ERROR_CODE";
    String FILE = PACKAGE + ".FILE";
    String PROGRESS = PACKAGE + ".PROGRESS";
    String BUFFER_SIZE = PACKAGE + ".BUFFER_SIZE";

    int ACTION_SELECT_ACCOUNT_PATH = 102;
    int ACTION_GET_FILES_LIST = 103;
    int ACTION_GET_FILE = 104;
    int ACTION_UPLOAD = 107;

    int RESULT_CHOOSE_FOLDER = 100;

    String PREFIX = "//sbshare/";
    String SUCCESS = "SUCCESS";
}
