package JReduce;

import Helper.Debugger;
import Helper.Globals;
import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 6/6/16.
 */
public class Tester implements ITester {
    private String _jarFileName;
    private String _fullyQuantifiedClassName;
    private String _testName;
    private Boolean _result;
    private List<Class> _classes;
    private List<String> _strClassesList;
    private long TIME_TO_WAIT_BEFORE_GIVING_UP;
    public Tester() {


    }

    public Tester(String jarFileName, String fullyquantifiedClassName, String testName) {
        _jarFileName = jarFileName;
        _fullyQuantifiedClassName = fullyquantifiedClassName;
        _testName = testName;
        _result = true;
        TIME_TO_WAIT_BEFORE_GIVING_UP = Globals.TEST_RUNNING_TIME_OUT * 1000;
    }


    public Tester(String jarFileName, List<String> classes) {
        _jarFileName = jarFileName;
        this._strClassesList = classes;
        TIME_TO_WAIT_BEFORE_GIVING_UP = Globals.TEST_RUNNING_TIME_OUT * 1000;
    }

    private URL[] LoadJarFile(String jarFileName) throws MalformedURLException {
        File file = new File(jarFileName);

        URL url = file.toURL();
        URL[] urls = new URL[]{url};

        return urls;
    }

    public boolean run() {
        ClassLoader cl = null;
        try {
            cl = new URLClassLoader(LoadJarFile(_jarFileName));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Class<?> cls = null;
        try {
            cls = cl.loadClass(_fullyQuantifiedClassName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        Request r = Request.method(cls, _testName);
        JUnitCore core = new JUnitCore();
        Result result = core.run(r);
        return result.wasSuccessful();
    }


    public boolean runAll() throws InterruptedException {


            JUnitCore core = new JUnitCore();
            List<Class<?>> classes = new ArrayList<Class<?>>();
            for (String strClass : _strClassesList ) {
                ClassLoader cl = null;
                try {
                    cl = new URLClassLoader(LoadJarFile(_jarFileName));
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

                Class<?> cls = null;
                try {
                    cls = cl.loadClass(strClass);
                    classes.add(cls);
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

            }

            try{
            ThreadedJUnitTestRunner threadedrunner = new ThreadedJUnitTestRunner(classes);
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