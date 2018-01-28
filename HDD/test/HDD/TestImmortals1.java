package HDD;

import junit.framework.TestCase;
import ASTManipulation.ClassIterator;
import ASTManipulation.ClassMethodLineManipulator;
import ASTManipulation.TreeManipulator;
import Helper.Debugger;
import Helper.FileWriterUtil;
import japa.parser.ast.stmt.Statement;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;

import java.util.List;

/**
 * Created by root on 1/23/18.
 */
public class TestImmortals1 extends TestCase{


    public void testFilterInterfaceFle(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/Filter.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("filter")){
                ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),3);
                statements.stream().forEach(q -> {


                    Debugger.log(q.toString());
                });
            }


        });
    }

}
