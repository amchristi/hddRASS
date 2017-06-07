package HDD;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Created by arpit on 6/5/16.
 */
public class Temp {

    public void load() throws MalformedURLException, ClassNotFoundException {
        File file  = new File("/home/arpit/workspace/commons-validator.jar");

        URL url = file.toURL();
        URL[] urls = new URL[]{url};

        ClassLoader cl = new URLClassLoader(urls);
        Class cls = cl.loadClass("com.mypackage.myclass");

    }
}
