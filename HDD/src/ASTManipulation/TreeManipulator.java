package ASTManipulation;

import Helper.Debugger;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.stmt.*;
import sun.reflect.generics.tree.Tree;

import java.util.List;

import static java.lang.System.exit;

/**
 * Created by arpit on 6/11/16.
 */
public class TreeManipulator {
    BlockStmt root;
    int highestDepthLevel;
    CompilationUnit cu;
    String testMethod;

    public TreeManipulator(CompilationUnit cu, String testMethod){
        this.root = root;
        highestDepthLevel = 0;
        this.cu = cu;
        this.testMethod = testMethod;

    }

    public int GetHeigestDepthLevel() {
        for (TypeDeclaration t : cu.getTypes()) {
            for (BodyDeclaration b : t.getMembers()) {
                if (b.getClass().getSimpleName().toString().equals("MethodDeclaration")) {
                    MethodDeclaration m = (MethodDeclaration) b;
                    if (m.getName().toString().equals(testMethod)) {
                        return GetHeighestDepthLevel(m.getBody());

                    }
                }
            }
        }
        return 0;
    }
    public int GetHeighestDepthLevel(BlockStmt block){
        if(block.getStmts() == null)
            return 0;
        if(block.getStmts().size() == 0)
            return 0;
        int[] tempDepthLevels = new int[block.getStmts().size()];
        int i = 0;

        for (Statement s:block.getStmts()) {
            if(s.getClass().getSimpleName().toString().equals("ExpressionStmt")){
                tempDepthLevels[i] = 0;
                i++;
            }
            else if(s.getClass().getSimpleName().toString().equals("ReturnStmt") ){
                tempDepthLevels[i++] = 0;
            }
            else if(StatementTypeQuery.isContinueStmt(s)){
                tempDepthLevels[i++] = 0;
            }
            else if (s.getClass().getSimpleName().toString().equals("ForStmt")){
                ForStmt forstmt = (ForStmt)s;
                BlockStmt blkstmt = (BlockStmt)forstmt.getBody();
                tempDepthLevels[i++] = GetHeighestDepthLevel(blkstmt);

            }
            else if (s.getClass().getSimpleName().toString().equals("WhileStmt")){
                WhileStmt whilestmt = (WhileStmt)s;
                tempDepthLevels[i++] = GetHeighestDepthLevel((BlockStmt)whilestmt.getBody());
            }
            else if (s.getClass().getSimpleName().toString().equals("IfStmt")){
                IfStmt ifStmt = (IfStmt)s;
                tempDepthLevels[i]  = GetHeighestDepthLevel(ifStmt);
                i++;
            }
            else if(StatementTypeQuery.isSwitchStmt(s)){
                tempDepthLevels[i] = GetHeighestDepthLevel((SwitchStmt) s);
                i++;
            }
            else if(StatementTypeQuery.isForEachStmt(s)){
                tempDepthLevels[i] = GetHighestDepthLevel((ForeachStmt)s);
                i++;
            }
            else if(StatementTypeQuery.isThrowStmt(s)){
                tempDepthLevels[i] = 0;
                i++;
            }
            else if(StatementTypeQuery.isTryStmt(s)){
                tempDepthLevels[i] = GetHeighestDepthLevel((TryStmt) s);
                i++;
            }
            else if(StatementTypeQuery.isBreakStmt(s)){
                tempDepthLevels[i++] = 0;
            }
            else if(StatementTypeQuery.isSynchronizedStmt(s)){
                tempDepthLevels[i] =GetHighestDepthLevel((SynchronizedStmt) s);
                i++;
            }
            else{
                Debugger.log(s);
                Debugger.log("*************** NOT IMPLEMENTED ***************************");
                exit(-99);
            }


        }
        return 1 + FindMax(tempDepthLevels);
    }

    private int GetHeighestDepthLevel(SwitchStmt s) {

        Debugger.log("*********** in swich statement");
        List<SwitchEntryStmt> caseStmts =  s.getEntries();
        if(caseStmts == null || caseStmts.size() == 0){
            return 0;

        }
        int max = 0;
        for (SwitchEntryStmt casestmt:  caseStmts ){

            if(casestmt == null || casestmt.getStmts() == null){
                continue;
            }
            int maxInner = 0;
            for (Statement sInner: casestmt.getStmts()) {
                if(StatementTypeQuery.isExprStmt(sInner) || StatementTypeQuery.isBreakStmt(sInner))
                    maxInner = Math.max(maxInner,1);
                else if(StatementTypeQuery.isSForStmt(sInner))
                    maxInner = Math.max(maxInner,1 + GetHeighestDepthLevel((ForStmt) sInner));
                else if(StatementTypeQuery.isIfElseStmt(sInner)){

                     maxInner= Math.max(maxInner,1+GetHeighestDepthLevel((IfStmt)sInner)) ;
                }
                else{
                    Debugger.log("Not implemented : In TreeManipulator.java");
                    exit(-99);
                }

            }

            max = Math.max(max,maxInner);

        }
        return max;
    }

    private int GetHeighestDepthLevel(ForStmt forstmt){
        if(EmptyStatementChecker.isEmptyStatement(forstmt.getBody()))
                return 0;
        BlockStmt blkstmt = (BlockStmt)forstmt.getBody();
        return  GetHeighestDepthLevel(blkstmt);


    }

    private int GetHighestDepthLevel(ForeachStmt foreachStmt){
        if(EmptyStatementChecker.isEmptyStatement(foreachStmt.getBody()))
            return 0;
        BlockStmt blkstmt = (BlockStmt)foreachStmt.getBody();
        return GetHeighestDepthLevel(blkstmt);
    }

    private int GetHighestDepthLevel(SynchronizedStmt syncStmt){
        if(EmptyStatementChecker.isEmptyStatement(syncStmt.getBlock())){
            return 0;
        }
        BlockStmt blkStmt = (BlockStmt)syncStmt.getBlock();
        return GetHeighestDepthLevel(blkStmt);
    }

    private int GetHeighestDepthLevel(IfStmt ifStmt){

        int ifLength;
        int elseLength;
        if(StatementTypeQuery.isExprStmt(ifStmt.getThenStmt()))
            ifLength = 1;
        else
            ifLength = GetHeighestDepthLevel((BlockStmt)ifStmt.getThenStmt());
        if(ifStmt.getElseStmt() != null && StatementTypeQuery.isExprStmt(ifStmt.getElseStmt()))
            elseLength = 1;
        else{
            if(ifStmt.getElseStmt() != null){
                if(StatementTypeQuery.isIfElseStmt(ifStmt.getElseStmt())){
                    elseLength  = 1 + GetHeighestDepthLevel((IfStmt)ifStmt.getElseStmt());
                }
                else{
                    elseLength = GetHeighestDepthLevel((BlockStmt)ifStmt.getElseStmt());
                }

            }
            else{
                elseLength = 0;
            }


        }

        return Math.max(ifLength,elseLength);

    }

    private int GetHeighestDepthLevel(TryStmt tryStmt){
        int tryLength, catchlength,finallylength;
        catchlength  = tryLength = finallylength= 0;
        Debugger.log(tryStmt);
        BlockStmt tryBlock = tryStmt.getTryBlock();

        if(tryBlock != null){
            tryLength = GetHeighestDepthLevel(tryBlock);
        }
        if(tryStmt.getCatchs() != null) {
            if(tryStmt.getCatchs().size() != 0){
                for (CatchClause catchClause:tryStmt.getCatchs()) {
                    catchlength = Integer.max(catchlength,GetHeighestDepthLevel(catchClause.getCatchBlock()));

                }
            }
            else{
                Debugger.log("**************** catch is empty ***************************");
            }

        }
        else{
            Debugger.log("**************** catch is null ***************************");
        }

        BlockStmt finallyBlock = tryStmt.getFinallyBlock();
        if(finallyBlock != null){
            finallylength = GetHeighestDepthLevel(finallyBlock);
        }

        return  Integer.max(Integer.max(tryLength,catchlength ),finallylength);
    }

    private boolean ContainsStatement(BlockStmt blkStmt, List<Statement> childStatements){
        boolean bIf = false;
        for (Statement s: blkStmt.getStmts()) {
            bIf = childStatements.stream().anyMatch(y -> y.toString().equals(s.toString()));
            if(bIf)
                return bIf;

        }


        return bIf;
    }
    private boolean ContainsStatement(BlockStmt blkStmt, Statement child){
        boolean bIf = false;
        for (Statement s: blkStmt.getStmts()) {
            bIf = child.toString().equals(s.toString());
            if(bIf)
                return bIf;

        }
        return bIf;
    }

    public boolean ContainsStatement(Statement parent,Statement child){
        if(StatementTypeQuery.isBlockStmt(parent)){
            return ContainsStatement((BlockStmt)parent,child);
        }
        if(StatementTypeQuery.isIfElseStmt(parent)){
            return ContainsStatement((IfStmt)parent,child);
        }
        if(StatementTypeQuery.isForEachStmt(parent)){
            return ContainsStatement((ForeachStmt)parent,child);
        }
        if(StatementTypeQuery.isSForStmt(parent)){
            return ContainsStatement((ForStmt)parent,child);
        }
        if(StatementTypeQuery.isTryStmt(parent)){
            return ContainsStatement((TryStmt)parent,child);
        }
        if(StatementTypeQuery.isWhileStmt(parent)){
            return ContainsStatement((WhileStmt)parent,child);
        }
        Debugger.log("stope");
        return false;
    }

    private boolean ContainsStatement(ForeachStmt forEachStmt, Statement child){
        if(EmptyStatementChecker.isEmptyStatement(forEachStmt.getBody()))
            return false;
        BlockStmt blkstmt = (BlockStmt)forEachStmt.getBody();
        return ContainsStatement(blkstmt,child);
    }
    private boolean ContainsStatement(WhileStmt whileStmt, Statement child){
        if(EmptyStatementChecker.isEmptyStatement(whileStmt.getBody()))
            return false;
        BlockStmt blkstmt = (BlockStmt)whileStmt.getBody();
        return ContainsStatement(blkstmt,child);
    }
    private boolean ContainsStatement(ForStmt forStmt,Statement child){
        if(EmptyStatementChecker.isEmptyStatement(forStmt.getBody()))
            return false;
        BlockStmt blkstmt = (BlockStmt)forStmt.getBody();
        return ContainsStatement(blkstmt,child);
    }

    private boolean ContainsStatement(TryStmt tryStmt,Statement child){
        boolean foundInTryBlock = false;
        boolean foundInCatchClauses = false;
        boolean foundInFinallyClause = false;
        if(EmptyStatementChecker.isEmptyStatement(tryStmt.getTryBlock())){
            if(EmptyStatementChecker.isEmptyStatement(tryStmt.getFinallyBlock())){
                boolean allcatchCauseEmpty = true;
                for (CatchClause catchClause: tryStmt.getCatchs()) {
                    if(EmptyStatementChecker.isEmptyStatement(catchClause.getCatchBlock()))
                        continue;
                    else{
                        allcatchCauseEmpty = false;
                        break;
                    }
                }
                if(allcatchCauseEmpty)
                    return false;


            }
        }

        if(!EmptyStatementChecker.isEmptyStatement(tryStmt.getTryBlock())){
            foundInTryBlock = ContainsStatement(tryStmt.getTryBlock(),child);
        }
        if(!EmptyStatementChecker.isEmptyStatement(tryStmt.getFinallyBlock())){
            foundInFinallyClause = ContainsStatement(tryStmt.getFinallyBlock(),child);
        }
        for (CatchClause catchClause: tryStmt.getCatchs())
        {
            foundInFinallyClause = foundInCatchClauses || ContainsStatement(catchClause.getCatchBlock(),child);

        }
        return foundInTryBlock || foundInCatchClauses || foundInFinallyClause;
    }

    private boolean ContainsStatement(IfStmt ifstmt, Statement child){
        boolean bIf = false;
        boolean bElse = false;
        if(StatementTypeQuery.isExprStmt(ifstmt.getThenStmt())){
            bIf = child.equals(ifstmt.toString());
            if(bIf)
                return bIf;
        }
        else{
            bIf = ContainsStatement((BlockStmt)ifstmt.getThenStmt(),child);
            if(bIf)
                return bIf;
        }


        if(ifstmt.getElseStmt() != null && StatementTypeQuery.isExprStmt(ifstmt.getElseStmt())){
            bElse = child.toString().equals(ifstmt.toString());
            if(bElse)
                return bElse;
        }

        else{
            if(ifstmt.getElseStmt() != null){
                if(StatementTypeQuery.isIfElseStmt(ifstmt.getElseStmt())){
                    if(StatementTypeQuery.isIfElseStmt(ifstmt.getElseStmt())){
                        bElse = ContainsStatement(ifstmt.getElseStmt(),child);
                    }
                    else{
                        bElse = ContainsStatement((BlockStmt)ifstmt.getElseStmt(),child);
                    }


                    if(bElse)
                        return bElse;
                }
                else{
                    bElse = ContainsStatement((BlockStmt)ifstmt.getElseStmt(),child);
                    if(bElse)
                        return bElse;
                }

            }
            else{
                bElse = false;
            }


        }
        return bIf || bElse;
    }

    private boolean ContainsStatement(SynchronizedStmt synchronizedStmt,Statement child){
        return true;
    }


    private int FindMax(int[] numbers){
        int largest = Integer.MIN_VALUE;
        for(int i =0;i<numbers.length;i++) {
            if(numbers[i] > largest) {
                largest = numbers[i];
            }
        }
        return largest;
    }



}
