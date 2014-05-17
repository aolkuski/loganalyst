package program;

import core.DateFormatException;
import datastructures.Log;
import io.LogReader;
import replacer.Replacer;

import java.io.IOException;

/**
 * Created by Alex on 2014-05-04.
 */
public class Runner {

    public static void main(String... args) throws IOException, DateFormatException {
        System.out.println("Hello world!");
        Replacer r = new Replacer();
        Log log = LogReader.read();

//        LogWriter.write(log);
        System.out.println(r.replace(log.toString()));
//        PropertyHandler props = new PropertyHandler("config\\default.properties");
//        System.out.println(Level.parse("INFO"));

//        LogLineParser.parseLogLevel("");

        // kolejnosc:
        /*
        1. Wczytaj ustawienia z propertiesów
        2. Wczytaj pliki:
            dostepne formaty
            logi z folderu do mergowania
                wczytaj linijke
                parsuj
                wrzucaj do struktury
        3. Zamieniaj regexem, jesli trzeba
        4. Wypluj do pliku
         */
    }
}
