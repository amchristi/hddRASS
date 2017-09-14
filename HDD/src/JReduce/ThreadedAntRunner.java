package JReduce;

import Helper.*;

import java.io.IOException;
import java.util.Random;

/**
 * Created by root on 1/28/17.
 */
public class ThreadedAntRunner extends Thread{
    private boolean result;
    boolean tempResult;
    BuildAndRunAnts buildAndRunAnts;
    String correctnessString = Globals.EmptyString;
    boolean isSingleRun = true;

    public ThreadedAntRunner(BuildAndRunAnts buildAndRunAnts){
        this.buildAndRunAnts = buildAndRunAnts;

    }

    public boolean GetResult(){return result;}

    public void run(){
        if(!this.buildAndRunAnts.antRuncommand.isEmpty()){
            //this.buildAndRunAnts.buildCommand = Globals.antTestCommand;
            Random rn = new Random();
            int randomNumber = rn.nextInt(100000);
            String fileName = Globals.antTestResultPath + "tempAntRun" + String.valueOf(randomNumber) + ".txt";
            //String fileName = "/home/ubuntu/research/HDD/tempAntRun" + ".txt";
            Command.exec(new String[]{"bash","-c",this.buildAndRunAnts.antRuncommand + "  &>  " + fileName});
            try {
                result =  ((FileReaderUtil.Contains(fileName,this.buildAndRunAnts.testSuiteName))    && (FileReaderUtil.Contains(fileName,this.buildAndRunAnts.runSuccessString)));
                //result = (FileReaderUtil.Contains(fileName,this.buildAndRunAnts.runSuccessString));
                if(result){
                    //Debugger.log("**************************************Test results are true. ***************************************************");
                    System.out.println("**************************************Test results are true. Reduction performed.  ***************************************************");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            finally {
                FileOperationUtil.removeDirectory(fileName);
            }



        }
    }

}
