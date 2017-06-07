package Helper;

/**
 * Created by arpit on 9/21/16.
 */
public class Debugger {
    public static boolean DEBUG = false;
    public static void log(Object o){
        if(DEBUG){
            System.out.println(o.toString());
        }

    }
}
