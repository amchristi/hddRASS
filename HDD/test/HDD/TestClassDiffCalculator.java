package HDD;

import Helper.Debugger;
import PostProcessing.ClassDiffCalculator;
import PostProcessing.StatInfo;
import junit.framework.TestCase;
import org.junit.Test;

/**
 * Created by root on 1/22/17.
 */
public class TestClassDiffCalculator extends TestCase {

    @Test
    public void testDiffjexl1(){

        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/AntBuilder/AntBuilder.java","/home/ubuntu/results/reduced/AntBuilder/AntBuilder_8_10.java");


        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testDiffDomainValidator(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/DomainValidator/DomainValidator.java","/home/ubuntu/results/reduced/DomainValidator/DomainValidator_1_10.java");
        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testDiffEngine(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/Engine/Engine.java","/home/ubuntu/results/reduced/Engine/Engine_1_10.java");
        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testGnuParser(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/GnuParser/GnuParser.java","/home/ubuntu/results/reduced/GnuParser/GnuParser_1_10.java");
        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testJexlEvalContext(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/JexlEvalContext/JexlEvalContext.java","/home/ubuntu/results/reduced/JexlEvalContext/JexlEvalContext_1_10.java");
        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testDiffLocationMapper(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/LocationMapper/LocationMapper.java","/home/ubuntu/results/reduced/LocationMapper/LocationMapper_1_10.java");
        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testDefaultParser(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/DefaultParser/DefaultParser.java","/home/ubuntu/results/reduced/DefaultParser/DefaultParser_3_10.java");

        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }

    @Test
    public void testSchedule(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/Schedule/Schedule.java","/home/ubuntu/results/reduced/Schedule/Schedule_1_10.java");

        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }


    @Test
    public void testDiffUrlValidator(){
        ClassDiffCalculator clsDiff = new ClassDiffCalculator("/home/ubuntu/results/reduced/UrlValidator.java","/home/ubuntu/results/reduced/UrlValidator_1_10.java");
        StatInfo statInfo = clsDiff.FindReductionInfo();
        Debugger.log(statInfo.highestlevelFromLeafNode);
        Debugger.log(statInfo.numberOfNodesReduced);

    }
}
