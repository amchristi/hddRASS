package Helper;

/**
 * Created by arpit on 6/11/16.
 */
public class JarCreaterCommand {
    private String rootDirectory;
    private String jarFileName;
    private String jarFilePath;

    public JarCreaterCommand(){

    }
    public JarCreaterCommand(String root,String jarFileName, String jarFilePath){
        rootDirectory = root;
        this.jarFileName = jarFileName;
        this.jarFilePath = jarFilePath;
    }

    public void Create(){
        String commandString = "jar cf " + jarFileName + " " + rootDirectory;
        Command.exec(commandString);

    }
}
