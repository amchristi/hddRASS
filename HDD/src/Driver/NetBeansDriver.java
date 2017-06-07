package Driver;

import Helper.Stuffs;
import JReduce.HierarchicalClassReducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 5/24/17.
 */
public class NetBeansDriver {

    public static void main(String[] args){

        String testclassRelativePath = "/home/ubuntu/tools/main";
        String testClassName = "sfsdfsd";
        //String testClassName = "UrlValidatorTest";
        String rootFolder = "/home/ubuntu/tools/main";
        String relativeFilePath = "/openide.awt/src/org/openide/awt/Manager.java";
        String methodName = "adasf";
        String className = "Manager";
        String testJarFile = "sdfsd";
        String jarFile = "sdfsdf";
        String buildCommand = "ant -buildfile /home/ubuntu/tools/main/openide.awt/build.xml";
        String packageName = "";
        String pristineFolder = "";
        String testSuiteName = "";
        List<String> testClasses = new ArrayList<String>();
        HierarchicalClassReducer classReducer = new HierarchicalClassReducer(rootFolder,relativeFilePath,jarFile,packageName,
                testJarFile,testClasses,buildCommand,className, false);
        classReducer.testSuiteName = "org.openide.awt";
        classReducer.ReduceClassFromFullPath();
    }
}
