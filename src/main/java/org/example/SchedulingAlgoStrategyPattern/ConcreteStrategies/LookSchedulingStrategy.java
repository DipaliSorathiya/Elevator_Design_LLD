package org.example.SchedulingAlgoStrategyPattern.ConcreteStrategies;

import org.example.CommandPatternMp.ConcreteClasses.ElevatorRequest;
import org.example.CommonEnums.Direction;
import org.example.SchedulingAlgoStrategyPattern.SchedulingStrategy;
import org.example.UtilityClasses.Elevator;

import java.util.Queue;

public class LookSchedulingStrategy implements SchedulingStrategy {
    @Override
    public int getNextStop(Elevator elevator) {

        int currentFloor = elevator.getCurrentFloor();
        Queue<ElevatorRequest> requests = elevator.getRequestsQueue();

        // if there are no pending requests, remain on the current floor.
        if(requests == null || requests.isEmpty()){
            return currentFloor;
        }
        ElevatorRequest primaryRequest = requests.peek();
        int primaryFloor = primaryRequest.getFloor();

        Direction travelDirection;
        if(primaryFloor > currentFloor){
            travelDirection = Direction.UP;
        }
        else if(primaryFloor < currentFloor){
            travelDirection = Direction.DOWN;
        }
        else{
            return currentFloor;
        }

        Integer candidate = null;
        for(ElevatorRequest req : requests){
            int reqFloor = req.getFloor();

            if(travelDirection == Direction.UP && reqFloor > currentFloor && reqFloor <= primaryFloor){
                if(req.checkIsInternalRequest() || (!req.checkIsInternalRequest()
                && req.getDirection() == Direction.DOWN)){
                    if(candidate == null || reqFloor > candidate){
                        candidate = reqFloor;
                    }
                }
            }
        }
        return  (candidate !=null) ?candidate : primaryFloor;
    }
}
