/*
 * solution for the 0/1 knapsack problem using
 * a dynamic programming approach
 */
class Knapsack {

    /*
     * number of objects to pick from
     */
    private int n;

    /*
     * weight barrier w indicating the
     * maximum capacity of the knapsack
     */
    static int w;

    /*
     * worklist matrix with objects at row i
     * and running weight at column j
     */
    private int[][] matrix;

    private MyObject[] objects;

    Knapsack(MyObject[] objects, int weight) {
        this.objects = objects;
        n = objects.length;
        w = weight;
        matrix = getMatrix();
    }

    /*
     * initializing worklist matrix
     * with n + 1 rows and w + 1 cols
     */
    private int[][] getMatrix() {
        int[][] m = new int[n + 1][w + 1];
        // i := set of all objects from 1 to i
        for (int i = 0; i <= n; i++) {
            // j := weight capacity of knapsack
            for (int j = 0; j <= w; j++) {
                // entries := max value obtained from set i
                //            under restriction of weight j
                m[i][j] = 0;
            }
        }
        return m;
    }

    void printMatrix() {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                System.out.format("%d ", matrix[i][j]);
            }
            System.out.println();
        }
    }

    void printMaxValue() {
        System.out.format("The maximum obtainable value is %d \n", matrix[n][w]);
    }

    /*
     * backtraces worklist in order to print the picked objects
     */
    void printPickedObjects() {
        int i = n;
        int j = w;
        while (i != 0) {
            if (matrix[i][j] != matrix[i - 1][j]) {
                System.out.format("Object %d with weight = %d and value = %d\n",
                        i, objects[i - 1].getWeight(), objects[i - 1].getValue());
                j = j - objects[i - 1].getWeight();
            }
            i--;
        }
    }

    void calculateMaxValue() {

        for (int i = 1; i <= n; i++) {
            for (int j = 1; j <= w; j++) {
                /*
                 * check if current object's
                 * weight is less or eq than j
                 */
                if (objects[i - 1].getWeight() <= j) {
                    /*
                     * pick either:
                     * max value obtainable without object i
                     * or max value obtainable with object i
                     *
                     *
                     */
                    matrix[i][j] = Math.max(
                            objects[i - 1].getValue() + matrix[i - 1][j - objects[i - 1].getWeight()],
                            matrix[i - 1][j]
                    );
                } else {
                    matrix[i][j] = matrix[i - 1][j];
                }
            }
        }
    }


    public static void main(String args[]) {
        /*
         * finite set of objects, each with
         * a weight w and a value v
         */
        MyObject[] myObjects = {
                new MyObject(2, 10),
                new MyObject(3, 100),
                new MyObject(1, 90),
                new MyObject(4, 50)
        };

        /*
         * create a knapsack with the objects listed above
         * and a capacity of e.g. 5
         */
        Knapsack knapsack = new Knapsack(myObjects, 5);

        knapsack.calculateMaxValue();

        knapsack.printMatrix();

        knapsack.printMaxValue();

        knapsack.printPickedObjects();


    }
}

