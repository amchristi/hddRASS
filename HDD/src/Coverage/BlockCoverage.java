package Coverage;

import ASTManipulation.EmptyStatementChecker;
import ASTManipulation.ExpressionTypeQuery;
import ASTManipulation.StatementType;
import ASTManipulation.StatementTypeQuery;
import Helper.Globals;
import japa.parser.ast.expr.Expression;
import japa.parser.ast.expr.MethodCallExpr;
import japa.parser.ast.expr.StringLiteralExpr;
import japa.parser.ast.stmt.*;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static java.lang.System.exit;

/**
 * Created by root on 12/19/17.
 */
public class BlockCoverage {

    public BlockStmt blockStmt;
    public List<Statement> coveredStatements;
    public List<Statement> uncoveredStatements;
    Set<UUID> uuidSet;

    public BlockCoverage(BlockStmt blockStmt, Set<UUID> uuidSet){
        this.blockStmt = blockStmt;
        this.uuidSet = uuidSet;
        this.uncoveredStatements = new LinkedList<Statement>();

    }

    public List<Statement>  measaure(){

        List<Statement> coveredStatementList = new LinkedList<>();
        if(blockStmt == null || EmptyStatementChecker.isEmptyStatement(blockStmt)){
            return coveredStatementList;
        }
        List<Statement> statementList = blockStmt.getStmts();

        boolean wasLastStatementWasAnnotatedStatement = false;
        Statement lastStatement = null;
        for (Statement s : statementList
             ) {

            if(isAnnotationStatement(s) ){
                wasLastStatementWasAnnotatedStatement = true;
                lastStatement = s;
            }
            else{
                if(wasLastStatementWasAnnotatedStatement){
                    if(!isStatementCovered(lastStatement))
                        continue;

                    if(StatementTypeQuery.isBlockStmt(s)){
                        coveredStatementList.addAll(measure((BlockStmt)s));
                    }
                    if(StatementTypeQuery.isExprStmt(s)){
                        //coveredStatementList.add(s);
                    }
                    if(StatementTypeQuery.isIfElseStmt(s)){
                        coveredStatementList.addAll(measure((IfStmt)s));
                    }
                    if(StatementTypeQuery.isTryStmt(s)){
                        coveredStatementList.addAll(measure((TryStmt)s));
                    }
                    if(StatementTypeQuery.isSForStmt(s)){
                        coveredStatementList.addAll(measure((ForStmt)s));
                    }
                    if(StatementTypeQuery.isForEachStmt(s)){
                        coveredStatementList.addAll(measure((ForeachStmt)s));
                    }
                    if(StatementTypeQuery.isWhileStmt(s)){
                        coveredStatementList.addAll(measure((WhileStmt)s));
                    }
                    if(StatementTypeQuery.isSwitchStmt(s)){
                        coveredStatementList.addAll(measure((SwitchStmt)s));
                    }
                    if(StatementTypeQuery.isReturnStmt(s)){

                    }
                    if(StatementTypeQuery.isBreakStmt(s)){

                    }
                    if(StatementTypeQuery.isSynchronizedStmt(s)){
                        coveredStatementList.addAll(measure((SynchronizedStmt)s));
                    }
                    if(StatementTypeQuery.isContinueStmt(s)){

                    }
                    if(StatementTypeQuery.isThrowStmt(s)){

                    }
                    if(StatementTypeQuery.FindType(s) == StatementType.Unknown){
                        System.out.println("!!!!!!!!!!!!!!!!!!! UNKNOWN STATEMENT TYPE ENCOUNTERED. FIX PROGRAM.  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        exit(-100);
                    }
                    coveredStatementList.add(s);
                    wasLastStatementWasAnnotatedStatement = false;

                }

            }

        }

        for(Statement s : statementList){
            for(Statement s2: coveredStatementList){
                if(!s.toString().equals(s2.toString())){
                    uncoveredStatements.add(s);
                }
            }

        }
        System.out.println(coveredStatementList);
        return coveredStatementList;
    }





    private List<Statement> measure(BlockStmt blockStatement){
        List<Statement> statementList = blockStatement.getStmts();
        List<Statement> coveredStatementList = new LinkedList<Statement>();
        boolean wasLastStatementWasAnnotatedStatement = false;
        Statement lastStatement = null;
        if(statementList == null){
            return coveredStatementList;
        }
        for (Statement s : statementList
                ) {

            if(isAnnotationStatement(s) ){
                wasLastStatementWasAnnotatedStatement = true;
                lastStatement = s;
            }
            else{
                if(wasLastStatementWasAnnotatedStatement){
                    if(!isStatementCovered(lastStatement))
                        continue;

                    if(StatementTypeQuery.isBlockStmt(s)){
                        coveredStatementList.addAll(measure((BlockStmt)s));
                    }
                    if(StatementTypeQuery.isExprStmt(s)){
                        //coveredStatementList.add(s);
                    }
                    if(StatementTypeQuery.isIfElseStmt(s)){
                        coveredStatementList.addAll(measure((IfStmt)s));
                    }
                    if(StatementTypeQuery.isTryStmt(s)){
                        coveredStatementList.addAll(measure((TryStmt)s));
                    }
                    if(StatementTypeQuery.isSForStmt(s)){
                        coveredStatementList.addAll(measure((ForStmt)s));
                    }
                    if(StatementTypeQuery.isForEachStmt(s)){
                        coveredStatementList.addAll(measure((ForeachStmt)s));
                    }
                    if(StatementTypeQuery.isWhileStmt(s)){
                        coveredStatementList.addAll(measure((WhileStmt)s));
                    }
                    if(StatementTypeQuery.isSwitchStmt(s)){
                        coveredStatementList.addAll(measure((SwitchStmt)s));
                    }
                    if(StatementTypeQuery.isReturnStmt(s)){

                    }
                    if(StatementTypeQuery.isBreakStmt(s)){

                    }
                    if(StatementTypeQuery.isSynchronizedStmt(s)){
                        coveredStatementList.addAll(measure((SynchronizedStmt)s));
                    }
                    if(StatementTypeQuery.isContinueStmt(s)){

                    }
                    if(StatementTypeQuery.FindType(s) == StatementType.Unknown){
                        System.out.println("!!!!!!!!!!!!!!!!!!! UNKNOWN STATEMENT TYPE ENCOUNTERED. FIX PROGRAM.  !!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                        exit(-100);
                    }
                    coveredStatementList.add(s);
                    wasLastStatementWasAnnotatedStatement = false;

                }

            }

        }

        for(Statement s : statementList){
            for(Statement s2: coveredStatementList){
                if(!s.toString().equals(s2.toString())){
                    uncoveredStatements.add(s);
                }
            }

        }

        return coveredStatementList;
    }

    private List<Statement> measure(IfStmt ifStmt){
        List<Statement> statementList = new LinkedList<>();

        Statement s = ifStmt.getThenStmt();
        if (s != null ){
            if(StatementTypeQuery.isBlockStmt(s)){
                statementList.addAll(measure((BlockStmt)s));

            }
            if(StatementTypeQuery.isIfElseStmt(s)){
                statementList.addAll(measure((IfStmt) s));
            }


        }



        Statement s2 = ifStmt.getElseStmt();
        if(s2 != null)
        {
            if(StatementTypeQuery.isBlockStmt(s2)){
                statementList.addAll(measure((BlockStmt)s2));
            }
            if(StatementTypeQuery.isIfElseStmt(s2)){
                statementList.addAll(measure((IfStmt)s2));
            }
        }



        return statementList;
    }

    private List<Statement> measure(ForStmt forStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        Statement s = forStmt.getBody();
        if(s != null){
            if(StatementTypeQuery.isBlockStmt(s)){
                coveredStatementList.addAll(measure((BlockStmt)s));
            }
            if(StatementTypeQuery.isIfElseStmt(s)){
                coveredStatementList.addAll(measure((IfStmt) s));
            }
        }

        return coveredStatementList;
    }

    private List<Statement> measure(ForeachStmt forEachStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        Statement s = forEachStmt.getBody();
        if(s != null){
            if(StatementTypeQuery.isBlockStmt(s)){
                coveredStatementList.addAll(measure((BlockStmt)s));
            }
            if(StatementTypeQuery.isIfElseStmt(s)){
                coveredStatementList.addAll(measure((IfStmt) s));
            }
        }

        return coveredStatementList;
    }

    private List<Statement> measure(WhileStmt whileStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        Statement s = whileStmt.getBody();
        if(s != null){
            if(StatementTypeQuery.isBlockStmt(s)){
                coveredStatementList.addAll(measure((BlockStmt)s));
            }
            if(StatementTypeQuery.isIfElseStmt(s)){
                coveredStatementList.addAll(measure((IfStmt) s));
            }
        }

        return coveredStatementList;
    }

    private List<Statement> measure(TryStmt tryStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        BlockStmt b = tryStmt.getTryBlock();
        if(b != null){
            coveredStatementList.addAll(measure(b));
        }
        BlockStmt bFInally = tryStmt.getFinallyBlock();
        if(bFInally != null){
            coveredStatementList.addAll(measure(bFInally));
        }
        List<CatchClause> catchClauses = tryStmt.getCatchs();
        if(catchClauses != null ){
            for (CatchClause c: catchClauses
                    ) {
                coveredStatementList.addAll(measure(c));
            }
        }

        return  coveredStatementList;

    }

    private List<Statement> measure(CatchClause catchClause){
        List<Statement> coveredStatementList = new LinkedList<>();
        BlockStmt b = catchClause.getCatchBlock();
        if(b != null){
            coveredStatementList.addAll(measure(b));
        }
        return  coveredStatementList;
    }

    private List<Statement> measure(SwitchStmt switchStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        for (SwitchEntryStmt sw: switchStmt.getEntries()
             ) {
            coveredStatementList.addAll(measure(sw));

        }
        return coveredStatementList;
    }

    private List<Statement> measure(SwitchEntryStmt switchEntryStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        List<Statement> statementList = switchEntryStmt.getStmts();
        boolean wasLastStatementWasAnnotatedStatement = false;
        for (Statement s : statementList
                ) {

            if(isAnnotationStatement(s)){
                wasLastStatementWasAnnotatedStatement = true;
            }
            else{
                if(wasLastStatementWasAnnotatedStatement){

                    if(StatementTypeQuery.isBlockStmt(s)){
                        coveredStatementList.addAll(measure((BlockStmt)s));
                    }
                    if(StatementTypeQuery.isExprStmt(s)){
                        //coveredStatementList.add(s);
                    }
                    if(StatementTypeQuery.isIfElseStmt(s)){
                        coveredStatementList.addAll(measure((IfStmt)s));
                    }
                    if(StatementTypeQuery.isTryStmt(s)){
                        coveredStatementList.addAll(measure((TryStmt)s));
                    }
                    if(StatementTypeQuery.isSForStmt(s)){
                        coveredStatementList.addAll(measure((ForStmt)s));
                    }
                    if(StatementTypeQuery.isForEachStmt(s)){
                        coveredStatementList.addAll(measure((ForeachStmt)s));
                    }
                    if(StatementTypeQuery.isWhileStmt(s)){
                        coveredStatementList.addAll(measure((WhileStmt)s));
                    }

                    coveredStatementList.add(s);
                    wasLastStatementWasAnnotatedStatement = false;

                }

            }

        }

        return coveredStatementList;


    }

    private List<Statement> measure(SynchronizedStmt synchStmt){
        List<Statement> coveredStatementList = new LinkedList<>();
        BlockStmt b = synchStmt.getBlock();
        if(b != null){
            coveredStatementList.addAll(measure(b));
        }

        return coveredStatementList;
    }

    private boolean isAnnotationStatement(Statement s){
        if(!StatementTypeQuery.isExprStmt(s)){
            return false;
        }
        ExpressionStmt expressionStmt = (ExpressionStmt)s;
        if(!ExpressionTypeQuery.isMethodCall(expressionStmt.getExpression()))
            return false;

        MethodCallExpr methodCallExpr = (MethodCallExpr) expressionStmt.getExpression();
        if(!methodCallExpr.getName().equals("writeline"))
            return false;
        if(methodCallExpr.getArgs().size() != 2)
            return false;
        Expression e = (Expression)(methodCallExpr.getArgs().get(1));
        if(!ExpressionTypeQuery.isStringLiteralExpression(e))
            return false;


        try {
            UUID.fromString(((StringLiteralExpr)e).getValue());
            return true;
        } catch (Exception ex) {
            return false;
        }


    }

    private boolean isStatementCovered(Statement s){
        if(!StatementTypeQuery.isExprStmt(s)){
            return false;
        }
        ExpressionStmt expressionStmt = (ExpressionStmt)s;
        if(!ExpressionTypeQuery.isMethodCall(expressionStmt.getExpression()))
            return false;

        MethodCallExpr methodCallExpr = (MethodCallExpr) expressionStmt.getExpression();
        if(!methodCallExpr.getName().equals("writeline"))
            return false;
        if(methodCallExpr.getArgs().size() != 2)
            return false;
        Expression e = (Expression)(methodCallExpr.getArgs().get(1));
        if(!ExpressionTypeQuery.isStringLiteralExpression(e))
            return false;


        try {
            UUID uuid = UUID.fromString(((StringLiteralExpr)e).getValue());
            return this.uuidSet.contains(uuid);

        } catch (Exception ex) {
            return false;
        }
    }


}
