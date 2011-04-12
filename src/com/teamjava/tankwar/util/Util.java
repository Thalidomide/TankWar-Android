package com.teamjava.tankwar.util;

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
}
