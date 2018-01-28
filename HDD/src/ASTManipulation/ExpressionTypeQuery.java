package ASTManipulation;

import japa.parser.ast.expr.Expression;

/**
 * Created by root on 12/19/17.
 */
public class ExpressionTypeQuery {
    public static ExpressionType FindType(Expression e){
        ExpressionType expressionType;
        switch (e.getClass().getSimpleName()){
            case "MethodDeclaration":
                expressionType = ExpressionType.MethodDeclaration;
                break;
            case "MethodCallExpr":
                expressionType = ExpressionType.MethodCallExpr;
                break;
            case "StringLiteralExpr":
                expressionType = ExpressionType.StringLiteralExpr;
                break;

            default:
                expressionType = ExpressionType.Unknown;
                break;
        }
        return  expressionType;
    }

    public static boolean isMethodDeclaration(Expression e){
        return FindType(e) == ExpressionType.MethodDeclaration;

    }

    public static boolean isMethodCall(Expression e){
        return FindType(e) == ExpressionType.MethodCallExpr;

    }

    public static boolean isStringLiteralExpression(Expression e){
        return FindType(e) == ExpressionType.StringLiteralExpr;

    }
}
