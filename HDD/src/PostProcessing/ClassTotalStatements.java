package PostProcessing;

import ASTManipulation.ClassIterator;
import ASTManipulation.ClassMethodLineManipulator;
import Helper.CompilationUnitHelper;
import Helper.Debugger;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.stmt.Statement;

import java.util.List;

/**
 * Created by root on 4/25/17.
 */
public class ClassTotalStatements {
    String originalFile;
    int highestDepthLevel = 0;

    public ClassTotalStatements(String originalFile){
        this.originalFile = originalFile;

    }

    public int FindTotalSTatemetns(){
        ClassIterator classIteratorOriginal = new ClassIterator(originalFile);
        List<MethodDeclaration> methodsOriginal = classIteratorOriginal.GetAllMethods();
        int sum = 0;
        for (MethodDeclaration m1 : methodsOriginal){
            ClassMethodLineManipulator clm = new ClassMethodLineManipulator(CompilationUnitHelper.CreateCompilationUnit(originalFile),m1.getName());

            int n = clm.GetHeigestDepthLevel();
            this.highestDepthLevel = Math.max(n,this.highestDepthLevel);
            for(int i =n;i>0;i--){


                List<Statement> statements = clm.GetNthLevelStatementsFromMethodName(m1.getName(),i);
                sum += statements.size();




            }
        }

        return sum;
    }



    public static void main(String[] args){
        ClassTotalStatements cts = new ClassTotalStatements("/home/ubuntu/results/reduced/OptionGroup/OptionGroup.java");
        int total = cts.FindTotalSTatemetns();

        System.out.println(total);
        System.out.println(cts.highestDepthLevel);
    }
}
