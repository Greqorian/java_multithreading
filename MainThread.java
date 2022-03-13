import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainThread {

    private static int[] threads = { 1, 2, 4, 8 };
    private static int[] range = { 15, 270 };

    private static ArrayList<Thread> sideThreads = new ArrayList<Thread>();
    private static ArrayList<Integer[]> intervalle = new ArrayList<Integer[]>();
    private static ArrayList<Integer> primes = new ArrayList<Integer>();
    private static ArrayList<String> timeReport = new ArrayList<String>();

    /**
     * Pawlak Gregor 563317
     * Übung 2
     * Nebenläufige Programmierung
     *
     */
    public static void main(String[] args) {

        MainThread MeinThread = new MainThread();
        MainThread.printConsoleToTxt();

        for (int i = 0; i < threads.length; i++) {

            sideThreads.clear();
            intervalle.clear();
            primes.clear();

            intervalle = MeinThread.getIntervalls(range, threads[i]);

            System.out.println("");
            System.out.println("Thread ID: 0 Start the main Thread");
            System.out.println("Numbers range: " + Arrays.toString(range));
            System.out.println("Pararell threads: " + intervalle.size());
            System.out.println("-------------------");

            long startTime = System.nanoTime();

            MainThread.startWorkers(intervalle, primes, sideThreads);
            MainThread.joinThreads(sideThreads);
           
            long endTime = System.nanoTime();
            long totalTime = endTime - startTime;

            System.out.println("-------  End of processing ------------");
            System.out.println("Thread ID: 0 All found Primes: " + primes.toString());
            System.out.println("Processing time in nanoseconds: " + totalTime);

            timeReport.add("Threads: " + threads[i] + " Total processing time: " + totalTime);

            MainThread.printArrayToTxt(primes, range);

        }

        System.out.println("");
        System.out.println("-------------------- Time for number of threads summary -----------------------");
        for (int z = 0; z < timeReport.size(); z++) {
            System.out.println(timeReport.get(z));
        }
        System.out.println("Die Aufteilung der Aufgabe auf mehrere Threads hat einen messbaren Vorteil für die Verarbeitungszeit gebracht. Das beste Ergebnis wurde bei 2 Threads erzielt.");
        System.out.println("Splitting the task into multiple threads yielded a measurable benefit in processing time. The best time result was obtained for 2 threads.");
    }

     /**
     * Divides range of numbers through given number of threads 
     *
     * @param range range of numbers to divide 
     * @param threads number of threads  
     * @return list of threads
     */
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

    /**
     * Initialise Workers which return primes and threads
     *
     * @param intervals array of intervals  
     * @param primes array of prime number
     * @param sideThreads array of threads
     */
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

    /**
     * Joins threads 
     *
     * @param sideThreads array of threads
     */
    private static void joinThreads(ArrayList<Thread> sideThreads) {
        for (int y = 0; y < sideThreads.size(); y++) {
            try {
                sideThreads.get(y).join();
            } catch (InterruptedException e) {
            }

        }

    }

     /**
     * Prints primes to primes.text 
     *
     * @param primes prime numbers
     * @param range numbers in range
     */
    private static void printArrayToTxt(ArrayList<Integer> primes, int[] range) {

        boolean append = false;
        boolean autoFlush = false;

        try {
            PrintStream pr = new PrintStream(
                    new FileOutputStream("primes.txt", append), autoFlush);

            pr.println("Prime numbers found in the range: " + Arrays.toString(range));
            pr.println("-----------------------");

            for (int i = 0; i < primes.size(); i++) {
                pr.println(primes.get(i));
            }
            pr.close();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("No such file exists.");
        }
        System.out.println("Prime numbers saved to file: Primes.txt");
    }

     /**
     * Prints console output to file
     *
     */
    private static void printConsoleToTxt() {

        boolean append = true;
        boolean autoFlush = false;

        try {

            PrintStream out = new PrintStream(
                    new FileOutputStream("consoleOutput.txt", append), autoFlush);
            out.println("------- Console output ---------");
            System.setOut(out);

        } catch (IOException e1) {
            System.out.println("Error during reading/writing");
        }

    }

}
