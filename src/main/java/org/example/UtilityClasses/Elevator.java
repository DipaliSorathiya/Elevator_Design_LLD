package org.example.UtilityClasses;

import org.example.CommandPatternMp.ConcreteClasses.ElevatorRequest;
import org.example.CommonEnums.Direction;
import org.example.CommonEnums.ElevatorState;
import org.example.ObserverPatternMP.ElevatorObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Elevator {

    private int id;
    private int currentFloor;
    private Direction direction;
    private ElevatorState state;
    private List<ElevatorObserver> observers;
    private Queue<ElevatorRequest> requests;

    public ElevatorState getState() {
        return state;
    }

    public int getId() {
        return id;
    }

    public Direction getDirection() {
        return direction;
    }

    public int getCurrentFloor() {
        return currentFloor;
    }

    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    public Elevator(int id){
        this.id =id;
        this.currentFloor = 1;
        this.direction = Direction.IDLE;
        this.state = ElevatorState.IDLE;
        this.observers = new ArrayList<>();
        this.requests = new LinkedList<>();

    }

    public void addObserver(ElevatorObserver observer){
        observers.add(observer);
    }

    public void removeObserver(ElevatorState observer){
        observers.remove(observer);
    }

    private void notifyStateChange(ElevatorState state){
                for(ElevatorObserver observer:observers){
                    observer.onElevatorStateChange(this,state);

                }
    }

    // notify all observers about a floor change.
    private void notifyFloorChange(int floor){
        for(ElevatorObserver observer:observers){
            observer.onElevatorFloorChange(this,floor);
        }
    }


    // set a new state for the elevator and notify observers.
     public void setState(ElevatorState newState){
        this.state = newState;
        notifyStateChange(newState);
     }

     // Set the direction of the elevator.
    public void setDirection(Direction newDirection){
        this.direction = newDirection;
    }

    // Add a new floor request to the queue
    public void addRequest(ElevatorRequest elevatorRequest){
        // avoid duplicate request.
        if(!requests.contains(elevatorRequest)){
            requests.add(elevatorRequest);
        }
        int requestedFloor = elevatorRequest.getFloor();
        // if elevator is idle, determine direction and start moving.
        if(state == ElevatorState.IDLE && !requests.isEmpty()){
            if(requestedFloor > currentFloor){
                direction = Direction.UP;
            }else if(requestedFloor < currentFloor){
                direction = Direction.DOWN;
            }
            setState(ElevatorState.MOVING);
        }
    }

    // move the elevator to the next stop as decided by the scheduling strategy.
    public void moveToNextStop(int nextStop){
        if(state != ElevatorState.MOVING)
            return;
        while(currentFloor != nextStop){
            // update floor based on direction
            if(direction == Direction.UP){
                currentFloor++;
            }
            else {
                currentFloor--;
            }
            notifyFloorChange(currentFloor);
            if(currentFloor == nextStop){
                completeArrival();
                return;
            }
        }
    }

    //Handle the elevator's arrival at a destination floor.
    private void completeArrival() {
        // stop the elevator and notify observers.
        setState(ElevatorState.STOPPED);
        requests.removeIf(request -> request.getFloor() == currentFloor);
        // if no more requests set state to IDLE.
        if(requests.isEmpty()){
            direction = Direction.IDLE;
            setState(ElevatorState.IDLE);
        }
        else {
            setState(ElevatorState.MOVING);
        }
    }

    public Queue<ElevatorRequest> getRequestsQueue() {
        return new LinkedList<>(requests);
    }

    public List<ElevatorRequest> getDestinationFloors() {
        return new ArrayList<>(requests);
    }
}
