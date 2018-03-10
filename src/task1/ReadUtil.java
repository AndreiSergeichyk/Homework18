package task1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

public final class ReadUtil {

    private ReadUtil() {
    }

    public static StringBuffer readText(Path path) {
        StringBuffer textBuffer = new StringBuffer();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(path.toFile()))) {
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                textBuffer.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return textBuffer;
    }
}
