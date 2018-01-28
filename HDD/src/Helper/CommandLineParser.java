package Helper;

import org.apache.commons.cli.*;

/**
 * Created by arpit on 9/15/16.
 */
public class CommandLineParser {
    org.apache.commons.cli.CommandLineParser parser;
    Options options;
    CommandLine commandLine;
    public CommandLineParser(String[] args) throws ParseException {

        options = new Options();
        options.addOption("rootFolder",true,"root folder path, mostly src path of program");
        options.addOption("level",true,"level of granuality, class or method");
        options.addOption("relativePath",true,"File Path of java file");
        options.addOption("methodName",true,"method to be reduced, empty if reduction is at class level");
        options.addOption("testJarFileName",true,"jar file for test");
        options.addOption("className",true,"class to be reduced, in case of level is method, class containing the method");
        options.addOption("jarFileName", true, "jar file of program");
        options.addOption("buildCommand",true,"build command");
        options.addOption("packageName",true,"pacakge name");
        options.addOption("timeoutForInfiniteLoop", true, "time out to detect infinite loop in seconds");
        options.addOption("testClassesFilePath", true, "test class file path, data file where set of test cases are being kept");
        options.addOption("immortalsTester", false, "Immortals tester, special case to run immortals python tester.");
        options.addOption("jarOrApk", true, "Speficy if final outcome is jar or apk.");
        options.addOption("pristineFolder",true,"Specify pristine folder");
        options.addOption("testClassRelativePath", true,"test class relative path");
        options.addOption("resultsFolder", true,"result folder path");
        options.addOption("testSuiteName", true,"test suite name");
        options.addOption("runCommand", true,"test suite name");
        options.addOption("buildOutputPath", true, "build output path");
        options.addOption("runOutputPath", true, "run output path");
        options.addOption("buildSuccessString", true, "build Success String");
        options.addOption("buildSuccessString2", true, "build Success String2");
        options.addOption("runSuccessString", false, "run Success String");
        options.addOption("testJsonFile",true,"test json file");
        options.addOption("compileOutputPath",true,"compile output path");
        options.addOption("testResultOutputPath", true,"test result output path");
        options.addOption("isDebug", true,"set to produce verbose output");
        options.addOption("totalTests", true,"total number of tests");
        options.addOption("percentRemoval", true,"percentage of tests need removal");




        parser = new org.apache.commons.cli.DefaultParser();

        commandLine = parser.parse(options,args);

    }

    public String getRootFolder(){
        return commandLine.getOptionValue("rootFolder");

    }
    public String getLevel(){
        return commandLine.getOptionValue("level");
    }
    public String getRelativePath(){
        return commandLine.getOptionValue("relativePath");
    }
    public String getmehtodame(){
        return commandLine.getOptionValue("methodName");
    }
    public String getClassName(){
        return commandLine.getOptionValue("className");
    }
    public String getTestJarFileName(){
        return commandLine.getOptionValue("testJarFileName");
    }
    public String getJarFileName(){
        return commandLine.getOptionValue("jarFileName");
    }
    public String getBuildcommand(){return commandLine.getOptionValue("buildCommand");}
    public String getPackageName(){return commandLine.getOptionValue("packageName");}
    public int gettimeoutForInfiniteLoop(){
        String temp = commandLine.getOptionValue("timeoutForInfiniteLoop");
        if(temp == null || temp.isEmpty()){
            return 0;
        }
        return Integer.parseInt(temp);
    }
    public String getTestClassesFilePath(){return commandLine.getOptionValue("testClassesFilePath");}
    public String getImmortalsTester(){
        return commandLine.getOptionValue("immortalsTester");
    }
    public String getJarOrApk(){return commandLine.getOptionValue("jarOrApk");}
    public String getPristineFolder(){return commandLine.getOptionValue("pristineFolder");}
    public String getTestClassRelativePath(){return commandLine.getOptionValue("testClassRelativePath");}
    public String getResultFolder(){return commandLine.getOptionValue("resultsFolder");}
    public String getTestSuiteName(){return commandLine.getOptionValue("testSuiteName") == null ?
            Globals.EmptyString:commandLine.getOptionValue("testSuiteName");}
    public String getBuildOutputPath(){return commandLine.getOptionValue("buildOutputPath");}
    public String getRunOutputPath (){return commandLine.getOptionValue("runOutputPath");}
    public String getBuildSuccessString() {return commandLine.getOptionValue("buildSuccessString");}
    public String getRunSuccessString(){return commandLine.getOptionValue("runSuccessString");}
    public String getTestJsonFile(){return commandLine.getOptionValue("testJsonFile");}
    public String getCompileOutputPath(){return commandLine.getOptionValue("compileOutputPath");}
    public String getTestResultOutputPath(){return commandLine.getOptionValue("testResultOutputPath");}
    public String getRunCommand(){return commandLine.getOptionValue("runCommand");}
    public String getBuildSuccessString2(){return commandLine.getOptionValue("buildSuccessString2");}
    public boolean getIsDebug(){return Boolean.parseBoolean(commandLine.getOptionValue("isDebug"));}
    public int getTotalTests(){return Integer.parseInt(commandLine.getOptionValue("totalTests"));}
    public int getPercentRemoval(){return Integer.parseInt(commandLine.getOptionValue("percentRemoval"));}



    public static void main(String[] args) throws ParseException{
        try {
            CommandLineParser parser = new CommandLineParser(args);
        }catch(ParseException parseEx){
            System.out.println("Error in command line argument, provide correct command line arguments");
            System.out.println(parseEx.getMessage());
        }

    }

}
