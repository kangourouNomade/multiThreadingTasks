package threadLesson;

import java.math.BigInteger;
import java.util.List;

public class ArrayMultiThreadCalc implements Runnable{
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    private List<Integer> array;
    private BigInteger result;

    public ArrayMultiThreadCalc(List<Integer> array) {
        this.array = array;
    }

    public ArrayMultiThreadCalc() {
    }

    public List<Integer> getArray() {
        return array;
    }

    public void setArray(List <Integer> array) {
        this.array = array;
    }

    public BigInteger getResult() {
        return result;
    }

    public BigInteger calculationOfArrElementsSum(List <Integer> array){
        BigInteger result = BigInteger.ZERO;
        for (int i = 0; i < array.size(); i++){
            result = result.add(new BigInteger(String.valueOf(array.get(i))));
        }
    return result;
    }

    @Override
    public void run() {
        Thread thread = Thread.currentThread();
        ArrayMultiThreadCalc arrayCalc = new ArrayMultiThreadCalc(array);
        result = arrayCalc.calculationOfArrElementsSum(array);
        System.out.println("The sum of array elements from thread with ID: " + ANSI_GREEN + thread.getId() + ANSI_RESET
                + " and name: " + ANSI_GREEN + thread.getName() + ANSI_RESET + " is: " + result);
    }

    @Override
    public String toString() {
        return "ArrayMultiThreadCalc{" +
                "array=" + array +
                ", result=" + result +
                '}';
    }
}
