package JReduce;

import Helper.Command;
import Helper.FileOperationUtil;

import java.io.IOException;

/**
 * Created by christia
 * build immportals project and then run all the tests.
 */
public class BuildAnRunImmortals extends BuildAndRunAbstract {
    public BuildAnRunImmortals(String command, ITester tester){
        this.buildCommand = command;
        this.tester = tester;

    }

    // TODO: Need to change this as per immortals way to build projects
    public boolean build() throws IOException {
        Command.exec(new String[]{"bash","-c",this.buildCommand + " | grep \"error:\" &> temp.txt"});

        boolean buildOK = FileOperationUtil.isEmptyFile("temp.txt");
        return  buildOK;

    }

    public boolean run(){
        try {
            try {
                return !build() ? false : tester.runAll();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }
}
