import java.util.Random;
import java.util.UUID;

public class UnboundedThreadPoolExample {

    public static void main(String[] args) {
        int TOTAL_FLIGHT_BOOKINGS = 20;
        receiveAndBookFlightTicketsSequentially(TOTAL_FLIGHT_BOOKINGS);

        receiveAndBookFlightTicketsParallel(TOTAL_FLIGHT_BOOKINGS);
    }

    /**
     * Simple method to book given number of flights parallely by spawning as many threads as required
     * @param totalBookings
     */
    public static void receiveAndBookFlightTicketsParallel(int totalBookings) {
        System.out.println("Booking " + totalBookings + " flights parallely");
        while(totalBookings > 0) {
            FlightBookingRequest flightBookingRequest = waitForNextFlightBookingRequest();
            Thread newThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    flightBookingRequest.bookFlight();
                }
            });
            newThread.start();
            totalBookings--;
        }
    }

    /**
     * Simple method to book given number of flights sequentially
     * @param totalBookings
     */
    public static void receiveAndBookFlightTicketsSequentially(int totalBookings) {
        System.out.println("Booking " + totalBookings + " flights sequentially");
        while(totalBookings > 0) {
            FlightBookingRequest flightBookingRequest = waitForNextFlightBookingRequest();
            flightBookingRequest.bookFlight();
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

