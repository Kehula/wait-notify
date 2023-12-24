package com.company;

public class Main {

    public static void main(String[] args) throws InterruptedException {
	// write your code here
        TestClass testInstance = new TestClass();
        Thread t1 = new Thread(() -> {
//            System.out.println("Thread1 putting value");
            try {
//                testInstance.put("Thread1");
                Thread.sleep(10);
                System.out.println("Thread1 getting value " + testInstance.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        Thread t2 = new Thread(() -> {
//            System.out.println("Thread2 putting value");
            try {
//                testInstance.put("Thread2");
                Thread.sleep(10);
                System.out.println("Thread2 getting value " + testInstance.get());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t1.start();
        t2.start();
        testInstance.put("first");
        testInstance.put("second");
        t1.join();
        t2.join();
        System.out.println("main: finished");
    }

    private static final class TestClass {
        String value = null;
        synchronized void put(String newVal) throws InterruptedException {
            while(value != null)
                wait();
            value = newVal;
            notifyAll();
        }

        synchronized String get() throws InterruptedException {
            while(value == null)
                wait();
            String result = value;
            value = null;
            notifyAll();
            return result;
        }
    }
}
