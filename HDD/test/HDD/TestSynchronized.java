package HDD;

import ASTManipulation.ClassIterator;
import ASTManipulation.ClassMethodLineManipulator;
import ASTManipulation.TreeManipulator;
import Helper.Debugger;
import Helper.FileWriterUtil;
import japa.parser.ast.stmt.Statement;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.List;

/**
 * Created by root on 2/12/17.
 */
public class TestSynchronized extends TestCase{



    @Test
    public void testMethod1HighestDepthLevel(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/SynchronizedExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
            Debugger.log("Highest depth level for method " + p.toString() + ": " + treeManipulator.GetHeigestDepthLevel());;

        });

    }

    @Test
    public void testReduceStatementsSynchronized(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/SynchronizedExample.java");

        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("method2")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });

                    classMethodLineManipulator.ReduceMethodStatements("ThrowsExample",p,statements);
                    FileWriterUtil.appendLine("temp.txt","***************************************************************");
                    FileWriterUtil.appendLine("temp.txt",classMethodLineManipulator._newcu.toString());
                }

            }


        });

    }
}
