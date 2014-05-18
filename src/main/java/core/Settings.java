package core;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Alex on 2014-05-18.
 */
public class Settings {

    private static Properties props = new Properties();

    public Settings(String settingsFilePath) throws IOException {
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(settingsFilePath);
        try {
            // load from resource (if in jar)
            props.load(in);
        } catch (Exception e) {
            // load from normal file (if ran from eclipse/intellij)
            props.load(new FileInputStream(new File(settingsFilePath)));
        }
    }

    public static String getSetting(String settingName) {
        return props.getProperty(settingName);
    }
}
