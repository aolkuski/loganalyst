package datastructures;

import java.util.logging.Level;

/**
 * Created by Alex on 2014-05-02.
 */
public class LogLine implements Comparable<LogLine> {
    private long date;
    private Level logLevel;
    private String content;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Level getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(Level logLevel) {
        this.logLevel = logLevel;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    @Override
    public int compareTo(LogLine o) {
        // comparison across
        if (this.getDate() > o.getDate()) {
            return 1;
        } else if (this.getDate() < o.getDate()) {
            return -1;
        } else {
            if (this.getLogLevel().intValue() - o.getLogLevel().intValue() != 0) {
                return ((Integer) this.getLogLevel().intValue()).compareTo(o.getLogLevel().intValue());
            }
        }

        return 0;
    }


}
