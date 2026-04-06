package org.devops.smartparkingsystem.billing;


import jakarta.websocket.server.ServerEndpoint;
import lombok.RequiredArgsConstructor;
import org.devops.smartparkingsystem.event.VehicleExitedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class BillingService {

    private final BillingRepository billingRepository;




    @EventListener
    public void handleVehicleExit (VehicleExitedEvent vehicleExitedEvent) {
        // calculate   charge by duration


        var duration = Duration.between( vehicleExitedEvent.entryTime() , vehicleExitedEvent.exitTime())
;

        double amount = Math.max(20 , ( duration.toMinutes() / 60.0 ) * 50) ; // 5- rupees per  hour



        var billingRecord = new BillingRecord(null, vehicleExitedEvent.vehicleNumber() ,
                amount ,
                LocalDateTime.now())
;

    billingRepository.save(billingRecord);
        System.out.println( " billed " + billingRecord.getAmount());

    }
}
