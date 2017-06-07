package JReduce;

import Helper.Globals;
import japa.parser.ParseException;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

/**
 * Created by arpit on 6/27/16.
 */

public class Main {

    public static void main(String[] args) throws ParseException, org.apache.commons.cli.ParseException {
        Helper.CommandLineParser commandLineParser = null;
        try {
             commandLineParser = new Helper.CommandLineParser(args);
        }
        catch(org.apache.commons.cli.ParseException parseEx){
            System.out.println("Error in command line argument, provide correct command line arguments");
            System.out.println(parseEx.getMessage());
        }


        String rootFolder = commandLineParser.getRootFolder();
        String relativeFilePath = commandLineParser.getRelativePath();
        String methodName = commandLineParser.getmehtodame();
        String className = commandLineParser.getClassName();
        String testJarFile = commandLineParser.getTestJarFileName();
        String jarFile = commandLineParser.getJarFileName();
        String buildCommand = commandLineParser.getBuildcommand();
        System.out.println("*************************");
        System.out.println(buildCommand);
        System.out.println("*************************");
        methodName = "isValidAuthority";
        JavaMethod javaMethod = new JavaMethod(rootFolder,relativeFilePath,methodName,className, jarFile);
        List<String> strClasses = new ArrayList<String>();
        strClasses.add("org.apache.commons.validator.routines.UrlValidatorTest");
        Globals.TEST_RUNNING_TIME_OUT = commandLineParser.gettimeoutForInfiniteLoop();
        //strClasses.add("QuicksortTest");
        Tester t = new Tester(testJarFile,strClasses);
        //Reducer dd = new Reducer(javaMethod, t);
        //dd.ReduceSequentical();

        HierarchicalReducer hReducer = new HierarchicalReducer(javaMethod,rootFolder,relativeFilePath,methodName,testJarFile,strClasses,buildCommand,className);
        hReducer.Reduce();

    }
}
