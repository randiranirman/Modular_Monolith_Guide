package org.devops.smartparkingsystem.allocation;

import lombok.RequiredArgsConstructor;
import org.devops.smartparkingsystem.event.VehicleEnteredEvent;
import org.devops.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service

@RequiredArgsConstructor
public class SlotAllocationService {

    private final SlotRepository slotRepository;






    @EventListener
    public  void handleVehicleEntry(VehicleEnteredEvent event) {


        // find the available slot to allocate
         // then update the slot db


        var slot = slotRepository.findFirstByAvailableTrue().orElseThrow( ()  -> new  RuntimeException("No available slots")) ;

        slot.setAvailable(false);
        slot.setVehicleNumber(event.vehicleNumber());
        slotRepository.save(slot);


        System.out.println(" Alloacted  Slot" + slot.getSlotCode() );








    }

    @EventListener
    public void handleVehicleExit (VehicleExitedEvent event) {



        slotRepository.findByVehicleNumberAndAvailableFalse(

                event.vehicleNumber()
        ).ifPresentOrElse(
                 slot ->
                 {
                     slot.setAvailable(true);
                     slot.setVehicleNumber(null);
                     slotRepository.save(slot);
                     System.out.println(" free slot " + slot.getSlotCode());

                 },() -> {
                     throw  new RuntimeException(" No slot found for vehivle ") ;

                }
        );




    }
}
