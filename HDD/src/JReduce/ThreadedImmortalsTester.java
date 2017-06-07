package JReduce;

import Helper.PythonTester;

/**
 * Created by christia
 * if thread does not finish in time, we assume that we are in infinte loop
 */
public class ThreadedImmortalsTester extends Thread{
    private boolean result;
    boolean tempResult;
    PythonTester tester;

    public ThreadedImmortalsTester(PythonTester tester){
        this.tester = tester;
        result = false;
    }
    public boolean GetResult(){
        return result;
    }

    public void run(){
        tempResult = true;
        tempResult &= tester.test();
        result = tempResult;
        return;
    }
}
