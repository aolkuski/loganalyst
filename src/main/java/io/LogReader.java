package io;

import java.io.File;

/**
 * Created by Alex on 2014-05-07.
 */
public class LogReader {

    public void read() {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String outputFilePath = props.getPropertyValue("inputLogDir");

        File outputDir = new File(outputFilePath);

        if (outputDir.exists()) {
            outputDir.delete();
        }
        outputDir.mkdirs();


    }
}
