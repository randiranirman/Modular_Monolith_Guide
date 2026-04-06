package org.devops.smartparkingsystem.event;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record VehicleEnteredEvent(String vehicleNumber , LocalDateTime entryTime) {
}
