package ASTManipulation; /**
 * Created by arpit on 5/15/16.
 */


import java.io.*;
import java.util.List;

import japa.parser.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.*;
import japa.parser.ast.body.MethodDeclaration;

import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.ModifierVisitorAdapter;
import japa.parser.ast.visitor.VoidVisitorAdapter;
import java.lang.reflect.Type;


public  class ParserExample  {
    public static void abcd(){
        FileInputStream in = null;
        try {
            java.lang.String current = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:"+current);
        }catch(Exception ex2){

        }

        try {
            in = new FileInputStream("/home/ubuntu/immortals/trunk/client/ATAKLite/src/com/bbn/ataklite/MainActivity.java");
        }
        catch(FileNotFoundException fex){

            }
        CompilationUnit cu = null;
        try {
            // parse the file
            cu = JavaParser.parse(in);
            System.out.println(cu.toString());
            new MethodVisitor().visit(cu,null);
        }
        catch(ParseException pex){

        }
        finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // prints the resulting compilation unit to default system output
        System.out.println(cu.toString());
        ModifierVisitorAdapter v = new MyModifiedVisitor();
        Node visit = v.visit(cu, null);
        System.out.println("*****************************");


        ClassMethodLineManipulator cmlc = new ClassMethodLineManipulator(cu,"add");
        /*ArrayList<Statement> stmts = cmlc.GetExpressioinStatements("add");
        for (Statement s : stmts){
            System.out.println(s.toString());

            Statement replacementStatement = new EmptyStmt(s.getBeginLine(),s.getBeginColumn(),s.getEndLine(),s.getEndColumn());
            System.out.println("*** " +replacementStatement.toString());
        }*/
        //cmlc.ReduceMethodStatements("add");
        //System.out.println("********************** PRINTING REDUCED class *******************************");
        //System.out.println(cmlc._newcu.toString());
        //WriteFile(cmlc._newcu,"/home/arpit/research/untitled/src/test2.java".toString());

        System.out.println("********************** CALLING GetNthLevelStatementsFromMethodName *******************************");
        //cmlc.GetNthLevelStatementsFromMethodName("add", 3);
        System.out.println(cmlc.IsHighestDepthLevel("add", 2));


        List<Statement> nthLevelStmts =  cmlc.GetNthLevelStatementsFromMethodName("add",2);
        List<Statement> nMinusOnethLevelStmts = cmlc.GetNthLevelStatementsFromMethodName("add",1);
        cmlc.ReduceMethodStatements("ASTManipulation.test", "add", nthLevelStmts);




    }

    private static void WriteFile(CompilationUnit cu,  java.lang.String filename){

        BufferedWriter output = null;
        try {
            File file = new File(filename);
            output = new BufferedWriter(new FileWriter(file));
            output.write(cu.toString());
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static class MethodVisitor extends VoidVisitorAdapter {

        @Override
        public void visit(MethodDeclaration n, Object arg) {
            // here you can access the attributes of the method.
            // this method will be called for all methods in this
            // CompilationUnit, including inner class methods
            System.out.println(n.getName());
        }
    }

    private static class MyModifiedVisitor extends ModifierVisitorAdapter{

    }



}
