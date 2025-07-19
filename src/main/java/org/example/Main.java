package org.example;

import org.example.CommonEnums.Direction;
import org.example.CommonEnums.ElevatorState;
import org.example.ObserverPatternMP.ElevatorDisplay;
import org.example.UtilityClasses.Building;
import org.example.UtilityClasses.Elevator;
import org.example.UtilityClasses.ElevatorController;

import java.util.Scanner;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        // initialize a building with 10 floors and 3 elevators
        Building building = new Building("Office Tower",10,3);
        ElevatorController controller = building.getElevatorcontroller();

        // create an ElevatorDisplay to observe and display elevator events
        ElevatorDisplay display = new ElevatorDisplay();
        for(Elevator elevator : controller.getElevators()){
            elevator.addObserver(display);
        }

        // simulate elevator requests using a command line interface.
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        System.out.println("Elevator System Simulation: ");
        System.out.println("Building: " + building.getName());
        System.out.println("Floors: " +building.getNumberOfFloors());
        System.out.println("Elevators : "+controller.getElevators());
        int choice = scanner.nextInt();
        while(running){

            switch(choice){
                case 1:

                        System.out.print("Enter elevator Id:");
                        int externalElevatorId = scanner.nextInt();
                        controller.setCurrentElevator(externalElevatorId);
                        System.out.println("Enter floor number: ");
                        int floorNum = scanner.nextInt();
                        System.out.println("Direction (1 for UP, 2 for DOWN): ");
                        int dirChoice = scanner.nextInt();;
                        Direction dir = dirChoice == 1? Direction.UP : Direction.DOWN;
                        controller.requestElevator(externalElevatorId,floorNum,dir);
                        break;
                case 2:
                    System.out.println("Enter elevator Id: ");
                    int elevatorId = scanner.nextInt();
                    controller.setCurrentElevator(elevatorId);
                    System.out.println("Enter destination floor: ");
                    


            }
        }




    }
}