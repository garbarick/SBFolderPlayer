package ru.net.serbis.folder.player.receiver;

import android.content.*;
import android.view.*;
import ru.net.serbis.folder.player.service.*;
import ru.net.serbis.utils.*;

public class MediaButtonReceiver extends BroadcastReceiver
{  
    @Override
    public void onReceive(Context context, Intent intent)
    {
        if (intent == null)
        {
            return;
        }
        if (!Intent.ACTION_MEDIA_BUTTON.equals(intent.getAction()))
        {
            return;
        }
        if (!intent.hasExtra(intent.EXTRA_KEY_EVENT))
        {
            return;
        }
        KeyEvent event = intent.getExtras().getParcelable(intent.EXTRA_KEY_EVENT);
        if (KeyEvent.ACTION_UP != event.getAction())
        {
            return;
        }
        switch (event.getKeyCode())
        {
            case KeyEvent.KEYCODE_MEDIA_PLAY:
            case KeyEvent.KEYCODE_MEDIA_PAUSE:
            case KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE:
                PlayerReceiver.sendAction(context, PlayerActions.PLAY_PAUSE);
                break;

            case KeyEvent.KEYCODE_MEDIA_NEXT:
                PlayerReceiver.sendAction(context, PlayerActions.NEXT);
                break;
                
            case KeyEvent.KEYCODE_MEDIA_PREVIOUS:
                PlayerReceiver.sendAction(context, PlayerActions.PREVIOUS);
                break;

            case KeyEvent.KEYCODE_MEDIA_SKIP_FORWARD:
            case KeyEvent.KEYCODE_MEDIA_STEP_FORWARD:
                PlayerReceiver.sendAction(context, PlayerActions.SKIP_RIGHT);
                break;

            case KeyEvent.KEYCODE_MEDIA_SKIP_BACKWARD:
            case KeyEvent.KEYCODE_MEDIA_STEP_BACKWARD:
                PlayerReceiver.sendAction(context, PlayerActions.SKIP_LEFT);
                break;

            default:
                UITool.get().toast("KeyEvent: " + event);
        }
    }
} 
