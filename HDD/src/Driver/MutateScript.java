package Driver;

import Helper.*;
import JReduce.BuildAnRunImmortals;
import JReduce.HierarchicalClassReducer;
import japa.parser.ast.CompilationUnit;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.parser.ParseException;

import static java.lang.System.exit;

/**
 * Created by root on 10/17/17.
 */
public class MutateScript {


    public static void main(String[] args) throws FileNotFoundException,IOException,ParseException{



        JSONParser parser = new JSONParser();

        ClassLoader cl = MutateScript.class.getClassLoader();
        URL url = cl.getResource("mutatescript.json");
        String mutatteScriptJsonFile = url.getFile();

        String immotalsConfigFile = cl.getResource("immortals.properties").getFile();
        String generalConfigFile = cl.getResource("config.properties").getFile();
        Configuration.buildConfiguration(generalConfigFile,immotalsConfigFile);
        Globals.IS_IMMORTALS_RUN = Configuration.isImmortalRun;
        Debugger.DEBUG = Configuration.isDebug;



        JSONObject jsonObject = (JSONObject)parser.parse(new FileReader(mutatteScriptJsonFile));
        System.out.println(jsonObject);

        JSONArray requiredValidators =  (JSONArray)jsonObject.get("requiredValidators");
        String buildTool = jsonObject.get("buildTool").toString();
        String buildToolPath = jsonObject.get("buildToolPath").toString();
        String buildToolValidationParameters =jsonObject.get("buildToolValidationParameters").toString();
        String applicationPath = jsonObject.get("applicationPath").toString();
        String sourceSubpath = jsonObject.get("sourceSubpath").toString();
        String buildToolBuildParamethers = jsonObject.get("buildToolBuildParameter").toString();
        String rootFolder = applicationPath;
        String buildFilePath = applicationPath +  jsonObject.get("buildFilePath").toString();

        List<String> testClasses = new ArrayList<String>();
        buildTool = Globals.EmptyString;
        String buildCommand = buildToolPath  + " --build-file" + " " + buildFilePath + " " + buildToolBuildParamethers ;
        //buildCommand = applicationPath + buildCommand;
        Debugger.log(buildCommand);
        String testCommand = buildToolPath +  " --build-file" + " " + buildFilePath + " " + buildToolValidationParameters ;
        for ( Object   obj : requiredValidators            ) {
            Debugger.log(obj.toString());
            testCommand += " --tests " + obj.toString();

        }



        //String runSucessString = "tests=\"2\" skipped=\"0\" failures=\"0\" errors=\"0\"";
        String runSucessString = Configuration.runSuccessString;

        BuildAnRunImmortals buildAndRunImmortals = new BuildAnRunImmortals( buildCommand,Configuration.packageName,testCommand,Configuration.buildSuccessString,runSucessString,Configuration.testResultPath,Configuration.testFileRegEx);
        if(!buildAndRunImmortals.build()){
            Debugger.log("Initial program did not compile correctly. Contact Austin.");
            exit(-100);
        }
        if(!buildAndRunImmortals.run()){
            Debugger.log("Initial program did not run correctly. Contact Austin.");
            exit(-200);
        }
        Debugger.log(testCommand);

        List<String> files = new ArrayList<String>();
        File f = new File(applicationPath+sourceSubpath);
        Path p = f.toPath();
        files = FileOperationUtil.getFileNames(files,p);


        for (String file: files) {

            System.out.println(file.toString());
            String className = Stuffs.DeriveClassNameFromFullPath(file);

            CompilationUnit cu = new CompilationUnit();

            CompilationUnit cu2 = CompilationUnitHelper.CreateCompilationUnit(cu,file);
            String relativePath = Stuffs.GetRelativePathFromFullPath(rootFolder,file);
            String packageName = cu2.getPackage().getName().toString();
            HierarchicalClassReducer classReducer = new HierarchicalClassReducer(rootFolder,relativePath,"" ,packageName,
                    "",testClasses,buildCommand,className, false);
            classReducer.testSuiteName = Configuration.packageName;
            classReducer.buildAndRun = buildAndRunImmortals;
            classReducer.ReduceClassFromFullPath();

        }




        



    }
}
