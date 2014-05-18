package core;

import datastructures.LogLine;
import org.apache.log4j.Level;

import java.io.*;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Alex on 2014-05-11.
 */
public class LogLineParser {

    private String commonDateFormatsFilePath;
    private ArrayList<String> dateFormats;
    private LogLine line;
    private static String defaultDateFormat = null;


    public LogLineParser() throws FileNotFoundException {
        commonDateFormatsFilePath = Settings.getSetting("commonDateFormatsFilePath");
        dateFormats = getDateFormats();
    }


    public LogLine parseLine(String logLine, int fileNumber, int origLineNumber) throws IOException, DateFormatException {
        line = new LogLine();
        Integer dateFormatLength = 0;
        Integer levelBeginPosition = 0;
        line.setOrigLineNumber(origLineNumber);
        line.setFileNumber(fileNumber);

        // insert info about date to structure
        if ((dateFormatLength = parseDate(logLine)) == null) {
            throw new DateFormatException("No format date format specified in file is applicable to this line:\n" + logLine);
        }
        ;
        String logLineWithoutDate = logLine.substring(dateFormatLength).trim();

        if ((levelBeginPosition = parseLogLevel(logLineWithoutDate)) == null) {
            line.setContent(logLineWithoutDate);
        } else {
            if (levelBeginPosition == 0) {
                // it means that there is nothing between date and logger level
                line.setContent(logLineWithoutDate.substring(this.line.getLogLevel().toString().length()).trim());
            } else {
                // parse additional info from between date and logger level
                line.setAdditionalInfo(logLineWithoutDate.substring(0, levelBeginPosition).trim());
                line.setContent(logLineWithoutDate.substring(this.line.getLogLevel().toString().length() + levelBeginPosition).trim());
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
        SimpleDateFormat sdf = null;

        if (defaultDateFormat != null) {
            sdf = new SimpleDateFormat(defaultDateFormat);
            try {
                output = sdf.parse(logLine.substring(0, defaultDateFormat.length()));
                line.setDate(output.getTime());
                return defaultDateFormat.length();
            } catch (ParseException pe) {
                defaultDateFormat = null;
            }
        }

        for (String dateFormat : dateFormats) {
            sdf = new SimpleDateFormat(dateFormat);
            try {
                output = sdf.parse(logLine.substring(0, dateFormat.length()));
                line.setDate(output.getTime());
                defaultDateFormat = dateFormat;
                return dateFormat.length();
            } catch (ParseException pe) {
                // Do nothing. Try again.
            }
        }
        return null;
    }


    private ArrayList<String> getDateFormats() throws FileNotFoundException {
        BufferedReader br = null;

        try {
            br = new BufferedReader(new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(commonDateFormatsFilePath)));
        } catch (NullPointerException npe) {
            br = new BufferedReader(new FileReader("src\\main\\resources\\commonDateFormats"));
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
