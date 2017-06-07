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
 * Created by root on 11/17/16.
 */
public class TestIfElseIfElseDepthLevel extends TestCase {
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }


    public void testGetDepthLevel(){

        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/IfElseIfElse.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
            Debugger.log("Highest depth level for method " + p.toString() + ": " + treeManipulator.GetHeigestDepthLevel());;

        });

    }

    public void testGetNthLevelStmtsForIfElseIfElse(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/IfElseIfElse.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodIfElseIfElse")){
                ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),3);
                statements.stream().forEach(q -> {


                    Debugger.log(q.toString());
                });
            }


        });
    }

    public void testGetNthLevelStatementFromIfElse(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/IfElseIfElse.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodSimpleIfElse")){
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

    public void testReduceStatements(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/IfElseIfElse.java");
        FileWriterUtil.writeLine("temp.txt","");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("methodIfElseIfElse")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });

                    classMethodLineManipulator.ReduceMethodStatements("IfElseIfElse",p,statements);
                    FileWriterUtil.appendLine("temp.txt","***************************************************************");
                    FileWriterUtil.appendLine("temp.txt",classMethodLineManipulator._newcu.toString());
                }

            }


        });
    }
}
