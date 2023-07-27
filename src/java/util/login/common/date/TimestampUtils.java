package util.login.common.date;

import java.time.Instant;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

/**
 * @author xuliangliang.1995
 */
public class TimestampUtils {

    private static final ZoneId DEFAULT_ZONE_ID = ZoneId.systemDefault();

    private static final long OFFSET_SECONDS_OF_DEFAULT_ZONE_ID = calculateOffsetMillisSecondsByZoneId(DEFAULT_ZONE_ID);

    private static final long TOTAL_MILLIS_SECONDS_OF_DAY = TimeUnit.DAYS.toMillis(1);

    /**
     * get current timestamp
     * @return
     */
    public static long now() {
        return System.currentTimeMillis();
    }

    /**
     * start timestamp of the day
     * @param timestamp13
     * @return start timestamp of the day by default zone
     */
    public static long startOfTheDay(long timestamp13) {
        return timestamp13 - (timestamp13 + OFFSET_SECONDS_OF_DEFAULT_ZONE_ID) % TOTAL_MILLIS_SECONDS_OF_DAY;
    }

    /**
     * start timestamp of the day
     * @param timestamp13
     * @param zoneId
     * @return start timestamp of the day by given zone
     */
    public static long startOfTheDay(long timestamp13, ZoneId zoneId) {
        return timestamp13 - timestamp13 % TOTAL_MILLIS_SECONDS_OF_DAY - calculateOffsetMillisSecondsByZoneId(zoneId);
    }

    /**
     * clear seconds of the given timestamp
     * @param timestamp13
     * @return
     */
    public static long clearSeconds(long timestamp13) {
        return timestamp13 - timestamp13 % 60_000;
    }

    /**
     * clear millis seconds of given timestamp
     * @param timestamp13
     * @return
     */
    public static long clearMillisSeconds(long timestamp13) {
        return timestamp13 - timestamp13 % 1_000;
    }

    /**
     * get offset millis seconds by {@link ZoneId}
     * @param zoneId
     * @return
     */
    private static long calculateOffsetMillisSecondsByZoneId(ZoneId zoneId) {
        return TimeUnit.SECONDS.toMillis(zoneId.getRules()
                .getOffset(Instant.now()).getTotalSeconds());
    }

}
