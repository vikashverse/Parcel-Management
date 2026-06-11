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
public class OfficerController {

    @Autowired
    private BookingService bookingService;

    private boolean notLoggedIn(HttpSession session) {
        return session.getAttribute("username") == null;
    }

    // ── Officer Home ──────────────────────────────────────────
    @GetMapping("/officer/home")
    public String officerHome(HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        model.addAttribute("totalBookings",     bookingService.countAll());
        model.addAttribute("pendingBookings",   bookingService.countByStatus("Pending"));
        model.addAttribute("inTransitBookings", bookingService.countByStatus("In Transit"));
        model.addAttribute("deliveredBookings", bookingService.countByStatus("Delivered"));
        return "OfficerHome";
    }

    // ── Officer Tracking — list all bookings ──────────────────
    @GetMapping("/officer/tracking")
    public String officerTracking(HttpSession session, Model model,
                                  @RequestParam(required = false) String q) {
        if (notLoggedIn(session)) return "redirect:/login";
        List<Booking> bookings = (q != null && !q.isBlank())
                ? bookingService.search(q)
                : bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        model.addAttribute("q", q);
        model.addAttribute("totalCount",     bookingService.countAll());
        model.addAttribute("transitCount",   bookingService.countByStatus("In Transit"));
        model.addAttribute("deliveredCount", bookingService.countByStatus("Delivered"));
        model.addAttribute("pendingCount",   bookingService.countByStatus("Pending"));
        return "OfficerTracking";
    }

    // ── Officer Booking History ───────────────────────────────
    @GetMapping("/officer/history")
    public String officerHistory(HttpSession session, Model model) {
        if (notLoggedIn(session)) return "redirect:/login";
        model.addAttribute("bookings", bookingService.getAllBookings());
        return "OfficerBookingHistory";
    }

    // ── Delivery Update page ──────────────────────────────────
    @GetMapping("/officer/delivery-update")
    public String deliveryUpdatePage(HttpSession session, Model model,
                                     @RequestParam(required = false) String bookingId) {
        if (notLoggedIn(session)) return "redirect:/login";
        if (bookingId != null && !bookingId.isBlank()) {
            bookingService.findByBookingId(bookingId).ifPresent(b -> model.addAttribute("booking", b));
        }
        return "deliveryUpdate";
    }

    @PostMapping("/officer/delivery-update")
    public String updateDelivery(@RequestParam String bookingId,
                                 @RequestParam String status,
                                 @RequestParam(required = false) String currentLocation,
                                 @RequestParam(required = false) String deliveryAgent,
                                 @RequestParam(required = false) String expectedDeliveryDate,
                                 @RequestParam(required = false) String remarks,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (notLoggedIn(session)) return "redirect:/login";
        String officerName = (String) session.getAttribute("username");
        Booking updated = bookingService.updateStatus(bookingId, status, currentLocation,
                deliveryAgent, expectedDeliveryDate, remarks, officerName);
        if (updated != null) {
            redirectAttributes.addFlashAttribute("successMsg",
                    "Status of " + bookingId + " updated to \"" + status + "\"");
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Booking ID not found: " + bookingId);
        }
        return "redirect:/officer/delivery-update";
    }

    // ── Pickup Scheduling page ────────────────────────────────
    @GetMapping("/officer/pickup-scheduling")
    public String pickupSchedulingPage(HttpSession session, Model model,
                                       @RequestParam(required = false) String bookingId) {
        if (notLoggedIn(session)) return "redirect:/login";
        if (bookingId != null && !bookingId.isBlank()) {
            bookingService.findByBookingId(bookingId).ifPresent(b -> model.addAttribute("booking", b));
        }
        return "pickupScheduling";
    }

    @PostMapping("/officer/pickup-scheduling")
    public String schedulePickup(@RequestParam String bookingId,
                                 @RequestParam String assignedOfficer,
                                 @RequestParam String pickupDate,
                                 @RequestParam String pickupTime,
                                 @RequestParam(required = false) String pickupAddress,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {
        if (notLoggedIn(session)) return "redirect:/login";
        String pickupDateTime = pickupDate + " " + pickupTime;
        Booking updated = bookingService.schedulePickup(bookingId, assignedOfficer,
                pickupDateTime, pickupAddress);
        if (updated != null) {
            redirectAttributes.addFlashAttribute("successMsg",
                    "Pickup scheduled for " + bookingId + " on " + pickupDateTime);
        } else {
            redirectAttributes.addFlashAttribute("errorMsg", "Booking ID not found: " + bookingId);
        }
        return "redirect:/officer/pickup-scheduling";
    }

    // ── Officer Booking Service (create booking on behalf of customer) ──
    @GetMapping("/officer/booking")
    public String officerBookingPage(HttpSession session) {
        if (notLoggedIn(session)) return "redirect:/login";
        return "booking";
    }
}
