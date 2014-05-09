package datastructures;

import io.PropertyHandler;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.TreeSet;

/**
 * Created by Alex on 2014-05-09.
 */
public class Log {

    private TreeSet<LogLine> log = new TreeSet<LogLine>();


//    @Override
//    String toString(){
//
//    }

    public void printLog() {

    }

    public void writeLogToFile(Log log) throws FileNotFoundException {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String outputFilePath = props.getPropertyValue("outputLogPath");
        File outputFile = new File(outputFilePath);

        if (outputFile.exists()) {
            outputFile.delete();
        }

        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(outputFile, false));

//        TODO: w zależności od metod Log'a, trzeba przez niego przejść i wyrzucić Stringa albo wziąć LOG'a jako stringa i zapisać do pliku.
//        while()

    }
}
