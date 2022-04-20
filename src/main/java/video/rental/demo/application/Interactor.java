package video.rental.demo.application;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rating;
import video.rental.demo.domain.Rental;
import video.rental.demo.domain.Repository;
import video.rental.demo.domain.Video;

public class Interactor {
	private Repository repository;
	
	public Interactor(Repository repository) {
		this.repository = repository;
	}
	public String clearRental(int customerCode) {
		StringBuilder builder = new StringBuilder();
		Customer foundCustomer = repository.findCustomerById(customerCode);
	    if (foundCustomer == null) {
	        throw new IllegalArgumentException("No such customer exists");
	    }
	
	    builder.append("Id: " + foundCustomer.getCode() + ", "
	            + "Name: " + foundCustomer.getName() + ", "
	            + "Rentals: " + foundCustomer.getRentals().size() + "\n");
	    for (Rental rental : foundCustomer.getRentals()) {
	        builder.append("\tTitle: " + rental.getVideo().getTitle() + ", "
	                + "Price Code: " + rental.getVideo().getPriceCode());
	    }
	
	    foundCustomer.setRentals(new ArrayList<Rental>());
	    repository.saveCustomer(foundCustomer);
	    
	    return builder.toString();
	}

	public void returnVideo(int customerCode, String videoTitle) {
		Customer foundCustomer = repository.findCustomerById(customerCode);
	    if (foundCustomer == null) {
	        throw new IllegalArgumentException("No such customer exists");
	    }
	    
	    List<Rental> customerRentals = foundCustomer.getRentals();
	
	    for (Rental rental : customerRentals) {
	        if (rental.getVideo().getTitle().equals(videoTitle) && rental.getVideo().isRented()) {
	            Video video = rental.returnVideo();
	            video.setRented(false);
	            repository.saveVideo(video);
	            break;
	        }
	    }
	
	    repository.saveCustomer(foundCustomer);
	}

	public Customer getCustomerReport(int code) {
	    Customer foundCustomer = repository.findCustomerById(code);
	    if (foundCustomer == null) {
	        throw new IllegalArgumentException("No such customer exists");
	    }
		return foundCustomer;
	}

	public void rentVideo(int code, String videoTitle) {
		Customer foundCustomer = repository.findCustomerById(code);
	    if (foundCustomer == null)
	        throw new IllegalArgumentException("No such customer exists");
	
	    Video foundVideo = repository.findVideoByTitle(videoTitle);
	    if (foundVideo == null)
	        throw new IllegalArgumentException("Cannot find the video " + videoTitle);
	    if (foundVideo.isRented())
	        throw new IllegalStateException("The video " + videoTitle + " is already rented");
	
	    if (foundVideo.rentFor(foundCustomer)) {
	        repository.saveVideo(foundVideo);
	        repository.saveCustomer(foundCustomer);
	    } else {
	        throw new IllegalStateException("Customer " + foundCustomer.getName()
	                + " cannot rent this video because he/she is under age.");
	    }
	}

	public void registerCustomer(String name, int code, String dateOfBirth) {
		// dirty hack for the moment
		if (repository.findAllCustomers().stream().mapToInt(Customer::getCode).anyMatch(c -> c == code)) {
		    throw new IllegalArgumentException("Customer code " + code + " already exists");
		}
	
		repository.saveCustomer(new Customer(code, name, LocalDate.parse(dateOfBirth)));
	}

	public void registerVideo(String title, int videoType, int priceCode, LocalDate registeredDate, Rating rating) {
		// dirty hack for the moment
		if (repository.findAllVideos().stream().map(Video::getTitle).anyMatch(t -> t.equals(title))) {
		    throw new IllegalArgumentException("Video " + title + " already exists");
		}
	
		repository.saveVideo(new Video(title, videoType, priceCode, rating, registeredDate));
	}

	public String listVideos() {
		StringBuilder builder = new StringBuilder();
		for (Video video : repository.findAllVideos()) {
			builder.append("Video type: " + video.getVideoType() + ", "
	                + "Price code: " + video.getPriceCode() + ", "
	                + "Rating: " + video.getVideoRating() + ", "
	                + "Title: " + video.getTitle() + "\n");
	    }
		return builder.toString();
	}

	public String listCustomers() {
		StringBuilder builder = new StringBuilder();
		for (Customer customer : repository.findAllCustomers()) {
			builder.append("ID: " + customer.getCode() + ", "
	                + "Name: " + customer.getName() + ", "
	                + "Rentals: " + customer.getRentals().size() + "\n");
	        for (Rental rental : customer.getRentals()) {
	            builder.append("\tTitle: " + rental.getVideo().getTitle() + ", "
	                    + "Price Code: " + rental.getVideo().getPriceCode() + ", "
	                    + "Return Status: " + rental.getStatus() + "\n");
	        }
	    }
		return builder.toString();
	}
}
