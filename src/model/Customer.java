package model;

import java.util.regex.Pattern;

public class Customer {
    private String firstName;
    private   String lastName;
    private   String email;

    public  Customer(String firstName,String lastName,String email){
        String emailRegex = "^(.+)@(.+)\\.(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if (!pattern.matcher(email).matches()){
            throw (new IllegalArgumentException("Invalid email address, please enter the email in correct format:"));
        }


        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
    }
    @Override
    public int hashCode(){
        int hash = 7;
        hash = 31 * hash +  (firstName == null ? 0 :firstName.hashCode());
        hash = 31 * hash + (lastName == null ? 0 : lastName.hashCode());
        hash = 31 * hash + (email== null ? 0 : email.hashCode());
        return hash;

    }
    @Override
    public  boolean equals(final Object o){
        if (this==o){return true;}
        if (o==null){return false;}
        if(getClass()!=o.getClass()){return false;}
        Customer customer =(Customer) o;
        return email.equals(customer.email)&&firstName.equals(customer.firstName)
                &&lastName.equals(customer.lastName);


    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }



    @Override
    public String toString(){
        return "Customer's name: "+ firstName+" "+lastName+"," +"    Email address: "+email+"\n";
    }
}
