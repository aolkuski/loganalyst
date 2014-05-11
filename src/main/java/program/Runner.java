package program;

import core.LogLineParser;
import io.PropertyHandler;

import java.util.logging.Level;

/**
 * Created by Alex on 2014-05-04.
 */
public class Runner {

    public static void main(String ... args){
        System.out.println("Hello world!");

        PropertyHandler props = new PropertyHandler("config\\default.properties");
        System.out.println(Level.parse("INFO"));

        LogLineParser.parseLogLevel("");
    }
}
