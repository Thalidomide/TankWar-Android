package com.teamjava.tankwar.util;

import android.content.Context;
import android.media.MediaPlayer;

/**
 * @author raymond
 */
public class MediaPlayerUtil
{
    private MediaPlayer mediaPlayer;
    private Context context;

    public MediaPlayerUtil(Context context)
    {
        this.context = context;
    }

    public void playSound(int file_resource )
    {
        mediaPlayer = MediaPlayer.create(context, file_resource);
        mediaPlayer.start();
    }
}
