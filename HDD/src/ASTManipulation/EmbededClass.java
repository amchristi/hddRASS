package ASTManipulation;

import Helper.Debugger;

/**
 * Created by root on 3/14/17.
 */
public class EmbededClass {

    private static class Class2{
        public static void hello(){
            Debugger.log("hello world");
        }
    }

    public void method123(){
        Class2.hello();
    }
}
