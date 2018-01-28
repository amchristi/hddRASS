package JReduce;

import ASTManipulation.ClassMethodLineManipulator;
import ASTManipulation.StatementTypeQuery;
import ASTManipulation.TreeManipulator;
import Helper.*;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.stmt.Statement;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 6/27/16.
 */
public class Reducer {
    JavaMethod javaMethod;
    JavaMethod newJavaMethod;

    Delta delta;
    CompilationUnit cu;
    ITester tester;
    CompilationUnit copyOfcu;
    int highestDepthLevel;
    int currentDepthLevel;
    String buildcommand;
    BuildAndRunAbstract buildAndRunCommand;
    String testSuitename = Globals.EmptyString;
    public Reducer(JavaMethod javaMethod, ITester tester, int currentDepthLevel, String buildCommand, String testSuitename){
        this.javaMethod = javaMethod;
        delta = new Delta();
        this.currentDepthLevel = currentDepthLevel;
        CreateInitialDelta();
        this.tester = tester;
        this.buildcommand = buildCommand;
        highestDepthLevel = 0;
        ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
        TreeManipulator tree = new TreeManipulator(javaMethod.cu,javaMethod.testMethodName);
        highestDepthLevel = tree.GetHeigestDepthLevel();
        this.testSuitename = testSuitename;


    }


    public Reducer(JavaMethod javaMethod, ITester tester, int currentDepthLevel, String buildCommand, String testSuitename,BuildAndRunAbstract buildAndRun){
        this.javaMethod = javaMethod;
        delta = new Delta();
        this.currentDepthLevel = currentDepthLevel;
        CreateInitialDelta();
        this.tester = tester;
        this.buildcommand = buildCommand;
        highestDepthLevel = 0;
        ClassMethodLineManipulator classMethodLineManipulator = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
        TreeManipulator tree = new TreeManipulator(javaMethod.cu,javaMethod.testMethodName);
        highestDepthLevel = tree.GetHeigestDepthLevel();
        this.testSuitename = testSuitename;
        buildAndRunCommand = buildAndRun;

    }



    private void CreateInitialDelta(){
        TreeManipulator tree;
        tree = new TreeManipulator(javaMethod.cu,javaMethod.testMethodName);
        ClassMethodLineManipulator cmlm = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
        highestDepthLevel = tree.GetHeigestDepthLevel();
        List<Statement> statements = cmlm.GetNthLevelStatementsFromMethodName(javaMethod.testMethodName,currentDepthLevel);
        if(Globals.H1){
            statements = applyHeuristicsH1(statements);
        }
        if(Globals.H3){
            statements = applyHeuristicsH3(statements);
        }
        if(Globals.H2){
            statements = applyHeuristicsH2(statements);
        }
        if(statements != null)
            delta.c.addAll(statements);
        this.cu = javaMethod.cu;


    }

    public void Reduce(){
        int i;
        for(i=highestDepthLevel; i>0; i++){
            ClassMethodLineManipulator cmlm = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
            List<Statement> statements = cmlm.GetNthLevelStatementsFromMethodName(javaMethod.testMethodName,i);
            if(Globals.H1){
                statements = applyHeuristicsH1(statements);
            }
            if(Globals.H3){
                statements = applyHeuristicsH3(statements);
            }
            if(Globals.H2){
                statements = applyHeuristicsH2(statements);
            }

            delta.c.addAll(statements);
            // use cu created in create initial delta for first time
            // but later, create and use new cu read from the file.

         }
    }





    public void ReduceSequentical() throws ParseException {
        Delta delta = this.delta;
        int n = 2;
        boolean firstCondition;
        boolean secondCondition;
        Delta temp_delta;
        List<Delta> list;
        Delta intermediateResult = delta;

        ITester tempTester = tester;
        int iteration = 0;
        if(delta.c.size() == 0)
            return;


        while (true) {
            try {

                // if delta just contains one statement
                // remove it, run the tests
                // and take a decision. no need to go through reduction.


                firstCondition = false;
                secondCondition = false;
                String str2 = this.cu.toString();
                if (n > delta.len()) {

                    Debugger.log("here");
                    List<Statement> statements = delta.c;
                    boolean resultOfTestRun = isResultOfTestRun(statements);
                    if (resultOfTestRun) {
                        //if (intermediateResult != null && (intermediateResult.len() > temp_delta.len()))
                         //   intermediateResult = temp_delta;
                        n = 2;
                        //delta = temp_delta;
                        firstCondition = true;
                        break;
                    }
                    else{
                        // revert back to original java files
                        //firstCondition = false;
                        Revert(str2);
                        break;

                    }
                    //continue;
                    //break;
                }
                //Command command2 = new Command();
                //command2.exec("ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml");
                //build(this.buildcommand);
                //boolean result = tester.runAll();
                BuildAndRunAbstract buildAndRun;
                if(!Globals.IS_IMMORTALS_RUN){
                    if( testSuitename.isEmpty())
                        buildAndRun= new BuildAndRun(this.buildcommand, tester);
                    else
                        buildAndRun = new BuildAndRunAnts(this.buildcommand,testSuitename, buildAndRunCommand.runCommand, buildAndRunCommand.buildSuccessString, buildAndRunCommand.runSuccessString);
                }
                else{
                    buildAndRun = this.buildAndRunCommand;

                }



                boolean result = buildAndRun.run();
                list = delta.split(n);


                for (int i = 0; i < list.size(); i++) {
                    temp_delta = list.get(i);
                    List<Statement> statements = temp_delta.c;

                    if(statements.size() == 0)
                        continue;





                    String str = this.cu.toString();

                    boolean resultOfTestRun = isResultOfTestRun(statements);

                    if (resultOfTestRun) {
                        if (intermediateResult != null && (intermediateResult.len() > temp_delta.len()))
                            intermediateResult = temp_delta;
                        n = 2;
                        delta.c.removeAll(temp_delta.c);
                        firstCondition = true;
                        break;
                    }
                    else{
                        // revert back to original java files
                        //firstCondition = false;
                        Revert(str);

                    }
                }

                if (!firstCondition) {
                    Delta deltaInverse;
                    for (int i = 0; i < list.size(); i++) {
                        temp_delta = list.get(i);

                        deltaInverse = temp_delta.minus(list);
                        List<Statement> statements = deltaInverse.c;
                        String str = this.cu.toString();
                        boolean resultOfTestRun = isResultOfTestRun(statements);
                        if (resultOfTestRun) {
                            if (intermediateResult != null && (intermediateResult.len() > temp_delta.len()))
                                intermediateResult = temp_delta;
                            n = Math.max(n - 1, 2);
                            delta = delta.minus(deltaInverse.c);
                            secondCondition = true;
                        }
                        else{
                            // revert back to original java files;
                            Revert(str);
                        }
                    }
                }

                if (!firstCondition && !secondCondition) {
                    if (n >= delta.len()) {
                        return ;
                    }
                    if (n < delta.len()) {
                        n = Math.min(delta.len(), 2 * n);
                    }
                }

            } catch (Exception e) {

                return;
            }
        }
}

    public void ReduceSequentialGreedy() throws ParseException {
        if(delta.len() == 0)
            return;
        for (int i = delta.len()-1;i>= 0; i--){
            List<Statement> statementList = new ArrayList<Statement>();
            statementList.add((Statement) delta.c.get(i));
            String str = this.cu.toString();
            try{

                if(!isResultOfTestRun(statementList)){
                    Revert(str);
                }
                else{
                    Debugger.log("************ greedy reduction successful *******************");
                }

            }
            catch (InterruptedException ex){
                Revert(str);
            }



        }
    }




    private void Revert(String str) {
        FileWriterUtil.write(this.javaMethod.rootFolderName + this.javaMethod.reltiveTestFilePath, str);


        BuildAndRunAbstract buildAndRun;
        if(!Globals.IS_IMMORTALS_RUN){
            if( testSuitename.isEmpty())
                buildAndRun= new BuildAndRun(this.buildcommand, tester);
            else
                buildAndRun = new BuildAndRunAnts(this.buildcommand,testSuitename,this.buildAndRunCommand.runCommand,this.buildAndRunCommand.buildSuccessString,this.buildAndRunCommand.runSuccessString);
        }
        else{
            buildAndRun = this.buildAndRunCommand;
        }



        try {
            boolean result = buildAndRun.run();
        } catch (IOException e) {
            e.printStackTrace();
        }
        FileInputStream in = null;
        String fullPath = this.javaMethod.rootFolderName + this.javaMethod.reltiveTestFilePath;
        try {
            in = new FileInputStream(fullPath);
        }
        catch(FileNotFoundException fex){

        }

        try {
            // parse the file
            this.cu = JavaParser.parse(in);

            Debugger.log(this.cu.toString());
        }
        catch(Exception ex){

        }
    }

    private boolean isResultOfTestRun(List<Statement> statements) throws InterruptedException {
        ClassMethodLineManipulator clm = new ClassMethodLineManipulator(this.cu,this.javaMethod.testMethodName);
        clm.ReduceMethodStatements(this.javaMethod.className, this.javaMethod.testMethodName,statements);
        Debugger.log(clm._newcu);
        Debugger.log(this.cu);
        ;

        Debugger.log("**************");
        Debugger.log(this.javaMethod.rootFolderName + " " + this.javaMethod.reltiveTestFilePath + " " + this.javaMethod.className + " "+ this.javaMethod.testMethodName);
        Debugger.log("**************");

        FileWriterUtil.write(this.javaMethod.rootFolderName + this.javaMethod.reltiveTestFilePath, clm._newcu.toString());

        BuildAndRunAbstract buildAndRun;
        if(!Globals.IS_IMMORTALS_RUN){
            if( testSuitename.isEmpty())
                buildAndRun= new BuildAndRun(this.buildcommand, tester);
            else
                buildAndRun = new BuildAndRunAnts(this.buildcommand,testSuitename,this.buildAndRunCommand.runCommand,this.buildAndRunCommand.buildSuccessString,this.buildAndRunCommand.runSuccessString);

        }
        else{
                buildAndRun = this.buildAndRunCommand;
        }

        try {
            return buildAndRun.run();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        //Command command = new Command();

        //Command.exec("ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml");
        //return tester.runAll();
    }

    private void build(String commandString){
        Command command = new Command();
        command.exec(commandString);

    }

    private List<Statement> applyHeuristicsH3(List<Statement> statements){
        List<Statement> newStatements = new ArrayList<Statement>();
        for (Statement s: statements
             ) {
            try {
                if(FileReaderUtil.Contains(Globals.h3PreComputedStatementFile,s.toString())){
                   newStatements.add(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newStatements;
    }

    private List<Statement> applyHeuristicsH1(List<Statement> statements){
        List<Statement> newStatements = new ArrayList<Statement>();
        for (Statement s: statements
                ) {
            try {
                if(FileReaderUtil.Contains(Globals.h1PreComputedStatetmentFile,s.toString())){
                    newStatements.add(s);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return newStatements;
    }

    private List<Statement> applyHeuristicsH2(List<Statement> statements){
        List<Statement> newStatements = new ArrayList<Statement>();
        for (Statement s: statements
                ) {
            if(isH2Statement(s))
                newStatements.add(s);

        }
        return newStatements;
    }

    private boolean isH2Statement(Statement s){
        return StatementTypeQuery.isIfElseStmt(s) || StatementTypeQuery.isExprStmt(s) || StatementTypeQuery.isReturnStmt(s) || StatementTypeQuery.isBlockStmt(s);
    }

    /**
     * Created by arpit on 6/11/16.
     */



}
