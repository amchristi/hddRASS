package PreProcessig;

import ASTManipulation.MethodRemover;
import Helper.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import org.json.simple.JSONArray;

import java.io.*;
import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 1/1/17.
 */
public class PrepareClassBasedOnLabeling {
    public String inputFilePath;
    public String universalFilePath;
    public String aFilePath;
    public int highestLabelLevel = 0;
    public int percentage = 100;
    private String className;
    public List<String> deletedMethodList;

    public PrepareClassBasedOnLabeling(String inputFilePath, int highestLabelLevel){
        this.inputFilePath = inputFilePath;
        this.highestLabelLevel = highestLabelLevel;

        deletedMethodList = new ArrayList<>();
    }


    public  void process(){
        CompilationUnit cu = new CompilationUnit();

        CompilationUnit cu2 = CompilationUnitHelper.CreateCompilationUnit(cu,inputFilePath);
        List<String> donoRemoveList = new ArrayList<String>();
        donoRemoveList.add(Stuffs.DeriveClassNameFromFullPath(inputFilePath));
        MethodRemover methodRemover = new MethodRemover(cu2, donoRemoveList);
        methodRemover.removeMethods(this.highestLabelLevel);
        Debugger.log(methodRemover._newcu);
        FileWriterUtil.write(inputFilePath,methodRemover._newcu.toString());

    }

    public void processAsPerPercentage(int percentage){
        CompilationUnit cu = new CompilationUnit();

        CompilationUnit cu2 = CompilationUnitHelper.CreateCompilationUnit(cu,inputFilePath);
        List<String> donoRemoveList = new ArrayList<String>();
        donoRemoveList.add(Stuffs.DeriveClassNameFromFullPath(inputFilePath));
        MethodRemover methodRemover = new MethodRemover(cu2,donoRemoveList);
        methodRemover.removeMethodsByPercentage(percentage);
        Debugger.log(methodRemover._newcu);
        FileWriterUtil.write(inputFilePath,methodRemover._newcu.toString());
        deletedMethodList.addAll(methodRemover.deletedMethodList);
    }


    public CompilationUnit processGetCompliment(String u, String a){
        // cuU, cuA.
        CompilationUnit cuDash = null;

        return cuDash;

        // cuDash = cuU - cuA;
    }

    public void processAsPerLabeling(JSONArray requiredTests, JSONArray optionalTests){
        CompilationUnit cu = new CompilationUnit();

        CompilationUnit cu2 = CompilationUnitHelper.CreateCompilationUnit(cu,inputFilePath);
        List<String> donoRemoveList = new ArrayList<String>();
        donoRemoveList.add(Stuffs.DeriveClassNameFromFullPath(inputFilePath));
        MethodRemover methodRemover = new MethodRemover(cu2,donoRemoveList);
        methodRemover.removeMethodsByExternalLabeling(requiredTests,optionalTests);
        Debugger.log(methodRemover._newcu);
        FileWriterUtil.write(inputFilePath,methodRemover._newcu.toString());
        deletedMethodList.addAll(methodRemover.deletedMethodList);
    }


    public CompilationUnit processGetCompliment( String u, String a,String comFile){
        // cuU, cuA.
        CompilationUnit cuDash = null;
        CompilationUnit cu = new CompilationUnit();

        CompilationUnit cu2 = CompilationUnitHelper.CreateCompilationUnit(cu,a);
        MethodRemover methodRemover = new MethodRemover(cu2);
        List<BodyDeclaration> avaibleTestMethods = methodRemover.getAvaibleTestMethods();

        CompilationUnit cu3 = CompilationUnitHelper.CreateCompilationUnit(cu,u);
        methodRemover = new MethodRemover(cu3);
        CompilationUnit complement = methodRemover.complement(avaibleTestMethods, comFile);

        return complement;

        // cuDash = cuU - cuA;


    }

    public static void main(String args[]){
        PrepareClassBasedOnLabeling preprocessing = new PrepareClassBasedOnLabeling("/home/ubuntu/research/HDD/testdata/QuicksortTest.java",0);
        //preprocessing.processAsPerPercentage(25);

        String file1 = "/home/ubuntu/results/reduced/Digester/DigesterTestCase.java";
        String file2 = "/home/ubuntu/results/reduced/Digester/DigesterTestCase_1_10.java";
        String file3 = "/home/ubuntu/results/complement/Digester/DigesterTestCase_1_10.java";






//        String origianal = BASE_PATH+"XmlProperty\\XmlPropertyTest.java";

        PrepareClassBasedOnLabeling prepareClassBasedOnLabeling = new PrepareClassBasedOnLabeling("/home/ubuntu/research/HDD/testdata/QuicksortTest.java",0);


        CompilationUnit cu = prepareClassBasedOnLabeling.processGetCompliment(file1,file2,file3);

        System.out.println(cu.toString());

        FileWriterUtil.write(file3,cu.toString());



    }


}
