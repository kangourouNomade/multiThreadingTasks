package threadLesson;

import com.sun.scenario.effect.Merge;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";

    public static void main(String[] args) {

//      100 threads calculating factorials of numbers that equals the current thread number;
        FactorialThread[] trArr = new FactorialThread[100];
        for (int i = 0; i < trArr.length; i++) {
            trArr[i] = new FactorialThread();
            Thread thrd = new Thread(trArr[i], Integer.toString((i + 1)));
            thrd.start();
            try {
                thrd.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        for (FactorialThread ft : trArr) {
            System.out.println(ft.toString());
        }
//      Multithreading array sum calculation
        System.out.println();
        System.out.println("Executing code for testing ArraySplitter and ArrayMultiThreadCalc classes:");
        Random randomizer = new Random();
        int[] array = new int[214748364];
        for (int i = 0; i < array.length; i++) {
            array[i] = randomizer.nextInt(0,450);
            }
        System.out.println("We initialize array of " + array.length + " elements with random numbers between: 0 and 450");
        List <List<Integer>> newArray = ArraySplitter.integerListDivision(array);

        System.out.println("Your device provides: " + ANSI_GREEN + Runtime.getRuntime().availableProcessors() + ANSI_RESET + " available cores");
        System.out.println("We divided the array into " + ANSI_GREEN + newArray.size() + ANSI_RESET + " partitions");

        List <Thread> threads = new ArrayList<>();
        List <ArrayMultiThreadCalc> multiThreadCalc = new ArrayList<>();
        long startTimeMulti = System.currentTimeMillis();
        System.out.println();
        System.out.println("Start calculating through multithreading");
            for (int i = 0; i < newArray.size(); i++){
                multiThreadCalc.add(new ArrayMultiThreadCalc(newArray.get(i)));
                threads.add(new Thread(multiThreadCalc.get(i)));
                threads.get(i).start();
            }

            threads.forEach(thread -> {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

        BigInteger arraySum = BigInteger.ZERO;
            for (int i = 0; i < newArray.size(); i++){
                arraySum = arraySum.add(multiThreadCalc.get(i).getResult());
            }
        long endTimeMulti = System.currentTimeMillis();
            long resultTimeMulti = endTimeMulti-startTimeMulti;
        System.out.println();
            System.out.println("The array sum is: " + ANSI_GREEN + arraySum + ANSI_RESET);
        System.out.println(String.format("Number of threads: %d, time elapsed: %f sec., the sum: %s ",
                threads.size(), resultTimeMulti/1000D, arraySum));

// single Thread:
        System.out.println();
        System.out.println("And now single thread calculation");
        BigInteger result = BigInteger.ZERO;
        long startTimeSingle = System.currentTimeMillis();
        for (int i = 0; i < array.length; i++){
            result = result.add(BigInteger.valueOf(array[i]));
        }
        long endTimeSingle = System.currentTimeMillis();
        long resultTimeSingle = endTimeSingle-startTimeSingle;
        long timeDifference = resultTimeSingle - resultTimeMulti;
        System.out.println(String.format("Single thread calculation. Time elapsed: %f sec., sum: %s ",
                resultTimeSingle/1000D, result));
        boolean isEqual = result.equals(arraySum);
        System.out.println();
        System.out.println("Calculation results of multiThread and singleThread matches: " + isEqual);
        System.out.println(String.format("The difference between elapsed time is: %f sec.", timeDifference/1000D));

        // Array sort shell method
        int[] array1 = new int[12];
        for (int i = 0; i < array1.length; i++) {
            array1[i] = randomizer.nextInt(0, 12);
        }

        List<List<Integer>> splittedArray = ArraySplitter.integerListDivision(array1);
        List <Thread> threads1 = new ArrayList<>();
        for (int i = 0; i < splittedArray.size(); i++){
            ArrayMultiThreadShellSort sorter = new ArrayMultiThreadShellSort(splittedArray.get(i));
            threads1.add(new Thread(sorter));
            threads1.get(i).start();
        }
        threads1.forEach(thread1 -> {
            try {
                thread1.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        List <Integer> finish = ArrayMultiThreadShellSort.toMergeLists(splittedArray);
        System.out.println(finish);
        List <Integer> idea = ArrayMultiThreadShellSort.sorterMerger(splittedArray);
        System.out.println(idea);
    }

    }
}