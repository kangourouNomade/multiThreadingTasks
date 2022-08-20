package threadLesson;

import java.math.BigInteger;

public class FactorialThread implements Runnable {

    private String name;
    private BigInteger factorial;

    public FactorialThread() {
        super();

    }

    private BigInteger factorial() {
        BigInteger fact = new BigInteger("1");
        BigInteger num = new BigInteger(
                (Thread.currentThread().getName().substring(Thread.currentThread().getName().lastIndexOf("-") + 1)));

        for (int i = 1; i <= num.intValue(); i += 1) {
            fact = fact.multiply(new BigInteger(integerToString(i)));
        }
        return fact;
    }

    private String integerToString(int n) {
        String res = "";
        try {
            res = Integer.toString(n);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return res;
    }

    private void showFactorial(BigInteger f) {
        this.setFactorial(f);
        this.setName(Thread.currentThread().getName());

    }

    @Override
    public void run() {
        showFactorial(factorial());
    }

    public BigInteger getFactorial() {
        return factorial;
    }

    private void setFactorial(BigInteger factorial) {
        this.factorial = factorial;
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "FactorialThread [name = " + name + ", factorial = " + factorial + "]";
    }

}

