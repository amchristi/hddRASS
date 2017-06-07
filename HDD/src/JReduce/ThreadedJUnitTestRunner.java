package JReduce;

import org.junit.runner.JUnitCore;

import java.util.List;

/**
 * Created by arpit on 9/4/16.
 */
public class ThreadedJUnitTestRunner extends Thread{
    private boolean result;
    boolean tempResult;

    Class<?> cls;
    private List<Class<?>> classes;


    public ThreadedJUnitTestRunner(Class<?> cls){
        this.cls = cls;
        result = false;

    }
    public ThreadedJUnitTestRunner(List<Class<?>> classes){
        this.classes = classes;
        this.result = false;
    }

    public boolean GetResult(){
        return result;
    }



    public void run(){
        JUnitCore core = new JUnitCore();
        //result = core.run(cls).wasSuccessful();
        tempResult = true;
        //classes.stream().forEach(x -> tempResult &= core.run(x).wasSuccessful());
        for (Class<?> cls: classes) {
            tempResult &= core.run(cls).wasSuccessful();
            if(!tempResult){
                Helper.Debugger.log(cls.getSimpleName());
            }
        }
        result = tempResult;
        return;

    }

    public void run2(){
        JUnitCore core = new JUnitCore();
        //result = core.run(cls).wasSuccessful();
        tempResult = true;
        //classes.stream().forEach(x -> tempResult &= core.run(x).wasSuccessful());
        for (Class<?> cls: classes) {
            tempResult &= core.run(cls).wasSuccessful();
            if(!tempResult){
                Helper.Debugger.log(cls.getSimpleName());
            }
        }
        result = tempResult;
        return;

    }
}
