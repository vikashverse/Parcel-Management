package com.tcs.ilp.ParcelManagement.service;

import com.tcs.ilp.ParcelManagement.model.Booking;
import com.tcs.ilp.ParcelManagement.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    /** Save a new booking (called after customer submits booking form) */
    public Booking createBooking(Booking booking) {
        return bookingRepository.save(booking);
    }

    /** Get all bookings for a customer */
    public List<Booking> getBookingsByCustomer(String customerUserId) {
        return bookingRepository.findByCustomerUserIdOrderByBookedAtDesc(customerUserId);
    }

    /** Get all bookings — for officer view */
    public List<Booking> getAllBookings() {
        return bookingRepository.findAllByOrderByBookedAtDesc();
    }

    /** Find a single booking by its booking ID string */
    public Optional<Booking> findByBookingId(String bookingId) {
        return bookingRepository.findByBookingId(bookingId);
    }

    /** Officer updates status + optional fields */
    public Booking updateStatus(String bookingId, String status,
                                String currentLocation, String deliveryAgent,
                                String expectedDeliveryDate, String remarks,
                                String assignedOfficer) {
        Optional<Booking> opt = bookingRepository.findByBookingId(bookingId);
        if (opt.isEmpty()) return null;

        Booking b = opt.get();
        if (status != null && !status.isBlank())               b.setStatus(status);
        if (currentLocation != null && !currentLocation.isBlank()) b.setCurrentLocation(currentLocation);
        if (deliveryAgent != null && !deliveryAgent.isBlank())     b.setDeliveryAgent(deliveryAgent);
        if (expectedDeliveryDate != null && !expectedDeliveryDate.isBlank()) b.setExpectedDeliveryDate(expectedDeliveryDate);
        if (remarks != null && !remarks.isBlank())             b.setRemarks(remarks);
        if (assignedOfficer != null && !assignedOfficer.isBlank()) b.setAssignedOfficer(assignedOfficer);

        return bookingRepository.save(b);
    }

    /** Officer schedules pickup — sets assignedOfficer + status to Picked Up */
    public Booking schedulePickup(String bookingId, String assignedOfficer,
                                  String pickupDateTime, String currentLocation) {
        Optional<Booking> opt = bookingRepository.findByBookingId(bookingId);
        if (opt.isEmpty()) return null;

        Booking b = opt.get();
        b.setAssignedOfficer(assignedOfficer);
        b.setPickupDateTime(pickupDateTime);
        b.setCurrentLocation(currentLocation);
        b.setStatus("Picked Up");
        return bookingRepository.save(b);
    }

    /** Search bookings (officer) */
    public List<Booking> search(String query) {
        if (query == null || query.isBlank()) return getAllBookings();
        return bookingRepository.search(query);
    }

    /** Stats for officer dashboard */
    public long countByStatus(String status) {
        return bookingRepository.countByStatus(status);
    }

    public long countAll() {
        return bookingRepository.count();
    }
}
