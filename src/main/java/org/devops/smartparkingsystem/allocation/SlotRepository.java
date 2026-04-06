package org.devops.smartparkingsystem.allocation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface SlotRepository extends JpaRepository<Slot, Long> {

    Optional<Slot> findFirstByAvailableTrue()

            ;

     Optional<Slot> findByVehicleNumberAndAvailableFalse(String vehicleNumber);

}
