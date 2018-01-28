package Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by root on 1/6/18.
 */
public class Configuration {
    public static String buildSuccessString;
    public static String runSuccessString;
    public static String packageName;
    public static String testResultPath;
    public static String testFileRegEx;
    public static String intermediateTestResultOutputPath;
    public static String compileOutputPath;
    public static boolean isImmortalRun;
    public static boolean isDebug;
    public static void buildConfiguration(String generalConfigurationFile, String immortalsConfigurationFile){
        Properties p = new Properties();
        InputStream input = null;
        try{
            input = new FileInputStream(immortalsConfigurationFile);
            p.load(input);

            buildSuccessString = p.getProperty("BUILD_SUCCESS_STRING");
            runSuccessString = p.getProperty("RUN_SUCCESS_STRING");
            packageName = p.getProperty("PACKAGE_NAME");
            testResultPath = p.getProperty("TEST_RESULT_PATH");
            testFileRegEx = p.getProperty("TEST_FILE_REGEX");
            intermediateTestResultOutputPath = p.getProperty("INTERMEDIATE_TEST_RESULT_OUTPUTPATH");
            compileOutputPath = p.getProperty("INTERMEDIATE_COMPILE_OUTPUT_PATH");

        }
        catch (IOException ioex){

        }

        p = new Properties();
        input = null;

        try{
            input = new FileInputStream(generalConfigurationFile);
            p.load(input);
            isImmortalRun = Boolean.parseBoolean(p.getProperty("IS_IMMORTALS_RUN"));
            isDebug = Boolean.parseBoolean(p.getProperty("IS_DEBUG"));




        }
        catch (IOException ioex){

        }

    }
}
