package ASTManipulation;

import Helper.Debugger;
import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.Parameter;
import japa.parser.ast.body.TypeDeclaration;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/17/16.
 * Have to create this as somehow cannot read classes directly from APK for IMMORTALS
 */
public class ClassIterator {
    private String fullClassPath; // .java file
    private CompilationUnit cu;

    public ClassIterator(String fullClassPath){
        this.fullClassPath = fullClassPath;
        CreateCompliationUnit();
    }

    private void CreateCompliationUnit(){
        FileInputStream in = null;
        String fullPath = fullClassPath;
        try {
            String current = new java.io.File(".").getCanonicalPath();
            Debugger.log("Current dir:"+current);
        }catch(Exception ex2){

        }

        try {
            fullPath = fullClassPath;
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
            Debugger.log(pex);
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

    public List<String> GetAllMethodsName(){
        List<String> result = new ArrayList<String>();

        cu.getTypes().stream().forEach(p ->
                {
                    ((TypeDeclaration)p).getMembers().stream().forEach(q ->
                            {
                                if(((BodyDeclaration)q).getClass().getSimpleName().toString().equals("MethodDeclaration")){
                                    MethodDeclaration m = (MethodDeclaration)q;
                                    result.add(m.getName());

                                }
                            }
                    );
                }

        );

        return result ;


    }

    public List<MethodDeclaration> GetAllMethods(){
        List<MethodDeclaration> result = new ArrayList<MethodDeclaration>();
        cu.getTypes().stream().forEach(p ->
                {
                    ((TypeDeclaration)p).getMembers().stream().forEach(q ->
                            {
                                if(((BodyDeclaration)q).getClass().getSimpleName().toString().equals("MethodDeclaration")){
                                    MethodDeclaration m = (MethodDeclaration)q;
                                    result.add(m);

                                }
                            }
                    );
                }

        );

        return result ;
    }

    public boolean ComapreMethods(MethodDeclaration m1, MethodDeclaration m2){

        if(!m1.getName().toString().equals(m2.getName().toString()))
            return false;
        List<Parameter> p1 = m1.getParameters();
        List<Parameter> p2 = m2.getParameters();
        if(p1 == null && p2 == null)
            return true;
        if(p1 == null && p2 != null){
            return false;
        }
        if(p1 != null && p2 == null){
            return false;
        }
        if(p1.size() != p2.size())
            return false;

        for(int i = 0;i< p1.size();i++){
            if(!p1.get(i).getType().toString().equals(p2.get(i).getType().toString()))
                return false;
        }
        return true;

    }



    public CompilationUnit GetCompilationUnit(){
        return this.cu;
    }

    public static void main(String[] args){
        //ClassIterator classIterator = new ClassIterator("/home/ubuntu/immortals/trunk/applications/client/ATAKLite/src/com/bbn/ataklite/MainActivity.java");
        //classIterator.GetAllMethodsName().stream().forEach(p -> Debugger.log(p));


        ClassIterator classIterator = new ClassIterator("/home/ubuntu/research/jena/jena-core/src/main/java/org/apache/jena/util/LocationMapper.java");

        classIterator.GetAllMethodsName().stream().forEach(p -> Debugger.log(p));

    }


}
