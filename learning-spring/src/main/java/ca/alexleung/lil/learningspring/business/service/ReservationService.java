package ca.alexleung.lil.learningspring.business.service;

import ca.alexleung.lil.learningspring.business.domain.RoomReservation;
import ca.alexleung.lil.learningspring.data.entity.Guest;
import ca.alexleung.lil.learningspring.data.entity.Reservation;
import ca.alexleung.lil.learningspring.data.entity.Room;
import ca.alexleung.lil.learningspring.data.repository.GuestRepository;
import ca.alexleung.lil.learningspring.data.repository.ReservationRepository;
import ca.alexleung.lil.learningspring.data.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ReservationService {
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository, ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    // Gets all RoomReservations, including those without a matching Reservation.
    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = roomRepository.findAll();

        // Construct a RoomReservation for every room.
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getRoomId());
            roomReservation.setRoomName(room.getRoomName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getRoomId(), roomReservation);
        });

        // Stuff extra info for each reservation.
        Iterable<Reservation> reservations = reservationRepository.findReservationByResDate(new java.sql.Date(date.getTime()));
        reservations.forEach(reservation -> {
            RoomReservation roomReservation = roomReservationMap.get(reservation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = guestRepository.findById(reservation.getGuestId()).get();
            roomReservation.setGuestId(guest.getGuestId());
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
        });

        return roomReservationMap.values().stream()
                .sorted(Comparator.comparing(RoomReservation::getRoomName).thenComparing(RoomReservation::getRoomNumber))
                .collect(Collectors.toList());
    }

    public List<Guest> getGuests() {
        return StreamSupport.stream(guestRepository.findAll().spliterator(), false)
                .sorted(Comparator.comparing(Guest::getLastName).thenComparing(Guest::getFirstName))
                .collect(Collectors.toList());
    }
}
