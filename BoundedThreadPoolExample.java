
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BoundedThreadPoolExample {

    public static void main(String[] args) {
        int TOTAL_FLIGHT_BOOKINGS = 20;
        receiveAndBookFlightTicketsParallel(TOTAL_FLIGHT_BOOKINGS);
    }

    /**
     * Simple method to book given number of flights parallely by spawning 4 threads
     * @param totalBookings
     */
    public static void receiveAndBookFlightTicketsParallel(int totalBookings) {
        System.out.println("Booking " + totalBookings + " flights parallely");
        /**
         * Using factory method exposed by Executors class to get an instance of thread pool of fixed size (=4)
         * Advantages of using bounded thread pool
         * 1. Time in creating a thread is avoided by the thread pool, and reduces overall latency of operation
         * 2. System doesn't go out of memory as threads are created under limits
         * 3. Thread pool size can be fine tuned to achieve optimum performance.
         *    We should have enough threads to keep all processors busy but not so many to overwhelm the system
         */
        Executor executor = Executors.newFixedThreadPool(4);

        while(totalBookings > 0) {
            FlightBookingRequest flightBookingRequest = waitForNextFlightBookingRequest();
            executor.execute(new Runnable() {
                @Override
                public void run() {
                    flightBookingRequest.bookFlight();
                }
            });
            totalBookings--;
        }
    }

    /**
     * Helper method to generate flight booking requests at random interval of 0ms to 1000ms
     * @return FlightBookingRequest
     */
    private static FlightBookingRequest waitForNextFlightBookingRequest() {
        Random random = new Random();
        int MAX_BOUND = 1000000;
        try {
            Thread.sleep(random.nextInt(1000));
            return new FlightBookingRequest(random.nextInt(MAX_BOUND), UUID.randomUUID().toString(), UUID.randomUUID().toString());
        } catch (InterruptedException e) {
            //suppress exception
        }
        return null;
    }
}

/**
 * Helper class to model FlightBookingRequest
 */
class FlightBookingRequest {
    private int requestId;
    private String sourceCity;
    private String destinationCity;
    private boolean isBookingConfirmed;

    public FlightBookingRequest(int requestId, String sourceCity, String destinationCity) {
        this.requestId = requestId;
        this.sourceCity = sourceCity;
        this.destinationCity = destinationCity;
        this.isBookingConfirmed = false;
    }

    public void bookFlight() {
        if(!sourceCity.equals(destinationCity)) {
            System.out.println("Flight booking confirmed for requestId: " + requestId);
            this.isBookingConfirmed = true;
        }

    }
}

