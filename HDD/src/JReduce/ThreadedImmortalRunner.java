package JReduce;

import Driver.Configuration;
import Helper.Command;
import Helper.FileOperationUtil;
import Helper.FileReaderUtil;
import Helper.Globals;

import java.io.IOException;
import java.util.Random;

/**
 * Created by root on 9/4/17.
 */
public class ThreadedImmortalRunner extends Thread{
    private boolean result;
    boolean tempResult;
    BuildAnRunImmortals buildAnRunImmortals;
    String correctnessString = Globals.EmptyString;
    boolean isSingleRun = true;

    public ThreadedImmortalRunner(BuildAnRunImmortals buildAnRunImmortals){
        this.buildAnRunImmortals = buildAnRunImmortals;
    }
    public boolean GetResult(){return result;}
    public void run(){
        if(!this.buildAnRunImmortals.antRuncommand.isEmpty()){
            //this.buildAndRunAnts.buildCommand = Globals.antTestCommand;
            Random rn = new Random();
            int randomNumber = rn.nextInt(100000);
            //String fileName = Globals.antTestResultPath + "tempAntRun" + String.valueOf(randomNumber) + ".txt";
            String fileName = Configuration.intermediateTestResultOutputPath + "tempAntRun" + String.valueOf(randomNumber) + ".txt";
            //String fileName = "/home/ubuntu/research/HDD/tempAntRun" + ".txt";
            //TODO: arpit, uncomment this after discussion with Austin, how does bbn's test mechanism handles infinte loop.
            Command.exec(new String[]{"bash","-c",this.buildAnRunImmortals.antRuncommand + "  &>  " + fileName});


            try {
                String resultFile = FileOperationUtil.getLatestResultFile(this.buildAnRunImmortals.testResultPath,this.buildAnRunImmortals.testFileRegEx);
                result =  ((FileReaderUtil.Contains(resultFile,this.buildAnRunImmortals.testSuiteName))    && (FileReaderUtil.Contains(resultFile,this.buildAnRunImmortals.runSuccessString)));
                //result = (FileReaderUtil.Contains(fileName,this.buildAndRunAnts.runSuccessString));
                if(result){
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
