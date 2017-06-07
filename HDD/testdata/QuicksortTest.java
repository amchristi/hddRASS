package com.osustar;

import java.util.Arrays;
import java.util.Random;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class QuicksortTest {

    int SIZE = 10;

    int MAX = 50;

    int[] numbers = new int[SIZE];

    @Before
    public void setUp() throws Exception {
        Random generator = new Random();
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = generator.nextInt(MAX);
        }
    }

    @MethodLabel(level=1)
    @test
    public void testNull() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }


    @MethodLabel(level=0)
    @test
    public void test3() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }


    @test
    public void test1() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }

    public void test2(){

    }

    @MethodLabel(level=1)
    @test
    public void testNull1() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }


    @MethodLabel(level=0)
    @test
    public void test31() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }


    @test
    public void test11() {
        Quicksort sorter = new Quicksort();
        sorter.sort(null);
    }

    public void test21(){

    }

}
