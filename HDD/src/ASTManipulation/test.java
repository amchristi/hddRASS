package ASTManipulation; /**
 * Created by arpit on 5/16/16.
 */

import java.util.Date.*;
public class test {
    int a;
    int b;
    public int add(int a, int b){
        int i = 0;
        int k = method(3,4);
        /*while(i < 5){
            i++;
        }*/
        for(int j = 0; j< 10; j++){
            System.out.println(j);
            i = i + 1;
        }
        while(i < 5){
            i++;
        }
        a = 3 * 4;
        dymmyMethod(3);
        return a + b;
    }

    public int method(int a, int b){
        return 0;
    }

    public void dymmyMethod(int a){
        System.out.println(a);
    }
}
