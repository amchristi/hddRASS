package testdata;

import java.io.IOException;

/**
 * Created by root on 11/20/16.
 */
public class ThrowsExample {

    public void methodThrowsIOException() throws IOException{
        throw new IOException();

    }

    public void methodThrowsIOExceptionInABlock() throws IOException{
        if(true){
            IOException e = new IOException();
            throw e;

        }
    }


}
