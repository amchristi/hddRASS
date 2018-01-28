package PostProcessing;

import ASTManipulation.ClassIterator;
import Helper.Debugger;

import Helper.FileOperationUtil;
import Helper.FileWriterUtil;
import com.sun.deploy.ref.Helpers;

import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.stmt.Statement;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.lang.instrument.ClassDefinition;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 1/22/17.
 */
public class ClassDiffCalculatorNew
{

    private static String className;
    private String originalFile,reducedFile;

    public ClassDiffCalculatorNew(String originalFile, String reducedFile){
        this.originalFile = originalFile;
        this.reducedFile = reducedFile;
    }

    public StatInfo FindReductionInfo(){
        int toatalNodes = 0;
        int highestFromLeaf = 0;
        List<Statement> statements = new ArrayList<>();
        ClassIterator classIteratorOriginal = new ClassIterator(originalFile);
        ClassIterator classIternatorReduced = new ClassIterator(reducedFile);

        List<MethodDeclaration> methodsOriginal = classIteratorOriginal.GetAllMethods();
        List<MethodDeclaration> methodsReduced = classIternatorReduced.GetAllMethods();

        for (MethodDeclaration m1 : methodsOriginal
                ) {
            for ( MethodDeclaration m2: methodsOriginal
                    ) {
                if (classIteratorOriginal.ComapreMethods(m1, m2)) {
                    try {


                        if (m1.getName().equals("checkFile")) {

                            Debugger.log("stop");
                        }
                        MethodDiffCalculatoNew methodDiff = new MethodDiffCalculatoNew(originalFile, reducedFile, m1.getName());
                        StatInfo statInfo = methodDiff.Calculate();
                        toatalNodes += statInfo.numberOfNodesReduced;
                        statements.addAll(statInfo.statementList);
                        highestFromLeaf = Math.max(highestFromLeaf, statInfo.highestlevelFromLeafNode);
                    } catch (Exception ex) {
                        Debugger.log(ex.toString());


                    }

                }
            }
        }
        StatInfo statInfo = new StatInfo();
        statInfo.highestlevelFromLeafNode = highestFromLeaf;
        statInfo.numberOfNodesReduced = toatalNodes;
        statInfo.statementList = statements;

        return statInfo;



    }
    public StatInfo findReductionInfo(String outFile){
        int toatalNodes = 0;
        int highestFromLeaf = 0;
        FileWriterUtil.appendLine(outFile,className);
        List<Statement> statements = new ArrayList<>();
        ClassIterator classIteratorOriginal = new ClassIterator(originalFile);
        ClassIterator classIternatorReduced = new ClassIterator(reducedFile);

        List<MethodDeclaration> methodsOriginal = classIteratorOriginal.GetAllMethods();
        List<MethodDeclaration> methodsReduced = classIternatorReduced.GetAllMethods();

        for (MethodDeclaration m1 : methodsOriginal
                ) {
            for ( MethodDeclaration m2: methodsOriginal
                    ) {
                if (classIteratorOriginal.ComapreMethods(m1, m2)) {
                    try {


                        if (m1.getName().equals("checkFile")) {

                            Debugger.log("stop");
                        }
                        if(m1.getName().equals("undo")){
                            Debugger.log("stop here");
                        }
                        FileWriterUtil.appendLine(outFile,"~~~");
                        FileWriterUtil.appendLine(outFile,m1.getName());

                        MethodDiffCalculatoNew methodDiff = new MethodDiffCalculatoNew(originalFile, reducedFile, m1.getName());
                        StatInfo statInfo = methodDiff.Calculate();
                        toatalNodes += statInfo.numberOfNodesReduced;
                        statements.addAll(statInfo.statementList);
                        for (int i = 0; i < statInfo.statementList.size(); i++) {
                            Statement statement = statInfo.statementList.get(i);
                            FileWriterUtil.appendLine(outFile,"~");
                            FileWriterUtil.appendLine(outFile,statement.toString());

                        }
                        highestFromLeaf = Math.max(highestFromLeaf, statInfo.highestlevelFromLeafNode);
                    } catch (Exception ex) {
                        Debugger.log(ex.toString());


                    }

                }
            }
        }
        StatInfo statInfo = new StatInfo();
        statInfo.highestlevelFromLeafNode = highestFromLeaf;
        statInfo.numberOfNodesReduced = toatalNodes;
        statInfo.statementList = statements;

        return statInfo;



    }


    public static void main(String[] args){
        className = "DefaultIntervalCategoryDataset";
        ClassDiffCalculatorNew classDiffCalculatorNew = new ClassDiffCalculatorNew("/home/ubuntu/results/reduced/DefaultIntervalCategoryDataset/DefaultIntervalCategoryDataset.java","//home/ubuntu/results/reduced/DefaultIntervalCategoryDataset/DefaultIntervalCategoryDataset_1_20.java");
        StatInfo i = classDiffCalculatorNew.findReductionInfo("/home/ubuntu/results/diff/DefaultIntervalCategoryDataset_1_20.java");








    }
}
