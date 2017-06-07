package HDD;
import ASTManipulation.*;
import Helper.Command;
import Helper.FileOperationUtil;
import Helper.FileWriterUtil;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.stmt.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
/**
 * Created by arpit on 6/5/16.
 */
public class DD {
    JavaTestCase javaTestCase;
    JavaTestCase reducedJavaTestCase;
    Delta delta;
    ITester tester;
    CompilationUnit cu;

    public DD(JavaTestCase testcase, ITester tester){
        this.javaTestCase = testcase;
        this.tester = tester;
        delta = new Delta(new ArrayList<Statement>());
        CreateInitialDelta();

    }

    private void CreateInitialDelta(){
        TreeManipulator tree = new TreeManipulator(javaTestCase.cu,javaTestCase.testMethodName);
        ClassMethodLineManipulator cmlm = new ClassMethodLineManipulator(javaTestCase.cu,javaTestCase.testMethodName);
        List<Statement> statements = cmlm.GetNthLevelStatementsFromMethodName(javaTestCase.testMethodName,tree.GetHeigestDepthLevel());
        delta.c.addAll(statements);
        this.cu = javaTestCase.cu;

    }


    private void ddMin(){
        Delta delta = this.delta;
        int n = 2;
        boolean firstCondition;
        boolean secondCondition;
        Delta temp_delta;
        List<Delta> list;
        Delta intermediateResult = delta;

        ITester tempTester = tester;




    }


    public void ReduceSequentical() throws ParseException {
        Delta delta = this.delta;
        int n = 2;
        boolean firstCondition;
        boolean secondCondition;
        Delta temp_delta;
        List<Delta> list;
        Delta intermediateResult = delta;
        String str;
        ITester tempTester = tester;


        while (true) {
            try {
                firstCondition = false;
                secondCondition = false;
                if (n > delta.len()) {
                    return;
                }
                boolean result = tester.run();
                list = delta.split(n);


                for (int i = 0; i < list.size(); i++) {
                    temp_delta = list.get(i);

                    List<Statement> statements = temp_delta.c;
                    // christia: replace the test call with Tester.run
                    // create a reduced test case
                    // write that test case into test file
                    // create the new jar
                    // run the test with new jar.

                    System.out.println("************* printing this.cu *********************");
                    System.out.println(this.cu.toString());

                    System.out.println("************* end printing this.cu *********************");
                    str = this.cu.toString();

                    ClassMethodLineManipulator clm = new ClassMethodLineManipulator(this.javaTestCase.cu,this.javaTestCase.testMethodName);
                    clm.ReduceMethodStatements(this.javaTestCase.className,this.javaTestCase.testMethodName,statements);
                    System.out.println(clm._newcu.toString());

                    System.out.println("**************");
                    System.out.println(this.javaTestCase.rootFolderName + " " + this.javaTestCase.reltiveTestFilePath + " " );
                    System.out.println("**************");

                    FileWriterUtil.write(this.javaTestCase.rootFolderName + this.javaTestCase.reltiveTestFilePath, clm._newcu.toString());

                    Command command = new Command();
                    //Command.exec("ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml | grep ':error' &> temp.txt");
                    Command.exec(new String[]{"bash","-c","ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml | grep \"error:\" &> temp.txt"});
                    boolean buildOK = FileOperationUtil.isEmptyFile("temp.txt");


                    boolean resultOfTestRun = buildOK? tester.run() : false;

                    if (buildOK && resultOfTestRun) {
                        if (intermediateResult != null && (intermediateResult.len() > temp_delta.len()))
                            intermediateResult = temp_delta;
                        n = 2;
                        delta = temp_delta;
                        firstCondition = true;
                        break;
                    }
                    else{
                        // revert back to original java files
                        //firstCondition = false;
                        FileWriterUtil.write(this.javaTestCase.rootFolderName + this.javaTestCase.reltiveTestFilePath, str);
                        Command.exec(new String[]{"bash","-c","ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml | grep \"error:\" &> temp.txt"});
                        boolean buildOK2 = FileOperationUtil.isEmptyFile("temp.txt");
                        FileInputStream in = null;
                        String fullPath = this.javaTestCase.rootFolderName + this.javaTestCase.reltiveTestFilePath;
                        try {
                            in = new FileInputStream(fullPath);
                        }
                        catch(FileNotFoundException fex){

                        }

                        try {
                            // parse the file
                            this.cu = JavaParser.parse(in);

                            System.out.println(this.cu.toString());
                        }
                        catch(Exception ex){

                        }

                    }
                }

                if (!firstCondition) {
                    Delta deltaInverse;
                    for (int i = 0; i < list.size(); i++) {
                        temp_delta = list.get(i);

                        deltaInverse = temp_delta.minus(list);
                        List<Statement> statements = deltaInverse.c;
                        // christia: replace the test call with Tester.run
                        // create a reduced test case
                        // write that test case into test file
                        // create the new jar
                        // run the test with new jar.
                        ClassMethodLineManipulator clm = new ClassMethodLineManipulator(this.cu,this.javaTestCase.testMethodName);
                        str = this.cu.toString();
                        clm.ReduceMethodStatements(this.javaTestCase.className,this.javaTestCase.testMethodName,statements);
                        System.out.println(clm._newcu);


                        System.out.println("**************");
                        //System.out.println(this.javaMethod.rootFolderName + " " + this.javaMethod.reltiveTestFilePath + " " + this.javaMethod.className + " "+ this.javaMethod.testMethodName);
                        System.out.println("**************");
                        FileWriterUtil.write(this.javaTestCase.rootFolderName + this.javaTestCase.reltiveTestFilePath, clm._newcu.toString());

                        Command command = new Command();
                        Command.exec(new String[]{"bash","-c","ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml | grep \"error:\" &> temp.txt"});
                        boolean buildOK = FileOperationUtil.isEmptyFile("temp.txt");
                        boolean resultOfTestRun = buildOK? tester.run(): false;

                        if (buildOK && resultOfTestRun) {
                            if (intermediateResult != null && (intermediateResult.len() > temp_delta.len()))
                                intermediateResult = temp_delta;
                            n = Math.max(n - 1, 2);
                            delta = deltaInverse;
                            secondCondition = true;
                        }
                        else{
                            // revert back to original java files;
                            FileWriterUtil.write(this.javaTestCase.rootFolderName + this.javaTestCase.reltiveTestFilePath, str);
                            Command.exec(new String[]{"bash","-c","ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml | grep \"error:\" &> temp.txt"});
                            boolean buildOK2 = FileOperationUtil.isEmptyFile("temp.txt");
                            FileInputStream in = null;
                            String fullPath = this.javaTestCase.rootFolderName + this.javaTestCase.reltiveTestFilePath;
                            try {
                                in = new FileInputStream(fullPath);
                            }
                            catch(FileNotFoundException fex){

                            }

                            try {
                                // parse the file
                                this.cu = JavaParser.parse(in);

                                System.out.println(this.cu.toString());
                            }
                            catch(Exception ex){

                            }
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










}
