import java.util.Arrays;

public class ArraysWork {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] arr = new float[SIZE];
    static float[] arrTransfer1 = new float[HALF];
    static float[] arrTransfer2 = new float[HALF];

    public static void main(String[] args) {

        arrayOne();
        arrayTwo();

    }

    public static void arrayOne() {

        for (int i = 0; i < arr.length; i++) {
            arr[i] += 1;
        }
        long start = System.currentTimeMillis();

        for (int i = 0; i < arr.length; i++) {
            arr[i] = (float) (arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println("Total execution time arrayOne: " + (end - start) + " ms");

    }

    public static void arrayTwo() {

        for (int i = 0; i < arr.length; i++) {
            arr[i] += 1;
        }

        long start = System.currentTimeMillis();

        System.arraycopy(arr, 0, arrTransfer1, 0, HALF);
        System.arraycopy(arr, HALF, arrTransfer2, 0, HALF);

        Thread thread1 = new Thread(() -> {

            for (int i = 0; i < HALF; i++) {
                arrTransfer1[i] = (float) (arrTransfer1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }

        });


        Thread thread2 = new Thread(() -> {

            for (int i = 0; i < HALF; i++) {
                arrTransfer2[i] = (float) (arrTransfer2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }

        });
        thread1.start();
        thread2.start();

        System.arraycopy(arrTransfer1, 0, arr, 0, HALF);
        System.arraycopy(arrTransfer2, 0, arr, HALF, HALF);

        thread1.interrupt();
        thread2.interrupt();

        long end = System.currentTimeMillis();
        System.out.println("Total execution time arrayOne: " + (end - start) + " ms");

    }
}
