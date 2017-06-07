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
public class TestTryCatchFinallyExample  extends TestCase{
    @Before
    public void setUp() throws Exception {

    }

    @After
    public void tearDown() throws Exception {

    }

    public void testHighestDepthLevelTryCatch(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
            Debugger.log("Highest depth level for method " + p.toString() + ": " + treeManipulator.GetHeigestDepthLevel());;

        });

    }

    public void testGetNthLevelSimpleTryCatch(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("tryCatchMethod")){
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

    public void testGetNthLevelTryCatchFinally(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("tryCatchFinallyMethod")){
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

    public void testGetNthLevelMultipleCatch(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("multipleCatch")){
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

    public void testReduceSimpleTryCatch(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        FileWriterUtil.writeLine("temp.txt","");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("tryCatchMethod")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });

                    classMethodLineManipulator.ReduceMethodStatements("TryCatchFinallyExample",p,statements);
                    FileWriterUtil.appendLine("temp.txt","***************************************************************");
                    FileWriterUtil.appendLine("temp.txt",classMethodLineManipulator._newcu.toString());
                }

            }


        });

    }

    public void testReduceTryCatchFinally(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        FileWriterUtil.writeLine("temp.txt","");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("tryCatchFinallyMethod")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });

                    classMethodLineManipulator.ReduceMethodStatements("TryCatchFinallyExample",p,statements);
                    FileWriterUtil.appendLine("temp.txt","***************************************************************");
                    FileWriterUtil.appendLine("temp.txt",classMethodLineManipulator._newcu.toString());
                }

            }


        });

    }

    public void testReduceMultipleCatch(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/TryCatchFinallyExample.java");
        FileWriterUtil.writeLine("temp.txt","");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("multipleCatch")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });

                    classMethodLineManipulator.ReduceMethodStatements("TryCatchFinallyExample",p,statements);
                    FileWriterUtil.appendLine("temp.txt","***************************************************************");
                    FileWriterUtil.appendLine("temp.txt",classMethodLineManipulator._newcu.toString());
                }

            }


        });

    }
}
