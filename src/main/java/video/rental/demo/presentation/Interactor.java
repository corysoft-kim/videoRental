package video.rental.demo.presentation;

import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rating;

import java.time.LocalDate;

public interface Interactor {
    String clearRental(int customerCode);

    void returnVideo(int customerCode, String videoTitle);

    Customer getCustomerReport(int code);

    void rentVideo(int code, String videoTitle);

    void registerCustomer(String name, int code, String dateOfBirth);

    void registerVideo(String title, int videoType, int priceCode, LocalDate registeredDate, Rating rating);

    String listVideos();

    String listCustomers();
}
