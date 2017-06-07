package PreProcessig;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;

/**
 * Created by root on 1/2/17.
 */
public class AnnotationProcessing {

    private Method method;
    private Class<?> class1;

    public int processAnnotations(){

        return readAnnotationOn(method);
    }

    int readAnnotationOn(AnnotatedElement element)
    {
        try
        {
            System.out.println("\n Finding annotations on " + element.getClass().getName());
            Annotation[] annotations = element.getAnnotations();
            for (Annotation annotation : annotations)
            {
                if (annotation instanceof MethodLabel)
                {
                    MethodLabel fileInfo = (MethodLabel) annotation;
                    return fileInfo.level();
                }
            }
        } catch (Exception e)
        {
            e.printStackTrace();
        }
        return 0;
    }


}
