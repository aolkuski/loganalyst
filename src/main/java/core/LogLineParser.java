package core;

import datastructures.LogLine;
import io.PropertyHandler;
import org.apache.log4j.Level;

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
import java.util.logging.Logger;

/**
 * Created by Alex on 2014-05-11.
 */
public class LogLineParser {

    private PropertyHandler props;
    private String commonDateFormatsFilePath;
    private ArrayList<String> dateFormats;
    private LogLine line;

    public LogLineParser(String propertyHandlerPath) {
        props = new PropertyHandler(propertyHandlerPath);
        commonDateFormatsFilePath = props.getPropertyValue("commonDateFormatsFilePath");
        dateFormats = getDateFormats();
    }


    public LogLine parseLine(String logLine) throws IOException, DateFormatException {
        line = new LogLine();
        Integer dateFormatLength = 0;
        Integer levelBeginPosition = 0;

        // insert info about date to structure
        if ((dateFormatLength = parseDate(logLine)) == null) {
            throw new DateFormatException("No format date format specified in file is applicable to this line:\n" + logLine);
        }
        ;
        String logLineWithoutDate = logLine.substring(dateFormatLength).trim();

        if ((levelBeginPosition = parseLogLevel(logLineWithoutDate)) == null) {
            Logger.getLogger("LogLineParser").info("No log level specified");
            line.setContent(logLineWithoutDate);
        } else {
            if (levelBeginPosition == 0) {
                // it means that there is nothing between date and logger level
                line.setContent(logLineWithoutDate.substring(this.line.getLogLevel().toString().length() - 1).trim());
            } else {
                // parse additional info from between date and logger level
                line.setAdditionalInfo(logLineWithoutDate.substring(0, levelBeginPosition).trim());
                line.setContent(logLineWithoutDate.substring(this.line.getLogLevel().toString().length() - 1 + levelBeginPosition).trim());
            }
        }
        return line;
    }

    public Integer parseLogLevel(String line) {
        String loggerType = null;
        int firstOccurence = line.length();
        for (Field f : Level.class.getFields()) {
            if (line.indexOf(f.getName()) <= firstOccurence && line.indexOf(f.getName()) > 0) {
                loggerType = f.getName();
                firstOccurence = line.indexOf(f.getName());
            }
        }
        if (loggerType != null) {
            try {
                this.line.setLogLevel(Level.toLevel(loggerType.toUpperCase()));
            } catch (IllegalArgumentException iae) {
                return null;
            }
        }
        if (firstOccurence == line.length()) {
            return null;
        }
        return firstOccurence;
    }

    private Integer parseDate(String logLine) {
        Date output = null;
        for (String dateFormat : dateFormats) {
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
            try {
                output = sdf.parse(logLine.substring(0, dateFormat.length()));
                line.setDate(output.getTime());
                return dateFormat.length();
            } catch (ParseException pe) {
                // Do nothing. Try again.
            }
        }
        return null;
    }


    private ArrayList<String> getDateFormats() {
        BufferedReader br = null;
        try {
            System.out.println(commonDateFormatsFilePath);
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
