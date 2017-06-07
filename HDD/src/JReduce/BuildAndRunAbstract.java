package JReduce;

import Helper.Command;

import java.io.IOException;

/**
 * Created by arpit on 9/4/16.
 */
public class BuildAndRunAbstract {
    String buildCommand;
    String runCommand;
    String buildSuccessString;
    String runSuccessString;
    JReduce.ITester tester;
    String testSuiteName;
    public BuildAndRunAbstract(){
        buildCommand = null;
        tester = null;
    }
    public BuildAndRunAbstract(String command, Tester tester){
        this.buildCommand = command;
        this.tester = tester;
    }
    public  boolean build() throws IOException {
        return false;
    }
    public boolean run() throws IOException{
        if(!build())
            return false;
        return false;
    }

}
