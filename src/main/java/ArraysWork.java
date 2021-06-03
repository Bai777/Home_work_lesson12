import java.util.Arrays;

public class ArraysWork {

    static final int SIZE = 10_000_000;
    static final int HALF = SIZE / 2;
    static float[] arr1 = new float[SIZE];
    static float[] arr2 = new float[SIZE];
//    static float[] arrTransfer1 = new float[HALF];
//    static float[] arrTransfer2 = new float[HALF];

    public static void main(String[] args) {

        arrayOne();
        arrayTwo();

    }

    public static void arrayOne() {

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] += 1;
        }
        long start = System.currentTimeMillis();

        for (int i = 0; i < arr1.length; i++) {
            arr1[i] = (float) (arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long end = System.currentTimeMillis();
        System.out.println("Total execution time arrayOne: " + (end - start) + " ms");

    }

    public static void arrayTwo(){

        for (int i = 0; i < arr2.length; i++) {
            arr2[i] += 1;
        }

        long start = System.currentTimeMillis();

        float[] arrTransfer1 = Arrays.copyOfRange(arr2, 0,  HALF);
        float[] arrTransfer2 = Arrays.copyOfRange(arr2, HALF,  arr2.length);

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


        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return;
        }


        System.arraycopy(arrTransfer1, 0, arr2, 0, HALF);
        System.arraycopy(arrTransfer2, 0, arr2, HALF, arrTransfer2.length);

        thread1.interrupt();
        thread2.interrupt();

        long end = System.currentTimeMillis();
        System.out.println("Total execution time arrayOne: " + (end - start) + " ms");
        System.out.println("Arrays arr1 equals arr2: " + Arrays.equals(arr1, arr2));

    }
}
