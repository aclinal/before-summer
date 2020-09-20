package ca.alexleung.hotel.web;

import ca.alexleung.hotel.business.service.ReservationService;
import ca.alexleung.hotel.data.entity.Guest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@WebMvcTest(GuestsWebController.class)
public class GuestsWebControllerTest {
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
    List<Guest> guests = new ArrayList<>();
    Guest guest = new Guest();
    guest.setLastName("Unit");
    guest.setFirstName("Junit");
    guest.setGuestId(1);
    guest.setEmailAddress("junit@example.com");
    guest.setAddress("123 Spring Ave.");
    guest.setCountry("USA");
    guest.setState("FL");
    guest.setPhoneNumber("(123) 456-7890");
    guests.add(guest);

    given(reservationService.getGuests()).willReturn(guests);

    // This tests both the controller works correctly and that the templating engine formats "Lastname, Firstname"
    mockMvc.perform(get("/guests"))
        .andExpect(status().isOk())
        .andExpect(content().string(containsString("Unit, Junit")))
        .andExpect(content().string(containsString("junit@example.com")));
  }
}
