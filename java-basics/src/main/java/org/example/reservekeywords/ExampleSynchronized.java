package org.example.reservekeywords;

public class ExampleSynchronized {

    static int counter = 0;

    synchronized static void increment() {
        for (int i = 0; i < 1000000; i++) {
            counter++;
        }
    }

    public static void main(String[] args) throws InterruptedException {

        Thread thread1 = new Thread(ExampleSynchronized::increment);
        Thread thread2 = new Thread(ExampleSynchronized::increment);

        thread1.start();
        thread2.start();

        thread2.join();
        thread1.join();

        System.out.println(counter);

    }
}
