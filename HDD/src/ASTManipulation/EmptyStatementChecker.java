package ASTManipulation;

import japa.parser.ast.stmt.Statement;

/**
 * Created by arpit on 8/31/16.
 */
public  class EmptyStatementChecker {

    static String EMPTY_NEWLINE = "{\n}";
    public static boolean isEmptyStatement(Statement s){
        if(s == null)
            return true;
        if(s.toString().equals(EMPTY_NEWLINE))
            return true;
        return false;
    }

}
