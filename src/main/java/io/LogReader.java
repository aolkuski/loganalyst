package io;

import datastructures.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogReader {

    public static Log read() throws FileNotFoundException {
        if (!directoryExists()) {
            throw new FileNotFoundException("Directory with files to merge not found.");
        }

        Log log = new Log();


        return log;
    }

    private static boolean directoryExists() {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String inputLogDir = props.getPropertyValue("inputLogDir");

        File outputDir = new File(inputLogDir);

        return outputDir.exists();
    }

    private static ArrayList<File> listFiles(String dirPath) throws FileNotFoundException {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
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
            if (f.isFile() && (!f.isDirectory()) && f.exists() && (f.length() > Integer.valueOf(props.getPropertyValue("minimumFileLengthB")))) {
                files.add(f);
            }

        }

        return files;
    }
}
