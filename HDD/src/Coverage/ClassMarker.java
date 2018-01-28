package Coverage;

import Helper.*;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.ImportDeclaration;
import japa.parser.ast.body.BodyDeclaration;
import japa.parser.ast.body.MethodDeclaration;
import japa.parser.ast.body.TypeDeclaration;
import japa.parser.ast.expr.NameExpr;
import japa.parser.ast.stmt.BlockStmt;
import japa.parser.ast.visitor.GenericVisitor;
import japa.parser.ast.visitor.VoidVisitor;

import java.util.LinkedList;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by root on 12/6/17.
 */
public class ClassMarker {

    public CompilationUnit cu;
    public String classNameWithFullPath;
    public String outputWriteFilePath;

    public ClassMarker(String classNameWithFullPath, String outputWriteFilePath){
        this.classNameWithFullPath = classNameWithFullPath;
        this.cu = CompilationUnitHelper.CreateCompilationUnit(classNameWithFullPath);
        this.outputWriteFilePath = outputWriteFilePath;

    }

    public void mark(){

        this.cu.getTypes().stream().forEach(x -> {
            TypeDeclaration t = (TypeDeclaration)x;
            Object o = t.getData();
            for (BodyDeclaration b: t.getMembers()
                 ) {
                if (b.getClass().getSimpleName().toString().equals("MethodDeclaration")) {
                    MethodDeclaration m = (MethodDeclaration)b;
                    BlockStmt blk  = m.getBody();
                    BlockMarker blockMkaer = new BlockMarker(blk);
                    blockMkaer.outputFileName = this.outputWriteFilePath;
                    BlockStmt newBlock = blockMkaer.mark();
                    m.setBody(newBlock);
                }
            }


            t.getMembers().add(Instrumentation.intrumentationMethod());
        });

        ImportDeclaration importDeclaration = new ImportDeclaration();
        NameExpr nameExpr = new NameExpr("java.io.*");
        importDeclaration.setName(nameExpr);
        if(cu.getImports() != null){
            cu.getImports().add(importDeclaration);
        }



        Debugger.log(cu);


    }


    public static void main(String[] args){

        ClassMarker classMarker = new ClassMarker("/home/ubuntu/research/HDD/testdata/Coverage1.java","/home/ubuntu/temp/test.txt");
        classMarker.mark();;
        FileWriterUtil.write("/home/ubuntu/research/HDD/testdata/annotatedfiles/Coverage1.java",classMarker.cu.toString());


        classMarker = new ClassMarker("/home/ubuntu/backup/ManagerOriginal.java","/home/ubuntu/temp/ManagerOriginal.txt");
        classMarker.mark();
        FileWriterUtil.write("/home/ubuntu/research/HDD/testdata/annotatedfiles/ManagerOriginal.java",classMarker.cu.toString());







    }
}


