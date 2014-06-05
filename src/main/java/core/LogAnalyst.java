package core;

import datastructures.Log;
import datastructures.LogLine;
import org.apache.log4j.Level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.util.*;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogAnalyst {

    public static void analyze(Log log) {
        Map<String, List<LogLine>> segregatedLogs = segragateLogs(Level.ERROR, log, true);
        for (String classNameProblem : segregatedLogs.keySet()) {
            List<List<LogLine>> logLinesList = new ArrayList<List<LogLine>>();
            for (LogLine index : segregatedLogs.get(classNameProblem)) {
                logLinesList.add(getLogLineSurrounding(3, 0, index.getOrigLineNumber(), log.getLogAsSet()));
            }
            analyzeSyntax(logLinesList, 3, classNameProblem);
        }
        analyzeTime(segregatedLogs);
    }

    public static void analyzeTime(Map<String, List<LogLine>> segregatedLogs) {
        for (String errorType : segregatedLogs.keySet()) {
            List<LogLine> errorLogs = segregatedLogs.get(errorType);
            System.out.print("Error " + errorType + " appeared " + errorLogs.size() + " times, ");
            //analyse hours
            int[] hours = new int[24];
            for (LogLine logLine : errorLogs) {
                int closestHour = getClosestHour(logLine.getDateObject());
                hours[closestHour]++;
            }
            findPatternInTime(hours, errorLogs.size());
            findRushHours(hours, errorLogs.size());
            findRushDays(errorLogs);
        }
    }

    public static void findPatternInTime(int[] hours, int peaks) {
        int[] distance = new int[12];
        for (int i = 0; i < 12; i++) {
            if (hours[i] > 0) {
                for (int j = 0; j < 12; j++) {
                    if (hours[i + j % 24] > 0) {
                        distance[j]++;
                    }
                }
            }
        }
        for (int i = 0; i < 12; i++) {
            if (distance[i] * 2 > peaks) {
                System.out.print("Problems happen each " + i + hours + "\n");
            }
        }
    }

    /**
     * Sums up occurrences of the error during specific periods (3 hours) - detects rush hours if exist
     *
     * @param hours
     * @param total
     * @return
     */
    public static void findRushHours(int[] hours, int total) {
        if (total < 3) {
            return;
        }
        int max = 0;
        int beginning = 0;
        for (int i = 0; i < 22; i++) {
            if (hours[i] + hours[i + 1] + hours[i + 2] > max) {
                max = hours[i] + hours[i + 1] + hours[i + 2];
                beginning = i;
            }
        }
        int average = total / 3;
        if (max >= average * 2) { //twice bigger than the average means the hours of interest
            System.out.print("Error appear mostly around " + (beginning + 2) + "\n");
        } else {
            System.out.print("No rush hours were detected\n");
        }
    }

    public static void analyzeSyntax(List<List<LogLine>> logLinesList, int threshold, String error) {
        if (logLinesList.size() < threshold) {
            return;
        }
        Map<String, Integer> occurrences = new HashMap<String, Integer>();
        for (List<LogLine> logList : logLinesList) {
            for (LogLine logLine : logList) {
                String content = logLine.getContent();
                if (occurrences.containsKey(content)) {
                    occurrences.put(content, occurrences.get(content) + 1);
                } else {
                    occurrences.put(content, 1);
                }
            }
        }
        for (String keys : occurrences.keySet()) {
            if (occurrences.get(keys) > threshold) {
                System.out.print("Error " + error + " was usually preceded/followed by " + keys + "\n");
            }
        }
    }

    public static void findRushDays(List<LogLine> errorLogs) {
        if (errorLogs.size() < 3) {
            return;
        }
        int[] days = new int[7];
        int max = 0;
        int day = 0;
        int total = errorLogs.size();
        for (LogLine logLine : errorLogs) {
            int dayOfWeek = logLine.getDateObject().getDay();
            days[dayOfWeek]++;
            if (days[dayOfWeek] > max) {
                max = days[dayOfWeek];
                day = dayOfWeek;
            }
        }
        if (max * 2 >= total && total > 3) {
            DateFormatSymbols dateFormatSymbols = new DateFormatSymbols();
            System.out.print("Error appears mostly on " + dateFormatSymbols.getWeekdays()[day] + "\n");
        }
    }

    /**
     * returns the hour (0-23) closest to the given date - so for example for 10:35 it returns 11.
     *
     * @param date
     * @return
     */
    public static int getClosestHour(Date date) {
        return date.getMinutes() < 30 ? date.getHours() : (date.getHours() + 1) % 24;
    }

    /**
     * Finds all logs with given severity level and segregates them depending on their cause
     *
     * @param level
     * @return
     */
    public static Map<String, List<Integer>> segragateLogsIds(Level level, Log log, boolean classOnly) {
        Map<String, List<Integer>> segragatedLogs = new HashMap<String, List<Integer>>();
        TreeSet<LogLine> logLines = log.getLogAsSet();
        for (LogLine logLine : logLines) {
            if (logLine.getLogLevel() != null && logLine.getLogLevel().equals(level)) {
                String cause = classOnly ? logLine.getContent().substring(0, logLine.getContent().indexOf("]") + 1) : logLine.getContent();
                if (segragatedLogs.containsKey(cause)) {
                    segragatedLogs.get(cause).add(logLine.getOrigLineNumber());
                } else {
                    List<Integer> originalNumbersList = new ArrayList<Integer>();
                    originalNumbersList.add(logLine.getOrigLineNumber());
                    segragatedLogs.put(cause, originalNumbersList);
                }
            }
        }
        return segragatedLogs;
    }


    /**
     * Finds all logs with given sevirity level and segregates them depending on their cause
     *
     * @param level
     * @return
     */
    public static Map<String, List<LogLine>> segragateLogs(Level level, Log log, boolean classOnly) {
        Map<String, List<LogLine>> segragatedLogs = new HashMap<String, List<LogLine>>();
        TreeSet<LogLine> logLines = log.getLogAsSet();
        for (LogLine logLine : logLines) {
            if (logLine.getLogLevel() != null && logLine.getLogLevel().equals(level)) {
                String cause = classOnly ? logLine.getContent().substring(0, logLine.getContent().indexOf("]") + 1) : logLine.getContent();
                if (segragatedLogs.containsKey(cause)) {
                    segragatedLogs.get(cause).add(logLine);
                } else {
                    List<LogLine> originalNumbersList = new ArrayList<LogLine>();
                    originalNumbersList.add(logLine);
                    segragatedLogs.put(cause, originalNumbersList);
                }
            }
        }
        return segragatedLogs;
    }

    /**
     * Finds all log lines before and after given index
     *
     * @param before
     * @param after
     * @return
     */
    public static List<LogLine> getLogLineSurrounding(int before, int after, int index, TreeSet<LogLine> logLineTreeSet) {
        List<LogLine> logLines = new ArrayList<LogLine>();
        int start = index - before > 0 ? index - before : 0;
        int end = index + after < logLineTreeSet.size() ? index + after : logLineTreeSet.size();
        for (LogLine logLine : logLineTreeSet) {
            if (logLine.getOrigLineNumber() >= start && logLine.getOrigLineNumber() != index) {
                logLines.add(logLine);
            }
            if (logLine.getOrigLineNumber() >= end) {
                break;
            }
        }
        return logLines;
    }



    private static BufferedWriter prepareResources() throws IOException {
        String outputFilePath = Settings.getSetting("outputLogDir");

        File outputDir = new File(outputFilePath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File outputLog = new File(Settings.getSetting("outputLogDir") + Settings.getSetting("outputAnalyzeFileName"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputLog));

        return bw;
    }

    public static void writeAnalyze() throws IOException {
        BufferedWriter bw = prepareResources();
    }
}
