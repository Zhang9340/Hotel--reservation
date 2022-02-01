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
import java.util.InputMismatchException;
import java.util.Scanner;


public class MainMenu {
    static HotelResource hotelResource=HotelResource.getInstance();
    static AdminResource adminResource =AdminResource.getInstance();

     public static SimpleDateFormat simpleDateFormat= new SimpleDateFormat("MM/dd/yyyy");
     public static final DateTimeFormatter dateFormatter = new DateTimeFormatterBuilder()
            .parseStrict()
            .appendPattern("MM/dd/uuuu")
            .toFormatter()
            .withResolverStyle(ResolverStyle.STRICT);
     public static boolean isValidateDate(String date,DateTimeFormatter dateFormatter){
        try {
            LocalDate.parse(date,dateFormatter);
        }catch (DateTimeParseException e){
            System.out.println("Invalid date , Please enter a date in valid format : MM/DD/YYYY");
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
         String lastName;
         String firstName;
         String email;
         boolean running;
         //adding first name
         System.out.println("Please enter your First Name:");
         do {
             try {

             firstName= scanner.nextLine();
             if (firstName.isEmpty()){throw new IllegalArgumentException();}
             running=false;
         }catch (IllegalArgumentException ex){

                 System.out.println("The First name can't be null,Please enter your first name: ");
                  running=true;
                  firstName=null;
         }

         }while (running);
         //adding last name
         System.out.println("Please enter your Last Name:");
         do {
             try {

                 lastName = scanner.nextLine();
                 if (lastName.isEmpty()){throw new IllegalArgumentException();}
                 running=false;
             }catch (IllegalArgumentException ex){

                 System.out.println("The last name can't be null ,please enter your last name:");
                 running=true;
                 lastName=null;
             }

         }while (running);
         //adding email
         System.out.println("Please enter your email in format name@domain.com :");
         do {

             email = scanner.nextLine();
             try {
                 hotelResource.createACustomer(email, firstName, lastName);
                 System.out.println("Welcome " + firstName + " " + lastName + " ,your account "+email+" has been successfully created！");
                 running = false;
             } catch (IllegalArgumentException|NullPointerException ex) {
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

                  System.out.println("Please enter your email in the format name@domain.com : ");
                  String email=scanner.nextLine();
                  hotelResource.bookARoom(email,room,checkInDate,checkOutDate);
                  System.out.println("Your reservation:"+hotelResource.getCustomersReservations(email));
                  running=false;


               }catch (IllegalArgumentException ex){
                    System.out.println(ex.getLocalizedMessage());

                    running=true;
             }catch (NullPointerException e){
                 System.out.println("The account is not in the system ,please make sure you enter the correct format ");
                 running=true;
             }


         }while(running);

     }//for customer already has account//checked
     public static void reservationSystem(Scanner scanner,Date checkInDate,Date checkOutDate){
         boolean isValidRoom;
         boolean isSuccessful;
         boolean isTheRoomIdExits;
         boolean bookARoom;


         IRoom roomChoice;
         // check If the customer wants to book a room
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
                 scanner.nextLine();
                 System.out.println("Please enter yes/no in correct format:");
                 isSuccessful=false;
                 bookARoom=false;

             }

         }while (!isSuccessful);
         // Reserve the room
         if (bookARoom) {

             // select a room
             do {
                 System.out.println("Please Select the room number : ");
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
                 System.out.println("Do you have an account with us ,please select yes/no:");
                 try {
                     String yesNo = scanner.nextLine();
                     if (yesNo.equals("yes")) {
                         System.out.println("Your reservation: ");
                         makeReservation(roomChoice, checkInDate, checkOutDate, scanner);


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
     public static void findRoomsAndReserve(Scanner scanner){
         boolean isValidDate;
         Date checkInDate;
         Date checkOutDate;

         // validate the input date
         do {
             System.out.println("Please enter checkin date in format MM/DD/YYYY");
             String checkInDateString= scanner.nextLine();
             isValidDate=isValidateDate(checkInDateString,dateFormatter);
             checkInDate =getDate(checkInDateString,simpleDateFormat);
         }while(!isValidDate);


         // Enter check in and check out date
         do {
             System.out.println("Please enter checkout date in format  MM/DD/YYYY");
             String  checkOutDateString= scanner.nextLine();
             isValidDate=isValidateDate(checkOutDateString,dateFormatter);
             checkOutDate =getDate(checkOutDateString,simpleDateFormat);
             if(isValidDate && checkInDate!=null){

                 isValidDate =isCheckoutDateValid(checkInDate,checkOutDate);
             }

         }while(!isValidDate);

        //Find room and make reservation
         Collection<IRoom> roomFind= hotelResource.findARoom(checkInDate,checkOutDate); // Return the available room list

         if (!roomFind.isEmpty()){
             System.out.println("Here are rooms available in the given date range from "+checkInDate+" to "+ checkOutDate);

             for (IRoom rooms: roomFind){

                 System.out.println("\n"+rooms);

             }

             reservationSystem(scanner,checkInDate,checkOutDate);




         }else
         {
             //provide recommended room
             assert checkInDate != null;
             Date checkInDate7days = new Date(checkInDate.getTime()+Duration.ofDays(7).toMillis());
             assert checkOutDate != null;
             Date checkOutDate7days =new Date(checkOutDate.getTime()+Duration.ofDays(7).toMillis());
             Collection<IRoom> recommendedRooms =hotelResource.findARoom(checkInDate7days,checkOutDate7days);
             if (!recommendedRooms.isEmpty()){
                 System.out.println("No room in the given date range,Here are the recommended room from "+checkInDate7days+" to "+checkOutDate7days+":");


                 for (IRoom rooms: recommendedRooms){
                     System.out.println(rooms);

                 }
                 reservationSystem(scanner,checkInDate7days,checkOutDate7days);


             }else {
                 System.out.println("There is no room in the reservation system!");
             }
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
                                findRoomsAndReserve(scanner);
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
                                    }catch (NullPointerException e){
                                        System.out.println("The account is not in the system ,please make sure you enter the correct format or have an account with us.");
                                        running=false;
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
                                keepRunning = false;
                                test = false;

                            }
                        }

                    }catch(IllegalArgumentException| InputMismatchException e){
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