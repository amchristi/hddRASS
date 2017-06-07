package HDD;
import java.io.*;
import java.util.ArrayList;
import java.util.List;



import japa.parser.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.*;
import japa.parser.ast.body.MethodDeclaration;

import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.NullLiteralExpr;
import japa.parser.ast.stmt.EmptyStmt;
import japa.parser.ast.stmt.ExpressionStmt;
import japa.parser.ast.stmt.Statement;
import japa.parser.ast.visitor.ModifierVisitorAdapter;
import japa.parser.ast.visitor.VoidVisitorAdapter;


/**
 * Created by arpit on 6/11/16.
 */
public class JavaTestCase {
    public String rootFolderName;
    public String reltiveTestFilePath;
    public String testMethodName;
    public String className;
    public CompilationUnit cu;


    public JavaTestCase(String rootFolderName, String reltiveTestFilePath,String testMethodName, String classname){
        this.rootFolderName = rootFolderName;
        this.reltiveTestFilePath = reltiveTestFilePath;
        this.testMethodName = testMethodName;
        this.className = classname;
        CreateCompilationUnit(rootFolderName,reltiveTestFilePath);

    }

    private void CreateCompilationUnit(String rootFolderName, String reltiveTestFilePath){
        FileInputStream in = null;
        String fullPath = rootFolderName + reltiveTestFilePath;
        try {
            java.lang.String current = new java.io.File(".").getCanonicalPath();
            System.out.println("Current dir:"+current);
        }catch(Exception ex2){

        }

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

    }


    JavaTestCase CreateTestCase(Delta<?> delta){
        return null;
    }



}
