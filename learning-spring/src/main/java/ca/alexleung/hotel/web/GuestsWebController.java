package ca.alexleung.hotel.web;

import ca.alexleung.hotel.business.service.ReservationService;
import ca.alexleung.hotel.data.entity.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/guests")
public class GuestsWebController {
    private final ReservationService reservationService;

    @Autowired
    public GuestsWebController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    @GetMapping
    public String getGuests(Model model) {
        List<Guest> allGuests = reservationService.getGuests();
        model.addAttribute("allGuests", allGuests);
        return "guests";
    }
}
