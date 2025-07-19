package org.example.SchedulingAlgoStrategyPattern;

import org.example.UtilityClasses.Elevator;

public interface SchedulingStrategy {
     int getNextStop(Elevator elevator);
}
