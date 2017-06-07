package HDD;

import JReduce.*;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/26/17.
 */
public class TestTester extends TestCase {

    @Test
    public void testCruiseControlWTF() throws InterruptedException {
        List<String> classes = new ArrayList<String>();
        classes.add("net.sourceforge.cruisecontrol.builders.AntBuilderTest");
        JReduce.Tester tester = new JReduce.Tester("/home/ubuntu/research/cruisecontrol/out/artifacts/cruisecontrol/cruisecontrol.jar",  classes);
        boolean temp = tester.runAll();
    }
}
