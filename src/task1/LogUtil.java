package task1;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class LogUtil {

    public static final int START = 0;
    public static final int PROCENT = 100;
    private static Pattern pattern;
    private static Matcher matcher;

    private LogUtil() {
    }

    public static Deque<String> getSegmentTimeLog(StringBuffer oldLogs) {
        Deque<String> newLogs = new LinkedList<>();
        String format = "%s-%s %s";
        int start = START;
        pattern = Pattern.compile("(?<startTime>\\d{2}:\\d{2})\\s*(?<employment>\\W*)(?<endTime>\\d{2}:\\d{2})");
        matcher = pattern.matcher(oldLogs);
        while (matcher.find(start)) {
            String startTime = matcher.group("startTime");
            String endTime = matcher.group("endTime");
            String employement = matcher.group("employment");
            String newLogLine = String.format(format, startTime, endTime, employement);

            if (!employement.equals("Конец")) {
                newLogs.addLast(newLogLine);
            } else {
                newLogs.addLast("Конец");
            }
            start = matcher.start("endTime");
        }

        return newLogs;
    }

    public static Deque<String> getTotalTimeLog(StringBuffer oldLogs) {
        List<String> days = Arrays.asList(oldLogs.toString().split("Конец"));
        System.out.println(days);
        Deque<String> resultLog = new LinkedList<>();

        for (String value : days) {
            Map<String, Integer> totalTimeLog = new LinkedHashMap<>();
            Map<String, Integer> detailedLog = new LinkedHashMap<>();
            pattern = Pattern.compile("(?<startTime>\\d{2}:\\d{2})-(?<endTime>\\d{2}:\\d{2})\\s*(?<employment>\\W*)");
            matcher = pattern.matcher(value);
            while (matcher.find()) {
                Integer intervalTime = TimeUtil.getIntervalTime(matcher.group("startTime"), matcher.group("endTime"));
                String employment = matcher.group("employment");
                if (isValid(employment)) {
                    totalTimeLog.merge(employment, intervalTime, (a, b) -> a + b);
                } else {
                    totalTimeLog.merge("Лекции", intervalTime, (a, b) -> a + b);
                    detailedLog.put(employment, intervalTime);
                }
            }
            compouse(resultLog, totalTimeLog, detailedLog);
        }

        return resultLog;
    }

    private static void compouse(Deque<String> resultLog, Map<String, Integer> totalTimeLog, Map<String, Integer> detailedLog) {
        String format = "%s: %s минут %s%%";
        Optional<Integer> totalTime = totalTimeLog.values().stream()
                .reduce((s1, s2) -> s1 + s2);
        System.out.println(totalTime.get());
        totalTimeLog.keySet().stream()
                .forEach(it -> resultLog.addLast(String.format(format, it, totalTimeLog.get(it), totalTimeLog.get(it) * PROCENT / totalTime.get())));
        resultLog.addLast("Лекции: ");
        detailedLog.keySet().stream()
                .forEach(it -> resultLog.addLast(String.format(format, it, detailedLog.get(it), detailedLog.get(it) * PROCENT / totalTime.get())));
        resultLog.addLast("");
    }

    private static boolean isValid(String employment) {
        Pattern pattern = Pattern.compile("^(Упражнения)|(Перерыв)|(Обеденный перерыв)|(Решение)$");
        Matcher matcher = pattern.matcher(employment);

        return matcher.matches();
    }
}
