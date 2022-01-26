package service;

public class Tester {
    public static void main(String[] args) {
        CustomerService customerService = CustomerService.getInstance();

        customerService.addCustomer("bob@gmail.com","Bob"," Smith");
        customerService.addCustomer("Charlie@gmail.com","Charlie","Han");
        customerService.addCustomer("Charlie@gmail.com","Charlie","Han");
        System.out.println(customerService.getAllCustomers());
    }
}
