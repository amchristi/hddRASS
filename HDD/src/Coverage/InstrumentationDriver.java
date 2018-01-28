package Coverage;

import Helper.CompilationUnitHelper;
import Helper.FileOperationUtil;
import Helper.FileReaderUtil;
import Helper.FileWriterUtil;
import japa.parser.ast.CompilationUnit;

/**
 * Created by root on 12/22/17.
 */
public class InstrumentationDriver {
    public String className;
    public String inFilePath;
    public String outFilePath;
    public String coverageFilePath;
    public String uncoverageFilePath;
    public InstrumentationDriver(String className){
        outFilePath = "/home/ubuntu/results/annotated/" + className + "/";
        inFilePath = "/home/ubuntu/results/reduced/" + className + "/";
        coverageFilePath = "/home/ubuntu/results/coverage/" + className + "/";
        uncoverageFilePath = "/home/ubuntu/results/uncoverage/" + className + "/";

        this.className =className;
    }

    public void instrument(){
        inFilePath = inFilePath + className + ".java";
        FileOperationUtil.createDirectory(coverageFilePath);
        for(int i = 1;i<=5;i++){

            String textFile = "";
            String tempOutfilePath = "";
            textFile += coverageFilePath +  className + "_" + i +  "_10.coverage";
            tempOutfilePath = outFilePath +  i;
            FileOperationUtil.createDirectory2(tempOutfilePath);

            ClassMarker marker = new ClassMarker(inFilePath,textFile);
            marker.mark();
            FileWriterUtil.write(tempOutfilePath + "/" + className + ".java",marker.cu.toString());
            FileOperationUtil.createEmptyFile(textFile);
        }


    }

    public static void main(String[] args){
        InstrumentationDriver instrumentationDriver = new InstrumentationDriver("DefaultIntervalCategoryDataset");
        instrumentationDriver.instrument();
    }
}
