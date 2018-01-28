package PostProcessing;

import ASTManipulation.ClassMethodLineManipulator;
import ASTManipulation.ExtendedStatement;
import ASTManipulation.TreeManipulator;
import Helper.Debugger;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.stmt.Statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import PostProcessing.StatInfo;
import sun.swing.plaf.synth.DefaultSynthStyle;

/**
 * Created by root on 1/4/17.
 */
public class MethodDiffCalculatoNew {

    ClassMethodLineManipulator clmOriginal,clmReduced;
    TreeManipulator treeOriginal,treeReduced;
    String methodName;

    public MethodDiffCalculatoNew(String fileOriginal, String fileReduced,String methodName){
        clmOriginal = new ClassMethodLineManipulator(CreateCompilationUnit(fileOriginal),methodName);
        clmReduced = new ClassMethodLineManipulator(CreateCompilationUnit(fileReduced),methodName);
        treeOriginal = new TreeManipulator(clmOriginal._cu,methodName);
        treeReduced = new TreeManipulator(clmReduced._cu,methodName);
        this.methodName = methodName;
    }

    private CompilationUnit CreateCompilationUnit(String javaFile){
        FileInputStream in = null;
        CompilationUnit cu = null;
        String fullPath = javaFile;
        try {
            String current = new java.io.File(".").getCanonicalPath();
            Debugger.log("Current dir:"+current);
        }catch(Exception ex2){

        }

        try {
            in = new FileInputStream(fullPath);
        }
        catch(FileNotFoundException fex){
            Debugger.log("file not found");

        }

        try {
            // parse the file
            cu = JavaParser.parse(in);



        }
        catch(ParseException pex){

        }
        catch(Exception ex){
            Debugger.log(ex);
        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // prints the resulting compilation unit to default system output

        return cu;

    }

    public StatInfo Calculate(){
        int highestDepthLevelOriginal = treeOriginal.GetHeigestDepthLevel();
        int highestDepthLevelReduced = treeReduced.GetHeigestDepthLevel();
        PostProcessing.StatInfo statInfo = new PostProcessing.StatInfo();
        List<Statement> lastPoolOfOriginalStatement = new ArrayList<>();
        List<Statement> lastPoolOfReducedStatement = new ArrayList<>();
        List<ExtendedStatement> originalStatementExtended = new ArrayList<ExtendedStatement>();
        List<ExtendedStatement> lastpoolOfOriginalStatementExtended = new ArrayList<ExtendedStatement>();
        List<ExtendedStatement> reducedStatementExtended = new ArrayList<>();
        int diffBetweenHighestLevels = highestDepthLevelOriginal - highestDepthLevelReduced;
        if(highestDepthLevelOriginal > highestDepthLevelReduced){
            statInfo.highestlevelFromLeafNode = highestDepthLevelOriginal - highestDepthLevelReduced;
            for(int i = highestDepthLevelOriginal;i>highestDepthLevelReduced;i--){
                List<Statement> statements = clmOriginal.GetNthLevelStatementsFromMethodName(this.methodName,i);
                statInfo.numberOfNodesReduced += statements.size();
                if(i == highestDepthLevelReduced + 1){
                    lastPoolOfOriginalStatement.addAll(statements);
                    lastpoolOfOriginalStatementExtended.addAll(lastPoolOfOriginalStatement.stream().map(x -> new ExtendedStatement(x,0)).collect(Collectors.toList()));

                }

            }

        }



        for(int i = highestDepthLevelReduced; i> 0;i--){
            List<Statement> currentPoolOfOriginalStatement = clmOriginal.GetNthLevelStatementsFromMethodName(this.methodName,i);
            List<Statement> currentPoolOfReducedStatement = clmReduced.GetNthLevelStatementsFromMethodName(this.methodName,i);
            List<Statement> diffStatement = clmOriginal.GetNthLevelStatementsFromMethodName(this.methodName,i);
            diffStatement.removeAll(currentPoolOfReducedStatement);
            originalStatementExtended.clear();
            originalStatementExtended.addAll(currentPoolOfOriginalStatement.stream().map(x -> new ExtendedStatement(x,0)).collect(Collectors.toList()));
            if(currentPoolOfOriginalStatement.size() != currentPoolOfReducedStatement.size()){
                statInfo.numberOfNodesReduced += Math.max(currentPoolOfOriginalStatement.size() - currentPoolOfReducedStatement.size(),0);
                statInfo.statementList = diffStatement;
//                for (int j = 0; j < currentPoolOfOriginalStatement.size(); j++) {
//                    Statement statement = currentPoolOfOriginalStatement.get(j);
//                    boolean exist = false;
//                    for (int k = 0; k < currentPoolOfReducedStatement.size(); k++) {
//                        Statement statement1 = currentPoolOfReducedStatement.get(k);
//                        if (statement.toString().equals(statement1.toString())) {
//                            exist = true;
//                            break;
//
//                        }
//                    }
//                    if (!exist) {
//                        statInfo.statementList.add(statement);
//                    }
//                }
                originalStatementExtended.forEach(x -> {
                    for (ExtendedStatement e: lastpoolOfOriginalStatementExtended) {
                        if(treeOriginal.ContainsStatement(x.statement,e.statement))
                            x.level = e.level + 1;
                    }



                });

            }
            else{
                if(i != highestDepthLevelReduced){
                    originalStatementExtended.forEach(x -> {
                        for (ExtendedStatement e: lastpoolOfOriginalStatementExtended) {
                            if(treeOriginal.ContainsStatement(x.statement,e.statement))
                                x.level = e.level + 1;
                        }

                    });

                }

            }

            lastpoolOfOriginalStatementExtended.clear();
            lastpoolOfOriginalStatementExtended.addAll(diffStatement.stream().map(x -> new ExtendedStatement(x,0)).collect(Collectors.toList()));

        }


        if(highestDepthLevelReduced == 0){
            List<Statement> diffStatement = new ArrayList<Statement>();
            for(int i = highestDepthLevelOriginal;i>0;i--){
                diffStatement.addAll(clmOriginal.GetNthLevelStatementsFromMethodName(this.methodName,i));
            }
            statInfo.statementList = diffStatement;

        }
        Debugger.log(lastpoolOfOriginalStatementExtended);
        Debugger.log(originalStatementExtended);
        if(!(originalStatementExtended.size() == 0)){
            statInfo.highestlevelFromLeafNode += originalStatementExtended.stream().mapToInt(x -> x.level).max().getAsInt() ;
        }
        if(statInfo.numberOfNodesReduced > 0){
            if(statInfo.highestlevelFromLeafNode  == 0){
                statInfo.highestlevelFromLeafNode = 1;
            }
        }


        return statInfo;
    }

}
