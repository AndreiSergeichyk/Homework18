package task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    public static void main(String[] args) {
        Path sourceLogPath = Paths.get("resource", "sourceLog.txt");
        Path timeSegmentsLogPath = Paths.get("resource", "timeSegmentsLog.txt");
        StringBuffer logs = ReadUtil.readText(sourceLogPath);
        try {
            Files.write(timeSegmentsLogPath, LogUtil.getSegmentTimeLog(logs));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Path totalTimeLogPath = Paths.get("resource", "totalTimeLog");
        logs = ReadUtil.readText(timeSegmentsLogPath);
        try {
            Files.write(totalTimeLogPath, LogUtil.getTotalTimeLog(logs));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}