package program;

import io.PropertyHandler;

/**
 * Created by Alex on 2014-05-04.
 */
public class Runner {

    public static void main(String ... args){
        System.out.println("Hello world!");

        PropertyHandler props = new PropertyHandler("config\\default.properties");
        System.out.println(props.getPropertyValue("dupa") == null);
    }
}
