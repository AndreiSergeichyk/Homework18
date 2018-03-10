package task1;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class TimeUtil {

    public static final int MKSEC = 1000;
    public static final int SEC = 60;

    private TimeUtil() {
    }

    public static Integer getIntervalTime(String startTime, String endTime) {
        Date startData = null;
        Date endData = null;
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");

        try {
            startData = format.parse(startTime);
            endData = format.parse(endTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        long difference = endData.getTime() - startData.getTime();
        Integer min = (int) (difference / (MKSEC * SEC));

        return min;
    }
}
