package JReduce;

import Helper.PythonRunner;

/**
 * Created by christia
 * Threaded runner, if thread does not return within certain amount of time
 * it signals infinite loop. Primitive way.
 */
public class ThreadedPythonRunner {
    private boolean result;
    boolean tempResult;
    PythonRunner pythonRunner;
    public boolean GetResult(){
        return result;
    }

    public ThreadedPythonRunner(PythonRunner pythonRunner){
        this.pythonRunner = pythonRunner;
    }

    public void run(){

    }

}
