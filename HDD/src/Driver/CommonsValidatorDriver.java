package Driver;

import Helper.*;
import JReduce.BuildAndRun;
import JReduce.BuildAndRunAbstract;
import JReduce.BuildAndRunAnts;
import JReduce.HierarchicalClassReducer;
import PreProcessig.PrepareClassBasedOnLabeling;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static java.lang.System.exit;

/**
 * Created by arpit on 9/28/16.
 */
public class CommonsValidatorDriver {
    public static void main(String args[]) throws Exception {



        Helper.CommandLineParser commandLineParser = null;


        try {
            commandLineParser = new Helper.CommandLineParser(args);
        }
        catch(org.apache.commons.cli.ParseException parseEx){
            System.out.println("Error in command line argument, provide correct command line arguments");
            System.out.println(parseEx.getMessage());
        }

        Debugger.log(commandLineParser.getTestClassesFilePath());
        List<String> testClasses = new ArrayList<String>();
        try {
            testClasses = Helper.FileReaderUtil.ReadFileByLine(commandLineParser.getTestClassesFilePath());
        }
        catch(Exception ex){
            ex.printStackTrace();
            Debugger.log("********************** EXIT -200 ********************************");
            exit(-200);
        }
        testClasses.stream().forEach(x -> Debugger.log(x));
        String testclassRelativePath = commandLineParser.getTestClassRelativePath();
        String testClassName = Stuffs.DeriveClassNameFromFullPath(testclassRelativePath);
        //String testClassName = "UrlValidatorTest";
        String rootFolder = commandLineParser.getRootFolder();
        String relativeFilePath = commandLineParser.getRelativePath();
        String methodName = commandLineParser.getmehtodame();
        String className = commandLineParser.getClassName();
        String testJarFile = commandLineParser.getTestJarFileName();
        String jarFile = commandLineParser.getJarFileName();
        String buildCommand = commandLineParser.getBuildcommand();
        buildCommand = "mvn -f /home/ubuntu/research/commons-text/pom.xml compile";
        String runCommand = "mvn -f /home/ubuntu/research/commons-text/pom.xml test";
        String buildSuccessString = "BUILD SUCCESS";
        String runSuccessString = "";

        String packageName = commandLineParser.getPackageName();
        String pristineFolder = commandLineParser.getPristineFolder();
        String testSuiteName = commandLineParser.getTestSuiteName();
        Globals.TEST_RUNNING_TIME_OUT = commandLineParser.gettimeoutForInfiniteLoop();
        System.out.println("*************************");
        System.out.println(buildCommand);
        System.out.println("*************************");
        String resultFolder = commandLineParser.getResultFolder();
        FileOperationUtil.copyFile(pristineFolder+ relativeFilePath, rootFolder + relativeFilePath);
        FileOperationUtil.createDirectory(resultFolder);
        FileOperationUtil.copyFile(pristineFolder + relativeFilePath,resultFolder + className + ".java");
        FileOperationUtil.copyFile(pristineFolder + testclassRelativePath,rootFolder + testclassRelativePath);
        int percentage = 10;
        BuildAndRunAbstract buildAndRun;
        boolean buildOK;
        if( testSuiteName.isEmpty())
            buildAndRun= new BuildAndRun(buildCommand, null);
        else
            buildAndRun = new BuildAndRunAnts(buildCommand,testSuiteName,runCommand,buildSuccessString,runSuccessString);

        buildOK = buildAndRun.build();

        if(!buildOK){
            Debugger.log("************************************* ERROR IN BUILDING *******************************************");
            exit(-100);
        }

        for(int i = 2;i<=2;i++){

            buildOK = buildAndRun.build();
            if(!buildOK){
                Debugger.log("************************************* ERROR IN BUILDING *******************************************");
                exit(-100);
            }
            PrepareClassBasedOnLabeling prepareClassBasedOnLabeling = new PrepareClassBasedOnLabeling(rootFolder + testclassRelativePath,0);
            prepareClassBasedOnLabeling.processAsPerPercentage(percentage);
            Globals.NoOfTestsToConsider = prepareClassBasedOnLabeling.deletedMethodList.size();
            buildOK = buildAndRun.build();
            if(!(prepareClassBasedOnLabeling.deletedMethodList.size() > 0))
                buildOK = false;
            boolean inwhileloop  = false;
            while(!buildOK ){
                FileOperationUtil.copyFile(pristineFolder + testclassRelativePath,rootFolder + testclassRelativePath);
                PrepareClassBasedOnLabeling prepareClassBasedOnLabeling2 = new PrepareClassBasedOnLabeling(rootFolder + testclassRelativePath,0);
                prepareClassBasedOnLabeling2.processAsPerPercentage(percentage);
                Globals.NoOfTestsToConsider = prepareClassBasedOnLabeling.deletedMethodList.size();
                buildOK = buildAndRun.build();
                if(buildOK && prepareClassBasedOnLabeling2.deletedMethodList.size() > 0){
                    FileWriterUtil.appendLine(resultFolder + className + ".txt", i + "," + percentage + ","+ prepareClassBasedOnLabeling2.deletedMethodList.size() );
                }
                else{
                    buildOK = false;
                }
                inwhileloop = true;
            }
            if(buildOK && !inwhileloop){
                FileWriterUtil.appendLine(resultFolder + className + ".txt", i + "," + percentage + ","+ prepareClassBasedOnLabeling.deletedMethodList.size() );
            }
            FileOperationUtil.copyFile(rootFolder + testclassRelativePath,resultFolder+ testClassName + "_" + i + "_"+ percentage +".java");

            runSuccessString = "Tests run: " + (Globals.TotalNoOfTests- Globals.NoOfTestsToConsider) + ", Failures: 0, Errors: 0";
            buildAndRun = new BuildAndRunAnts(buildCommand,testSuiteName,runCommand,buildSuccessString,runSuccessString);
            HierarchicalClassReducer classReducer = new HierarchicalClassReducer(rootFolder,relativeFilePath,jarFile,packageName,
                    testJarFile,testClasses,buildCommand,className, false);
            classReducer.testSuiteName = testSuiteName;
            classReducer.buildAndRun = buildAndRun;
            classReducer.ReduceClassFromFullPath();

            FileOperationUtil.copyFile(rootFolder + relativeFilePath,resultFolder+ className + "_" + i + "_"+ percentage +".java");

            //PrepareClassBasedOnLabeling prepareClassBasedOnLabeling3 = new PrepareClassBasedOnLabeling(rootFolder + testclassRelativePath,0);
            //prepareClassBasedOnLabeling3.processAsPerPercentage(10);
            //buildOK = buildAndRun.build();
            //inwhileloop = false;
            //while(!buildOK){
            //    FileOperationUtil.copyFile(Globals.resultsFolder + testClassName + "_" + i + "_"+ "10" +".java",rootFolder + testclassRelativePath);
            //    PrepareClassBasedOnLabeling prepareClassBasedOnLabeling2 = new PrepareClassBasedOnLabeling(rootFolder + testclassRelativePath,0);
            //    prepareClassBasedOnLabeling2.processAsPerPercentage(10);
            //    buildOK = buildAndRun.build();
            //    if(buildOK){
            //        FileWriterUtil.appendLine(Globals.resultsFolder + className + ".txt", i + "_" + "20" + prepareClassBasedOnLabeling2.deletedMethodList.size() );
            //    }
            //    inwhileloop = true;
            //}
            //if(buildOK && !inwhileloop){
            //    FileWriterUtil.appendLine(Globals.resultsFolder + className + ".txt", i + "_" + "20" + prepareClassBasedOnLabeling3.deletedMethodList.size() );
            //}


            //FileOperationUtil.copyFile(rootFolder + testclassRelativePath,Globals.resultsFolder + testClassName + "_" + i + "_"+ "20" +".java");
            //HierarchicalClassReducer classReducer2 = new HierarchicalClassReducer(rootFolder,relativeFilePath,jarFile,packageName,
            //        testJarFile,testClasses,buildCommand,className, false);
            //classReducer2.ReduceClassFromFullPath();
            //FileOperationUtil.copyFile(rootFolder + relativeFilePath,Globals.resultsFolder+ className + "_" + i + "_"+ "20" +".java");

            FileOperationUtil.copyFile(pristineFolder+ relativeFilePath, rootFolder + relativeFilePath);
            FileOperationUtil.copyFile(pristineFolder + testclassRelativePath,rootFolder + testclassRelativePath);

        }

        //HierarchicalClassReducer classReducer = new HierarchicalClassReducer(rootFolder,relativeFilePath,jarFile,packageName,
        //        testJarFile,testClasses,buildCommand,className, false);
        //classReducer.ReduceClass();


    }


}
