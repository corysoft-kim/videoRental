package video.rental.demo.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "VIDEO", uniqueConstraints = {@UniqueConstraint(columnNames = {"title"})})
public class Video {
    @Id
    private String title;
    private Rating videoRating;
    private int priceCode;

    public static final int REGULAR = 1;
    public static final int NEW_RELEASE = 2;
    public static final int CHILDREN = 3;

    private int videoType;
    public static final int VHS = 1;
    public static final int CD = 2;
    public static final int DVD = 3;

    private LocalDate registeredDate;
    private boolean rented;

    public Video() {
    } // for hibernate

    public Video(String title, int videoType, int priceCode, Rating videoRating, LocalDate registeredDate) {
        this.title = title;
        this.videoType = videoType;
        this.priceCode = priceCode;
        this.videoRating = videoRating;
        this.registeredDate = registeredDate;
        this.rented = false;
    }

    public Video(Video another) {
        this.title = another.title;
        this.videoRating = another.videoRating;
        this.priceCode = another.priceCode;
        this.videoType = another.videoType;
        this.registeredDate = another.registeredDate;
        this.rented = another.rented;
    }

    public int getLateReturnPointPenalty() {
        //@formatter:off
        switch (videoType) {
            case VHS: return 1;
            case CD : return 2;
            case DVD: return 3; 
        }
        //@formatter:on
        return 0;
    }

    public int getPriceCode() {
        return priceCode;
    }

    public void setPriceCode(int priceCode) {
        this.priceCode = priceCode;
    }

    public String getTitle() {
        return title;
    }

    public Rating getVideoRating() {
        return videoRating;
    }

    public boolean isRented() {
        return rented;
    }

    public void setRented(boolean rented) {
        this.rented = rented;
    }

    public LocalDate getRegisteredDate() {
        return registeredDate;
    }

    public int getVideoType() {
        return videoType;
    }
}
