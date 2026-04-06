package org.devops.smartparkingsystem.allocation;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Entity
public class Slot {


    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Long id ;
    private String slotCode ;
    private boolean available ;
    private String  vehicleNumber ;



}
