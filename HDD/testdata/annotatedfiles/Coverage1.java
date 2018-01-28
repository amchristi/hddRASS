package testdata;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;

/**
 * Created by root on 11/17/16.
 */
public class Coverage1 {

    public void methodSimpleIfElse() {
        writeline("/home/ubuntu/temp/test.txt", "823d1b8c-4778-4267-a9f2-b48385bd8f95");
        for (int i = 0; i < 10; i++) {
            writeline("/home/ubuntu/temp/test.txt", "a0cac9c5-7833-41f7-b5ea-3587be63f603");
            System.out.println("hello world");
        }
    }

    public void writeline(String fullFilePath, String text) {
        try {
            java.io.File file = new File(fullFilePath);
            FileWriter fileWriter = new FileWriter(file, true);
            BufferedWriter output = new BufferedWriter(fileWriter);
            output.append(text);
            output.newLine();
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
