package org.sheep.util;

import java.util.Date;

public class TimeUtil {
    private TimeUtil() {}

    public static int getCurrentTime() {
        return (int) (new Date().getTime() / 1000);
    }
}
