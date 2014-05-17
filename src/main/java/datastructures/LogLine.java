package datastructures;

import io.PropertyHandler;
import org.apache.log4j.Level;

import java.text.SimpleDateFormat;

/**
 * Created by Alex on 2014-05-02.
 * <p/>
 * <p/>
 * G 	Era designator 	Text 	AD
 * y 	Year 	Year 	1996; 96
 * Y 	Week year 	Year 	2009; 09
 * M 	Month in year 	Month 	July; Jul; 07
 * w 	Week in year 	Number 	27
 * W 	Week in month 	Number 	2
 * D 	Day in year 	Number 	189
 * d 	Day in month 	Number 	10
 * F 	Day of week in month 	Number 	2
 * E 	Day name in week 	Text 	Tuesday; Tue
 * u 	Day number of week (1 = Monday, ..., 7 = Sunday) 	Number 	1
 * a 	Am/pm marker 	Text 	PM
 * H 	Hour in day (0-23) 	Number 	0
 * k 	Hour in day (1-24) 	Number 	24
 * K 	Hour in am/pm (0-11) 	Number 	0
 * h 	Hour in am/pm (1-12) 	Number 	12
 * m 	Minute in hour 	Number 	30
 * s 	Second in minute 	Number 	55
 * S 	Millisecond 	Number 	978
 * z 	Time zone 	General time zone 	Pacific Standard Time; PST; GMT-08:00
 * Z 	Time zone 	RFC 822 time zone 	-0800
 * X 	Time zone 	ISO 8601 time zone 	-08; -0800; -08:00
 */
public class LogLine implements Comparable<LogLine> {

    private final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd HH:mm:ss,SSS";

    private long date;
    private Level logLevel;
    private String content;
    private String additionalInfo;
    private String initialDateFormat;
    private Integer fileNumber;
    private Integer origLineNumber;

    public Integer getFileNumber() {
        return fileNumber;
    }

    public void setFileNumber(Integer fileNumber) {
        this.fileNumber = fileNumber;
    }

    public Integer getOrigLineNumber() {
        return origLineNumber;
    }

    public void setOrigLineNumber(Integer origLineNumber) {
        this.origLineNumber = origLineNumber;
    }

    public String getInitialDateFormat() {
        return initialDateFormat;
    }

    public void setInitialDateFormat(String initialDateFormat) {
        this.initialDateFormat = initialDateFormat;
    }

    public String getAdditionalInfo() {
        return additionalInfo;
    }

    public void setAdditionalInfo(String additionalInfo) {
        this.additionalInfo = additionalInfo;
    }

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
        // comparison across dates
        if (this.getDate() > o.getDate()) {
            return 1;
        } else if (this.getDate() < o.getDate()) {
            return -1;
        } else {
            // comparison across log lvels
            if ((this.logLevel != null) && (o.getLogLevel() != null)) {
                if (this.getLogLevel().getSyslogEquivalent() - o.getLogLevel().getSyslogEquivalent() != 0) {
                    return ((Integer) this.getLogLevel().getSyslogEquivalent()).compareTo(o.getLogLevel().getSyslogEquivalent());
                } else {
                    // comaprison across line number
                    if (this.getOrigLineNumber() - o.getOrigLineNumber() != 0) {
                        return this.getOrigLineNumber().compareTo(o.getOrigLineNumber());
                    }
                }
            }
        }
        return this.getContent().compareToIgnoreCase(o.getContent());
    }

    @Override
    public String toString() {
        PropertyHandler props = new PropertyHandler("config\\default.properties");
        String dateFormat = props.getPropertyValue("outputDateFormat");
        SimpleDateFormat sdt = new SimpleDateFormat(dateFormat);
        String time = sdt.format(this.getDate());
        String line = "" + time + " ";
        if (this.getAdditionalInfo() != null) {
            line += this.getAdditionalInfo().toString() + " ";
        }
        if (this.getLogLevel() != null) {
            line += this.getLogLevel().toString() + " ";
        }
        line += this.getContent();
        return line;
    }

    @Override
    public boolean equals(Object object) {
        if (this.hashCode() == ((LogLine) object).hashCode()) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = (int) this.getDate() + this.getContent().hashCode();
        return hash;
    }


}
