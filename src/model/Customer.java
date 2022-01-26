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
            throw (new IllegalArgumentException("Invalid email address, please try again."));
        }


        this.email=email;
        this.firstName=firstName;
        this.lastName=lastName;
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
        return "The name of the user: "+ firstName+" "+lastName+"\n"+"Email address: "+email+"\n";
    }
}
