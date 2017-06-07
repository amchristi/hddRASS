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
