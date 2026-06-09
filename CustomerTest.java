import static org.junit.Assert.assertEquals;
import org.junit.Test;

public class CustomerTest {

    @Test
    public void testCustomerName() {

        Customer customer = new Customer(
                1,
                "Ali",
                "123456",
                "ali@gmail.com",
                "Lahore"
        );

        assertEquals("Ali", customer.name);
    }
}