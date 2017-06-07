package Helper;

import japa.parser.JavaParser;
import japa.parser.ParseException;
import japa.parser.ast.CompilationUnit;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 * Created by root on 1/1/17.
 */
public  class CompilationUnitHelper {

    public static CompilationUnit CreateCompilationUnit(CompilationUnit cu, String fullClassPath) {


        FileInputStream in = null;
        String fullPath = fullClassPath;
        try {
            String current = new java.io.File(".").getCanonicalPath();
            Debugger.log("Current dir:" + current);
        } catch (Exception ex2) {

        }

        try {
            in = new FileInputStream(fullPath);
        } catch (FileNotFoundException fex) {
            Debugger.log("file not found");

        }

        try {
            // parse the file
            cu = JavaParser.parse(in);

        } catch (ParseException pex) {
            pex.printStackTrace();

        } catch (Exception ex) {
            Debugger.log(ex);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return cu;
    }

    public static CompilationUnit CreateCompilationUnit(String fullClassPath){
        FileInputStream in = null;
        String fullPath = fullClassPath;
        CompilationUnit cu = null;
        try {
            String current = new java.io.File(".").getCanonicalPath();
            Debugger.log("Current dir:" + current);
        } catch (Exception ex2) {
            return null;

        }

        try {
            in = new FileInputStream(fullPath);
        } catch (FileNotFoundException fex) {
            Debugger.log("file not found");
            return null;

        }

        try {
            // parse the file
            cu = JavaParser.parse(in);

        } catch (ParseException pex) {
            pex.printStackTrace();

        } catch (Exception ex) {
            Debugger.log(ex);
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return cu;
    }
}