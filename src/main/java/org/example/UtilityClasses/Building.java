package org.example.UtilityClasses;

public class Building {

    private String name;
    private int numberOfFloors;
  private ElevatorController elevatorcontroller;

    public Building(String name,int numberOfFloors,int numberOfElevators) {
        this.name = name;
        this.numberOfFloors = numberOfFloors;
        this.elevatorcontroller = new ElevatorController(numberOfElevators,numberOfFloors);
    }

    public String getName() {
        return name;
    }

    public int getNumberOfFloors() {
        return numberOfFloors;
    }

    public ElevatorController getElevatorcontroller() {
        return elevatorcontroller;
    }




}
