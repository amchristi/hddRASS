package HDD;

import ASTManipulation.ClassIterator;
import ASTManipulation.ClassMethodLineManipulator;
import ASTManipulation.TreeManipulator;
import Helper.Debugger;
import Helper.FileWriterUtil;
import japa.parser.ast.stmt.Statement;
import junit.framework.TestCase;

import java.util.List;

/**
 * Created by root on 6/4/17.
 */
public class TestReturn extends TestCase {

    public void testMethodThrowsIOExceptionGetDepthLevel(){

        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/ReturnExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
            Debugger.log("Highest depth level for method " + p.toString() + ": " + treeManipulator.GetHeigestDepthLevel());;

        });

    }



    public void testReturnGetNthLevelStatementForReturn(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/ReturnExample.java");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("getUndoOrRedoPresentationName")){
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


    public void testReduceStatementReturns(){
        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/HDD/testdata/ReturnExample.java");
        FileWriterUtil.writeLine("temp.txt","");
        classIterator.GetAllMethodsName().stream().forEach(p -> {
            if(p.equals("getUndoOrRedoPresentationName")){
                TreeManipulator treeManipulator = new TreeManipulator(classIterator.GetCompilationUnit(),p);
                int highestDepthLevel = treeManipulator.GetHeigestDepthLevel();
                for(int i = highestDepthLevel; i> 0; i--){
                    ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(classIterator.GetCompilationUnit(),p.toString());
                    List<Statement> statements = classMethodLineManipulator.GetNthLevelStatementsFromMethodName(p.toString(),i);

                    Debugger.log("At depth level " + i);
                    statements.stream().forEach(q -> {

                        Debugger.log(q.toString());
                    });

                    classMethodLineManipulator.ReduceMethodStatements("ReturnExample",p,statements);
                    FileWriterUtil.appendLine("temp.txt","***************************************************************");
                    FileWriterUtil.appendLine("temp.txt",classMethodLineManipulator._newcu.toString());
                }

            }


        });



    }





}
