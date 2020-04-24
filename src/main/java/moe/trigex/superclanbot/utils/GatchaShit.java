package moe.trigex.superclanbot.utils;

import java.util.Random;

public class GatchaShit {
    public static int RollRange(int min, int max) {
        Random rng = new Random();
        int num = rng.nextInt((max - min) + 1) + min;
        return num;
    }

    public static boolean IsRollRangeWin(int min, int max) {
        if(RollRange(max, min) == max) {
            return true;
        } else {
            return false;
        }
    }
}
