import Helper.Command;
import Helper.FileWriterUtil;
import Helper.JarCreaterCommand;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.stmt.Statement;

import ASTManipulation.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
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

    public Reducer(JavaMethod javaMethod, ITester tester){
        this.javaMethod = javaMethod;

        delta = new Delta();
        CreateInitialDelta();
        this.tester = tester;

    }

    private void CreateInitialDelta(){
        TreeManipulator tree = new TreeManipulator(javaMethod.cu,javaMethod.testMethodName);
        ClassMethodLineManipulator cmlm = new ClassMethodLineManipulator(javaMethod.cu,javaMethod.testMethodName);
        List<Statement> statements = cmlm.GetNthLevelStatementsFromMethodName(javaMethod.testMethodName,tree.GetHeigestDepthLevel());
        delta.c.addAll(statements);
        this.cu = javaMethod.cu;


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


        while (true) {
            try {
                firstCondition = false;
                secondCondition = false;
                if (n > delta.len()) {
                    return;
                }
                boolean result = tester.runAll();
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
                    String str = this.cu.toString();

                    ClassMethodLineManipulator clm = new ClassMethodLineManipulator(this.cu,this.javaMethod.testMethodName);
                    clm.ReduceMethodStatements(this.javaMethod.className, this.javaMethod.testMethodName,statements);
                    System.out.println(clm._newcu);
                    System.out.println(this.cu);
                    ;

                    System.out.println("**************");
                    System.out.println(this.javaMethod.rootFolderName + " " + this.javaMethod.reltiveTestFilePath + " " + this.javaMethod.className + " "+ this.javaMethod.testMethodName);
                    System.out.println("**************");

                    FileWriterUtil.write(this.javaMethod.rootFolderName + this.javaMethod.reltiveTestFilePath, clm._newcu.toString());

                    Command command = new Command();
                    Command.exec("ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml");
                    boolean resultOfTestRun = tester.runAll();

                    if (resultOfTestRun) {
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
                        FileWriterUtil.write(this.javaMethod.rootFolderName + this.javaMethod.reltiveTestFilePath, str);
                        Command.exec("ant -buildfile /home/arpit/research/QuicksortApp/quicksortapp.xml");
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
                        ClassMethodLineManipulator clm = new ClassMethodLineManipulator(this.cu,this.javaMethod.testMethodName);
                        clm.ReduceMethodStatements(this.javaMethod.className, this.javaMethod.testMethodName,statements);
                        System.out.println(clm._newcu);


                        System.out.println("**************");
                        System.out.println(this.javaMethod.rootFolderName + " " + this.javaMethod.reltiveTestFilePath + " " + this.javaMethod.className + " "+ this.javaMethod.testMethodName);
                        System.out.println("**************");
                        FileWriterUtil.write(this.javaMethod.rootFolderName + this.javaMethod.reltiveTestFilePath, clm._newcu.toString());

                        Command command = new Command();
                        Command.exec("ant -buildfile /home/arpit/research/QuicksortApp/QuicksortApp.xml");
                        boolean resultOfTestRun = tester.runAll();

                        if (resultOfTestRun) {
                            if (intermediateResult != null && (intermediateResult.len() > temp_delta.len()))
                                intermediateResult = temp_delta;
                            n = Math.max(n - 1, 2);
                            delta = deltaInverse;
                            secondCondition = true;
                        }
                        else{
                            // revert back to original java files;
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

    /**
     * Created by arpit on 6/11/16.
     */



}
