package ASTManipulation;

import japa.parser.ast.stmt.Statement;

/**
 * Created by root on 1/8/17.
 */
public class ExtendedStatement {
    public Statement statement;
    public int level;

    public ExtendedStatement(Statement statement, int level){
        this.statement = statement;
        this.level = level;
    }



}
