package org.example.CommandPatternMp.ConcreteClasses;

import org.example.CommandPatternMp.ElevatorCommand;
import org.example.CommonEnums.Direction;
import org.example.UtilityClasses.ElevatorController;

public class ElevatorRequest implements ElevatorCommand {

    private int elevatorId;
    private int floor;
    private Direction requestDirections;
    private ElevatorController controller;
    private boolean isInternalRequest;


    public ElevatorRequest(int elevatorId,int floor,boolean isInternalRequest, Direction direction){
        this.elevatorId = elevatorId;
        this.floor = floor;
        this.isInternalRequest = isInternalRequest;
        this.requestDirections = direction;
        this.controller = new ElevatorController();
    }

    // execute method to process the request via the controller.
    @Override
    public void execute(){
        if(isInternalRequest){
            controller.requestFloor(elevatorId,floor);
        }
        else {
            controller.requestElevator(elevatorId,floor,requestDirections);
        }
    }

    public Direction getDirection() {
        return requestDirections;
    }

    public int getFloor(){
        return floor;
    }

    public boolean checkIsInternalRequest(){
        return isInternalRequest;
    }


}
