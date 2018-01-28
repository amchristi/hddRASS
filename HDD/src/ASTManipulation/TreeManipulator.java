package ASTManipulation;

import Helper.Debugger;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.stmt.*;
import sun.reflect.generics.tree.Tree;

import java.util.ArrayList;
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

    public List<Statement> GetAllLeafNodes(){
        List<Statement> leafStatements = new ArrayList<Statement>();
        for (TypeDeclaration t : cu.getTypes()) {
            for (BodyDeclaration b : t.getMembers()) {
                if (b.getClass().getSimpleName().toString().equals("MethodDeclaration")) {
                    MethodDeclaration m = (MethodDeclaration) b;
                    if (m.getName().toString().equals(testMethod)) {
                        leafStatements.addAll(GetAllLeafNodes(m.getBody()));

                    }
                }
            }
        }
        return leafStatements;
    }

    public List<Statement> GetAllLeafPlusOneNodes(){
        List<Statement> plusone = new ArrayList<Statement>();
        for (TypeDeclaration t : cu.getTypes()) {
            for (BodyDeclaration b : t.getMembers()) {
                if (b.getClass().getSimpleName().toString().equals("MethodDeclaration")) {
                    MethodDeclaration m = (MethodDeclaration) b;
                    if (m.getName().toString().equals(testMethod)) {
                        plusone.addAll(GetAllLeafPlusOneNodes(m.getBody()));

                    }
                }
            }
        }
        return plusone;
    }

    public List<Statement> GetAllLeafNodes(BlockStmt blockStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        if(blockStmt == null)
            return leafStatements;
        for (Statement s:blockStmt.getStmts()
                             ) {
            if(StatementTypeQuery.isLeafLevelStatement(s))
                leafStatements.add(s);
            else if(StatementTypeQuery.isSForStmt(s))
                leafStatements.addAll(GetAllLeafNodes((ForStmt)s));
            else if(StatementTypeQuery.isForEachStmt(s))
                leafStatements.addAll(GetAllLeafNodes((ForeachStmt)s));
            else if(StatementTypeQuery.isWhileStmt(s))
                leafStatements.addAll(GetAllLeafNodes((WhileStmt)s));
            else if(StatementTypeQuery.isIfElseStmt(s))
                leafStatements.addAll(GetAllLeafNodes((IfStmt)s));
            else if(StatementTypeQuery.isTryStmt(s))
                leafStatements.addAll(GetAllLeafNodes((TryStmt)s));
            //TODO: add other statement types;
        }

        return leafStatements;
    }

    public List<Statement> GetAllLeafPlusOneNodes(BlockStmt blockStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        if(blockStmt == null)
            return leafStatements;
        for (Statement s:blockStmt.getStmts()
                ) {

            if (StatementTypeQuery.isSForStmt(s))
                leafStatements.addAll(GetAllLeafPlusOneNodes((ForStmt) s));
            else if (StatementTypeQuery.isForEachStmt(s))
                leafStatements.addAll(GetAllLeafPlusOneNodes((ForeachStmt) s));
            else if (StatementTypeQuery.isWhileStmt(s))
                leafStatements.addAll(GetAllLeafPlusOneNodes((WhileStmt) s));
            else if (StatementTypeQuery.isIfElseStmt(s))
                leafStatements.addAll(GetAllLeafPlusOneNodes((IfStmt) s));
            else if (StatementTypeQuery.isTryStmt(s))
                leafStatements.addAll(GetAllLeafPlusOneNodes((TryStmt) s));
        }
        return leafStatements;
    }

    public List<Statement> GetAllLeafNodes(ForStmt forStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        if(forStmt.getBody() == null){
            return  leafStatements;
        }
        Statement inner = forStmt.getBody();
        if(StatementTypeQuery.isBlockStmt(inner)){
            leafStatements.addAll(GetAllLeafNodes((BlockStmt) inner));
        }
        else if(StatementTypeQuery.isLeafLevelStatement(inner)){
            leafStatements.add(inner);
        }

        return  leafStatements;

    }

    public List<Statement> GetAllLeafPlusOneNodes(ForStmt forStmt){
        List<Statement> plusOneStatements = new ArrayList<Statement>();
        if(forStmt.getBody() == null){
            //plusOneStatements.add(forStmt);
            return  plusOneStatements;
        }



        Statement inner = forStmt.getBody();
        if(StatementTypeQuery.isBlockStmt(inner)){
            int depth = GetHeighestDepthLevel((BlockStmt)inner);
            if(depth == 1){
              plusOneStatements.add(forStmt);
            }
            else{
                plusOneStatements.addAll(GetAllLeafPlusOneNodes(((BlockStmt) inner)));
            }

        }
        else if(StatementTypeQuery.isLeafLevelStatement(inner)){
            //leafStatements.add(inner);
            plusOneStatements.add(forStmt);
        }

        return  plusOneStatements;

    }


    public List<Statement> GetAllLeafNodes(ForeachStmt foreachStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        if(foreachStmt.getBody() == null){
            return  leafStatements;
        }
        Statement inner = foreachStmt.getBody();
        if(StatementTypeQuery.isBlockStmt(inner)){
            leafStatements.addAll(GetAllLeafNodes((BlockStmt) inner));
        }
        else if(StatementTypeQuery.isExprStmt(inner)){
            leafStatements.add(inner);
        }

        return  leafStatements;

    }


    public List<Statement> GetAllLeafPlusOneNodes(ForeachStmt foreachStmt){
        List<Statement> plusOneStatements = new ArrayList<Statement>();
        if(foreachStmt.getBody() == null){

            return plusOneStatements;
        }
        Statement inner = foreachStmt.getBody();
        if(StatementTypeQuery.isBlockStmt(inner)){
            int depth = GetHeighestDepthLevel((BlockStmt)inner);
            if(depth == 1){
                plusOneStatements.add(foreachStmt);
            }
            else{
                plusOneStatements.addAll(GetAllLeafPlusOneNodes(((BlockStmt) inner)));
            }

        }


        return  plusOneStatements;

    }

    public List<Statement> GetAllLeafNodes(WhileStmt whileStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        if(whileStmt.getBody() == null){
            return  leafStatements;
        }
        Statement inner = whileStmt.getBody();
        if(StatementTypeQuery.isBlockStmt(inner)){
            leafStatements.addAll(GetAllLeafNodes((BlockStmt) inner));
        }
        else if(StatementTypeQuery.isExprStmt(inner)){
            leafStatements.add(inner);
        }

        return  leafStatements;

    }

    public List<Statement> GetAllLeafPlusOneNodes(WhileStmt whileStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        if(whileStmt.getBody() == null){

            return  leafStatements;
        }
        Statement inner = whileStmt.getBody();
        if(StatementTypeQuery.isBlockStmt(inner)){
            int depth = GetHeighestDepthLevel((BlockStmt)inner);
            if(depth == 1){
                leafStatements.add(whileStmt);
            }
            else{
                leafStatements.addAll(GetAllLeafPlusOneNodes(((BlockStmt) inner)));
            }

        }


        return  leafStatements;

    }

    public List<Statement> GetAllLeafNodes(IfStmt ifStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        Statement ifpart = ifStmt.getThenStmt();
        Statement elsePart = ifStmt.getElseStmt();

        if(ifpart != null){
            if(StatementTypeQuery.isLeafLevelStatement(ifpart))
                leafStatements.add(ifpart);
            else if(StatementTypeQuery.isBlockStmt(ifpart)){
                leafStatements.addAll(GetAllLeafNodes((BlockStmt)ifpart));
            }
        }
        if(elsePart != null){
            if(StatementTypeQuery.isLeafLevelStatement(elsePart))
                leafStatements.add(elsePart);
            else if(StatementTypeQuery.isBlockStmt(elsePart))
                leafStatements.addAll(GetAllLeafNodes((BlockStmt)elsePart));

        }
        return  leafStatements;
    }

    public List<Statement> GetAllLeafPlusOneNodes(IfStmt ifStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        Statement ifpart = ifStmt.getThenStmt();
        Statement elsePart = ifStmt.getElseStmt();
        if(ifpart == null){
            return leafStatements;
        }
        if(elsePart == null){
            return  leafStatements;
        }
        if(ifpart != null){
            if(StatementTypeQuery.isBlockStmt(ifpart)){
                int depth = GetHeighestDepthLevel((BlockStmt) ifpart);
                if(depth == 1){
                  leafStatements.add(ifStmt)  ;
                }
                else{
                    leafStatements.addAll(GetAllLeafPlusOneNodes(((BlockStmt)ifpart)));
                }

            }
        }
        if(elsePart != null){
            if(StatementTypeQuery.isBlockStmt(elsePart)){
                int depth = GetHeighestDepthLevel((BlockStmt) elsePart);
                if(depth == 1){
                    leafStatements.add(elsePart)  ;
                }
                else{
                    leafStatements.addAll(GetAllLeafPlusOneNodes(((BlockStmt)elsePart)));
                }

            }
        }
        return  leafStatements;
    }


    public List<Statement> GetAllLeafNodes(TryStmt tryStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        BlockStmt trypart = tryStmt.getTryBlock();
        BlockStmt finallyPart = tryStmt.getFinallyBlock();

        if(trypart != null){
            if(StatementTypeQuery.isLeafLevelStatement(trypart))
                leafStatements.add(trypart);
            else
                leafStatements.addAll(GetAllLeafNodes((BlockStmt)trypart));
        }

        //TODO: add catch block later, does not exist in netbeans so don't wrroy about it right now.

        if(finallyPart != null){
            if(StatementTypeQuery.isLeafLevelStatement(finallyPart))
                leafStatements.add(finallyPart);
            else
                leafStatements.addAll(GetAllLeafNodes((BlockStmt)finallyPart));
        }

        return leafStatements;
    }

    public List<Statement> GetAllLeafPlusOneNodes(TryStmt tryStmt){
        List<Statement> leafStatements = new ArrayList<Statement>();
        BlockStmt trypart = tryStmt.getTryBlock();
        BlockStmt finallyPart = tryStmt.getFinallyBlock();

        if(trypart == null){
            return leafStatements;
        }
        if(finallyPart == null)
            return leafStatements;
        if(trypart != null){
            if(StatementTypeQuery.isBlockStmt(trypart)){
                int depth = GetHeighestDepthLevel((BlockStmt)trypart);
                if(depth == 1)
                    leafStatements.add(trypart);
                else
                    leafStatements.addAll(GetAllLeafPlusOneNodes((BlockStmt)trypart));
            }

        }

        //TODO: add catch block later, does not exist in netbeans so don't wrroy about it right now.

        if(finallyPart != null){
            if(StatementTypeQuery.isBlockStmt(finallyPart)){
                int depth = GetHeighestDepthLevel((BlockStmt)finallyPart);
                if(depth == 1)
                    leafStatements.add(finallyPart);
                else
                    leafStatements.addAll(GetAllLeafPlusOneNodes((BlockStmt)finallyPart));
            }

        }

        return leafStatements;
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
                if(StatementTypeQuery.isExprStmt(forstmt.getBody())){
                    GetHeighestDepthLevel((ExpressionStmt) forstmt.getBody());
                }
                else {
                    BlockStmt blkstmt = (BlockStmt) forstmt.getBody();
                    tempDepthLevels[i++] = GetHeighestDepthLevel(blkstmt);
                }

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
                System.out.println(s);
                Debugger.log("*************** NOT IMPLEMENTED ***************************");
               // exit(-99);
            }


        }
        return 1 + FindMax(tempDepthLevels);
    }

    public int GetHeighestDepthLevel(ExpressionStmt stmt){
        return 1;
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
        try{
            if(StatementTypeQuery.isExprStmt(ifStmt.getThenStmt()))
                ifLength = 1;
            else if(StatementTypeQuery.isReturnStmt(ifStmt.getThenStmt())){
                ifLength = 1;
            }
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

        }catch (ClassCastException ex){
            Debugger.log(ex.toString());

        }
        return -1;


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
