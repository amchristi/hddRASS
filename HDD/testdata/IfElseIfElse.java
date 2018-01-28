package testdata;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by root on 11/17/16.
 */
public class IfElseIfElse {


    public void methodIfElseIfElse(){
        int i = 10;
        BufferedWriter output = null;
        try {
            writeline("a","b");
            java.io.File file = new File(fullFilePath);
            FileWriter fileWriter = new FileWriter(file);
            output = new BufferedWriter(fileWriter);

            output.append(text);
            //output.newLine();
        } catch ( IOException e ) {
            e.printStackTrace();
        } finally {
            if ( output != null ) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        switch(i){
            case 1:
                System.out.println("hello1");
                break;
            case 2:
                System.out.println("hello1");
                System.out.println("hello1");
                break;
            default:
                System.out.println("hello1");
                break;



        }
        Object _instance;
        if(_instance == null) {
            synchronized (Singleton.class) {
                if (_instance == null) _instance = new Singleton();
            }
        }
        try{
            if(true)
                System.out.println("hello1");
        }
        catch (Exception exception){

        }
        for(int i = 0;i<10;i++){
            System.out.println("hello1");
        }
        for(int j = 0;j<10;j++)
            while(true)
                System.out.println("hello1");


        if(true)
            System.out.println("hello1");

        if(true){
            System.out.println("hello1");

        }
        else if(true) {
            System.out.println("hello");
        }
        else{
            System.out.println("hello");
        }

    }

    public void methodSimpleIfElse(){
        if(true){
            System.out.println("hello");
        }
        else{
            if(true){
                System.out.println("hello1");
            }
            else{
                System.out.println("hello1");
            }
        }


    }

    public void methodSimpleIfOnly(){
        if(true){
            System.out.println("hello1");
        }
    }
}
