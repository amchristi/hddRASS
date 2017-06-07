package HDD;

import japa.parser.ParseException;
import Helper.Command;
import Helper.FileWriterUtil;

/**
 * Created by arpit on 6/12/16.
 */
public class Main {

    public static void main(String[] args) throws ParseException {
        System.out.println("Hello world!!!!");
        String rootFolder = "/home/arpit/research/QuicksortApp";
        String relativeFilePath = "/src/com/osustar/QuicksortTest.java";
        String methodName = "testSpecial";
        String className = "QuicksortTest";
        JavaTestCase testcase = new JavaTestCase(rootFolder,relativeFilePath,methodName, className);

        Tester tester = new Tester("/home/arpit/research/QuicksortApp/out/artifacts/QuicksortApp_jar/QuicksortApp.jar","com.osustar.QuicksortTest", methodName);
        DD dd = new DD(testcase,tester);
        dd.ReduceSequentical();

        Command cmd = new Command();

        cmd.exec("cp /home/arpit/research/QuicksortAppCopy/src/com/osustar/QuicksortTest.java /home/arpit/research/QuicksortApp/src/com/osustar/");

    }

}
