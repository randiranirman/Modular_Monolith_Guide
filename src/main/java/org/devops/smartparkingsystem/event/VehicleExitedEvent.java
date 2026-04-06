package org.devops.smartparkingsystem.event;

import java.time.LocalDateTime;

public record VehicleExitedEvent(String vehicleNumber , LocalDateTime entryTime , LocalDateTime exitTime ) {


}
