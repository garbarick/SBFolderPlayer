package ru.net.serbis.folder.player.service;

public interface PlayerActions
{
    String INIT = "INIT";
    String INIT_MAIN = "INIT_MAIN";
    String READY = "READY";
    String PREVIOUS = "PREVIOUS";
    String SKIP_LEFT = "SKIP_LEFT";
    String PLAY_PAUSE = "PLAY_PAUSE";
    String SKIP_RIGHT = "SKIP_RIGHT";
    String NEXT = "NEXT";
    String CLOSE = "CLOSE";
    String NOTIFY = "NOTIFY";

    int ACTION_PLAY_PAUSE = 100;
    int ACTION_PREVIOUS = 101;
    int ACTION_NEXT = 102;
    int ACTION_SKIP_LEFT = 103;
    int ACTION_SKIP_RIGHT = 104;
    int ACTION_CLOSE = 105;
    int ACTION_INIT = 106;
    int ACTION_NOTIFY = 107;
    int PLAYER_ERROR = 400;

    String ERROR = "ERROR";
    String ERROR_CODE = "ERROR_CODE";
    String RESULT = "RESULT";
    String PROGRESS = "PROGRESS";
}
