package Coverage;

import Helper.CompilationUnitHelper;
import Helper.FileOperationUtil;
import Helper.FileReaderUtil;
import Helper.FileWriterUtil;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.stmt.Statement;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by root on 12/30/17.
 * This class measuares dead code or uncovered code while running tests
 * We use it to measure dead code with respect to unlabeled tests.
 * Hypothesis: Coverage by labeled tests + dead code by unlabled tests are the likely removal targets.
 */
public class ClassUnCoverage {

    public String annotatedClassFile;
    public String uuidFile;
    public String className;
    public String outputFile;
    List<Statement> uncoveredStatement;

    public ClassUnCoverage(String annotatedClassFile, String uuidFile, String className){
        this.annotatedClassFile = annotatedClassFile;
        this.uuidFile = uuidFile;
        this.className = className;
    }

    public void measure() throws IOException {
        ArrayList<String> uuids = (ArrayList<String>) FileReaderUtil.ReadFileByLine(uuidFile);
        Set<UUID> uuidSet = uuids.stream().map(x -> UUID.fromString(x)).distinct().collect(Collectors.toSet());
        CompilationUnit cu = CompilationUnitHelper.CreateCompilationUnit(annotatedClassFile);
        FileWriterUtil.writeLine2(outputFile,className);
        cu.getTypes().stream().filter(x -> x.getName().toString().equals(this.className)).forEach(y -> {
            y.getMembers().stream().filter(z -> z.getClass().getSimpleName().equals("MethodDeclaration")).forEach(a -> {
                MethodDeclaration m = (MethodDeclaration)a;
                BlockStmt blk  = m.getBody();
                BlockCoverage blockCoverage = new BlockCoverage(blk,uuidSet);
                List<Statement> coveredStatements = blockCoverage.measaure();

                FileWriterUtil.appendLine(outputFile,"~~~");

                FileWriterUtil.appendLine(outputFile,m.getName().toString());





                for (Statement s: blockCoverage.uncoveredStatements
                        ) {
                    FileWriterUtil.appendLine(outputFile,"~");
                    System.out.println(s.toString());
                    FileWriterUtil.appendLine(outputFile,s.toString());

                }

                System.out.println("method name : " + m.getName());
                System.out.println("uncovered: ");
                blockCoverage.uncoveredStatements.forEach(s -> System.out.println(s.toString()));

            });
        });

        System.out.println(cu.toString());



    }

    public static void main(String[] args) throws IOException{

        String className = "Digester";
        int label = 20;
        for(int i = 1; i<= 1; i++){
            String annotatedFilePath = "/home/ubuntu/results/annotated/" + className + "/" + i + "/" + className + ".java";
            String coverageFilePath = "/home/ubuntu/results/coverage/uncoveragepristine/" + className + "/" + className + "_" + i + "_" + label + ".coverage";
            ClassUnCoverage classUnCoverage = new ClassUnCoverage(annotatedFilePath,coverageFilePath,className);
            classUnCoverage.outputFile = "/home/ubuntu/temp/output.txt";
            classUnCoverage.measure();

            List<String> list = FileReaderUtil.ReadFileByLine("/home/ubuntu/temp/output.txt");
            String coveredStatementFile = "/home/ubuntu/results/coveredstatements/" + className  + "_" + i + "_" + label + ".uncovered";
            FileOperationUtil.createEmptyFile(coveredStatementFile);
            FileWriterUtil.writeLine(coveredStatementFile,"");
            for (String s: list
                    ) {
                if(!s.contains("writeline(")){
                    FileWriterUtil.appendLine(coveredStatementFile, s);
                    System.out.println(s);
                }

            }
        }




    }

}
