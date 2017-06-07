package Driver;

import Helper.Debugger;
import Helper.Globals;
import JReduce.HierarchicalClassReducer;
import org.apache.commons.cli.ParseException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 10/24/16.
 */
public class DummyDriver {
    public static void main(String[] args) throws ParseException {

        Helper.CommandLineParser commandLineParser = null;
        try {
            commandLineParser = new Helper.CommandLineParser(args);
        }
        catch(ParseException parseEx){
            System.out.println("Error in command line argument, provide correct command line arguments");
            System.out.println(parseEx.getMessage());
        }


        String rootFolder = commandLineParser.getRootFolder();
        String relativePath = commandLineParser.getRelativePath();

        Debugger.log(rootFolder);
        Debugger.log(relativePath);
        System.out.println("hello world");
    }
}
