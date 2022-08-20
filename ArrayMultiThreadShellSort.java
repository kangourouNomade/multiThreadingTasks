package threadLesson;

import java.util.ArrayList;
import java.util.List;

public class ArrayMultiThreadShellSort implements Runnable {
    private List <Integer> partition;
    private List <Integer> sortedPartition;

    public ArrayMultiThreadShellSort() {
        super();
    }

    public ArrayMultiThreadShellSort(List <Integer> partition) {
        this.partition = partition;
    }

    public List<Integer> getPartition() {
        return partition;
    }
    public List <Integer> getSortedPartition(){
        return sortedPartition;
    }

    public List <Integer> shellSort() {
            for (int step = partition.size() / 2; step > 0; step /= 2) {
                for (int i = step; i < partition.size(); i++) {
                    for (int j = i - step; j >= 0 && partition.get(j) > partition.get(j + step); j -= step) {
                        int x = partition.get(j);
                        partition.set(j, partition.get(j + step));
                        partition.set(j + step, x);
                    }
                }
            }

        return partition;
    }
        public void run () {
            Thread thread = Thread.currentThread();
            ArrayMultiThreadShellSort arrayShellSort = new ArrayMultiThreadShellSort(partition);
            sortedPartition = arrayShellSort.shellSort();
            System.out.println("Sorted partition from thread with ID: " + thread.getId()
                    + " and name: " + thread.getName() + " is: " + sortedPartition);



        }

        public static List <Integer> toSortbyMerging (List <Integer> a, List <Integer> b){
        List <Integer> sortedAndMerged = new ArrayList<>();
        int leftList = 0;
                int rightList = 0;
                int length = a.size() + b.size();
                for (int i = 0; i < length; i++) {
                    if (leftList >= a.size()) {
                        sortedAndMerged.add(i, b.get(rightList));
                        rightList += 1;
                    } else if (rightList >= b.size()) {
                        sortedAndMerged.add(i, a.get(leftList));
                        leftList += 1;
                    } else if (a.get(leftList) < b.get(rightList)) {
                        sortedAndMerged.add(i, a.get(leftList));
                        leftList += 1;
                    } else {
                        sortedAndMerged.add(i, b.get(rightList));
                        rightList += 1;
                    }
                }
        return sortedAndMerged;
    }

    public static List <Integer> toMergeLists (List <List <Integer>> toSort){
        List <Integer> sorted = new ArrayList<>();
        for (int i = 0; i < toSort.size(); i++){
        sorted = ArrayMultiThreadShellSort.toSortbyMerging(sorted, toSort.get(i));
            }
    return sorted;}

    public static List <Integer> sorterMerger (List <List <Integer>> unSortedArr){
    List <List <Integer>> sorted = new ArrayList<>();
    int size = unSortedArr.size();
    int reminder = size % 2;
if (reminder == 0){
        for (int i = 0; i < size-1; i+=2){
            sorted.add(ArrayMultiThreadShellSort.toSortbyMerging(unSortedArr.get(i), unSortedArr.get(i+1)));
        }
}
else {
    for (int i = 0; i < size - 2; i += 2) {
        sorted.add(ArrayMultiThreadShellSort.toSortbyMerging(unSortedArr.get(i), unSortedArr.get(i + 1)));
        sorted.add(ArrayMultiThreadShellSort.toSortbyMerging(unSortedArr.get(unSortedArr.size() - 1), new ArrayList<>()));
    }
}

        if (sorted.size() > 1) {
            ArrayMultiThreadShellSort.sorterMerger(sorted);
        }
        List <Integer> result = sorted.get(0);
return result;
    }
}


