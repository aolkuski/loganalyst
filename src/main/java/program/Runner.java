package program;

import core.DateFormatException;
import core.LogAnalyst;
import core.LogLineParser;
import core.Settings;
import datastructures.Log;
import datastructures.LogLine;
import io.LogReader;
import io.LogWriter;
import replacer.Replacer;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Alex on 2014-05-04.
 */
public class Runner {

    public static void main(String... args) throws IOException, DateFormatException, URISyntaxException {

        String load = "";
        Replacer r = null;
        System.out.println("\nYou've just run LogAnalyst v1.0");
        if (args.length == 0) {
            System.out.println("Default configuration is loaded, since there's not path to other configuration.\n");
            load = "default.properties";
        } else {
            load = args[0];
            System.out.println("Trying to load configuration specified in argument: " + load + "\n");
        }


        // initialization of settings
        new Settings(load);

        Log log = LogReader.read();


        // TODO: zmien co chcesz. Wazne, zeby pozniej dalej log byl logiem ;) no i zalozenie jest takie, ze analiza jest zawsze przed zamiana w replacerze :)
        if (Settings.getSetting("doAnalyze").equals("true")) {
            LogAnalyst.analyze(log);
        }

        // replacement on each line separately
        if (Settings.getSetting("replace").equals("true") && Settings.getSetting("replacementPerLine").equals("true")) {
            TreeSet<LogLine> tmpLog = new TreeSet<LogLine>();
            r = new Replacer();
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
            r = new Replacer();
            LogWriter.write(r.replace(log.toString()));
        }

        LogWriter.write(log);

        System.out.println("\nAll the output files are available in direcotry: " + Settings.getSetting("outputLogDir"));
        if (Settings.getSetting("doAnalyze").equals("true")) {
            System.out.println("Analysis is available in file: " + Settings.getSetting("outputAnalyzeFileName"));
        }
        if (Settings.getSetting("replace").equals("true")) {
            System.out.println("Lines in merged log were replaced using rules: " + r.getDescriptionOfRules());
        }
        System.out.println("While parsing, got " + LogLineParser.getNoOfErrorsOnParsing() + " errors. Those lines are not added to merged log.");
        System.out.println("Your merged log is available in file: " + Settings.getSetting("outputLogName"));
        System.out.println("\nDone!\n");
    }
}
