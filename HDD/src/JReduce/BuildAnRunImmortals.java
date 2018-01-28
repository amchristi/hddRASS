package JReduce;

import Driver.Configuration;
import Helper.*;

import java.io.IOException;

/**
 * Created by christia
 * build immportals project and then run all the tests.
 */
public class BuildAnRunImmortals extends BuildAndRunAbstract {
    public String antCompileCommand;
    public String antRuncommand;
    private long TIME_TO_WAIT_BEFORE_GIVING_UP;
    public String testResultXmlFile;
    public String testResultPath;
    public String testFileRegEx;
    public BuildAnRunImmortals(String buildCommand, String testsuitename, String runCommand, String buildSuccessString, String runSuccessString, String testResultPath, String testFileRegex){
        this.buildCommand = buildCommand;
        this.antCompileCommand = buildCommand;
        this.antRuncommand = runCommand;
        this.tester = tester;
        Globals.TEST_RUNNING_TIME_OUT = 90;
        this.TIME_TO_WAIT_BEFORE_GIVING_UP = Globals.TEST_RUNNING_TIME_OUT * 1000;
        this.testSuiteName = testsuitename;
        this.buildSuccessString = buildSuccessString;
        this.runSuccessString = runSuccessString;
        this.runCommand = runCommand;
        this.testSuiteName = testsuitename;
        this.testResultXmlFile = testResultXmlFile;
        this.testResultPath = testResultPath;
        this.testFileRegEx = testFileRegex;


    }

    // TODO: Need to change this as per immortals way to build projects
    public boolean build() throws IOException {
        boolean result = false;
        this.antCompileCommand =  this.buildCommand;

        if(!this.buildCommand.isEmpty()){

            Command.exec(new String[]{"bash","-c",this.antCompileCommand +   " &> "  + Configuration.compileOutputPath});
            try {
                this.buildSuccessString = Configuration.buildSuccessString;
                result =  (FileReaderUtil.Contains(Configuration.compileOutputPath,this.buildSuccessString));
                // TODO: arpit, remove this line later. dummy line make build pass.
                //result = true;
            } catch (IOException e) {
                e.printStackTrace();
            }

            return result;
        }
        return result;

    }

    public boolean run() throws IOException {

        try{
            ThreadedImmortalRunner threadedrunner = new ThreadedImmortalRunner(this);
            threadedrunner.start();
            // if infinite loop is introduced
            // give up and return test suite run result as false;
            Debugger.log(TIME_TO_WAIT_BEFORE_GIVING_UP);
            threadedrunner.join(TIME_TO_WAIT_BEFORE_GIVING_UP);

            threadedrunner.interrupt();
            threadedrunner.stop();
            if(!threadedrunner.GetResult())
                return false;
        }
        catch(InterruptedException ex){
            System.out.println("****************** thread interrupted ********************************");
        }


        return true;
    }

}
