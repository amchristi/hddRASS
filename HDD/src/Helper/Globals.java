package Helper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 9/28/16.
 */
public class Globals {
    public static String EmptyString = "";
    public static int TEST_RUNNING_TIME_OUT = 30;
    public static boolean IS_IMMORTALS_RUN = false;
    public static String backupJavaFileFullPath = "temp.txt";
    public static String resultsFolder = "/home/ubuntu/results/reduced/";
    public static String pristineFolder = "/home/ubuntu/pristine";
    public static int NoOfTestsToConsider = 0; // completely unnecessary, exists only for build and tests that runs through ants to provide support for ant.
    public static int TotalNoOfTests = 0;
    //public static int TotalNoOfTests = 10;
    // following 2 values are here for NetBeans reduction
    //public static String antBuildCommand = "ant -buildfile /home/ubuntu/tools/main/openide.awt/build.xml";
    //public static String antTestCommand = " ant -buildfile /home/ubuntu/tools/main/openide.awt/build.xml test";



    //public static String antBuildCommand = "mvn -f /home/ubuntu/research/commons-jexl3/pom.xml compile";
    //public static String antTestCommand = "mvn -f /home/ubuntu/research/commons-jexl3/pom.xml -Dtest=MethodTest test";
    //org.apache.commons.jxpath.ri.model.dom.DOMModelTest

    public static String antBuildCommand = "ant -buildfile /home/ubuntu/research/ant/build.xml build";
    public static String antTestCommand = "ant -buildfile /home/ubuntu/research/ant/build.xml junit-tests";

    public static String compileOutputPath = "/home/ubuntu/results/temp/tempAntCompile.txt";
    public static String antTestResultPath = "/home/ubuntu/results/temp/";

    public static boolean isSingleRun = false; // used to determine if we are trying to run multiple runs to collect stat info.
    public static List<String> notToProcess = new ArrayList<String>();
    public static boolean H1 = false;
    public static boolean H2 = false;
    public static boolean H3 = false;
    public static String h3PreComputedStatementFile = "/home/ubuntu/backup/Manager.covered";
    public static String h1PreComputedStatetmentFile = "/home/ubuntu/research/HDD/src/Driver/H1Statements.txt";
    public static boolean GREEDY= false;

}
