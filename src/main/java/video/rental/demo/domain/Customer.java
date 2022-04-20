package video.rental.demo.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Customer {
	@Id
	private int code;
	private String name;
	private LocalDate dateOfBirth;

	@OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<Rental> rentals = new ArrayList<>();

	public Customer() {	// for hibernate
	}

	public Customer(int code, String name, LocalDate dateOfBirth) {
		this.code = code;
		this.name = name;
		this.dateOfBirth = dateOfBirth;
	}

	public Customer(Customer another) {
		this.code = another.code;
		this.name = another.name;
		this.dateOfBirth = another.dateOfBirth;
		this.rentals = new ArrayList<>(another.rentals);
	}

	public int getCode() {
		return code;
	}

	public String getName() {
		return name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public List<Rental> getRentals() {
		return rentals;
	}

	public void setRentals(List<Rental> rentals) {
		this.rentals = rentals;
	}

	public String getReport() {
		String result = "Customer Report for " + getName() + "\n";

		List<Rental> rentalList = getRentals();
		double totalCharge = 0;
		int totalPoint = 0;

		for (Rental rentalItem : rentalList) {
			int daysRented = rentalItem.getDaysRented();
			int priceCode = rentalItem.getVideo().getPriceCode();
			
			double eachCharge = getCharge(daysRented, priceCode);
			int eachPoint = getPoint(rentalItem, daysRented);

			result += "\tTitle: " + rentalItem.getVideo().getTitle() + "\tDays rented: " + daysRented + "\tCharge: " + eachCharge
					+ "\tPoint: " + eachPoint + "\n";

			totalCharge += eachCharge;
			totalPoint += eachPoint;
		}
		
		result += "Total charge: " + totalCharge + "\tTotal Point: " + totalPoint + "\n";

		if (totalPoint >= 10) {
			System.out.println("Congratulations! You earned two free coupons");
		} else if (totalPoint >= 5) {
			System.out.println("Congratulations! You earned one free coupon");
		}

		return result;
	}

	private double getCharge(int daysRented, int priceCode) {
		double charge = 0;

		switch (priceCode) {
		case Video.REGULAR:
			charge += 2;
			if (daysRented > 2)
				charge += (daysRented - 2) * 1.5;
			break;
		case Video.NEW_RELEASE:
			charge = daysRented * 3;
			break;
		case Video.CHILDREN:
			charge += 1.5;
			if (daysRented > 3)
				charge += (daysRented - 3) * 1.5;
			break;
		}
		return charge;
	}

	private int getPoint(Rental each, int daysRented) {
		int point = 0;
		point++;
		if ((each.getVideo().getPriceCode() == Video.NEW_RELEASE))
			point++;

		if (daysRented > each.getDaysRentedLimit())
			point -= Math.min(point, each.getVideo().getLateReturnPointPenalty());
		return point;
	}

}
