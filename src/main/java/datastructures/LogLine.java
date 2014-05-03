package datastructures;

import java.util.logging.Level;

/**
 * Created by Alex on 2014-05-02.
 */
public class LogLine {
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
}
