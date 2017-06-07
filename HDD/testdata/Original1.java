package testdata;


public class Original1{

        public void method1(){
                if(true){
                        if(ture){
                                int i = 1;
                        }
                }
                if(true){
                        int i = 2;
                }

        }

        public void method2(){
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
                        if(true){
                                int i = j;
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
                                int k = 1;
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
                                int x = 6;
                        }
                        catch(Exception ex){
                                int n = 7;
                        }
                        finally{
                                int k = 2;
                        }
                }
        }


}