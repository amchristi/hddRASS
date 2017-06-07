package Helper;

/**
 * Created by root on 1/16/17.
 */
public class Stuffs {

    public static String DeriveClassNameFromFullPath(String filePath){
        String[] components = filePath.split("/");
        if(components.length > 1){
            return  (components[components.length - 1]).split("\\.")[0];

        }

        return null;
    }
}
