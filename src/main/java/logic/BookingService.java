package logic;

import java.util.ArrayList;

public class BookingService {
    private Booking bookingReference;
    private ArrayList<Ordering> orderings;

    public BookingService(Booking bookingReference) {
        this.bookingReference = bookingReference;
        this.orderings = new ArrayList<>();
    }

    public void addOrdering(Ordering newOrdering) {
        orderings.add(newOrdering);
    }

    public Restaurant getRestaurant() {
        return bookingReference.getRestaurant();
    }
}
