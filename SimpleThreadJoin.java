public class SimpleThreadJoin {

    public static void main(String[] args) throws InterruptedException {

        Thread internalThread = new Thread(new MyTask());

        /**
         * A DAEMON thread runs in the background,
         * and as soon as main threads stops, all daemon threads are stopped by JVM
         *
         * If spawned thread is not marked daemon,
         * then JVM will keep waiting (to end the process) for spawned thread to finish,
         * even if execution of main thread is complete
         *
         * Go ahead, Try to comment out the below line and see what happens
         */
        internalThread.setDaemon(true);

        internalThread.start();

        /**
         * If the join method is not called on the spawned thread, and main thread finishes its execution first
         * then JVM will kill all Daemon threads with exit of main thread,
         * but still wait for Non-Daemon threads to complete there execution
         *
         * So it's always a good idea (depends on use-case) to call join on the spawned thread
         * Go ahead, Try to comment out the below line and see what happens
         */
        internalThread.join();

        System.out.println("Main application thread execution complete");
    }
}

class MyTask implements Runnable {
    @Override
    public void run() {
        int maxIteration = 20;
        while(maxIteration > 0) {
            try {
                /**
                 * A thread can be suspended for a certain time by using the sleep method
                 * 
                 * However making threads sleep to wait for other threads to finish 
                 * (for co-ordination/synchronization among threads) is not a good practice
                 */
                Thread.sleep(500);
                System.out.println("Hello, I'm a thread executing MyTask");
                maxIteration--;
            } catch (InterruptedException e) {
                // Suppress  exception
            }
        }
    }
}

