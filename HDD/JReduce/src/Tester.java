import org.junit.runner.JUnitCore;
import org.junit.runner.Request;
import org.junit.runner.Result;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;

/**
 * Created by arpit on 6/6/16.
 */
public class Tester implements ITester{
    private String _jarFileName;
    private String _fullyQuantifiedClassName;
    private String _testName;
    private Boolean _result;
    private List<Class> _classes;
    private List<String> _strClassesList;
    public Tester(){


    }
    public Tester(String jarFileName, String fullyquantifiedClassName, String testName){
        _jarFileName = jarFileName;
        _fullyQuantifiedClassName = fullyquantifiedClassName;
        _testName = testName;
        _result = true;
    }


    public Tester(String jarFileName, List<String> classes){
        _jarFileName = jarFileName;
        this._strClassesList = classes;
    }

    private URL[] LoadJarFile(String jarFileName) throws MalformedURLException {
        File file  = new File(jarFileName);

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

    public boolean runAll(){
        JUnitCore core = new JUnitCore();

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
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            if(!core.run(cls).wasSuccessful())
                return false;
        }


        return true;
    }


}
