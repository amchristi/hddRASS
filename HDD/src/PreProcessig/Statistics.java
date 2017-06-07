package PreProcessig;

import ASTManipulation.ClassIterator;
import ASTManipulation.MethodRemover;
import Helper.CompilationUnitHelper;
import Helper.Debugger;
import japa.parser.ast.CompilationUnit;

import java.util.ArrayList;

/**
 * Created by root on 4/22/17.
 */
public class Statistics {

    public static void main(String args[]){
        int numberOfMethods = GetNumberOfMethods("/home/ubuntu/results/DomainValidator.java");
        Debugger.log("# of Methods :" + numberOfMethods);
        //int numberOfTestMethods = GetNumberOfTestMethods("/home/ubuntu/research/commons-text/src/test/java/org/apache/commons/text/ExtendedMessageFormatTest.java");
      //  Debugger.log("# of Test Methods :" + numberOfTestMethods);
    }

    public static int GetNumberOfMethods(String fileame){
        ClassIterator classIterator = new ClassIterator(fileame);

        return classIterator.GetAllMethods().size();

    }

    public static int GetNumberOfTestMethods(String filename){
        CompilationUnit cu = CompilationUnitHelper.CreateCompilationUnit(filename);
        MethodRemover mr = new MethodRemover(cu,new ArrayList<>());
        return mr.GetTestMethodCount();
    }
}
