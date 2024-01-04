package ru.net.serbis.folder.player.task;

import ru.net.serbis.folder.player.data.*;

public interface TaskCallback<R>
{
    void progress(int progress);
    void onResult(R result, TaskError error);
}
