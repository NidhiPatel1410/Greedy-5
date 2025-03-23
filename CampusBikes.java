// In this problem, first iterating over the workers and bikes array, and computing the manhattan distance between each worker and each
// bike and storing it in a treemap, so that we get the distance in sorted order. Treemap has keys as the distance and list of int 
// arrays of worker-bike pairs having that distance as value. Now iterating over the treemap and assigning the bike to each worker 
// one by one.

// Time Complexity : O(mnlogmn) m - length of bikes array, n - length of workers array, and log because inserting/removal from 
// treemap takes logarithmic time
// Space Complexity : O(mn)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
import java.util.*;

class CampusBikes {
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        // Base case
        if (workers == null || workers.length == 0 || bikes == null || bikes.length == 0) {
            return new int[] {};
        }

        int n = workers.length;
        int m = bikes.length;
        // Result array for storing bike assigned to each worker
        int[] result = new int[n];
        // Treemap for storing the distance:worker-bike pairs
        TreeMap<Integer, List<int[]>> map = new TreeMap<>();
        // Boolean arrays for keeping track of available bikes and workers that have the
        // bikes assigned
        boolean[] assignedBikes = new boolean[m];
        boolean[] assignedWorkers = new boolean[n];
        // Keep a count n, so that when we are done assigning all the workers one bike,
        // we can break out of loop
        int count = n;
        // Loop for workers
        for (int i = 0; i < n; i++) {
            // Loop for bikes
            for (int j = 0; j < m; j++) {
                // Calc distance
                int dist = findManhattanDist(workers[i], bikes[j]);
                // Store it in map
                if (!map.containsKey(dist)) {
                    map.put(dist, new ArrayList<>());
                }
                map.get(dist).add(new int[] { i, j });
            }
        }
        // Iterate over map
        for (int key : map.keySet()) {
            // Get the list
            List<int[]> curr = map.get(key);
            // Loop over list
            for (int i = 0; i < curr.size(); i++) {
                // One by one assign the bikes if not yet assigned and if bike available
                int[] wb = curr.get(i);
                // If not yet assigned
                if (!assignedWorkers[wb[0]]) {
                    // If bike available
                    if (!assignedBikes[wb[1]]) {
                        // Mark now both true so that next time, it becomes unavailable
                        assignedBikes[wb[1]] = true;
                        assignedWorkers[wb[0]] = true;
                        // Assign it to the worker
                        result[wb[0]] = wb[1];
                        // Decrement count
                        count--;
                        // Whenever all workers get assign with the bike, break
                        if (count == 0) {
                            break;
                        }
                    }
                }
            }
        }
        // Return result
        return result;
    }

    private int findManhattanDist(int[] worker, int[] bike) {
        // For manhattan distance, take the abs distance
        return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
    }

    public static void main(String[] args) {
        int[][] workers = new int[][] { { 0, 0 }, { 1, 1 }, { 2, 0 } };
        int[][] bikes = new int[][] { { 1, 0 }, { 2, 2 }, { 2, 1 } };
        CampusBikes cb = new CampusBikes();
        int[] res = cb.assignBikes(workers, bikes);
        for (int a : res) {
            System.out.println(a);
        }
    }
}

// Optimize time using hashmap:
// In this problem, first iterating over the workers and bikes array, and
// computing the manhattan distance between each worker and each
// bike and storing it in a hashmap, meanwhile also maintaining the min and max
// distance. So that when we loop over the hashmap, we
// can loop from min to max (indirectly looping in sorted order - So
// insertion/removal from hashmap takes O(1) times). Hashmap has
// same keys as the distance and list of int arrays of worker-bike pairs having
// that distance as value. Now iterating over the hashmap
// and assigning the bike to each worker one by one.

// Time Complexity : O(mn) m - length of bikes array, n - length of workers
// array
// Space Complexity : O(mn)
// Did this code successfully run on Leetcode : yes
// Any problem you faced while coding this : no
class CampusBikes {
    public int[] assignBikes(int[][] workers, int[][] bikes) {
        // Base case
        if (workers == null || workers.length == 0 || bikes == null || bikes.length == 0) {
            return new int[] {};
        }

        int n = workers.length;
        int m = bikes.length;
        // Result array for storing bike assigned to each worker
        int[] result = new int[n];
        // Hashmap for storing the distance:worker-bike pairs
        HashMap<Integer, List<int[]>> map = new HashMap<>();
        // Boolean arrays for keeping track of available bikes and workers that have the
        // bikes assigned
        boolean[] assignedBikes = new boolean[m];
        boolean[] assignedWorkers = new boolean[n];
        // Min and max for sorted traversal of map
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        // Keep a count n, so that when we are done assigning all the workers one bike,
        // we can break out of loop
        int count = n;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                // Calc dist
                int dist = findManhattanDist(workers[i], bikes[j]);
                // Store min and max
                min = Math.min(min, dist);
                max = Math.max(max, dist);
                // Put in map
                if (!map.containsKey(dist)) {
                    map.put(dist, new ArrayList<>());
                }
                map.get(dist).add(new int[] { i, j });
            }
        }
        // Loop over hashmap from min to max
        for (int j = min; j <= max; j++) {
            // If the distance is not in map, curr will be null, in that case continue
            List<int[]> curr = map.get(j);
            if (curr == null) {
                continue;
            }
            // Else loop to that list
            for (int i = 0; i < curr.size(); i++) {
                // Take one pair at a time
                int[] wb = curr.get(i);
                // Assign if not yet done
                if (!assignedWorkers[wb[0]]) {
                    if (!assignedBikes[wb[1]]) {
                        assignedBikes[wb[1]] = true;
                        assignedWorkers[wb[0]] = true;
                        result[wb[0]] = wb[1];
                        count--;
                        if (count == 0) {
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }

    private int findManhattanDist(int[] worker, int[] bike) {
        return Math.abs(worker[0] - bike[0]) + Math.abs(worker[1] - bike[1]);
    }

    public static void main(String[] args) {
        int[][] workers = new int[][] { { 0, 0 }, { 1, 1 }, { 2, 0 } };
        int[][] bikes = new int[][] { { 1, 0 }, { 2, 2 }, { 2, 1 } };
        CampusBikes cb = new CampusBikes();
        int[] res = cb.assignBikes(workers, bikes);
        for (int a : res) {
            System.out.println(a);
        }
    }
}