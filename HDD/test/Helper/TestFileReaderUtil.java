package Helper;

import com.sun.deploy.ref.Helpers;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by root on 1/28/17.
 */
public class TestFileReaderUtil extends TestCase {


    @Test
    public void testContains(){
        try {
            Assert.assertTrue(FileReaderUtil.Contains("/home/ubuntu/research/HDD/testdata/contains.txt","Testsuite: net.sourceforge.cruisecontrol.builders.AntBuilderTest"));
            Assert.assertTrue(FileReaderUtil.Contains("/home/ubuntu/research/HDD/testdata/contains.txt","Tests run: 21, Failures: 0, Errors: 0"));
        } catch (IOException e) {
            Assert.fail();
            e.printStackTrace();
        }
    }

}
