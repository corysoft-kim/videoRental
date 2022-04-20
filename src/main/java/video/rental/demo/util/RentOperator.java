package video.rental.demo.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rating;

public class RentOperator {
	  public boolean isUnderAge(Customer customer) {
	        // calculate customer's age in years and months

	        // parse customer date of birth
	        Calendar calDateOfBirth = Calendar.getInstance();
	        try {
	            calDateOfBirth.setTime(new SimpleDateFormat("yyyy-MM-dd").parse(customer.getDateOfBirth().toString()));
	        } catch (ParseException e) {
	            e.printStackTrace();
	        }

	        // get current date
	        Calendar calNow = Calendar.getInstance();
	        calNow.setTime(new java.util.Date());

	        // calculate age different in years and months
	        int ageYr = (calNow.get(Calendar.YEAR) - calDateOfBirth.get(Calendar.YEAR));
	        int ageMo = (calNow.get(Calendar.MONTH) - calDateOfBirth.get(Calendar.MONTH));

	        // decrement age in years if month difference is negative
	        if (ageMo < 0) {
	            ageYr--;
	        }
	        int age = ageYr;

	        // determine if customer is under legal age for rating
	        switch (videoRating) {
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
