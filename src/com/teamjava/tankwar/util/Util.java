package com.teamjava.tankwar.util;

import android.content.Context;
import android.media.MediaPlayer;

import java.util.Random;

/**
 * Common utils methods.
 *
 * @author raymond
 */
public class Util
{
    /**
     * Create a new random int from 0 to maxValue.
     *
     * @param maxValue max value for this random number.
     * @return a number from 0 to maxValue.
     * */
    public static int getRandomNumber(int maxValue){
        Random random = new Random();

        return random.nextInt(maxValue);
    }

    /**
     * Playes a sound in the Android Media Player.
     *
     * @param context the context to play
     * @param file_resource id for the file containing the sound
     * */
    public static void playSound(Context context, int file_resource )
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, file_resource);
        mediaPlayer.start();
    }
}
