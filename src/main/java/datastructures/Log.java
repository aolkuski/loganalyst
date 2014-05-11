package datastructures;

import java.util.ArrayList;
import java.util.TreeSet;

/**
 * Created by Alex on 2014-05-09.
 */
public class Log {

    private TreeSet<LogLine> log = new TreeSet<LogLine>();

    public boolean addLogLine(LogLine logLine) {
        int sizeBeforeAdding = log.size();
        log.add(logLine);
        if (log.size() > sizeBeforeAdding) {
            return true;
        }
        return false;
    }

    public void printLog() {

    }

    public TreeSet<LogLine> getLogAsSet() {
        return log;
    }

    public ArrayList<LogLine> getLogAsArrayList() {
        return new ArrayList<LogLine>(log);
    }

    @Override
    public String toString() {


        return "";
    }


}
