package LineComand;

import api.AdminResource;
import api.HotelResource;
import model.Customer;
import model.IRoom;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collection;
import java.util.Date;
import java.util.Scanner;


public class MainMenu {
    static HotelResource hotelResource=HotelResource.getInstance();
    static AdminResource adminResource =AdminResource.getInstance();

     public static SimpleDateFormat simpleDateFormat= new SimpleDateFormat("dd/MM/yyyy");
     public static final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("dd/MM/uuuu")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);
     public static boolean isValidateDate(String date,DateTimeFormatter dateFormatter){
        try {
            LocalDate.parse(date,dateFormatter);
        }catch (DateTimeParseException e){
            System.out.println("Invalid date , Please enter a date in valid format : DD/MM/YYYY)");
            return false;
        }
        return true;
    }

     public static  boolean isCheckoutDateValid(Date checkInDate, Date checkOutDate){
        if (checkInDate.before(checkOutDate)){
            return true;
        }else {
            System.out.println("The checkout date is invalid,Please select a checkout date after checkin date ");
            return false;
        }


    }
     public static Date getDate (String DateString, SimpleDateFormat simpleDateFormat){
        try{
            return simpleDateFormat.parse(DateString);
        } catch (ParseException e){
            //System.out.println("Some problem occurred parsing the date");
        }
        return null;
     }
     public static  boolean isRoomIDValid(String roomNumber){
        int numberString;
        try {
            numberString = Integer.parseInt(roomNumber);

        }
        catch (NumberFormatException e) {
            System.out.println("The room ID is invalid ,please input correct format");
            return false;
        }
         return true;
    }

     public static Customer addCustomerMenu(Scanner scanner) {
         boolean running;
         System.out.println(" Please enter your First Name:");
         String lastName = scanner.nextLine();
         System.out.println("Please enter your Last Name");
         String firstName = scanner.nextLine();
         System.out.println("Please enter your email address:");


         String email;
         do {
             email = scanner.nextLine();
             try {
                 hotelResource.createACustomer(email, firstName, lastName);
                 System.out.println("Welcome " + firstName + " " + lastName + " ,your account "+email+" has been successfully created！");
                 running = false;
             } catch (IllegalArgumentException ex) {
                 System.out.println(ex.getLocalizedMessage());
                 running = true;
             }

         } while (running);

         return hotelResource.getCustomer(email);
     }
     public static void makeReservation(IRoom room,Date checkInDate,Date checkOutDate,Scanner scanner){
         boolean running;

         do {
             try {
                  System.out.println("Please enter your email in the format: ");
                  String email=scanner.nextLine();
                  hotelResource.bookARoom(email,room,checkInDate,checkOutDate);

                  running=false;


               }catch (IllegalArgumentException ex){
                    System.out.println(ex.getLocalizedMessage());

                    running=true;
             }


         }while(running);

     }
     public static void findRooms(Date checkInDate,Date checkOutDate){
         Collection<IRoom> roomFind= hotelResource.findARoom(checkInDate,checkOutDate); // Return the available room list
         if (!roomFind.isEmpty()){
             System.out.println("Here are rooms available in the given date range : ");
             System.out.println("Checkin Date: "+checkInDate);
             System.out.println("Checkout Date: "+checkOutDate);
             int index = 1;
             for (IRoom rooms: roomFind){
                 System.out.println(index+"."+rooms);
                 index++;
             }

         }else
         {
             //provide recommended room
             assert checkInDate != null;
             Date checkInDate7days = new Date(checkInDate.getTime()+Duration.ofDays(7).toMillis());
             assert checkOutDate != null;
             Date checkOutDate7days =new Date(checkOutDate.getTime()+Duration.ofDays(7).toMillis());
             Collection<IRoom> recommendedRooms =hotelResource.findARoom(checkInDate7days,checkOutDate7days);
             if (!recommendedRooms.isEmpty()){
                 System.out.println("NO room in the given date range,Here are the recommended room: ");
                 int index = 1;
                 System.out.println("Checkin Date: "+checkInDate7days);
                 System.out.println("CheckOut Date: "+ checkOutDate7days);
                 for (IRoom rooms: roomFind){
                     System.out.println(index+"."+rooms);
                     index++;
                 }

             }else {
                 System.out.println("There is no room in the reservation system!");
             }
         }

     }

     public static  void findAndBookARoom(Scanner scanner){
                 boolean isValidDate;
                 boolean isValidRoom;
                 boolean isSuccessful;
                 boolean isTheRoomIdExits;
                 boolean bookARoom;
                 Date checkInDate;
                 Date checkOutDate;
                 IRoom roomChoice;



                 // validate the input date
                 do {
                     System.out.println("Please enter checkin date in format DD/MM/YYYY");
                     String checkInDateString= scanner.nextLine();
                     isValidDate=isValidateDate(checkInDateString,dateFormatter);
                     checkInDate =getDate(checkInDateString,simpleDateFormat);
                 }while(!isValidDate);


                 // Enter check in and check out date
                 do {
                     System.out.println("Please enter checkout date in format DD/MM/YYYY");
                     String  checkOutDateString= scanner.nextLine();
                     isValidDate=isValidateDate(checkOutDateString,dateFormatter);
                     checkOutDate =getDate(checkOutDateString,simpleDateFormat);
                     if(isValidDate && checkInDate!=null){

                         isValidDate =isCheckoutDateValid(checkInDate,checkOutDate);
                     }

                 }while(!isValidDate);




               ///find room
                 findRooms(checkInDate,checkOutDate);




                 // To check if the customer wants to book a room
                 do {
                     try{ System.out.println("Do you like to book a room? Please enter yes / no");
                     String yesNo=scanner.nextLine();
                     if (yesNo.equals("yes")){
                         isSuccessful=true;
                         bookARoom=true;
                     }else if (yesNo.equals("no")){
                         isSuccessful=true;
                         bookARoom=false;
                     }else {
                         throw new Exception();}
                     }catch (Exception ex){
                         System.out.println("Please enter yes/no in correct format:");
                         isSuccessful=false;
                         bookARoom=false;

                     }

                 }while (!isSuccessful);
                 // Reserve the room
                 if (bookARoom) {
                     // select a room

                     do {
                         System.out.println(" Please Select the room number : ");
                         String roomId = scanner.nextLine();
                         isValidRoom = isRoomIDValid(roomId);
                         if (isValidRoom) {

                             roomChoice = hotelResource.getRoom(roomId);
                             isTheRoomIdExits=true;




                         } else {
                             roomChoice = null;
                             isTheRoomIdExits=false;
                         }


                     } while (!isTheRoomIdExits);
                     //add account and make reservation
                     do {
                         System.out.println(" Do you have an account with us ,please select yes/no:");
                         try {
                             String yesNo = scanner.nextLine();
                             if (yesNo.equals("yes")) {
                             makeReservation(roomChoice, checkInDate, checkOutDate, scanner);
                             System.out.println("Your reservation: ");
                             isSuccessful = true;


                             }else if (yesNo.equals("no")) {
                             isSuccessful = true;
                             Customer customer = addCustomerMenu(scanner);
                             String customerEmail = customer.getEmail();
                             hotelResource.bookARoom(customerEmail, roomChoice, checkInDate, checkOutDate);// Make reservation
                             System.out.println("Your reservation: " + hotelResource.getCustomersReservations(customerEmail));

                             } else {
                             System.out.println("Please enter yes/no in correct format:");
                             isSuccessful = false;
                         }
                         }catch (IllegalArgumentException ex){
                             System.out.println("Please enter yes/no in correct format:");
                             isSuccessful=false;


                         }
                     } while (!isSuccessful);
                 }



     }


    public  void startMainMenu(){

            boolean keepRunning=true ;




            while (keepRunning){
                boolean test=true;
                System.out.println("---------------------Welcome to the hotel reservation application --------------------");
                System.out.println(" 1. Find and reserve a room ");
                System.out.println(" 2. See my reservation ");
                System.out.println(" 3. Create an account ");
                System.out.println(" 4. Admin");
                System.out.println(" 5. Exit ");
                System.out.println("--------------------------------------------------------------------------------------");
                while (test){
                    try {
                        System.out.println("Please enter number 1-5 from the menu:");
                        Scanner scanner = new Scanner(System.in);
                        int inputInt = scanner.nextInt();
                        scanner.nextLine();


                        switch (inputInt) {
                            case 1 -> {// Find and add room based on the checkin and checkout date
                                findAndBookARoom(scanner);
                                test = false;
                            }
                            case 2 -> { //See my reservation
                                test = false;
                                System.out.println("Please enter your email address: ");
                                String email;
                                boolean running;
                                do {
                                    email = scanner.nextLine();
                                    try {
                                        hotelResource.getCustomersReservations(email);
                                        running = false;
                                    } catch (IllegalArgumentException ex) {
                                        System.out.println(ex.getLocalizedMessage());
                                        running = true;
                                    }

                                } while (running);
                            }
                            case 3 -> { // create an account
                                addCustomerMenu(scanner);
                                test = false;
                            }
                            case 4 -> { //AdminMenu
                                AdminMenu adminMenuObject = new AdminMenu();
                                adminMenuObject.AdminMenuMain();
                                test = false;
                            }
                            case 5 -> {//exit
                                test = false;
                                keepRunning = false;
                            }
                        }

                    }catch(Exception e){
                        System.out.println("Invalid number!");

                    }
                }
            }
        }


    public static void main(String[] args) {
        MainMenu menuObject = new MainMenu();
        menuObject.startMainMenu();
    }
}