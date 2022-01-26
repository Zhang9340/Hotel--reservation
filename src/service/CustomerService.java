package service;
import model.Customer;
import java.util.*;

public class CustomerService {
    Collection< Customer> customerList= new HashSet<Customer>();
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

        return customerList;
    }
}
