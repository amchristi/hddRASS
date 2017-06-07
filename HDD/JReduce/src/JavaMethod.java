import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import japa.parser.*;
import japa.parser.ast.CompilationUnit;

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




}
