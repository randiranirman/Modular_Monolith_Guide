package org.devops.smartparkingsystem.entry;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( "/api")

@RequiredArgsConstructor
public class EntryController {

    private  final EntryService entryService;
    private  final ExitService exitService;


    @PostMapping("/entry")
    public ResponseEntity<String> entry(@RequestParam String vehicleNumber) {
        entryService.vehicleEntry(vehicleNumber);
        return ResponseEntity.ok("vehicle enterted" + vehicleNumber) ;
    }

    @PostMapping("/exit")
    public ResponseEntity<String> exit(@RequestParam String vehicleNumber) {
        exitService.vehicleExit(vehicleNumber);
        return ResponseEntity.ok("vehicle exited" + vehicleNumber) ;


    }




}
