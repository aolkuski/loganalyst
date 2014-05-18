package io;

import core.DateFormatException;
import core.LogLineParser;
import core.Settings;
import datastructures.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogReader {

    public static Log read() throws IOException, DateFormatException {
        LogLineParser parser = new LogLineParser();
        String inputLogDir = Settings.getSetting("inputLogDir");
        Log log = new Log();

        if (!directoryExists(inputLogDir)) {
            throw new FileNotFoundException("Directory with files to merge not found.");
        }

        ArrayList<File> files = listFiles(inputLogDir);

        for (int i = 0; i < files.size(); i++) {
            int lineNumber = 0;
            File f = files.get(i);
            BufferedReader br = Files.newBufferedReader(f.toPath(), StandardCharsets.UTF_8);
            for (String line = null; (line = br.readLine()) != null; ) {
                if (line.length() == 0) continue;
                log.addLogLine(parser.parseLine(line, i, lineNumber));
                lineNumber++;
            }
        }
        return log;
    }

    private static boolean directoryExists(String inputLogDir) {

        File outputDir = new File(inputLogDir);

        return outputDir.exists();
    }

    private static ArrayList<File> listFiles(String dirPath) throws FileNotFoundException {
        ArrayList<File> files = new ArrayList<File>();

        File dir = new File(dirPath);
        if (!dir.isDirectory()) {
            throw new FileNotFoundException(dir.getName() + " is not a valid directory");
        }

        File[] filesArray = dir.listFiles();
        if (filesArray.length == 0) {
            throw new FileNotFoundException("No files to merge");
        }

        for (File f : filesArray) {
            if (f.isFile() && (!f.isDirectory()) && f.exists() && (f.length() > Integer.valueOf(Settings.getSetting("minimumFileLengthB")))) {
                files.add(f);
            }
        }
        return files;
    }
}
