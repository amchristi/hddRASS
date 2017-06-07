package Helper;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by arpit on 9/28/16.
 */
public class MethodEnumerator {

    public static Method[] GetAllMethods(Class<?> cls){
        return cls.getMethods();

    }

    public static Method[] GetDeclaedMethods(Class<?> cls){

        return cls.getDeclaredMethods();
    }



}
