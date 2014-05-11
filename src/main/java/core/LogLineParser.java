package core;

import datastructures.LogLine;
import io.PropertyHandler;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;

/**
 * Created by Alex on 2014-05-11.
 */
public class LogLineParser {

    private PropertyHandler props;
    private String commonDateFormatsFilePath;
    private ArrayList<String> dateFormats;

    public LogLineParser(String propertyHandlerPath) {
        props = new PropertyHandler(propertyHandlerPath);
        commonDateFormatsFilePath = props.getPropertyValue("commonDateFormatsFilePath");
        dateFormats = getDateFormats();
    }


    public LogLine parseLine(String line) throws IOException {
        LogLine logLine = new LogLine();


        return logLine;
    }

    public static Level parseLogLevel(String line) {
        for (Field f : Level.class.getFields()) {
            // TODO sprawdź czy to przypadkiem nie jest coś, co się będzie nadawało pod klasę Level i spełniało swoją funkcję. Zrób coś z resztą linijki...
        }

        return Level.INFO;
    }

    private Long parseDate(String line) {
        Long timeInMilis = null;
        Date output = null;

        for (String dateFormat : dateFormats) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            try {
                output = sdf.parse(line.substring(0, dateFormat.length()));
            } catch (ParseException pe) {
                // Do nothing. Try again.
            }
        }

        return output.getTime();
    }

    private ArrayList<String> getDateFormats() {
        BufferedReader br = null;
        try {
            br = Files.newBufferedReader(new File(commonDateFormatsFilePath).toPath(), StandardCharsets.UTF_8);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<String> dateFormats = new ArrayList<String>();

        try {
            for (String line = null; (line = br.readLine()) != null; ) {
                dateFormats.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return dateFormats;
    }
}
