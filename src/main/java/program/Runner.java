package program;

import core.DateFormatException;
import core.LogAnalyst;
import datastructures.Log;
import datastructures.LogLine;
import io.LogReader;
import io.LogWriter;
import io.PropertyHandler;
import replacer.Replacer;

import java.io.IOException;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * Created by Alex on 2014-05-04.
 */
public class Runner {

    public static void main(String... args) throws IOException, DateFormatException {
        PropertyHandler props = new PropertyHandler("config\\default.prperties");
        Log log = LogReader.read();

        // TODO: zmien co chcesz. Wazne, zeby pozniej dalej log byl logiem ;) no i zalozenie jest takie, ze analiza jest zawsze przed zamiana :)
        if (props.getPropertyValue("doAnalyze").equals("true")) {
            LogAnalyst.analyze(log);
        }

        // replacement on each line separately
        if (props.getPropertyValue("replace").equals("true") && props.getPropertyValue("replacementPerLine").equals("true")) {
            TreeSet<LogLine> tmpLog = new TreeSet<LogLine>();
            Replacer r = new Replacer();
            Iterator it = log.getLogAsSet().descendingSet().descendingIterator();
            while (it.hasNext()) {
                LogLine line = (LogLine) it.next();
                line.setContent(r.replace(line.getContent()));
                tmpLog.add(line);
            }
            log.setLog(tmpLog);
        }

        // replacement on whole log is possible only when there's no other action after it
        if (props.getPropertyValue("replace").equals("true") && props.getPropertyValue("replacementPerLog").equals("true")) {
            Replacer r = new Replacer();
            LogWriter.write(r.replace(log.toString()));
            return;
        }

        LogWriter.write(log);

    }
}
