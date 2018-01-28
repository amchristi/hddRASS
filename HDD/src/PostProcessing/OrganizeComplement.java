package PostProcessing;

import Helper.FileOperationUtil;

/**
 * Created by root on 12/22/17.
 */
public class OrganizeComplement {
    public static void main(String[] args){
        String className = "HeaderGroup";
        String testName = "TestHeaderGroup";
        for(int i = 1;i<10;i++){
            FileOperationUtil.createDirectory2("/home/ubuntu/results/complement/" + className + "/" + i );

            FileOperationUtil.createEmptyFile("/home/ubuntu/results/complement/" + className + "/" + i + "/" + testName + ".java");

            FileOperationUtil.copyFileContents("/home/ubuntu/results/complement/" + className + "_1" + "_" + i,"/home/ubuntu/results/complement/" + className + "/" + i + "/" + testName + ".java");

        }
    }
}
