public class SimpleThreadInterrupt {

    public static void main(String[] args) throws InterruptedException {


        Thread internalThread = new Thread(new MySimpleTask());
        internalThread.start();

        /**
         *If the main thread waits for spawned thread to finish, and spawned thread goes mischevious(or in an infinite loop)
         * then execution of main thread/process won't complete.
         * In such cases, main(spawning) thread can interrupt the spawned thread after certain time
         * by calling interrupt method on spawned thread
         *
         * If you un-comment the below join line, the main thread will wait till eternity for spawned thread to finish
         */
//        internalThread.join();

        System.out.println("Main thread going for a nap at " + System.currentTimeMillis()/1000);
        Thread.sleep(5000);   // Main thread sleeps for 5sec
        internalThread.interrupt(); // Main thread interrupts spawned thread after after waking up
        System.out.println("Main thread is saying Goodbye at " + System.currentTimeMillis()/1000);

    }
}

class MySimpleTask implements Runnable {

    @Override
    public void run() {
        System.out.println("Hey, I'm a spawned thread. Good to meet you");
        try {
            System.out.println("Sorry but I'm tired. We could talk later. I think I could use a quick nap from time: " + System.currentTimeMillis()/1000);
            Thread.sleep(1000*60);
        } catch (InterruptedException e) {
            System.out.println("Why can't you let me sleep even on a Sunday?: " + System.currentTimeMillis()/1000);
        }

    }
}

