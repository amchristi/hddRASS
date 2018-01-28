package Helper;

import java.io.*;
import java.nio.file.*;
import java.nio.file.StandardCopyOption.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static boolean createDirectory2(String directory){

        File theDir = new File(directory);

// if the directory does not exist, create it
        if (!theDir.exists()) {
            System.out.println("creating directory: " + directory);
            boolean result = false;

            try{
                theDir.mkdirs();
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

    public static boolean createEmptyFile(String filePath){
        try {
            File file = new File(filePath);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write("");
            output.close();
        }
        catch (SecurityException sec){
            return false;

        }
        catch (IOException iex){
            return  false;

        }
        catch (Exception ex){
            return  false;
        }
        return true;
    }

    public static boolean createFileWithData(String filePath, String data){
        try {
            File file = new File(filePath);
            BufferedWriter output = new BufferedWriter(new FileWriter(file));
            output.write(data);
            output.close();
        }
        catch (SecurityException sec){
            return false;

        }
        catch (IOException iex){
            return  false;

        }
        catch (Exception ex){
            return  false;
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

    public static void copyFileContents(String sourceFileWithFullPath, String destFileWithFullPath){
        try {
            List<String> list =FileReaderUtil.ReadFileByLine(sourceFileWithFullPath);
            for (String  s: list
                 ) {
                FileWriterUtil.writeLine(destFileWithFullPath,s);

            }
        }
        catch (SecurityException sec){

        }
        catch (IOException ioex){

        }
        catch (Exception ex){

        }
    }


    public static List<String> getFileNames(List<String> fileNames, Path dir) {
        try(DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path path : stream) {
                if(path.toFile().isDirectory()) {
                    getFileNames(fileNames, path);
                } else {
                    fileNames.add(path.toAbsolutePath().toString());
                    System.out.println(path.getFileName());
                }
            }
        } catch(IOException e) {
            e.printStackTrace();
        }
        return fileNames;
    }

    public static List<String> getFileNames(String dirPath, String regex){
        List<String> files = new ArrayList<String>();
        File folder = new File(dirPath);
        for (final File fileEntry : folder.listFiles()) {
            if (!fileEntry.isDirectory()) {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(fileEntry.toString());
                boolean b = m.matches();
                Debugger.log(b);
                if(m.matches())
                    files.add(fileEntry.getAbsoluteFile().toString());
            }
        }


        return files;
    }

    public static String getLatestResultFile(String dirPath, String regex){
        List<String> files = new ArrayList<String>();
        long lastModified = 0;
        String latestFile = Globals.EmptyString;
        File folder = new File(dirPath);
        for (final File fileEntry : folder.listFiles()) {

            if (!fileEntry.isDirectory()) {
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(fileEntry.toString());
                boolean b = m.matches();
                Debugger.log(b);
                if(m.matches())
                    if(fileEntry.lastModified() > lastModified){

                        latestFile = fileEntry.getAbsoluteFile().toString();
                        lastModified = fileEntry.lastModified();
                    }

            }
        }


        return latestFile;
    }

    public static void main(String[] args){
        //.*MFile123\.tx.*
        List<String> files = getFileNames("/root/arpit-marti/applications/server/Marti/build/test-results/validate/",".*Tests.*xml.*");
        Debugger.log(files);

    }
}
