// import java.util.ArrayList;

// public class Manager {

//     int threads;
//     int[] range;

//     public Manager(int[] range, int threads) {
//         this.threads = threads;
//         this.range = range;
//     }

//     public ArrayList<Integer[]> getIntervalls(int[] range, int threads) {

//         int max = range[1] - range[0];
//         int size = Math.round((max - 1) / threads);
//         ArrayList<Integer[]> result = new ArrayList<Integer[]>();

//         for (int i = 0; i < threads; i++) {
//             int inf = i + i * size;
//             int sup = 0;

//             if (inf + size < max) {
//                 sup = inf + size;
//             } else {
//                 sup = max;
//             }
//             ;

//             Integer[] oneInterval = { inf + range[0], sup + range[0] };

//             result.add(oneInterval);

//             if (inf >= max || sup >= max)
//                 break;
//         }
//         return result;
//     }

//     public void startWorkers(ArrayList<Integer[]> intervals, ArrayList<Integer> primes) {

//         for (int i = 0; i < intervals.size(); i++) {

//             int id = i;
//             Integer[] oneInterval = intervals.get(i);

//             Thread t = new Thread(new Worker(oneInterval, id, primes));
//             t.start();

//             try {
//                 t.join();
//                 // Thread.sleep(1000);
//             } catch (InterruptedException e) {
//             }

//         }
//     }
// }
