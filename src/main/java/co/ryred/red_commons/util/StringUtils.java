package co.ryred.red_commons.util;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * Created by Cory Redmond on 29/12/2015.
 *
 * @author Cory Redmond <ace@ac3-servers.eu>
 */
public class StringUtils {

    /**
     * Converts a String to a UUID
     *
     * @param uuid The string to be converted
     * @return The result
     */
    public static UUID getUUIDFromString(String uuid)
    {
        uuid = uuid.replace( "-", "" );
        return UUID.fromString(uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32));
    }

    /**
     * Formats milliseconds into human readable text.
     *
     * @param millis Milliseconds to be formatted.
     * @return The formatted string that looks beautiful.
     */
    public static String getTimeString(long millis) {
        if (millis < 1L) {
            return "not very long!";
        } else {
            long days = TimeUnit.MILLISECONDS.toDays(millis);

            millis -= TimeUnit.DAYS.toMillis(days);
            long hours = TimeUnit.MILLISECONDS.toHours(millis);

            millis -= TimeUnit.HOURS.toMillis(hours);
            long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);

            millis -= TimeUnit.MINUTES.toMillis(minutes);
            long seconds = TimeUnit.MILLISECONDS.toSeconds(millis);
            StringBuilder sb = new StringBuilder();

            if (days > 0L) {
                sb.append(days).append(" day");
                if (days > 1L) {
                    sb.append("s");
                }
            }

            if (hours > 0L) {
                if (days > 0L) {
                    sb.append(", ");
                }

                sb.append(hours).append(" hour");
                if (hours > 1L) {
                    sb.append("s");
                }
            }

            if (minutes > 0L) {
                if (hours > 0L || days > 0L) {
                    sb.append(", ");
                }

                sb.append(minutes).append(" minute");
                if (minutes > 1L) {
                    sb.append("s");
                }
            }

            if (seconds > 0L) {
                if (minutes > 0L || hours > 0L || days > 0L) {
                    sb.append(", ");
                }

                sb.append(seconds).append(" second");
                if (seconds > 1L) {
                    sb.append("s");
                }
            }

            return sb.append(".").toString();
        }
    }

}
