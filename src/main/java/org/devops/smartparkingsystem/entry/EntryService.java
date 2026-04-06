package org.devops.smartparkingsystem.entry;


import lombok.RequiredArgsConstructor;
import org.devops.smartparkingsystem.allocation.SlotRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.devops.smartparkingsystem.event.VehicleEnteredEvent;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class EntryService {

    // save vehicle  entry details to db
    //  allocate  a parking slot

     // send  notitication to the  user

     private final  ParkingEntryRepository parkingEntryRepository;


     private final ApplicationEventPublisher publisher;
    private final SlotRepository slotRepository;


    public void vehicleEntry   ( String  vehicleNumber ) {


            var entry =  new ParkingEntry( null , vehicleNumber ,  LocalDateTime.now() , null , true);

            // in here we should publish an event

          publisher.publishEvent( new VehicleEnteredEvent(vehicleNumber , entry.getEntryTime()));



          parkingEntryRepository.save(entry);








      }



}
