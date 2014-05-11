package io;

import datastructures.Log;

import java.io.File;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogWriter {

    public static void write(Log log) {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String outputFilePath = props.getPropertyValue("outputLogDir");

        File outputDir = new File(outputFilePath);

        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }

        File outputLog = new File(props.getPropertyValue("outputLogName"));

//        log.g
    }
}
