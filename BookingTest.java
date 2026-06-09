import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class BookingTest {

    @Test
    public void testBookingAmount() {

        Room room = new Room(101, "Single", 100);

        Customer customer = new Customer(
                1,
                "Ali",
                "123456",
                "ali@gmail.com",
                "Lahore"
        );

        Booking booking = new Booking(
                1,
                room,
                customer,
                3,
                300
        );

        assertEquals(300, booking.totalAmount, 0.01);
    }
}