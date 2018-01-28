package PostProcessing;

/**
 * Created by root on 1/8/18.
 */
import Helper.FileReaderUtil;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class UniquieStatements {
    List<String> uniqueStatements;

    public UniquieStatements(String file){
        try {
            List<String> statements = FileReaderUtil.ReadFileByLine(file);
            uniqueStatements = statements.stream().distinct().collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<String> getUniqueStatements(){
        return uniqueStatements;
    }

    public int getUniqueStatementsCount(){
        return uniqueStatements.size();
    }

    public static void main(String[] args){
        UniquieStatements uniquieStatements = new UniquieStatements("/home/ubuntu/results/coverage/MillerUpdatingRegression/MillerUpdatingRegression_1_10.coverage");
        System.out.println(uniquieStatements.getUniqueStatementsCount());
    }
}
