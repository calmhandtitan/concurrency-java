
import java.util.concurrent.Callable;

public class CallableInterfaceExample {

    public static void main(String[] args) {
        SimpleSum simpleSum = new SimpleSum(2000);
        try {
            Long sum = simpleSum.call();
            System.out.println("Sum is " + sum);
        } catch (Exception e) {
            // suppress exception
        }
    }
}

/**
 * A class that implements the Callable interface (just like the runnable interface, but needs to return a value)
 * and computes sum of numbers from 1 to LIMIT passed to class constructor
  */
class SimpleSum implements Callable<Long> {

    int LIMIT;

    public SimpleSum(int LIMIT) {
        this.LIMIT = LIMIT;
    }

    @Override
    public Long call() throws Exception {
        Long sum = 0L;
        for(int i = 1; i <= LIMIT; i++) {
            sum += i;
        }
        return sum;
    }
}
