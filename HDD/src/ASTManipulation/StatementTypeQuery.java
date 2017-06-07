package ASTManipulation;

import japa.parser.ast.stmt.Statement;

/**
 * Created by arpit on 9/1/16.
 */
public class StatementTypeQuery {
    public static StatementType FindType(Statement s){
        StatementType statementType;
        switch (s.getClass().getSimpleName()){
           case "IfStmt":
               statementType =  StatementType.IfStmt;
                break;
           case "WhileStmt":
                statementType = StatementType.WhileStmt;
                break;
           case "ReturnStmt":
                statementType = StatementType.ReturnStmt;
               break;
           case "ForStmt":
                statementType = StatementType.ForStmt;
                break;
           case "ExpressionStmt":
                statementType = StatementType.ExprStmt;
                break;
            case "SwitchStmt":
                statementType = StatementType.SwitchStmt;
                break;
            case "BreakStmt":
                statementType = StatementType.BreakStmt;
                break;
            case "ForeachStmt":
                statementType = StatementType.ForeachStmt;
                break;
            case "BlockStmt":
                statementType = StatementType.BlockStmt;
                break;
            case "ThrowStmt":
                statementType = StatementType.ThrowStmt;
                break;
            case "TryStmt":
                statementType = StatementType.TryStmt;
                break;
            case "ContinueStmt":
                statementType = StatementType.ContnueStmt;
                break;
            case "SynchronizedStmt":
                statementType = StatementType.SynchronizedStmt;
                break;
           default:
                statementType = StatementType.Unknown;
                break;


       }
        return statementType;
    }

    public static boolean isExprStmt(Statement s){
        return FindType(s) == StatementType.ExprStmt;
    }
    public static boolean isIfElseStmt(Statement s){
        return FindType(s) == StatementType.IfStmt;
    }
    public static boolean isSwitchStmt(Statement s){
        return FindType(s) == StatementType.SwitchStmt;
    }
    public static boolean isBreakStmt(Statement s){
        return FindType(s) == StatementType.BreakStmt;
    }
    public static boolean isSwitchEntryStmt(Statement s){
        return FindType(s) == StatementType.SwitchEntryStmt;
    }

    public static boolean isSForStmt(Statement s){
        return FindType(s) == StatementType.ForStmt;
    }
    public static boolean isForEachStmt(Statement s){return FindType(s) == StatementType.ForeachStmt;}
    public static boolean isBlockStmt(Statement s){return FindType(s) == StatementType.BlockStmt;}
    public static boolean isThrowStmt(Statement s){return FindType(s) == StatementType.ThrowStmt;}
    public static boolean isTryStmt(Statement s){return FindType(s) == StatementType.TryStmt;}
    public static boolean isContinueStmt(Statement s){return FindType(s) == StatementType.ContnueStmt;}
    public static boolean isSynchronizedStmt(Statement s){return FindType(s) == StatementType.SynchronizedStmt;}
    public static boolean isWhileStmt(Statement s){return FindType(s) == StatementType.WhileStmt;}
}
