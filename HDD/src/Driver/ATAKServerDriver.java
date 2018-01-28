package Driver;

import Helper.CommandLineParser;
import Helper.Globals;
import JReduce.BuildAnRunImmortals;
import JReduce.BuildAndRunAbstract;
import JReduce.HierarchicalClassReducer;
import PreProcessig.PrepareClassBasedOnLabeling;

import java.util.ArrayList;
import java.util.List;
import Helper.Debugger;

/**
 * Created by root on 9/4/17.
 */
public class ATAKServerDriver {

   public static void main(String[] args){


       Helper.CommandLineParser commandLineParser = null;
       System.out.println("*************************************** Starting ATAK Server class(es) reduction *************************************************");


       try {
           commandLineParser = new Helper.CommandLineParser(args);
       }
       catch(org.apache.commons.cli.ParseException parseEx){
           System.out.println("Error in command line argument, provide correct command line arguments");
           System.out.println(parseEx.getMessage());
       }

       Globals.IS_IMMORTALS_RUN = true;
       Debugger.DEBUG = commandLineParser.getIsDebug();


       //String testClassName = "UrlValidatorTest";
       String rootFolder = commandLineParser.getRootFolder();

       String testclassRelativePath = commandLineParser.getTestClassRelativePath();
       String relativeFilePath = commandLineParser.getRelativePath();
       String methodName = commandLineParser.getmehtodame();
       String className = commandLineParser.getClassName();
       String testJarFile = commandLineParser.getTestJarFileName();
       String jarFile = commandLineParser.getJarFileName();
       String buildCommand = commandLineParser.getBuildcommand();
       String runCommand = commandLineParser.getRunCommand();
           String packageName =commandLineParser.getPackageName();
       String pristineFolder = "";
       String testSuiteName = commandLineParser.getTestSuiteName();
       List<String> testClasses = new ArrayList<String>();
       testClasses.add("com.bbn.marti.Tests");
       String buildSuccessString = commandLineParser.getBuildSuccessString2();
       String runSuccessString = "";
       runSuccessString = "name=\"com.bbn.marti.Tests\" tests=\"%d\" skipped=\"%d\" failures=\"%d\" errors=\"%d";
       String testResultXmlFile = commandLineParser.getTestResultOutputPath();
       runSuccessString = String.format(runSuccessString,2,0,0,0);
       Globals.compileOutputPath = commandLineParser.getCompileOutputPath();
       Globals.antTestResultPath = testResultXmlFile;

       //
       String testJsonFile = commandLineParser.getTestJsonFile();


       ATAKServerTestOrganizer testOrganizer = new ATAKServerTestOrganizer(testJsonFile);
       testOrganizer.Organize();




       //classReducer.buildAndRun = ""
       PrepareClassBasedOnLabeling prepareClassBasedOnLabeling = new PrepareClassBasedOnLabeling(rootFolder + testclassRelativePath,0);
       prepareClassBasedOnLabeling.processAsPerLabeling(testOrganizer.requiredTests, testOrganizer.optionalTests);

       BuildAndRunAbstract buildAndRun;
       buildAndRun = new BuildAnRunImmortals(buildCommand,testSuiteName,runCommand,buildSuccessString,runSuccessString,testResultXmlFile,"*Tests.*xml.*");
       HierarchicalClassReducer classReducer = new HierarchicalClassReducer(rootFolder,relativeFilePath,jarFile,packageName,
               testJarFile,testClasses,buildCommand,className, false, buildAndRun);
       classReducer.testSuiteName = commandLineParser.getTestSuiteName();
       classReducer.ReduceClassFromFullPath();


   }
}
