package testdata;

public class Original1{

    public void method1(){

            if(true){

            }
            if(true){

            }



    }

    public void method2(){
        if(true){
            int i = 1;
        }
        else{

        }
        for(int j = 0;j<10;j++){
            int i = 0;
            if(true){

            }
        }
    }

    public void method3(){
        if(true){
            int i = 1;
        }
        else{
            if(true){
                int i = 5;
            }
        }
        for(int j = 0;j<10;j++){
            int i = 0;
            try{
                int k = 0;
            }
            catch(Exception ex){

            }
            finally{
                int k = 2;
            }
        }
    }

    public void method4(){
        if(true){
            int i = 1;
        }
        else{
            if(true){
                int i = 5;
            }
        }
        for(int j = 0;j<10;j++){
            int i = 0;
            try{

            }
            catch(Exception ex){

            }
            finally{

            }
        }
    }


}