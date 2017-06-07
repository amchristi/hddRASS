package HDD;

import PostProcessing.MethodDiffCalculator;
import PostProcessing.StatInfo;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import sun.swing.plaf.synth.DefaultSynthStyle;

/**
 * Created by root on 1/8/17.
 */
public class TestMethodDiffCalculator extends TestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    public void testMethod1(){
        MethodDiffCalculator calculator = new MethodDiffCalculator("/home/ubuntu/research/HDD/testdata/Original1.java","/home/ubuntu/research/HDD/testdata/Reduced1.java","method1");
        StatInfo statinfo = calculator.Calculate();
        Assert.assertEquals(statinfo.highestlevelFromLeafNode,2);
        Assert.assertEquals(statinfo.numberOfNodesReduced,3);
    }

    public void testMethod2(){
        MethodDiffCalculator calculator = new MethodDiffCalculator("/home/ubuntu/research/HDD/testdata/Original1.java","/home/ubuntu/research/HDD/testdata/Reduced1.java","method2");
        StatInfo statinfo = calculator.Calculate();
        Assert.assertEquals(statinfo.highestlevelFromLeafNode,2);
        Assert.assertEquals(statinfo.numberOfNodesReduced,3);
    }

    public void testMethod3(){
        MethodDiffCalculator calculator = new MethodDiffCalculator("/home/ubuntu/research/HDD/testdata/Original1.java","/home/ubuntu/research/HDD/testdata/Reduced1.java","method3");
        StatInfo statinfo = calculator.Calculate();
        Assert.assertEquals(statinfo.highestlevelFromLeafNode,1);
        Assert.assertEquals(statinfo.numberOfNodesReduced,1);
    }

    public void testMethod4(){
        MethodDiffCalculator calculator = new MethodDiffCalculator("/home/ubuntu/research/HDD/testdata/Original1.java","/home/ubuntu/research/HDD/testdata/Reduced1.java","method4");
        StatInfo statinfo = calculator.Calculate();
        Assert.assertEquals(statinfo.highestlevelFromLeafNode,1);
        Assert.assertEquals(statinfo.numberOfNodesReduced,3);
    }


    public void testMethodNetBeans(){
        MethodDiffCalculator calculator = new MethodDiffCalculator("/home/ubuntu/results/UndoManager1.java","/home/ubuntu/results/Reduced1.java","method4");
        StatInfo statinfo = calculator.Calculate();
        Assert.assertEquals(statinfo.highestlevelFromLeafNode,1);
        Assert.assertEquals(statinfo.numberOfNodesReduced,3);
    }
}
