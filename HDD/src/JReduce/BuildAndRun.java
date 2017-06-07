package JReduce;

import HDD.*;
import Helper.Command;
import Helper.FileOperationUtil;

import java.io.IOException;

/**
 * Created by arpit on 9/4/16.
 */
public class BuildAndRun extends    BuildAndRunAbstract{

   public BuildAndRun(String command, JReduce.ITester tester){
       this.buildCommand = command;
       this.tester = tester;

   }

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
