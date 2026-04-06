package org.devops.smartparkingsystem.notification;


import org.devops.smartparkingsystem.event.VehicleEnteredEvent;
import org.devops.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {




    @EventListener
    public void notifyOnVehicleEntry (VehicleEnteredEvent event) {
        // logic to send notification  to the  user
        System.out.println(" Notification " + event.vehicleNumber() + "entered at " + event.entryTime());


    }



    @EventListener
    public void notifyOnVehicleExit(VehicleExitedEvent vehicleExitedEvent) {

        // logic to send   notification  to the user
        System.out.println(" notification  vehicle exited  " + vehicleExitedEvent.vehicleNumber());
    }
}
