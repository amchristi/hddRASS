package Helper;

import org.apache.commons.cli.ParseException;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/**
 * Created by arpit on 8/17/16.
 */
public class Main {
    public static void main(String[] args) throws ParseException {
        Command command = new Command();
        String[] comm = new String[2];

        Helper.CommandLineParser parser = new Helper.CommandLineParser(args);
        System.out.println(parser.getRootFolder());

        String jarfilePath = "";
        //List<Class<?>> classes =  //("/home/arpit/research/QuicksortApp/out/artifacts/QuicksortApp_jar/QuicksortApp.jar");
        File directory = new File("/home/arpit/research/QuicksortApp/src/com");

        List<Class<?>> classes = ClassEnumerator.getClassedFromThisJarFile("/scratch/research/commons-validator/commons-validator.jar","org.apache.commons.validator.routines");

        Debugger.log(classes.stream().filter(p -> p != null &&  p.getName().endsWith("Test")).count());

        //classes.forEach(p -> Debugger.log(p.getSimpleName()));
        classes.stream().filter(p ->  p != null &&   p.getName().endsWith("Test")).
                forEach(x -> {  //Debugger.log(x);
                                if(x != null)
                                    FileWriterUtil.appendLine("a.txt",x.getName()); ;
                             });

        //classes.forEach(p -> Debugger.log(MethodEnumerator.GetDeclaedMethods(p)));
        //classes.forEach(p -> Arrays.asList(MethodEnumerator.GetDeclaedMethods(p)).stream().forEach(x -> Debugger.log(x.getName())));


    }
}
