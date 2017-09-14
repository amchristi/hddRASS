package NetBeansCaseStudy;

import Helper.Debugger;
import Helper.FileWriterUtil;
import Helper.Globals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by root on 5/8/17.
 */
public class PollIDEForMemory {

    public static void collect(int pid){
        String output = Globals.EmptyString;

        String[] cmd = new String[1];

        cmd = new String[]{"bash","-c","jmap -histo:live " + Integer.toString(pid) + " | grep Total"};

        Runtime rt = Runtime.getRuntime();
        Process pr = null;
        try {
            pr = rt.exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader bfr = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        String line = "";
        String lastValue = Globals.EmptyString;
        try {

            while ((line = bfr.readLine()) != null) {
                output += line ;
                lastValue = line;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //System.out.println(lastValue);
        Object[] values = Arrays.stream(lastValue.split(" ")).toArray();
        System.out.println(values[values.length - 1]);
        float value = (float) (Integer.parseInt(values[values.length - 1].toString()) / 1000 * 1000);
        FileWriterUtil.appendLine("/home/ubuntu/temp.txt",Float.toString(value));


    }

    public static void main(String[] args) throws InterruptedException {
        FileWriterUtil.writeLine("/home/ubuntu/temp.txt", "starting................................................");
        for(int i = 0;i<60*3;i++){
            collect(4158);

            Thread.sleep(1000);
        }

        FileWriterUtil.writeLine("/home/ubuntu/temp.txt", "ending................................................");
    }
}
