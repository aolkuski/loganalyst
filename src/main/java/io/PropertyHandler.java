package io;

import java.io.*;
import java.util.Properties;

/**
 * Created by Alex on 2014-05-03.
 */
public class PropertyHandler {

    String propertiesPath;
    InputStream is = null;

    /**
     * Constructor of properties object.
     *
     * @param pathToProperties path to properties file
     */
    public PropertyHandler(String pathToProperties) {
        if (pathToProperties == null || pathToProperties == "") {
            propertiesPath = "config\\default.properties";
        } else {
            try {
                propertiesPath = this.getClass().getClassLoader().getResource("default.properties").getPath();
            } catch (NullPointerException npe) {
                is = this.getClass().getClassLoader().getResourceAsStream("default.properties");
            }
        }
    }

    public PropertyHandler(InputStream inputStream) {
        this.is = inputStream;
    }

    /**
     * Gets property value from file specified in constructor.
     *
     * @param propertyKey name of a property to be found
     * @return value of a found property
     */
    public String getPropertyValue(String propertyKey) {
        String val = "defaultName";

        val = System.getProperty(propertyKey);


        if (val == "" || val == null || val.equals("null")) {
            Properties props = new Properties();
            try {
                if (propertiesPath == null) {
                    props.load(is);
                } else {
                    props.load(new FileInputStream(new File(propertiesPath)));
                }
                val = props.getProperty(propertyKey);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return val;
    }
}
