package core;

import datastructures.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogAnalyst {

    public static void analyze(Log log) {
        // TODO: co chcesz i jak chcesz ;)
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
