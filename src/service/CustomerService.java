package service;
import model.Customer;
import java.util.*;

public class CustomerService {
    Collection< Customer> customerList= new HashSet<Customer>(); // This set store Customer information
    private static CustomerService customerService;
    private CustomerService() {}
    public static CustomerService getInstance() {
        if (Objects.isNull(customerService)) {
            customerService = new CustomerService();
        }
        return customerService;
    }
    public void addCustomer(String email,String firstName,String lastName){
        Customer newCustomer= new Customer(firstName,lastName,email);
        customerList.add(newCustomer);
    }
    public  Customer getCustomer(String customerEmail){
        for (Customer a: customerList) {
            if (a.getEmail().matches(customerEmail)){

               return a;
            }
        }

        return null;
    }
    public Collection<Customer> getAllCustomers(){
       if (customerList.size()==0){
           System.out.println("There is no customer in the system ");

       }else {
           System.out.println("Customers in the system: ");
           for (Customer customer: customerList){
            System.out.println(customer);
        }

       }
        return customerList;
    }
}
