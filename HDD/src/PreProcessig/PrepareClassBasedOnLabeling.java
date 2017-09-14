package PreProcessig;

import ASTManipulation.MethodRemover;
import Helper.*;
import japa.parser.ast.CompilationUnit;
import org.json.simple.JSONArray;

import java.rmi.StubNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 1/1/17.
 */
public class PrepareClassBasedOnLabeling {
    public String inputFilePath;
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


    public static void main(String args[]){
        PrepareClassBasedOnLabeling preprocessing = new PrepareClassBasedOnLabeling("/home/ubuntu/research/HDD/testdata/QuicksortTest.java",0);
        preprocessing.processAsPerPercentage(25);

    }


}
