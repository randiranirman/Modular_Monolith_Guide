package org.devops.smartparkingsystem.entry;


import lombok.RequiredArgsConstructor;
import org.devops.smartparkingsystem.allocation.SlotRepository;
import org.devops.smartparkingsystem.event.VehicleEnteredEvent;
import org.devops.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.cglib.core.Local;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ExitService {

    // save vehicle  entry details to db
    //  allocate  a parking slot

     // send  notitication to the  user

     private final  ParkingEntryRepository parkingEntryRepository;


     private final ApplicationEventPublisher publisher;



    public void vehicleExit   ( String  vehicleNumber ) {


           // get vehile entry from the DB
        // upadte exit time
        // save  it to DB
        // publish an event



        var entry = parkingEntryRepository.findByVehicleNumber(vehicleNumber)
                .orElseThrow( ()  -> new RuntimeException("No entry  foudn "))
;



        entry.setExitTime(LocalDateTime.now());

        entry.setActive(false);


        parkingEntryRepository.save(entry)
;
                //  publish  vehicle exited event


         publisher.publishEvent( new VehicleExitedEvent( vehicleNumber, entry.getEntryTime() , entry.getExitTime()));












      }



}
