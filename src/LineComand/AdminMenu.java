package LineComand;

import api.AdminResource;
import model.IRoom;
import model.Room;
import model.RoomType;

import java.util.*;

public class AdminMenu {
    public void  AdminMenuMain(){
        AdminResource adminResource= AdminResource.getInstance();
        boolean keepRunning =true;

        while (keepRunning){
            boolean test=true;
            System.out.println("---------------------  Admin Menu --------------------");
            System.out.println("Choose option");
            System.out.println("1. See all customers");
            System.out.println("2. See all rooms");
            System.out.println("3. See all reservations");
            System.out.println("4. Add a Room");
            System.out.println("5. Back to Main Menu");
            while (test){
                System.out.println("---------------------------------------------------");
                try {
                    System.out.println("Please enter number 1-5 from the menu:");
                    Scanner scanner = new Scanner(System.in);
                    int IntInput = scanner.nextInt();
                    scanner.nextLine();


                    switch (IntInput) {
                        case 1 -> {// see all customers
                            adminResource.getAllCustomer();
                            test = false;
                        }
                        case 2 -> { //See all rooms
                            adminResource.getAllRooms();
                            test = false;
                        }
                        case 3 -> { // see all reservations
                            adminResource.displayAllReservation();
                            test = false;
                        }
                        case 4 -> {// Add a Room
                            boolean toAddRoom = true;
                            boolean isValidRoomNumber;
                            boolean isValidPrice;
                            boolean isValidType;
                            boolean isAddingRoom;
                            String roomNumber;
                            Double roomPrice;
                            RoomType roomType;
                            while (toAddRoom) {
                                // add room number
                                do {
                                    int numberString;
                                    System.out.println("Please enter the room number");
                                    try {
                                        roomNumber = scanner.nextLine();
                                        numberString = Integer.parseInt(roomNumber);
                                        isValidRoomNumber = true;

                                    } catch (NumberFormatException e) {
                                        roomNumber=null;
                                        System.out.println("The room ID is invalid ,please input correct format");
                                        isValidRoomNumber = false;
                                    }
                                } while (!isValidRoomNumber);


                                //Add room price
                                do {
                                    System.out.println("Please enter the price for the room:");


                                    try {
                                        String roomPriceString = scanner.nextLine();
                                        roomPrice = Double.parseDouble(roomPriceString);
                                        isValidPrice = true;

                                    } catch (NumberFormatException ex) {
                                        roomPrice = null;
                                        System.out.println("Invalid price !");
                                        isValidPrice = false;
                                    }

                                } while (!isValidPrice);


                                //Add room type;

                                do {
                                    System.out.println("Please enter the room type : 1 for  Single bed,  2 for Double bed :");
                                    try {
                                        int roomTypeNumber = scanner.nextInt();
                                        scanner.nextLine();


                                        if (roomTypeNumber == 1) {
                                            roomType = RoomType.SINGLE;
                                            isValidType = true;
                                        } else if (roomTypeNumber == 2) {

                                            roomType = RoomType.DOUBLE;
                                            isValidType = true;

                                        } else {
                                            roomType = null;
                                            System.out.println("Invalid input! Please enter 1 or 2 ");
                                            isValidType = false;
                                        }

                                    } catch (InputMismatchException ex) {
                                        scanner.nextLine();
                                        roomType = null;
                                        isValidType = false;
                                        System.out.println("Invalid input! Please enter correct format.");

                                    }
                                } while (!isValidType);
                                // Add room

                                IRoom newRoom = new Room(roomNumber, roomPrice, roomType);
                                List<IRoom> roomToAdd = new ArrayList<>();
                                roomToAdd.add(newRoom);
                                adminResource.addRoom(roomToAdd);

                                //Continue to add room
                                do {
                                    System.out.println("Do you want to add another room ?  please select yes/no: ");
                                    try {
                                        String answer = scanner.nextLine();
                                        if (answer.equals("yes")) {

                                            isAddingRoom = true;


                                        } else if (answer.equals("no")) {
                                            System.out.println(" You are going back to the Admin menu");
                                            isAddingRoom = true;
                                            toAddRoom = false;


                                        } else {
                                            System.out.println("Please enter yes/no in correct format:");
                                            isAddingRoom = false;
                                        }
                                    } catch (IllegalArgumentException ex) {
                                        System.out.println("Please enter yes/no in correct format:");
                                        isAddingRoom = false;


                                    }
                                } while (!isAddingRoom);
                                test = false;
                            }
                        }
                        case 5 -> {//Return to main menu

                            keepRunning = false;
                            System.out.println("You are going back to Main menu");
                            MainMenu menuObject = new MainMenu();
                            menuObject.startMainMenu();
                        }
                    }
                }catch(Exception e){
                    System.out.println("Invalid number!");

                }
            }

        }

    }
}
