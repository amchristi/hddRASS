package PostProcessing;

import ASTManipulation.ClassMethodLineManipulator;
import ASTManipulation.Reducer;
import ASTManipulation.TreeManipulator;
import Helper.CompilationUnitHelper;
import Helper.Debugger;
import Helper.FileWriterUtil;
import Helper.Stuffs;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.stmt.Statement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/20/18.
 */
public class PreComputeH1Statements {

    CompilationUnit cu;
    String fileName;
    String className;

    public PreComputeH1Statements(String fileName){
        this.fileName = fileName;
        cu = CompilationUnitHelper.CreateCompilationUnit(fileName);
        className = Stuffs.DeriveClassNameFromFullPath(fileName);
    }

    public List<Statement> compute(){
        List<Statement> newSetOfStatement = new ArrayList<Statement>();
        ArrayList<BodyDeclaration> newBody = new ArrayList<BodyDeclaration>();
        for(TypeDeclaration t : cu.getTypes()){
            if(t.getName().toString().equals(className)){
                for (BodyDeclaration b : t.getMembers()) {
                    if (b.getClass().getSimpleName().toString().equals("MethodDeclaration")) {
                        MethodDeclaration m = (MethodDeclaration) b;


                            //ArrayList<Statement> x = ReduceStatementsFromBlock(m.getBody(), statementToBeReduced);
                        ClassMethodLineManipulator clm = new ClassMethodLineManipulator(this.cu,m.getName());
                        newSetOfStatement.addAll(clm.GetLeafStatements(className,m.getName()));

                    }
                }
            }
        }

        newSetOfStatement.forEach( x -> Debugger.log(x));
        return newSetOfStatement;

    }

    public static void main(String[] args){
        String filename = "/home/ubuntu/research/HDD/testdata/Manager.java";
        String outputFile = "/home/ubuntu/research/HDD/src/Driver/H1Statements.txt";
        PreComputeH1Statements p = new PreComputeH1Statements(filename);
        p.compute().forEach(y -> FileWriterUtil.appendLine(outputFile,y.toString()));

    }
}
