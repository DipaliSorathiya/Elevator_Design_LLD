package org.example.SchedulingAlgoStrategyPattern.ConcreteStrategies;

import org.example.CommandPatternMp.ConcreteClasses.ElevatorRequest;
import org.example.CommonEnums.Direction;
import org.example.SchedulingAlgoStrategyPattern.SchedulingStrategy;
import org.example.UtilityClasses.Elevator;

import java.util.PriorityQueue;
import java.util.Queue;

public class ScanSchedulingStrategy implements SchedulingStrategy {
    @Override
    public int getNextStop(Elevator elevator) {

        // retrieve elevator's current direction and floor
        Direction elevatorDirection = elevator.getDirection();
        int currentFloor = elevator.getCurrentFloor();

        Queue<ElevatorRequest> requests = elevator.getRequestsQueue();

        if(requests.isEmpty()){
            return currentFloor;
        }

        // Priority Queue to handle requests in up and down directions
        PriorityQueue<ElevatorRequest> upQueue = new PriorityQueue<>();
        PriorityQueue<ElevatorRequest> downQueue = new PriorityQueue<>(
                (a,b) -> b.getFloor() - a.getFloor() // max heap for downward requests.
        );

         // categorize requests based on their relative position to the current floor.
        while(!requests.isEmpty()){
            ElevatorRequest elevatorRequest = requests.poll();
            int floor = elevatorRequest.getFloor();
            if(floor > currentFloor) {
                upQueue.add(elevatorRequest);
            }
            else {
                downQueue.add(elevatorRequest);
            }

        }

        // Handle the case when elevator is IDLE
        if(elevatorDirection == Direction.IDLE){
            // determine the nearest request and set direction accordingily.
            int nearestUpwardRequest = upQueue.isEmpty() ? -1 : upQueue.peek().getFloor();
            int nearestDownwardRequest = downQueue.isEmpty() ? -1: downQueue.peek().getFloor();

            if(nearestUpwardRequest == -1){
                elevator.setDirection(Direction.DOWN);
                return downQueue.poll().getFloor();
            }
            else if(nearestUpwardRequest ==-1){
                elevator.setDirection(Direction.UP);
                return upQueue.poll().getFloor();
            }
            else {
                // choose the closet request.
                if(Math.abs(nearestUpwardRequest - currentFloor) <
                Math.abs(nearestDownwardRequest - currentFloor)){
                    elevator.setDirection(Direction.UP);
                    return upQueue.poll().getFloor();
                }else {
                    elevator.setDirection(Direction.DOWN);
                    return downQueue.poll().getFloor();
                }
            }
        }

        // Handle movement in the UP direction
        if(elevatorDirection == Direction.UP) {
            return !upQueue.isEmpty() ? upQueue.poll().getFloor(): switchDirection(elevator,downQueue);
        }

        // handle movment in the down direction

        else {
            return !downQueue.isEmpty() ? downQueue.poll().getFloor() : switchDirection(elevator,upQueue);
        }
    }


    // helper method to switch the elevator's direction when no further requests exits
    // in the current direction.
    private int switchDirection(Elevator elevator, PriorityQueue<ElevatorRequest> requestQueue){
        elevator.setDirection(elevator.getDirection() == Direction.UP ?  Direction.DOWN : Direction.UP);
        return requestQueue.isEmpty() ? elevator.getCurrentFloor() : requestQueue.poll().getFloor();
    }
}
