import japa.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 6/27/16.
 */

public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Hello world!!!!");
        String rootFolder = "/home/arpit/research/QuicksortApp";
        String relativeFilePath = "/src/com/osustar/Quicksort.java";
        String methodName = "sort";

        JavaMethod testcase = new JavaMethod(rootFolder,relativeFilePath,methodName,"Quicksort", "/home/arpit/research/QuicksortApp/out/artifacts/QuicksortApp_jar/QuicksortApp.jar");
        List<String> strClasses = new ArrayList<String>();
        strClasses.add("com.osustar.QuicksortTest");
        //strClasses.add("QuicksortTest");
        Tester t = new Tester("/home/arpit/research/QuicksortApp/out/artifacts/QuicksortApp_jar/QuicksortApp.jar",strClasses);
        Reducer dd = new Reducer(testcase, t);
        dd.ReduceSequentical();

    }
}
