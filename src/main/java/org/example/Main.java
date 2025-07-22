package org.example;

import org.example.CommonEnums.Direction;
import org.example.CommonEnums.ElevatorState;
import org.example.ObserverPatternMP.ElevatorDisplay;
import org.example.SchedulingAlgoStrategyPattern.ConcreteStrategies.FCFSSchedulingStrategy;
import org.example.SchedulingAlgoStrategyPattern.ConcreteStrategies.ScanSchedulingStrategy;
import org.example.UtilityClasses.Building;
import org.example.UtilityClasses.Elevator;
import org.example.UtilityClasses.ElevatorController;

import java.util.List;
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
                    // handle internal elevator floor request.
                    System.out.println("Enter elevator Id: ");
                    int elevatorId = scanner.nextInt();
                    controller.setCurrentElevator(elevatorId);
                    System.out.println("Enter destination floor: ");
                    int destFloor = scanner.nextInt();
                    controller.requestFloor(elevatorId,destFloor);
                    break;

                case 3:
                    // simulate the next step in the system
                    System.out.println("Simulating next step:");
                    controller.step();
                    displayElevatorStatus(controller.getElevators());

                case 4 :
                    // change the scheduling strategy
                    System.out.println("Select Strategy:");
                    System.out.println("1. SCAN Algorithm");
                    System.out.println("2. FCFS Algorithm");
                    System.out.println("3. Look Algorithm");
                    int strategyChoice = scanner.nextInt();
                    if(strategyChoice == 1){
                        controller.setSchedulingStrategy(new ScanSchedulingStrategy());
                        System.out.println("Strategy set to SCAN Algorithm");
                    }
                    else {
                        controller.setSchedulingStrategy(new FCFSSchedulingStrategy());
                        System.out.println("Strategy set to Nearest Elevator Fist:");
                    }
                    break;
                case 5:
                    // exit the simulation
                    running = false;
                    break;

                default:
                    // handle invalid choices.
                    System.out.println("Invalid choice!");
            }
        }
        scanner.close();
        System.out.println("Simulation Ended!!");
    }

    private static void displayElevatorStatus(List<Elevator> elevators){
        System.out.println("Elevator Status: ");
        for(Elevator elevator:elevators){
            System.out.println("Elevator " + elevator.getId() + ": Floor"
            + elevator.getCurrentFloor() +" , Direction"
            +elevator.getDirection() + ", State "+ elevator.getState()
            + " , Destinations: "+elevator.getDestinationFloors());
        }
    }
}