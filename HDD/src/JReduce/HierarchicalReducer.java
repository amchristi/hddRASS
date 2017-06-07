package JReduce;

import ASTManipulation.*;
import Helper.Debugger;
import Helper.Globals;
import japa.parser.ParseException;

import java.util.ArrayList;
import java.util.List;
/**
 * Created by arpit on 8/29/16.
 */
public class HierarchicalReducer {
    public JavaMethod javaMethod;
    String rootFolder = Globals.EmptyString;
    String relativeFilePath = Globals.EmptyString;
    String methodName = Globals.EmptyString;
    String className = "";
    List<String> testClasses = new ArrayList<String>();
    int highestDepthLevel;
    public String fullJarFilePath = Globals.EmptyString;
    public String testJarFilePath = Globals.EmptyString;
    String buildCommand;
    public String testsuiteName;
    public BuildAndRunAbstract buildAndRun;



    //strClasses.add("QuicksortTest");
    ITester tester = null;

    public HierarchicalReducer(JavaMethod javaMethod,String rootFolder,String relativeFilePath,String methodName, String testJarFileName,  List<String> testClasses, String buildCommand, String className){
        this.testClasses = testClasses;
        this.javaMethod = javaMethod;
        this.rootFolder = rootFolder;
        this.relativeFilePath = relativeFilePath;
        this.methodName = methodName;
        this.buildCommand = buildCommand;
        this.className = className;
        this.fullJarFilePath = javaMethod.jarFileWithPath;
        this.testJarFilePath = testJarFilePath;

        tester = new Tester(testJarFileName,testClasses);

        Initialize();
    }

    public HierarchicalReducer(JavaMethod javaMethod,String rootFolder,String relativeFilePath,String methodName, ITester tester, String buildCommand, String className){
        this.testClasses = testClasses;
        this.javaMethod = javaMethod;
        this.rootFolder = rootFolder;
        this.relativeFilePath = relativeFilePath;
        this.methodName = methodName;
        this.buildCommand = buildCommand;
        this.className = className;
        this.fullJarFilePath = javaMethod.jarFileWithPath;
        this.tester = tester;

        Initialize();
    }

    private void Initialize(){
        ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
        TreeManipulator tree = new TreeManipulator(javaMethod.cu,javaMethod.testMethodName);
        highestDepthLevel = tree.GetHeigestDepthLevel();
        Debugger.log(highestDepthLevel);
    }

    public void Reduce() throws ParseException {
        for(int i = highestDepthLevel; i> 0;i--){
            javaMethod = new JavaMethod(rootFolder,relativeFilePath,methodName,className,fullJarFilePath);
            if(testClasses.isEmpty() || this.testJarFilePath.isEmpty())
                tester = new Tester(fullJarFilePath,testClasses);
            ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
            TreeManipulator tree = new TreeManipulator(javaMethod.cu,javaMethod.testMethodName);
            if(buildAndRun == null){
                Reducer reducer = new Reducer(javaMethod,tester,i,this.buildCommand,testsuiteName);
                reducer.ReduceSequentical();
            }
            else{
                Reducer reducer = new Reducer(javaMethod,tester,i,this.buildCommand,testsuiteName,buildAndRun);
                reducer.ReduceSequentical();

            }


            Debugger.log("************************************************** " + i);
        }
        System.out.println("done.....................................................................................");
    }



}
