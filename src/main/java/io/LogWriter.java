package io;

import datastructures.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogWriter {


    public static void write(Log log) throws IOException {
        BufferedWriter bw = prepareResources();
        Iterator it = log.getLogAsSet().descendingSet().descendingIterator();
        while (it.hasNext()) {
            bw.write(it.next().toString());
            bw.newLine();
        }
        bw.flush();
        bw.close();
    }

    public static void write(String str) throws IOException {
        BufferedWriter bw = prepareResources();
        bw.write(str);
        bw.flush();
        bw.close();
    }

    private static BufferedWriter prepareResources() throws IOException {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String outputFilePath = props.getPropertyValue("outputLogDir");

        File outputDir = new File(outputFilePath);
        if (!outputDir.exists()) {
            outputDir.mkdirs();
        }
        File outputLog = new File(props.getPropertyValue("outputLogDir") + props.getPropertyValue("outputLogName"));
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputLog));

        return bw;
    }


}
