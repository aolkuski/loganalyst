package datastructures;

import java.util.logging.Level;

/**
 * Created by Alex on 2014-05-02.
 *
 *
 G 	Era designator 	Text 	AD
 y 	Year 	Year 	1996; 96
 Y 	Week year 	Year 	2009; 09
 M 	Month in year 	Month 	July; Jul; 07
 w 	Week in year 	Number 	27
 W 	Week in month 	Number 	2
 D 	Day in year 	Number 	189
 d 	Day in month 	Number 	10
 F 	Day of week in month 	Number 	2
 E 	Day name in week 	Text 	Tuesday; Tue
 u 	Day number of week (1 = Monday, ..., 7 = Sunday) 	Number 	1
 a 	Am/pm marker 	Text 	PM
 H 	Hour in day (0-23) 	Number 	0
 k 	Hour in day (1-24) 	Number 	24
 K 	Hour in am/pm (0-11) 	Number 	0
 h 	Hour in am/pm (1-12) 	Number 	12
 m 	Minute in hour 	Number 	30
 s 	Second in minute 	Number 	55
 S 	Millisecond 	Number 	978
 z 	Time zone 	General time zone 	Pacific Standard Time; PST; GMT-08:00
 Z 	Time zone 	RFC 822 time zone 	-0800
 X 	Time zone 	ISO 8601 time zone 	-08; -0800; -08:00

 */
public class LogLine implements Comparable<LogLine> {
    private long date;
    private Level logLevel;
    private String content;

    private final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

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
