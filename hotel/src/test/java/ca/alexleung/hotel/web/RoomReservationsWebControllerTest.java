package ca.alexleung.hotel.web;

import ca.alexleung.hotel.business.domain.RoomReservation;
import ca.alexleung.hotel.business.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(RoomReservationWebController.class)
public class RoomReservationsWebControllerTest {
  @MockBean
  private ReservationService reservationService;

  @Autowired
  private WebApplicationContext webApplicationContext;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders
        .webAppContextSetup(webApplicationContext)
        .build();
  }

  @Test
  public void getReservations() throws Exception {
    String dateString = "2020-01-01";
    Date date = DateUtils.createDateFromDateString(dateString);
    List<RoomReservation> roomReservations = new ArrayList<>();
    RoomReservation roomReservation = new RoomReservation();
    roomReservation.setLastName("Unit");
    roomReservation.setFirstName("Junit");
    roomReservation.setDate(date);
    roomReservation.setGuestId(1);
    roomReservation.setRoomId(100);
    roomReservation.setRoomName("Junit Room");
    roomReservation.setRoomNumber("J1");
    roomReservations.add(roomReservation);

    given(reservationService.getRoomReservationsForDate(date)).willReturn(roomReservations);

    // This tests both the controller works correctly and that the templating engine formats "Lastname, Firstname"
    mockMvc.perform(get("/reservations?date=2020-01-01"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Unit, Junit")));
  }
}
