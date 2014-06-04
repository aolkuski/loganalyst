package core;

import datastructures.Log;
import datastructures.LogLine;
import org.apache.log4j.Level;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogAnalyst {

    public static void analyze(Log log) {
        Map<String, List<LogLine>> segregatedLogs = segragateLogs(Level.ERROR, log, true);
        for (String classNameProblem : segregatedLogs.keySet()) {
            List<List<LogLine>> loglinesList = new ArrayList<List<LogLine>>();
            for (LogLine index : segregatedLogs.get(classNameProblem)) {
                loglinesList.add(getLogLineSurrounding(3, 0, index.getOrigLineNumber(), log.getLogAsSet()));
            }
        }

        analizeTime(segregatedLogs);
    }

    public static void analizeTime(Map<String, List<LogLine>> segregatedLogs) {
        for (String errorType : segregatedLogs.keySet()) {
            List<LogLine> errorLogs = segregatedLogs.get(errorType);
            System.out.print("Error " + errorType + " appeared " + errorLogs.size() + " times.");
            //analyse hours
            int[] hours = new int[24];
            for (LogLine logLine : errorLogs) {
                int closestHour = getClosestHour(logLine.getDateObject());
                hours[closestHour]++;
            }
        }
    }

    public static int getClosestHour(Date date) {
        return date.getMinutes() < 30 ? date.getHours() : (date.getHours() + 1) % 24;
    }

    /**
     * Finds all logs with given sevirity level and segregates them depending on their cause
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
            if (logLine.getOrigLineNumber() >= start) {
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
