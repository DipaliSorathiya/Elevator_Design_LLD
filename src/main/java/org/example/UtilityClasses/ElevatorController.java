package org.example.UtilityClasses;

import org.example.CommandPatternMp.ConcreteClasses.ElevatorRequest;
import org.example.CommonEnums.Direction;
import org.example.SchedulingAlgoStrategyPattern.ConcreteStrategies.ScanSchedulingStrategy;
import org.example.SchedulingAlgoStrategyPattern.SchedulingStrategy;

import java.util.ArrayList;
import java.util.List;

public class ElevatorController {

    private List<Elevator> elevators;
    private List<Floor> floors;
    private SchedulingStrategy schedulingStrategy;
    private int currentElevatorId;

    public ElevatorController() {

    }

    public ElevatorController(int numberOfElevators,int numberOfFloors){
        this.elevators = new ArrayList<>();
        this.floors = new ArrayList<>();
        this.schedulingStrategy = new ScanSchedulingStrategy();

        // initialize elevators with uniques IDs.
        for(int i=1;i<=numberOfElevators;i++){
            elevators.add(new Elevator(i));
        }
        // Initialize floors.
        for(int i=1;i<= numberOfFloors;i++){
            floors.add(new Floor(i));
        }
    }

    // set the scheduling strategy dynamically.
    public void setSchedulingStrategy(SchedulingStrategy strategy){
        this.schedulingStrategy = strategy;
    }

    // handle external elevator requests from a specific floor
    public void requestElevator(int elevatorId,int floorNumber, Direction direction){
        System.out.println("External request: Floor" +floorNumber + ", Direction " + direction);
        // find the elevator by its id
        Elevator selectedElevator = getElevatorById(elevatorId);
        if(selectedElevator != null){
            selectedElevator.addRequest(new ElevatorRequest(elevatorId,floorNumber,false,direction));
            System.out.println("Assined elevator" + selectedElevator.getId() + " to floor" + floorNumber);
        }
        else {
            System.out.println("No Elevator available for floor" + floorNumber);
        }

    }

    // handle internal elevator requests to a specific floor
    public void requestFloor(int elevatorId, int floorNumber){
        Elevator elevator = getElevatorById(elevatorId);
        System.out.println("Internal request: Elevator" + elevator.getId()+ "to floor "+ floorNumber);
        Direction direction = floorNumber > elevator.getCurrentFloor()
                ? Direction.UP: Direction.DOWN;

        // add the request to the elevator.
        elevator.addRequest(new ElevatorRequest(elevatorId,floorNumber,true,direction));
    }

    // find an elevator by its Id.
    private Elevator getElevatorById(int elevatorId) {
        for (Elevator elevator : elevators) {
            if (elevator.getId() == elevatorId)
                return elevator;
        }
        return null;
    }

    // perform a simulation step by moving all elevators.
    public void step() {
        for(Elevator elevator : elevators){
            if(!elevator.getRequestsQueue().isEmpty()){
                 int nextStep = schedulingStrategy.getNextStop(elevator);

                 // move the elevator to the next stop if needed.
                if(elevator.getCurrentFloor() != nextStep)
                    elevator.moveToNextStop(nextStep);
            }
        }
    }

    // get the list of all elevators.
    public List<Elevator> getElevators() {
         return elevators;
    }

    // get the list of all floors.
    public List<Floor> getFloors() {
        return floors;
    }

    public void setCurrentElevator(int elevatorId){
        this.currentElevatorId = elevatorId;
    }

}
