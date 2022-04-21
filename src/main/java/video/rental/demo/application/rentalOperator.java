package video.rental.demo.application;

import java.util.List;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rental;
import video.rental.demo.domain.Video;

public class rentalOperator {
	
    public static boolean rentFor(Customer customer, Video video) {
        if (!isUnderAge(customer, video)) {
        	video.setRented(true);
            Rental rental = new Rental(video);
            List<Rental> customerRentals = customer.getRentals();
            customerRentals.add(rental);
            customer.setRentals(customerRentals);
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean isUnderAge(Customer customer, Video video) {
        int age = customer.getAge();

        // determine if customer is under legal age for rating
        switch (video.getVideoRating()) {
            case TWELVE:
                return age < 12;
            case FIFTEEN:
                return age < 15;
            case EIGHTEEN:
                return age < 18;
            default:
                return false;
        }
    }
}
