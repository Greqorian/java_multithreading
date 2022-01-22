import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.File;

public class MainThread {

    private static int threads = 8;
    private static int[] range = { 15, 250 };

    private static ArrayList<Thread> sideThreads = new ArrayList<Thread>();
    private static ArrayList<Integer[]> intervalle = new ArrayList<Integer[]>();
    private static ArrayList<Integer> primes = new ArrayList<Integer>();

    public static void main(String[] args) {

        MainThread MeinThread = new MainThread();

        intervalle = MeinThread.getIntervalls(range, threads);

        System.out.println("Thread ID: 0 Start the main Thread");
        System.out.println("Numbers range: " + Arrays.toString(range));
        System.out.println("Pararell threads: " + intervalle.size());
        System.out.println("-------------------");

        long startTime = System.nanoTime();

        MainThread.startWorkers(intervalle, primes, sideThreads);

        for (int i = 0; i < sideThreads.size(); i++) {
            try {
                sideThreads.get(i).join();
                // Thread.sleep(1000);
            } catch (InterruptedException e) {
            }

        }

        long endTime = System.nanoTime();
        long totalTime = endTime - startTime;

        System.out.println("-------  End of processing ------------");
        System.out.println("Thread ID: 0 All found Primes: " + primes.toString());
        System.out.println("Processing time in nanoseconds: " + totalTime);

        MainThread.printArrayToTxt(primes, range);

    }

    private ArrayList<Integer[]> getIntervalls(int[] range, int threads) {

        int max = range[1] - range[0];
        int size = Math.round((max - 1) / threads);
        ArrayList<Integer[]> result = new ArrayList<Integer[]>();

        for (int i = 0; i < threads; i++) {
            int inf = i + i * size;
            int sup = 0;

            if (inf + size < max) {
                sup = inf + size;
            } else {
                sup = max;
            }
            ;

            Integer[] oneInterval = { inf + range[0], sup + range[0] };

            result.add(oneInterval);

            if (inf >= max || sup >= max)
                break;
        }
        return result;
    }

    private static void startWorkers(ArrayList<Integer[]> intervals, ArrayList<Integer> primes,
            ArrayList<Thread> sideThreads) {

        for (int i = 0; i < intervals.size(); i++) {

            int id = i + 1;
            Integer[] oneInterval = intervals.get(i);

            Thread t = new Thread(new Worker(oneInterval, id, primes));

            t.start();
            sideThreads.add(t);
        }
    }

    private static void printArrayToTxt(ArrayList<Integer> primes, int[] range) {

        try {
            PrintWriter pr = new PrintWriter("Primes.txt");

            pr.println("Prime numbers found in the range: " + range.toString());
            for (int i = 0; i < primes.size(); i++) {
                pr.println(primes.get(i));
            }
            pr.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No such file exists.");
        }
    }
}
