import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

class MainTest {

    @Test
    void testCreateOrder() {
        // Simulate user input for creating an order
        String input = "123\n" + // Order ID
                "2023-01-01\n" + // Order date
                "John Doe\n" + // Customer name
                "123456789\n" + // Customer phone
                "1\n" + // Product code
                "Product 1\n" + // Product name
                "100\n" + // Product price
                "2\n" + // Quantity
                "0\n"; // Enter 0 to finish creating order

        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        System.setIn(inputStream);

        Order order = Main.createOrder();

        assertNotNull(order);
        assertEquals(123, order.getId());
        assertEquals("2023-01-01", order.getOrderDate());
        assertEquals("John Doe", order.getCustomerName());
        assertEquals("123456789", order.getCustomerPhone());
        assertEquals(200, order.getTotalAmount()); // Assuming total amount calculation is correct
    }
}
