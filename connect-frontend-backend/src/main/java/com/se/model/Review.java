package com.se.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // The user leaving the review

    @ManyToOne
    @JoinColumn(name = "specialist_id", nullable = true)
    private Specialist specialist; // The specialist being reviewed (nullable if reviewing a service)

    @ManyToOne
    @JoinColumn(name = "facility_id", nullable = true)
    private Facility facility; // The service being reviewed (nullable if reviewing a specialist)

    @ManyToOne
    @JoinColumn(name = "salon_id", nullable = false)
    private Salon salon; // The salon being reviewed

    private String comment; // The review text
    private int rating; // The rating (e.g., out of 5 stars)
    private LocalDateTime timestamp; // When the review was submitted

    // Constructors
    public Review() {}

    public Review(Long id, User user, Specialist specialist, Facility facility, Salon salon, String comment, int rating, LocalDateTime timestamp) {
        this.id = id;
        this.user = user;
        this.specialist = specialist;
        this.facility = facility;
        this.salon = salon;
        this.comment = comment;
        this.rating = rating;
        this.timestamp = timestamp;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Specialist getSpecialist() {
        return specialist;
    }

    public void setSpecialist(Specialist specialist) {
        this.specialist = specialist;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
