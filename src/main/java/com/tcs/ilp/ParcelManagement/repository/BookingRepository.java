package com.tcs.ilp.ParcelManagement.repository;

import com.tcs.ilp.ParcelManagement.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find all bookings for a specific customer
    List<Booking> findByCustomerUserIdOrderByBookedAtDesc(String customerUserId);

    // Find by booking ID string (e.g. "BZ83421")
    Optional<Booking> findByBookingId(String bookingId);

    // All bookings ordered newest first (for officer view)
    List<Booking> findAllByOrderByBookedAtDesc();

    // Search by booking ID or receiver name (officer search)
    @Query("SELECT b FROM Booking b WHERE " +
           "LOWER(b.bookingId) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(b.senderName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(b.receiverName) LIKE LOWER(CONCAT('%', :q, '%')) OR " +
           "LOWER(b.customerUserId) LIKE LOWER(CONCAT('%', :q, '%'))")
    List<Booking> search(@Param("q") String query);

    // Count by status (for officer dashboard stats)
    long countByStatus(String status);
}
