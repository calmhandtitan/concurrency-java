public class SimpleJavaThread {

    public static void main(String[] args) {

        // Thread execution by creating an ANON class that implements the Runnable interface
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Hello I am simple task from Anonymous class");
            }
        });
        thread.start();


        // Thread execution by creating a Class that implements the Runnable interface
        Thread thread1 = new Thread(new SimpleTask());
        thread1.start();

        // Thread execution by creating a Class that extends the Thread class itself
        SimpleTaskForThread simpleTaskForThread = new SimpleTaskForThread();
        simpleTaskForThread.start();
    }
}

class SimpleTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Hello I'm a simple task from SimpleTask class");
    }
}

class SimpleTaskForThread extends Thread {
    @Override
    public void run() {
        System.out.println("Hello I am a simple task from SimpleTaskForThread class");
    }
}
