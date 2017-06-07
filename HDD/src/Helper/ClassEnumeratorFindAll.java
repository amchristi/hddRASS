package Helper;

/**
 * Created by arpit on 9/19/16.
 * taken from github and updated as per need.
 * https://github.com/ddopson/java-class-enumerator
 */
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;



public class ClassEnumeratorFindAll {
    private static void log(String msg) {
        System.out.println("ClassEnumeratorFindAll: " + msg);
    }
    private static URL[] LoadJarFile(String jarFileName) throws MalformedURLException {
        File file  = new File(jarFileName);

        URL url = file.toURL();
        URL[] urls = new URL[]{url};

        return urls;
    }
    private static Class<?> loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Unexpected ClassNotFoundException loading class '" + className + "'");
        }
    }
    private static Class<?> loadClass(String className, String jarFileName) {
            ClassLoader cl = null;
            try {
                cl = new URLClassLoader(LoadJarFile(jarFileName));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            Class<?> cls = null;
            try {
                cls = cl.loadClass(className);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }

            return cls;


    }

    /**
     * Given a directory returns all classes within that directory
     *
     * @param directory
     * @return Classes within Directory
     */
    public static List<Class<?>> processDirectory(File directory) {

        ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

        log("Reading Directory '" + directory + "'");
        try {
            // Get the list of the files contained in the package
            String[] files = directory.list();
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i];
                String className = null;

                // we are only interested in .class files
                if (fileName.endsWith(".class")) {
                    // removes the .class extension
                    className = fileName.substring(0, fileName.length() - 6);
                }

                log("FileName '" + fileName + "'  =>  class '" + className + "'");

                if (className != null) {
                    classes.add(loadClass(className));
                }

                // If the file is a directory recursively find class.
                File subdir = new File(directory, fileName);
                if (subdir.isDirectory()) {
                    classes.addAll(processDirectory(subdir));
                }

                // if file is a jar file
                if (fileName.endsWith(".jar")) {

                    classes.addAll(processJarfile(directory.getAbsolutePath() + "/" + fileName));

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return classes;
    }

    /**
     * Given a jar file's URL returns all classes within jar file.
     *
     */
    public static List<Class<?>> processJarfile(String jarPath) {
        List<Class<?>> classes = new ArrayList<Class<?>>();


        JarFile jarFile;

        try {
            jarFile = new JarFile(jarPath);

            // get contents of jar file and iterate through them
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                // Get content name from jar file
                String entryName = entry.getName();
                String className = null;

                // If content is a class save class name.
                if (entryName.endsWith(".class")) {
                    className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
                }

                log("JarEntry '" + entryName + "'  =>  class '" + className + "'");

                // If content is a class add class to List
                if (className != null) {
                    classes.add(loadClass(className,jarPath));
                    continue;
                }

                // If jar contains another jar then iterate through it
                if (entryName.endsWith(".jar")) {
                    classes.addAll(processJarfile(entryName));
                    continue;
                }
                // If content is a directory
                File subdir = new File(entryName);
                if (subdir.isDirectory()) {
                    classes.addAll(processDirectory(subdir));
                }

            }
            jarFile.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }

        return classes;
    }

    public static List<Class<?>> getClassWithinThisJarFile(String jarPath){
        List<Class<?>> classes = new ArrayList<Class<?>>();


        JarFile jarFile;

        try {
            jarFile = new JarFile(jarPath);

            // get contents of jar file and iterate through them
            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();

                // Get content name from jar file
                String entryName = entry.getName();
                String className = null;

                // If content is a class save class name.
                if (entryName.endsWith(".class")) {
                    className = entryName.replace('/', '.').replace('\\', '.').replace(".class", "");
                }

                log("JarEntry '" + entryName + "'  =>  class '" + className + "'");

                // If content is a class add class to List
                if (className != null) {
                    classes.add(loadClass(className,jarPath));
                    continue;
                }


                // If content is a directory
                //File subdir = new File(entryName);
                //if (subdir.isDirectory()) {
                //    classes.addAll(processDirectory(subdir));
                //}

            }
            jarFile.close();
        } catch (IOException e) {
            throw new RuntimeException("Unexpected IOException reading JAR File '" + jarPath + "'", e);
        }

        return classes;
    }

    public static void main(String[] args){

    }
}

