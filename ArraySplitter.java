package threadLesson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ArraySplitter {

    private static int numberOfThreads = Runtime.getRuntime().availableProcessors();

    public int getNumberOfThreads() {
        return numberOfThreads;
    }

    public void setNumberOfThreads() {
        this.numberOfThreads = Runtime.getRuntime().availableProcessors();
    }

    private static List<Integer> changeIntArrToList(int[] array) {
        Integer[] integerArr = IntStream.of(array).boxed().toArray(Integer[]::new);
        List<Integer> arrList = Arrays.asList(integerArr);
        return arrList;
    }

    public static List<List <Integer>> integerListDivision (int[] array) {
        List<Integer> arrList = new ArraySplitter().changeIntArrToList(array);
        System.out.println("Array List size is: " + arrList.size());
        List<List<Integer>> partitions = new ArrayList<>();
        int reminder;
        int partitionSize;
        int partitionReminder;
        if (arrList.size() <= numberOfThreads) {
            partitionSize = arrList.size();
            for (int i = partitionSize; i < arrList.size(); i += partitionSize) {
                partitions.add(arrList.subList(i, Math.min(i + partitionSize, arrList.size())));
            }
            return partitions;
        } else {
            reminder = arrList.size() % numberOfThreads;
            partitionSize = arrList.size() / numberOfThreads;
            partitionReminder = partitionSize + reminder;
            partitions.add(arrList.subList(0, (Math.min(partitionReminder, arrList.size()))));
            for (int i = partitionReminder; i < arrList.size(); i += partitionSize) {
                partitions.add(arrList.subList(i, Math.min(i + partitionSize, arrList.size())));
            }
            System.out.println("Partition list size: " + partitions.size());
            int numberOfElements = 0;
            for (int i = 0; i < partitions.size(); i++) {
                System.out.println("Partition sub list " + i + " size: " + partitions.get(i).size());
                numberOfElements += partitions.get(i).size();}
                System.out.println("Number of sub elements in partitions: " + numberOfElements);
                return partitions;
            }
        }
    }
