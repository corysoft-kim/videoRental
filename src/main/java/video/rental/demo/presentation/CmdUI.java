package video.rental.demo.presentation;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Scanner;

import video.rental.demo.application.InteractorImpl;
import video.rental.demo.domain.Customer;
import video.rental.demo.domain.Rating;

public class CmdUI {
    static final Scanner scanner = new Scanner(System.in);
    
    private InteractorImpl interactorImpl;
    
    public CmdUI(InteractorImpl interactorImpl) {
		super();
		this.interactorImpl = interactorImpl;
	}

	public void start() {
        scanner.useDelimiter("\\r\\n|\\n");

        boolean quit = false;
        while (!quit) {
            //@formatter:off
            try {
                switch (getCommand()) {
                    case 0: quit = true;            break;
                    case 1: listCustomers();        break;
                    case 2: listVideos();           break;
                    case 3: registerCustomer();     break;
                    case 4: registerVideo();        break;
                    case 5: rentVideo();            break;
                    case 6: returnVideo();          break;
                    case 7: getCustomerReport();    break;
                    case 8: clearRentals();         break;
                    default:                        break;
                }
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
            //@formatter:on
        }
        System.out.println("Bye");
    }

    public void clearRentals() {
        System.out.println("Enter customer code: ");
        int customerCode = scanner.nextInt();

        System.out.println(interactorImpl.clearRental(customerCode));
    }

	public void returnVideo() {
        System.out.println("Enter customer code: ");
        int customerCode = scanner.nextInt();

        System.out.println("Enter video title to return: ");
        String videoTitle = scanner.next();

        interactorImpl.returnVideo(customerCode, videoTitle);
    }

	public void listVideos() {
        System.out.println("List of videos");

        System.out.print(interactorImpl.listVideos());
        System.out.println("End of list");
    }

	public void listCustomers() {
        System.out.println("List of customers");

        interactorImpl.listCustomers();
        System.out.println("End of list");
    }

	public void getCustomerReport() {
        System.out.println("Enter customer code: ");
        int code = scanner.nextInt();
        System.out.println(interactorImpl.getCustomerReport(code).getReport());
    }

	public void rentVideo() {
        System.out.println("Enter customer code: ");
        int code = scanner.nextInt();

        System.out.println("Enter video title to rent: ");
        String videoTitle = scanner.next();
        
        interactorImpl.rentVideo(code, videoTitle);
    }

	//control coupling
	public void registerCustomer() {
		System.out.println("Enter customer name: ");
		String name = scanner.next();

		System.out.println("Enter customer code: ");
		int code = scanner.nextInt();

		System.out.println("Enter customer birthday (YYYY-MM-DD): ");
		String dateOfBirth = scanner.next();

		try {
			new SimpleDateFormat("yyyy-MM-dd").parse(dateOfBirth);
		} catch (Exception ignored) {
		}

		interactorImpl.registerCustomer(name, code, dateOfBirth);
	}
	
	public void registerVideo() {
		System.out.println("Enter video title to register: ");
		String title = scanner.next();

		System.out.println("Enter video type( 1 for VHD, 2 for CD, 3 for DVD ):");
		int videoType = scanner.nextInt();

		System.out.println("Enter price code( 1 for Regular, 2 for New Release 3 for Children ):");
		int priceCode = scanner.nextInt();

		System.out.println("Enter video rating( 1 for 12, 2 for 15, 3 for 18 ):");
		int videoRating = scanner.nextInt();

		Rating rating;
		if (videoRating == 1) rating = Rating.TWELVE;
		else if (videoRating == 2) rating = Rating.FIFTEEN;
		else if (videoRating == 3) rating = Rating.EIGHTEEN;
		else throw new IllegalArgumentException("No such rating " + videoRating);

		interactorImpl.registerVideo(title, videoType, priceCode, LocalDate.now(), rating);
    }

	public int getCommand() {
        System.out.println("\nSelect a command !");
        System.out.println("\t 0. Quit");
        System.out.println("\t 1. List customers");
        System.out.println("\t 2. List videos");
        System.out.println("\t 3. Register customer");
        System.out.println("\t 4. Register video");
        System.out.println("\t 5. Rent video");
        System.out.println("\t 6. Return video");
        System.out.println("\t 7. Show customer report");
        System.out.println("\t 8. Show customer and clear rentals");

        return scanner.nextInt();
    }
}
