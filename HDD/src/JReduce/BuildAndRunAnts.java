package JReduce;

import Helper.Command;
import Helper.Debugger;
import Helper.FileReaderUtil;
import Helper.Globals;

import java.io.IOException;
import java.util.List;

/**
 * Created by root on 1/28/17.
 */

// This class can be used for both ants and maven
public class BuildAndRunAnts extends BuildAndRunAbstract {
    private long TIME_TO_WAIT_BEFORE_GIVING_UP;
    public String antCompileCommand;
    public String antRuncommand;
    //public String testSuiteName;

    public BuildAndRunAnts(String buildCommand, String testsuitename, String runCommand, String buildSuccessString, String runSuccessString){
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


    }

    public boolean build(){
        boolean result = false;
        this.antCompileCommand =  this.buildCommand;

            if(!this.buildCommand.isEmpty()){

                Command.exec(new String[]{"bash","-c",this.antCompileCommand +   " &> "  + Globals.compileOutputPath});
                try {
                    //result =  (FileReaderUtil.Contains(Globals.compileOutputPath,"BUILD SUCCESS"));
                    result =  (FileReaderUtil.Contains(Globals.compileOutputPath,this.buildSuccessString));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return result;
            }
         return result;
    }
    public boolean run() throws IOException {

        try{
            ThreadedAntRunner threadedrunner = new ThreadedAntRunner(this);
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
