package HDD;

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
 * Created by root on 11/20/16.
 */
public class TestThrowsExample extends TestCase {

    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }
    public void testMethodThrowsIOExceptionGetDepthLevel(){

        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/ThrowsExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
            Debugger.log("Highest depth level for method " + p.toString() + ": " + treeManipulator.GetHeigestDepthLevel());;

        });

    }

    public void testThrowsGetNthLevelStatementForThrows(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/ThrowsExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodThrowsIOException")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });
                }

            }


        });


        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodThrowsIOExceptionInABlock")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });
                }

            }


        });
    }

    public void testReduceStatementThrows(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/ThrowsExample.java");
        FileWriterUtil.writeLine("temp.txt","");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodThrowsIOException")){
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


        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodThrowsIOExceptionInABlock")){
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
