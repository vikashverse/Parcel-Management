package com.tcs.ilp.ParcelManagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
@Table(name = "bookings")
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Auto-generated human-readable booking ID e.g. BZ83421
    @Column(unique = true, nullable = false)
    private String bookingId;

    // ── Sender ──────────────────────────────────────────────
    private String senderName;
    private String senderAddress;
    private String senderPincode;
    private String senderContact;

    // ── Receiver ─────────────────────────────────────────────
    private String receiverName;
    private String receiverAddress;
    private String receiverPincode;
    private String receiverContact;

    // ── Parcel details ────────────────────────────────────────
    private Double weight;
    private String size;          // small / medium / large
    private String shippingSpeed; // standard / express
    private String packaging;     // standard / eco / fragile
    private String description;

    // ── Scheduling ────────────────────────────────────────────
    private String pickupDateTime;   // e.g. "2026-05-21 9:00 AM - 11:00 AM"
    private String paymentMethod;    // cod / online

    // ── Financials ────────────────────────────────────────────
    private Double amount;

    // ── Status (officer updates this) ─────────────────────────
    // Pending → Picked Up → In Transit → At Hub → Out for Delivery → Delivered
    private String status = "Pending";

    // Officer fields
    private String assignedOfficer;
    private String currentLocation;
    private String deliveryAgent;
    private String expectedDeliveryDate;
    private String remarks;

    // ── Timestamps ────────────────────────────────────────────
    private LocalDateTime bookedAt;
    private LocalDateTime updatedAt;

    // ── Linked customer ──────────────────────────────────────
    private String customerUserId;   // FK-like reference to users.userId
    private String customerEmail;

    @PrePersist
    public void prePersist() {
        this.bookedAt  = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        if (this.bookingId == null || this.bookingId.isBlank()) {
            this.bookingId = "BZ" + System.currentTimeMillis() % 100000;
        }
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ── Helpers ───────────────────────────────────────────────
    public String getBookedAtFormatted() {
        if (bookedAt == null) return "—";
        return bookedAt.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
    }

    public String getUpdatedAtFormatted() {
        if (updatedAt == null) return "—";
        return updatedAt.format(DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm"));
    }

    // ── Getters & Setters ─────────────────────────────────────
    public Long getId() { return id; }

    public String getBookingId() { return bookingId; }
    public void setBookingId(String bookingId) { this.bookingId = bookingId; }

    public String getSenderName() { return senderName; }
    public void setSenderName(String senderName) { this.senderName = senderName; }

    public String getSenderAddress() { return senderAddress; }
    public void setSenderAddress(String senderAddress) { this.senderAddress = senderAddress; }

    public String getSenderPincode() { return senderPincode; }
    public void setSenderPincode(String senderPincode) { this.senderPincode = senderPincode; }

    public String getSenderContact() { return senderContact; }
    public void setSenderContact(String senderContact) { this.senderContact = senderContact; }

    public String getReceiverName() { return receiverName; }
    public void setReceiverName(String receiverName) { this.receiverName = receiverName; }

    public String getReceiverAddress() { return receiverAddress; }
    public void setReceiverAddress(String receiverAddress) { this.receiverAddress = receiverAddress; }

    public String getReceiverPincode() { return receiverPincode; }
    public void setReceiverPincode(String receiverPincode) { this.receiverPincode = receiverPincode; }

    public String getReceiverContact() { return receiverContact; }
    public void setReceiverContact(String receiverContact) { this.receiverContact = receiverContact; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }

    public String getSize() { return size; }
    public void setSize(String size) { this.size = size; }

    public String getShippingSpeed() { return shippingSpeed; }
    public void setShippingSpeed(String shippingSpeed) { this.shippingSpeed = shippingSpeed; }

    public String getPackaging() { return packaging; }
    public void setPackaging(String packaging) { this.packaging = packaging; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getPickupDateTime() { return pickupDateTime; }
    public void setPickupDateTime(String pickupDateTime) { this.pickupDateTime = pickupDateTime; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getAssignedOfficer() { return assignedOfficer; }
    public void setAssignedOfficer(String assignedOfficer) { this.assignedOfficer = assignedOfficer; }

    public String getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(String currentLocation) { this.currentLocation = currentLocation; }

    public String getDeliveryAgent() { return deliveryAgent; }
    public void setDeliveryAgent(String deliveryAgent) { this.deliveryAgent = deliveryAgent; }

    public String getExpectedDeliveryDate() { return expectedDeliveryDate; }
    public void setExpectedDeliveryDate(String expectedDeliveryDate) { this.expectedDeliveryDate = expectedDeliveryDate; }

    public String getRemarks() { return remarks; }
    public void setRemarks(String remarks) { this.remarks = remarks; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }

    public String getCustomerUserId() { return customerUserId; }
    public void setCustomerUserId(String customerUserId) { this.customerUserId = customerUserId; }

    public String getCustomerEmail() { return customerEmail; }
    public void setCustomerEmail(String customerEmail) { this.customerEmail = customerEmail; }
}
