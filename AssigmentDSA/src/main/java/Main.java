import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        QueueADT<Order> orderQueueADT = new QueueADTImpl<>(100);
        List<Order> orderList = new ArrayList<>();
        List<Product> availableProducts = new ArrayList<>();
        availableProducts.add(new Product(1, "Oc Huong", 145000));
        availableProducts.add(new Product(2, "Oc Mit", 75000));
        availableProducts.add(new Product(3, "Oc Mong Tay", 99000));
        availableProducts.add(new Product(4, "Ngao Hoa", 95000));
        availableProducts.add(new Product(5, "So Huyet", 100000));
        availableProducts.add(new Product(6, "Chan Ga", 60000));
        availableProducts.add(new Product(7, "Canh Ga", 25000));
        availableProducts.add(new Product(8, "Chao Ngao", 120000));
        availableProducts.add(new Product(9, "Mien Xao", 120000));
        availableProducts.add(new Product(10, "Lau Oc", 100000));
        availableProducts.add(new Product(12, "Cut Lon Xao Me", 60000));
        availableProducts.add(new Product(13, "Nem Chua Ran", 60000));
        availableProducts.add(new Product(14, "Khoai Tay Chien", 40000));
        availableProducts.add(new Product(15, "Banh Mi", 4000));

        while (true) {
            System.out.println("--------------------Nha Hang Oc Nam Tu xin chao quy khach--------------------");
            System.out.println("------------------------------Select:------------------------------");
            System.out.println("1. Create Order");
            System.out.println("2. Confirm orders");
            System.out.println("3. Show all orders in the order of increasing price");
            System.out.println("4. Search orders by ID");
            System.out.println("0. Exit");
            System.out.println("----------Enter your selection:----------");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Tạo hóa đơn
                    Order order = createOrder(availableProducts);  // Pass the list of available products
                    orderQueueADT.enqueue(order);
                    orderList.add(order);
                    System.out.println("Invoice added to queue.");
                    break;
                case 2:
                    // Duyệt đơn hàng
                    processOrders(orderQueueADT);
                    break;
                case 3:
                    // Hiển thị tất cả các đơn hàng theo thứ tự giá tăng dần
                    displayOrdersByPrice(orderList);
                    break;
                case 4:
                    // Tìm kiếm đơn hàng theo mã
                    System.out.println("Enter the order ID to search: ");
                    int orderId = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character
                    searchOrderByID(orderList, orderId);
                    break;
                case 0:
                    // Thoát
                    System.out.println("Exit.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Not a valid choice. Please try again.");
            }
        }
    }

    static Order createOrder(List<Product> availableProducts) {
        Scanner scanner = new Scanner(System.in);

        // Nhập thông tin chung cho đơn hàng
        System.out.println("Enter information for the order:");
        System.out.print("ID orders: ");
        int orderId = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character
        System.out.print("Order date: ");
        String orderDate = scanner.nextLine();
        System.out.print("Customer name: ");
        String customerName = scanner.nextLine();
        System.out.print("Customer's phone number: ");
        String customerPhone = scanner.nextLine();

        // Tạo đối tượng Order
        Order order = new Order(orderId);
        order.setOrderDate(orderDate);
        order.setCustomerName(customerName);
        order.setCustomerPhone(customerPhone);

        // Nhập thông tin cho các sản phẩm trong đơn hàng
        while (true) {
            System.out.println("--------------------Menu:--------------------");
            System.out.println("Product is in stock:");
            for (Product product : availableProducts) {
                System.out.println(product.getId() + ". " + product.getName() + " - " + product.getPrice());
            }

            System.out.println("Enter product ID for the order (Enter 0 to finish):");
            int productId = scanner.nextInt();
            if (productId == 0) {
                break; // Người dùng nhập 0 để kết thúc nhập sản phẩm
            }

            // Find the selected product from the available products
            Product selectedProduct = null;
            for (Product product : availableProducts) {
                if (product.getId() == productId) {
                    selectedProduct = product;
                    break;
                }
            }

            if (selectedProduct == null) {
                System.out.println("Invalid product ID. Please try again.");
                continue;
            }

            scanner.nextLine(); // Consume the newline character

            System.out.print("Amount: ");
            int quantity = scanner.nextInt();

            // Tạo đối tượng OrderItem
            OrderItem orderItem = new OrderItem(productId, selectedProduct, quantity);

            // Thêm OrderItem vào Order
            order.getOrderItems().add(orderItem);
        }

        // Tính tổng tiền cho đơn hàng
        long totalAmount = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalAmount += orderItem.getItem().getPrice() * orderItem.getQuantity();
        }
        order.setTotalAmount(totalAmount);

        return order;
    }



    static void processOrders(QueueADT<Order> orderQueue) {
        while (!orderQueue.isEmpty()) {
            Order order = orderQueue.dequeue();
            if (order != null) {
                order.setStatus(true);
                order.printOrder();
            }
        }
        System.out.println("All orders in the queue have been processed.");
    }

    static void displayOrdersByPrice(List<Order> orderList) {
        // Sắp xếp danh sách đơn hàng theo tổng giá tăng dần
        Collections.sort(orderList, Comparator.comparingLong(Order::getTotalAmount));

        // Hiển thị các đơn hàng
        System.out.println("List of orders in ascending price order:");
        for (Order order : orderList) {
            order.printOrder();
        }
    }

    static void searchOrderByID(List<Order> orderList, int orderId) {
        for (Order order : orderList) {
            if (order.getId() == orderId) {
                System.out.println("Order found:");
                order.printOrder();
                return;
            }
        }
        System.out.println("No orders with ID found " + orderId);
    }
}
