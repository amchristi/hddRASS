package testdata;

/**
 * Created by root on 11/17/16.
 */
public class IfElseIfElse {

    public void methodIfElseIfElse(){
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
