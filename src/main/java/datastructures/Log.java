package datastructures;

import java.util.ArrayList;
import java.util.Iterator;
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

    public TreeSet<LogLine> getLogAsSet() {
        return log;
    }

    public ArrayList<LogLine> getLogAsArrayList() {
        return new ArrayList<LogLine>(log);
    }

    public void setLog(TreeSet<LogLine> newLog) {
        this.log = newLog;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Iterator it = this.getLogAsSet().descendingSet().descendingIterator();
        while (it.hasNext()) {
            sb.append(it.next() + "\n");
        }
        return sb.toString();
    }


}
