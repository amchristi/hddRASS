package testdata;

/**
 * Created by root on 11/20/16.
 */
public class TryCatchFinallyExample {
    public void tryCatchMethod(){
        try{
            System.out.print("hello");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
    }

    public void tryCatchFinallyMethod(){
        try{
            System.out.print("try");
        }
        catch(Exception ex){
            ex.printStackTrace();
        }
        finally{
            System.out.println("finally");
        }
    }

    public void multipleCatch(){
        try{
            System.out.println("try");
        }
        catch(NullPointerException ex2){
            if(true){
                System.out.println("inside if");
            }
            ex2.printStackTrace();
        }
        catch(Exception ex){
            ex.printStackTrace();
        }

    }


}
