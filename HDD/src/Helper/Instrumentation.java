package Helper;

import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.stmt.*;
import japa.parser.ast.type.*;

import javax.management.ValueExp;
import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;


/**
 * Created by root on 12/14/17.
 */
public class Instrumentation {

    public static MethodDeclaration intrumentationMethod(){
        MethodDeclaration m = new MethodDeclaration();
        m.setName("writeline");
        m.setModifiers(ModifierSet.PUBLIC);
        ;
        List<Parameter> parameters = new LinkedList<Parameter>();
        Parameter p1 = new Parameter();
        Type t = new ReferenceType(new ClassOrInterfaceType("String"));
        p1.setType(t);
        VariableDeclaratorId v = new VariableDeclaratorId("fullFilePath");
        p1.setId(v);

        Parameter p2 = new Parameter();
        Type t2 = new ReferenceType(new ClassOrInterfaceType("String"));
        p2.setType(t2);
        VariableDeclaratorId v2= new VariableDeclaratorId("text");
        p2.setId(v2);

        parameters.add(p1);
        parameters.add(p2);

        m.setParameters(parameters);

        VoidType tReturnType = new VoidType();

        m.setType(tReturnType);

        BlockStmt b = instrumentationMethodBody();
        m.setBody(b);

        return m;
    }

    private static ExpressionStmt getExpressionStmt() {
        MethodCallExpr methodCallExpr = new MethodCallExpr();
        FieldAccessExpr expr = new FieldAccessExpr();
        expr.setField("out");
        ;
        NameExpr nameExpr = new NameExpr();
        nameExpr.setName("System");
        expr.setScope(nameExpr);
        methodCallExpr.setName("println");
        methodCallExpr.setScope(expr);

        StringLiteralExpr stringLiteralExpr = new StringLiteralExpr();
        stringLiteralExpr.setValue(UUID.randomUUID().toString());
        LinkedList<Expression> literalExprs = new LinkedList<>();
        literalExprs.add(stringLiteralExpr);

        methodCallExpr.setArgs(literalExprs);


        System.out.println(methodCallExpr.toString());
        ExpressionStmt exMarkerStmt = new ExpressionStmt();
        exMarkerStmt.setExpression(methodCallExpr);
        return exMarkerStmt;
    }

    private static ExpressionStmt getBufferWriterDeclarationStmt(){
        ExpressionStmt expressionStmt = new ExpressionStmt();
        VariableDeclarationExpr expr = new VariableDeclarationExpr();
        expr.setType(new ReferenceType(new ClassOrInterfaceType("BufferedWriter")));

        VariableDeclarator varD = new VariableDeclarator();
        VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId("output");
        varD.setInit(new NullLiteralExpr());
        varD.setId(variableDeclaratorId);
        List<VariableDeclarator> vars = new LinkedList<>();
        vars.add(varD);
        expr.setVars(vars);

        expressionStmt.setExpression(expr);

        return expressionStmt;


    }

    private static ExpressionStmt getFileInitialization(){

        ObjectCreationExpr objectCreationExpr = new ObjectCreationExpr();
        objectCreationExpr.setType(new ClassOrInterfaceType("File"));
        NameExpr nameExpr = new NameExpr("fullFilePath");
        List<Expression> args = new LinkedList<>();
        args.add(nameExpr);
        objectCreationExpr.setArgs(args);

        ExpressionStmt expressionStmt = new ExpressionStmt();
        VariableDeclarationExpr expr = new VariableDeclarationExpr();
        expr.setType(new ReferenceType(new ClassOrInterfaceType("java.io.File")));

        VariableDeclarator varD = new VariableDeclarator();
        VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId("file");
        varD.setInit(objectCreationExpr);
        varD.setId(variableDeclaratorId);
        List<VariableDeclarator> vars = new LinkedList<>();
        vars.add(varD);
        expr.setVars(vars);

        expressionStmt.setExpression(expr);


        return  expressionStmt;

    }


    private static ExpressionStmt getOutputInitialization(){

        ObjectCreationExpr objectCreationExpr = new ObjectCreationExpr();
        objectCreationExpr.setType(new ClassOrInterfaceType("BufferedWriter"));
        NameExpr nameExpr = new NameExpr("fileWriter");
        List<Expression> args = new LinkedList<>();
        args.add(nameExpr);
        objectCreationExpr.setArgs(args);

        ExpressionStmt expressionStmt = new ExpressionStmt();
        VariableDeclarationExpr expr = new VariableDeclarationExpr();
        expr.setType(new ReferenceType(new ClassOrInterfaceType("BufferedWriter")));

        VariableDeclarator varD = new VariableDeclarator();
        VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId("output");
        varD.setInit(objectCreationExpr);
        varD.setId(variableDeclaratorId);
        List<VariableDeclarator> vars = new LinkedList<>();
        vars.add(varD);
        expr.setVars(vars);

        expressionStmt.setExpression(expr);


        return  expressionStmt;

    }

    private static ExpressionStmt getFileWriteInitialization(){

        ObjectCreationExpr objectCreationExpr = new ObjectCreationExpr();
        objectCreationExpr.setType(new ClassOrInterfaceType("FileWriter"));
        NameExpr nameExpr = new NameExpr("file");
        BooleanLiteralExpr trueExpr = new BooleanLiteralExpr(true);
        List<Expression> args = new LinkedList<>();
        args.add(nameExpr);
        args.add(trueExpr);
        objectCreationExpr.setArgs(args);

        ExpressionStmt expressionStmt = new ExpressionStmt();
        VariableDeclarationExpr expr = new VariableDeclarationExpr();
        expr.setType(new ReferenceType(new ClassOrInterfaceType("FileWriter")));

        VariableDeclarator varD = new VariableDeclarator();
        VariableDeclaratorId variableDeclaratorId = new VariableDeclaratorId("fileWriter");
        varD.setInit(objectCreationExpr);
        varD.setId(variableDeclaratorId);
        List<VariableDeclarator> vars = new LinkedList<>();
        vars.add(varD);
        expr.setVars(vars);

        expressionStmt.setExpression(expr);


        return  expressionStmt;

    }

    public static ExpressionStmt outputAppendText(){
        NameExpr nameExpr = new NameExpr("output");
        NameExpr args1 = new NameExpr("text");
        LinkedList<Expression> args = new LinkedList<Expression>();
        args.add((Expression)args1);
        MethodCallExpr methodCallExpr = new MethodCallExpr();
        methodCallExpr.setName("append");
        methodCallExpr.setScope(nameExpr);
        methodCallExpr.setArgs(args);

        ExpressionStmt expressionStmt = new ExpressionStmt();
        expressionStmt.setExpression(methodCallExpr);
        return expressionStmt;



    }

    public static ExpressionStmt outputNewLine(){
        NameExpr nameExpr = new NameExpr("output");


        MethodCallExpr methodCallExpr = new MethodCallExpr();
        methodCallExpr.setName("newLine");
        methodCallExpr.setScope(nameExpr);


        ExpressionStmt expressionStmt = new ExpressionStmt();
        expressionStmt.setExpression(methodCallExpr);
        return expressionStmt;
    }

    public static ExpressionStmt ePrintStackTrace(){
        NameExpr nameExpr = new NameExpr("e");

        MethodCallExpr methodCallExpr = new MethodCallExpr();
        methodCallExpr.setName("printStackTrace");
        methodCallExpr.setScope(nameExpr);


        ExpressionStmt expressionStmt = new ExpressionStmt();
        expressionStmt.setExpression(methodCallExpr);
        return expressionStmt;
    }

    public static ExpressionStmt outputClose(){
        NameExpr nameExpr = new NameExpr("output");

        MethodCallExpr methodCallExpr = new MethodCallExpr();
        methodCallExpr.setName("close");
        methodCallExpr.setScope(nameExpr);


        ExpressionStmt expressionStmt = new ExpressionStmt();
        expressionStmt.setExpression(methodCallExpr);
        return expressionStmt;
    }


    public static BlockStmt catchBlock(){
        BlockStmt blockStmt = new BlockStmt();
        List<Statement> statementList = new LinkedList<>();
        statementList.add(ePrintStackTrace());
        blockStmt.setStmts(statementList);
        return blockStmt;
    }

    public static BlockStmt finallyBlock(){
        BlockStmt blockStmt = new BlockStmt();

        // if condition
        BinaryExpr binOpExpr = new BinaryExpr();
        binOpExpr.setOperator(BinaryExpr.Operator.notEquals);
        binOpExpr.setLeft((Expression)(new NameExpr("output")));
        binOpExpr.setRight((Expression) new NullLiteralExpr());

        // then block
        BlockStmt thenBlock = new BlockStmt();
        List<Statement> statementList = new LinkedList<>();

        // internal try catch
        TryStmt tryStmt = new TryStmt();
        BlockStmt tryBlock = new BlockStmt();
        List<Statement> tryBlockStatements = new LinkedList<>();
        tryBlockStatements.add(outputClose());
        tryBlock.setStmts(tryBlockStatements);
        tryStmt.setTryBlock(tryBlock);
        CatchClause catchClause = new CatchClause();
        ClassOrInterfaceType c = new ClassOrInterfaceType("IOException");
        Parameter p =  new Parameter();
        p.setType(c);
        p.setId(new VariableDeclaratorId("e"));
        catchClause.setExcept(p);
        BlockStmt catchInternalBlock = new BlockStmt();
        List<Statement> list = new LinkedList<>();
        list.add(ePrintStackTrace());
        catchInternalBlock.setStmts(list);
        catchClause.setCatchBlock(catchInternalBlock);
        tryStmt.setCatchs((new LinkedList<CatchClause>()));
        tryStmt.getCatchs().add(catchClause);

        thenBlock.setStmts(new LinkedList<Statement>());
        thenBlock.getStmts().add(tryStmt);

        IfStmt ifStmt = new IfStmt();
        ifStmt.setCondition(binOpExpr);
        ifStmt.setThenStmt(thenBlock);


        blockStmt.setStmts(new LinkedList<Statement>());
        blockStmt.getStmts().add(ifStmt);






        // else block is null



        return blockStmt;

    }


    public static BlockStmt instrumentationMethodBody(){
        BlockStmt blockStmt = new BlockStmt();
        List<Statement> statementList = new LinkedList<>();

        blockStmt.setStmts(statementList);
        //blockStmt.getStmts().add(getBufferWriterDeclarationStmt());
        blockStmt.getStmts().add(getFileInitialization());
        blockStmt.getStmts().add(getFileWriteInitialization());
        blockStmt.getStmts().add(getOutputInitialization());
        blockStmt.getStmts().add(outputAppendText());
        blockStmt.getStmts().add(outputNewLine());
        blockStmt.getStmts().add(outputClose());

        TryStmt tryStmt = new TryStmt();
        tryStmt.setTryBlock(blockStmt);;

        CatchClause catchClause = new CatchClause();
        Parameter parameter = new Parameter();
        ClassOrInterfaceType c = new ClassOrInterfaceType("IOException");
        parameter.setType(new ReferenceType(c));
        parameter.setId(new VariableDeclaratorId("e"));
        catchClause.setExcept(parameter);
        catchClause.setCatchBlock(catchBlock());
        List<CatchClause> catchClauseList = new LinkedList<CatchClause>();
        catchClauseList.add(catchClause);
        tryStmt.setCatchs(catchClauseList);
        //BlockStmt blockStmt1 = finallyBlock();
        //tryStmt.setFinallyBlock(blockStmt1);

        //statementList.add(tryStmt);
        //blockStmt.setStmts(statementList);
        //blockStmt.getStmts().add(0,tryStmt);
        //blockStmt.getStmts().add(tryStmt);



        List<Statement> methodStmts = new LinkedList<>();
        methodStmts.add(tryStmt);
        BlockStmt methodBlock = new BlockStmt();
        methodBlock.setStmts(methodStmts);


        return  methodBlock;

    }

    public static void main(String[] args){
        MethodDeclaration m = intrumentationMethod();

    }
}

