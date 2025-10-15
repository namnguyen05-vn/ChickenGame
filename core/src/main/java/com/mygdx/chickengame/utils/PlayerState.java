package com.mygdx.chickengame.utils;

/**
 * Class để lưu trạng thái player qua các màn chơi
 */
public class PlayerState {
    private static boolean isUpgraded = false;

    public static boolean isUpgraded() {
        return isUpgraded;
    }

    public static void setUpgraded(boolean upgraded) {
        isUpgraded = upgraded;
    }

    public static void reset() {
        isUpgraded = false;
    }
}