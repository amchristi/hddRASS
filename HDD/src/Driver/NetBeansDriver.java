package Driver;

import Helper.*;
import JReduce.BuildAndRun;
import JReduce.BuildAndRunAbstract;
import JReduce.BuildAndRunAnts;
import JReduce.HierarchicalClassReducer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/24/17.
 */
public class NetBeansDriver {

    public static void main(String[] args) throws IOException {

        String testclassRelativePath = "/home/ubuntu/tools/main";
        String testClassName = "sfsdfsd";
        //String testClassName = "UrlValidatorTest";
        String rootFolder = "/home/ubuntu/tools/main";
        String relativeFilePath = "/openide.awt/src/org/openide/awt/Manager.java";
        String methodName = "adasf";
        String className = "Manager";
        String testJarFile = "sdfsd";
        String jarFile = "sdfsdf";
        String buildCommand = "ant -buildfile /home/ubuntu/tools/main/openide.awt/build.xml";
        String runCommand = "ant -buildfile /home/ubuntu/tools/main/openide.awt/build.xml test";

        String packageName = "org.openide.awt";
        String pristineFolder = "";
        String testSuiteName = "org.openide.awt";

        String buildSuccessString = "BUILD SUCCESS";
        String runSuccessString = "BUILD SUCCESS";
        Globals.H1 = true;
        Globals.H2 = false;
        Globals.H3 = false;

        Globals.TEST_RUNNING_TIME_OUT = 60;

        System.out.println("*************************");
        System.out.println(buildCommand);
        System.out.println("*************************");

        BuildAndRunAbstract buildAndRun;
        boolean buildOK;
        if( testSuiteName.isEmpty())
            buildAndRun= new BuildAndRun(buildCommand, null);
        else
            buildAndRun = new BuildAndRunAnts(buildCommand,testSuiteName,runCommand,buildSuccessString,runSuccessString);

        buildOK = buildAndRun.build();
        if(buildOK)
            Debugger.log("initial build ok");



        List<String> testClasses = new ArrayList<String>();
        HierarchicalClassReducer classReducer = new HierarchicalClassReducer(rootFolder,relativeFilePath,jarFile,packageName,
                testJarFile,testClasses,buildCommand,className, false);
        ;
        classReducer.buildAndRun = buildAndRun;

        classReducer.testSuiteName = "org.openide.awt";

        Stopwatch timer1 = new Stopwatch();

        classReducer.ReduceClassFromFullPath();

        double time1 = timer1.elapsedTime();

        System.out.println("**************** PRINTING TIME ************************************");
        System.out.println(time1);
        FileWriterUtil.writeLine("/home/ubuntu/temp/netBeansTimer.txt",Double.toString(time1));

        System.out.println("NetBeans reduction done.");


    }
}
