package org.example.SchedulingAlgoStrategyPattern.ConcreteStrategies;

import org.example.CommandPatternMp.ConcreteClasses.ElevatorRequest;
import org.example.CommonEnums.Direction;
import org.example.SchedulingAlgoStrategyPattern.SchedulingStrategy;
import org.example.UtilityClasses.Elevator;

import java.util.Queue;

public class FCFSSchedulingStrategy implements SchedulingStrategy {
    @Override
    public int getNextStop(Elevator elevator) {

        // get the elevator's current direction and floor
        Direction elevatorDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        // retrieve the FIFO queue of floor requests
        Queue<ElevatorRequest> requestQueue = elevator.getRequestsQueue();

        // if the request queue is Empty , stay on the current floor.
        if(requestQueue.isEmpty())
            return currentFloor;

        // fetch the next requested floor
        int nextRequestedFloor = requestQueue.poll().getFloor();

        // if the next floor is the current floor , return it.
        if(nextRequestedFloor == currentFloor){
            return currentFloor;
        }

        if(elevatorDirection == Direction.IDLE){
            elevator.setDirection(nextRequestedFloor > currentFloor ? Direction.UP:Direction.DOWN);
        }
        else if(elevatorDirection == Direction.UP && nextRequestedFloor < currentFloor){
            elevator.setDirection(Direction.DOWN);
        }
        else if(nextRequestedFloor > currentFloor){
            elevator.setDirection(Direction.UP);
        }

        // return the next requested floor
        return nextRequestedFloor;
    }
}
