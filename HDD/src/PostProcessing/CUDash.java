package PostProcessing;

import Helper.Debugger;
import Helper.FileReaderUtil;
import Helper.Globals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by root on 1/14/18.
 * This class measures C union U', value of 3rd heuristic efficiency.
 */
public class CUDash {
    String CFile;
    String UDashFile;

    public static Set<String>  calcuate(String CFile,String UDashFile, String annotatedFile){
        List<String> uniqueCStatements;
        List<String> uniqueUDashStatements;
        List<String> allStatements;
        List<String> statements = null;
        try {
            statements = FileReaderUtil.ReadFileByLine(CFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        uniqueCStatements = statements.stream().distinct().collect(Collectors.toList());

        try {
            statements = FileReaderUtil.ReadFileByLine(UDashFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        uniqueUDashStatements = statements.stream().distinct().collect(Collectors.toList());

        allStatements = GetUniquieStatementsFromAnnotatedFiles(annotatedFile);

        Set<String> U = allStatements.stream().distinct().collect(Collectors.toSet());
        Set<String> a = uniqueCStatements.stream().collect(Collectors.toSet());
        Set<String> b = uniqueUDashStatements.stream().collect(Collectors.toSet());

        Set<String> bDahs = (new ArrayList<String>()).stream().collect(Collectors.toSet());

        for (String s: U

             )

        {
            boolean found = false;
            for (String s2 : b){
                if(s.equals(s2)){
                    found = true;
                }
            }
            if(!found){
                bDahs.add(s);
            }

        }
        Set<String> aUnionBDash = (new ArrayList<String>()).stream().collect(Collectors.toSet());

        for(String s : a){
            aUnionBDash.add(s);

        }

        for(String s : bDahs){
            if(!aUnionBDash.contains(s)){
                aUnionBDash.add(s);
            }
            else{
                Debugger.log("here");
            }
        }

        System.out.println(aUnionBDash);

        return a;
    }

    public  static List<String>  GetUniquieStatementsFromAnnotatedFiles(String annotatedFile){
        List<String> uuids = new ArrayList<String >();
        List<String> allWriteLineStatements = new ArrayList<String>();
        try {
            List<String> allstatements = FileReaderUtil.ReadFileByLine(annotatedFile);

            for (String s: allstatements
                 ) {
                if(s.contains("writeline")){
                    allWriteLineStatements.add(s);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(allWriteLineStatements);

        for (String s: allWriteLineStatements
             ) {
            String str = GetUniqueIDFromWritelineStatement(s);
            if(!str.equals(Globals.EmptyString))
                uuids.add(str);

        }
        return uuids;
    }

    public static String GetUniqueIDFromWritelineStatement(String str){

        String[] str2 = str.split("\"");
        if(str2.length >= 3)
            return str2[3];
        else
            return Globals.EmptyString;
    }


    public static void main(String[] args){
        //CUDash.calcuate("/home/ubuntu/research//HDD/testdata/annotatedfiles/cfile.txt","/home/ubuntu/research//HDD/testdata/annotatedfiles/udashfile.txt");



        String className = "HttpService";
        String label = "10";
        String cFile = "/home/ubuntu/results/coverage/coveragepristine/" + className + "/" + className + "_" + "1" + "_" +  label + ".coverage";
        String uDashFile = "/home/ubuntu/results/coverage/uncoveragepristine/" + className + "/" + className + "_" + "1" + "_" +  label + ".coverage";
        //String annotatedFile = "/home/ubuntu/results/annotated/" + className + "/1/" + className + ".java ";
        String annotatedFile = "/home/ubuntu/results/annotated/HttpService/1/HttpService.java";
        //GetUniquieStatementsFromAnnotatedFiles(annotatedFile);
        Set<String> union = CUDash.calcuate(cFile,uDashFile,annotatedFile);
        System.out.println(union.size());
    }

}
