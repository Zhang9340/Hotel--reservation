package LineComand;

import api.AdminResource;
import api.HotelResource;
import service.ReservationService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Date;
import java.util.Scanner;

public class MainMenu {

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





     public static void main(String[] args) {
        boolean keepRunning =true;
        HotelResource hotelResource=HotelResource.getInstance();
        AdminResource adminResource =AdminResource.getInstance();
        AdminResource.getInstance();

        Scanner scanner =new Scanner(System.in);
        while (keepRunning){

                System.out.println("Main Menu");
                System.out.println(" 1. Find and reserve a room ");
                System.out.println(" 2. See my reservation ");
                System.out.println(" 3. Create an account ");
                System.out.println(" 4. Admin");
                System.out.println(" 5. Exit ");
               try{
                   System.out.println("Please enter number 1-5:");
                 Scanner input =new Scanner(System.in);
                 int inputInt =input.nextInt();




                 switch (inputInt){

                     case 1:// Find and add room based on the checkin and checkout date
                         boolean isValidDate;
                         Date checkInDate;
                         Date checkOutDate;

                         // validate the input date
                         do {
                            System.out.println("Please enter checkin date in format DD/MM/YYYY");
                            String  checkInDateString= scanner.nextLine();
                            isValidDate=isValidateDate(checkInDateString,dateFormatter);
                            checkInDate =getDate(checkInDateString,simpleDateFormat);
                         }while(!isValidDate);



                         do {
                             System.out.println("Please enter checkout date in format DD/MM/YYYY");
                             String  checkOutDateString= scanner.nextLine();
                             isValidDate=isValidateDate(checkOutDateString,dateFormatter);
                             checkOutDate =getDate(checkOutDateString,simpleDateFormat);
                             if(isValidDate){
                               System.out.println(checkInDate);
                               System.out.println(checkOutDate);
                              isValidDate =isCheckoutDateValid(checkInDate,checkOutDate);
                             }

                         }while(!isValidDate);

                         hotelResource.findARoom(checkInDate,checkOutDate);





                 }
               } catch (Exception e){}






        }

    }




}
