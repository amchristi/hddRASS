package HDD;

/**
 * Created by root on 2/12/17.
 */
public class SynchronizedExample {

    public void method1(){
        boolean temp = true;
        Object o = new Object();

        synchronized (o){
            System.out.println(o.toString());

        }
    }

    public void method2(){
        Object o = new Object();

        synchronized (o){
            System.out.println(o.toString());
            if(true){
                System.out.println("in if");
            }

        }
    }
}
