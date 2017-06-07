package Helper;

import java.io.*;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption.*;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * Created by arpit on 8/15/16.
 */
public class FileOperationUtil {
    public static boolean isEmptyFile(String filename) throws FileNotFoundException, IOException {
        BufferedReader br;
        br = new BufferedReader(new FileReader(filename));
        if (br.readLine() == null) {
            return true;
        }
        return false;
    }

    public static boolean createDirectory(String directory){

        File theDir = new File(directory);

// if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + directory);
            boolean result = false;

            try{
                theDir.mkdir();
                result = true;
            }
            catch(SecurityException se){
                //handle it
                return false;

            }
            if(result) {
                System.out.println("DIR created");
                return true;
            }
        }
        return true;
    }

    public static void removeDirectory(String directory){
        File theDir = new File(directory);
        try{
            theDir.deleteOnExit();
        }
        catch(SecurityException secEx){
            secEx.printStackTrace();
        }

    }



    public static void copyFile(String sourceFileWithFullPath, String destFileWithFullPath){
        Path source = Paths.get(sourceFileWithFullPath);
        Path dest = Paths.get(destFileWithFullPath);
        try {
            Files.copy(source,dest,REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
