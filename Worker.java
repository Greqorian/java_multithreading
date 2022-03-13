import java.util.ArrayList;
import java.util.Arrays;

public class Worker implements Runnable {

    private Integer[] oneInterval; // [4,7]
    private int id;
    private ArrayList<Integer> primes;
    private ArrayList<Integer> numbers;

     /**
     * Worker constructor
     *
     * @param oneInterval interval of numbers
     * @param id number id of the thread 
     * @param primes array of prime numbers
     *
     */
    public Worker(Integer[] oneInterval, int id, ArrayList<Integer> primes) {
        this.oneInterval = oneInterval;
        this.id = id;
        this.primes = primes;
        this.numbers = new ArrayList<Integer>();
    }

    public ArrayList<Integer> getNumbers() {
        return this.numbers;
    }

    /**
     * get thread id
     *
     * @return thread id
     */
    public int getId() {
        return this.id;
    }

    @Override
    public void run() {
        System.out.println("Thread ID: " + id + " Starts parrallel thread");
        System.out.println("Thread ID: " + id + " Numbers interval: " + Arrays.toString(oneInterval));
        int range = oneInterval[1] - oneInterval[0];

        for (int i = 0; i < range; i++) {

            if (isPrime(oneInterval[0] + i)) {
                numbers.add(oneInterval[0] + i);
                synchronized (this) {
                    this.primes.add(oneInterval[0] + i);
                }
            }
        }

        System.out.println("Thread ID: " + id + " Prime numbers: " + numbers.toString());
    }

    /**
     * Überprüft, ob das Argument i eine Primzahl ist.
     *
     * @param i
     * @return true - Wenn das Argument i eine Primzahl ist
     * @return false - Wenn das Argument i KEINE Primzahl ist
     */
    private static boolean isPrime(int i) {
        if (i <= 1) {
            return false;
        }
        for (int j = 2; j <= i / 2; j++) {
            if (i % j == 0) {
                return false;
            }
        }
        return true;
    }
}
