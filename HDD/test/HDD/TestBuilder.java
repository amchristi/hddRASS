package HDD;

import Coverage.ClassMarker;
import JReduce.BuildAndRunAnts;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 1/29/17.
 */
public class TestBuilder extends TestCase {

    @Test
    public void testAntBuilder(){
        //BuildAndRunAnts antsBuilder = new BuildAndRunAnts("test123","");
        //antsBuilder.antCompileCommand = "ant -buildfile /home/ubuntu/research/cruisecontrol/main/build.xml compile-all";
        //Assert.assertTrue(antsBuilder.build());

        ClassMarker marker = new ClassMarker("/home/ubuntu/research/HDD/testdata/Coverage1.java","/home/ubuntu/temp.txt");
        marker.outputWriteFilePath = "/home/ubuntu/temp.txt";
        marker.mark();

    }
}
