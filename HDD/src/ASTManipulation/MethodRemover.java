package ASTManipulation;

import Helper.Debugger;
import Helper.FileWriterUtil;
import Helper.Stuffs;
import japa.parser.ast.Comment;
import japa.parser.ast.CompilationUnit;
import japa.parser.ast.body.*;
import japa.parser.ast.expr.*;
import japa.parser.ast.type.Type;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Created by root on 1/1/17.
 */
public class MethodRemover {

    public CompilationUnit _cu;
    public CompilationUnit _newcu;
    int HighestLabel;
    public List<String> doNotRemoveList;
    public List<String> deletedMethodList;


    public MethodRemover(CompilationUnit cu, List<String> doNotRemoveList){
        _cu = cu;
        _newcu = new CompilationUnit(_cu.getPackage(),_cu.getImports(),_cu.getTypes(),_cu.getComments());
        this.doNotRemoveList = doNotRemoveList;
        deletedMethodList = new ArrayList<>();

    }

    public void removeMethods(){


        _cu.getTypes().stream().forEach(x -> {
            List<BodyDeclaration> list = new ArrayList<BodyDeclaration>();
            x.getMembers().stream().forEach(y -> {
                BodyDeclaration b = (BodyDeclaration)y;
                if(b.getClass().getSimpleName().equals("MethodDeclaration")){
                    Debugger.log(b.getClass().getSimpleName());
                    MethodDeclaration m = (MethodDeclaration)b;

                        m.getAnnotations().stream().forEach(z -> {
                            if(z.getName().getName().equals("MethodLabel") && (((NormalAnnotationExpr)z).getPairs().size() == 1)) {
                                Expression e = ((NormalAnnotationExpr)z).getPairs().get(0).getValue();
                                Debugger.log("Final answer ************ : " + Integer.parseInt(((IntegerLiteralExpr)e).getValue()));

                            }

                        });

                }
            });
            _newcu.getTypes().get(0).setMembers(list);
        });

        //Debugger.log(_newcu);
    }

    public void removeMethods(int highestLevelAllowed){
        _cu.getTypes().stream().forEach(x -> {
            List<BodyDeclaration> list = new ArrayList<BodyDeclaration>();
            List<TypeDeclaration> listType = new ArrayList<TypeDeclaration>();
            List<FieldDeclaration> listFields = new ArrayList<FieldDeclaration>();
            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("TypeDeclaration"))
                    .forEach(y -> list.add((TypeDeclaration) y));
            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("FieldDeclaration"))
                    .forEach(y -> list.add((FieldDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("MethodDeclaration"))
                    .forEach(y -> {
                                MethodDeclaration m = (MethodDeclaration) y;

                                if (m.getAnnotations() == null || m.getAnnotations().size() == 0) {
                                    list.add(m);
                                } else {
                                    if(m.getAnnotations().stream().anyMatch(w -> w.getName().getName().equals("MethodLabel"))){
                                        m.getAnnotations().stream().forEach(z -> {
                                            if (z.getName().getName().equals("MethodLabel") && (((NormalAnnotationExpr) z).getPairs().size() == 1)) {
                                                Expression e = ((NormalAnnotationExpr) z).getPairs().get(0).getValue();
                                                int methodLabel = Integer.parseInt(((IntegerLiteralExpr) e).getValue());
                                                if (methodLabel <= highestLevelAllowed) {
                                                    list.add(m);
                                                }

                                            } else {

                                            }

                                        });
                                    }
                                    else{
                                        list.add(m);
                                    }


                                }

                            });



            /*x.getMembers().stream().forEach(y -> {
                BodyDeclaration b = (BodyDeclaration)y;
                if(b.getClass().getSimpleName().equals("MethodDeclaration")){
                    Debugger.log(b.getClass().getSimpleName());
                    MethodDeclaration m = (MethodDeclaration)b;

                    if(m.getAnnotations() == null || m.getAnnotations().size() == 0){
                        list.add(m);
                    }
                    else{
                        m.getAnnotations().stream().forEach(z -> {
                            if(z.getName().getName().equals("MethodLabel") && (((NormalAnnotationExpr)z).getPairs().size() == 1)) {
                                Expression e = ((NormalAnnotationExpr)z).getPairs().get(0).getValue();
                                int methodLabel =  Integer.parseInt(((IntegerLiteralExpr)e).getValue());
                                if(methodLabel <= highestLevelAllowed){
                                    list.add(m);
                                }

                            }
                            else{
                                list.add(m);
                            }

                        });
                    }


                }
            });*/
            _newcu.getTypes().get(0).setMembers(list);

        });

        Debugger.log(_newcu);


    }


    public void removeMethodsByPercentage(int percentageOfMethodToBeRemoved){
        _cu.getTypes().stream().forEach(x -> {
            List<BodyDeclaration> list = new ArrayList<BodyDeclaration>();
            List<TypeDeclaration> listType = new ArrayList<TypeDeclaration>();
            List<FieldDeclaration> listFields = new ArrayList<FieldDeclaration>();

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("TypeDeclaration"))
                    .forEach(y -> list.add((TypeDeclaration) y));
            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("FieldDeclaration"))
                    .forEach(y -> list.add((FieldDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("InitializerDeclaration"))
                    .forEach(y -> list.add((InitializerDeclaration) y));
            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("ConstructorDeclaration"))
                    .forEach(y -> list.add((ConstructorDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("ClassOrInterfaceDeclaration"))
                    .forEach(y -> list.add((ClassOrInterfaceDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("MethodDeclaration"))
                    .forEach(y -> {

                        MethodDeclaration m = (MethodDeclaration) y;

                       if((!doNotRemoveList.contains(m.getName())) && (isTestMethod(m))){
                           if(Math.random() >= (double)percentageOfMethodToBeRemoved/100) {
                               list.add(m);
                           }
                           else{
                               FileWriterUtil.appendLine("temp.txt",m.getName());
                               deletedMethodList.add(m.getName());
                           }
                       }
                       else{
                           list.add(m);
                       }

                    });



           _newcu.getTypes().get(0).setMembers(list);


        });

        Debugger.log(_newcu);


    }

    public void removeMethodsByExternalLabeling(JSONArray requiredTests, JSONArray optionalTests){
        _cu.getTypes().stream().forEach(x -> {
            List<BodyDeclaration> list = new ArrayList<BodyDeclaration>();
            List<TypeDeclaration> listType = new ArrayList<TypeDeclaration>();
            List<FieldDeclaration> listFields = new ArrayList<FieldDeclaration>();

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("TypeDeclaration"))
                    .forEach(y -> list.add((TypeDeclaration) y));
            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("FieldDeclaration"))
                    .forEach(y -> list.add((FieldDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("InitializerDeclaration"))
                    .forEach(y -> list.add((InitializerDeclaration) y));
            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("ConstructorDeclaration"))
                    .forEach(y -> list.add((ConstructorDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("ClassOrInterfaceDeclaration"))
                    .forEach(y -> list.add((ClassOrInterfaceDeclaration) y));

            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("MethodDeclaration"))
                    .forEach(y -> {

                        MethodDeclaration m = (MethodDeclaration) y;
                        if(isOptionalTestMethod(m,optionalTests)){
                            FileWriterUtil.appendLine("temp.txt",m.getName());
                            deletedMethodList.add(m.getName());
                        }
                        else{
                            list.add(m);
                        }

                    });



            _newcu.getTypes().get(0).setMembers(list);


        });

        Debugger.log(_newcu);


    }

    private boolean isTestMethod(MethodDeclaration m){
        List<AnnotationExpr> annotations = m.getAnnotations();
        if(annotations == null || annotations.size() == 0)
            return false;
        for (AnnotationExpr e: annotations) {
            if(e.getName().toString().equals("Test"))
                return  true;

        }
        return false;
    }

    private boolean isOptionalTestMethod(MethodDeclaration m, JSONArray optioalTests){
        boolean isOptioanlTest = false;
        if(!isTestMethod(m)){
            return false;
        }
        for (Object o :  optioalTests) {
            String methondName = Stuffs.DeriveMethodNameFromFullName(o.toString());
            if(m.getName().equals(methondName))
                return true;

        }
        return isOptioanlTest;
    }

    public int GetTestMethodCount(){
        List<MethodDeclaration> list = new ArrayList<>();
        _cu.getTypes().stream().forEach(x -> {


            x.getMembers().stream()
                    .filter(a -> ((BodyDeclaration)a).getClass().getSimpleName().equals("MethodDeclaration"))
                    .forEach(y -> {

                        MethodDeclaration m = (MethodDeclaration) y;

                        if((isTestMethod(m))){
                           list.add(m);
                        }


                    });






        });

        return list.size();


    }


}
