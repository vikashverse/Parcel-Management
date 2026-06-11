package com.tcs.ilp.ParcelManagement.controller;

import com.tcs.ilp.ParcelManagement.model.Booking;
import com.tcs.ilp.ParcelManagement.service.BookingService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class CustomerController {

    @Autowired
    private BookingService bookingService;

    // ── Guard helper ──────────────────────────────────────────
    private boolean notLoggedIn(HttpSession session) {
        return session.getAttribute("username") == null;
    }

    // ── Pages ─────────────────────────────────────────────────

    @GetMapping("/home")
    public String home(HttpSession session) {
        if (notLoggedIn(session)) return "redirect:/login";
        return "CustomerHome";
    }

    @GetMapping("/booking")
    public String bookingPage(HttpSession session) {
        if (notLoggedIn(session)) return "redirect:/login";
        return "booking";
    }

    @GetMapping("/support")
    public String support(HttpSession session) {
        if (notLoggedIn(session)) return "redirect:/login";
        return "CustomerSupport";
    }

    // ── Booking form POST → save to DB ────────────────────────
    @PostMapping("/booking/submit")
    public String submitBooking(@RequestParam String senderName,
                                @RequestParam String senderAddress,
                                @RequestParam String senderPincode,
                                @RequestParam String senderContact,
                                @RequestParam String receiverName,
                                @RequestParam String receiverAddress,
                                @RequestParam String receiverPincode,
                                @RequestParam String receiverContact,
                                @RequestParam Double weight,
                                @RequestParam String size,
                                @RequestParam String shippingSpeed,
                                @RequestParam String packaging,
                                @RequestParam(required = false) String description,
                                @RequestParam String pickupDate,
                                @RequestParam String pickupTime,
                                @RequestParam String paymentMethod,
                                @RequestParam Double amount,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        if (notLoggedIn(session)) return "redirect:/login";

        Booking b = new Booking();
        b.setSenderName(senderName);
        b.setSenderAddress(senderAddress);
        b.setSenderPincode(senderPincode);
        b.setSenderContact(senderContact);
        b.setReceiverName(receiverName);
        b.setReceiverAddress(receiverAddress);
        b.setReceiverPincode(receiverPincode);
        b.setReceiverContact(receiverContact);
        b.setWeight(weight);
        b.setSize(size);
        b.setShippingSpeed(shippingSpeed);
        b.setPackaging(packaging);
        b.setDescription(description);
        b.setPickupDateTime(pickupDate + " " + pickupTime);
        b.setPaymentMethod(paymentMethod);
        b.setAmount(amount);
        b.setStatus("Pending");
        // If officer is booking on behalf of walk-in customer, tag with sender name
        String role = (String) session.getAttribute("role");
        if ("OFFICER".equalsIgnoreCase(role)) {
            b.setCustomerUserId("WALK-IN: " + senderName);
            b.setCustomerEmail("walk-in@shipzen.com");
        } else {
            b.setCustomerUserId((String) session.getAttribute("userId"));
            b.setCustomerEmail((String) session.getAttribute("email"));
        }

        Booking saved = bookingService.createBooking(b);

        // Pass booking ID to next page via session (for payment/invoice)
        session.setAttribute("lastBookingId", saved.getBookingId());

        if ("online".equals(paymentMethod)) {
            return "redirect:/payment";
        } else {
            return "redirect:/invoice/" + saved.getBookingId();
        }
    }

    // ── Payment page ──────────────────────────────────────────
    @GetMapping("/payment")
    public String paymentPage(HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        String bookingId = (String) session.getAttribute("lastBookingId");
        if (bookingId != null) {
            bookingService.findByBookingId(bookingId).ifPresent(b -> model.addAttribute("booking", b));
        }
        return "payment";
    }

    @PostMapping("/payment/confirm")
    public String confirmPayment(HttpSession session) {
        if (notLoggedIn(session)) return "redirect:/login";
        String bookingId = (String) session.getAttribute("lastBookingId");
        if (bookingId != null) {
            // Mark payment confirmed — status stays Pending until officer picks up
            bookingService.findByBookingId(bookingId).ifPresent(b -> {
                // payment confirmed, no status change needed here
            });
            return "redirect:/invoice/" + bookingId;
        }
        return "redirect:/booking";
    }

    // ── Invoice page ──────────────────────────────────────────
    @GetMapping("/invoice/{bookingId}")
    public String invoicePage(@PathVariable String bookingId,
                              HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        Optional<Booking> opt = bookingService.findByBookingId(bookingId);
        if (opt.isEmpty()) return "redirect:/history";
        model.addAttribute("booking", opt.get());
        return "invoicePage";
    }

    // ── Tracking page ─────────────────────────────────────────
    @GetMapping("/tracking")
    public String trackingPage(HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        return "CustomerTracking";
    }

    @GetMapping("/tracking/{bookingId}")
    public String trackBooking(@PathVariable String bookingId,
                               HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        Optional<Booking> opt = bookingService.findByBookingId(bookingId);
        if (opt.isPresent()) {
            model.addAttribute("booking", opt.get());
            model.addAttribute("found", true);
        } else {
            model.addAttribute("found", false);
            model.addAttribute("searchedId", bookingId);
        }
        return "CustomerTracking";
    }

    // ── Booking history ───────────────────────────────────────
    @GetMapping("/history")
    public String historyPage(HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        String userId = (String) session.getAttribute("userId");
        List<Booking> bookings = bookingService.getBookingsByCustomer(userId);
        model.addAttribute("bookings", bookings);
        return "booking-history";
    }
}
