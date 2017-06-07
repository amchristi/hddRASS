package Helper;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 1/16/17.
 */
public class TestStuffs extends TestCase {

    @Test
    public void testDeriveClassFromFile(){
        String inputfile = "/home/ubuntu/research/commons-validator/src/test/java/org/apache/commons/validator/routines/UrlValidatorTest.java";
        String className = Stuffs.DeriveClassNameFromFullPath(inputfile);
        Assert.assertEquals("UrlValidatorTest",className);
    }

}


