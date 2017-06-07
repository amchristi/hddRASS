package JReduce;

import Helper.Debugger;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Time;

/**
 * Created by arpit on 6/27/16.
 */
public class JavaMethod {

    public String rootFolderName;
    public String reltiveTestFilePath;
    public String testMethodName;
    public String jarFileWithPath;


    public CompilationUnit cu;
    public String className;


    public JavaMethod(String rootFolderName, String reltiveTestFilePath, String testMethodName, String className, String jarFilePath){
        this.rootFolderName = rootFolderName;
        this.reltiveTestFilePath = reltiveTestFilePath;
        this.testMethodName = testMethodName;
        this.className = className;
        this.jarFileWithPath = jarFilePath;
        CreateCompilationUnit(rootFolderName,reltiveTestFilePath);



    }

    private void CreateCompilationUnit(String rootFolderName, String reltiveTestFilePath){
        FileInputStream in = null;
        String fullPath = rootFolderName + reltiveTestFilePath;
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
            this.cu = JavaParser.parse(in);

            Debugger.log(this.cu.toString());

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
        Debugger.log(cu.toString());

    }




}
