package program;

import core.DateFormatException;
import core.LogAnalyst;
import core.Settings;
import datastructures.Log;
import datastructures.LogLine;
import io.LogReader;
import io.LogWriter;
import io.PropertyHandler;
import replacer.Replacer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Alex on 2014-05-04.
 */
public class Runner {

    private static String pathToConfig;
    private static PropertyHandler props;

    public static PropertyHandler getPropertyHandler() {
        return props;
    }

    public static void main(String... args) throws IOException, DateFormatException, URISyntaxException {

        String load = "";
        System.out.println("You've just run LogAnalyst v1.0");
        if (args.length == 0) {
            System.out.println("Default configuration is loaded, since there's not path to other configuration.");
            load = "default.properties";
        } else {
            load = args[0];
            System.out.println("Trying to load configuration specified in argument: " + load);
        }
        // must be ;)
        new Settings(load);

        Log log = LogReader.read();


        // TODO: zmien co chcesz. Wazne, zeby pozniej dalej log byl logiem ;) no i zalozenie jest takie, ze analiza jest zawsze przed zamiana w replacerze :)
        if (Settings.getSetting("doAnalyze").equals("true")) {
            LogAnalyst.analyze(log);
        }

        // replacement on each line separately
        if (Settings.getSetting("replace").equals("true") && Settings.getSetting("replacementPerLine").equals("true")) {
            TreeSet<LogLine> tmpLog = new TreeSet<LogLine>();
            Replacer r = new Replacer(props);
            Iterator it = log.getLogAsSet().descendingSet().descendingIterator();
            while (it.hasNext()) {
                LogLine line = (LogLine) it.next();
                String content = r.replace(line.getContent());
                if (!content.equals("")) {
                    line.setContent(content);
                    tmpLog.add(line);
                }
            }
            log.setLog(tmpLog);
        }

        // replacement on whole log is possible only when there's no other action after it
        if (Settings.getSetting("replace").equals("true") && Settings.getSetting("replacementPerLog").equals("true")) {
            Replacer r = new Replacer(props);
            LogWriter.write(r.replace(log.toString()));
            return;
        }

        LogWriter.write(log);

    }
}
