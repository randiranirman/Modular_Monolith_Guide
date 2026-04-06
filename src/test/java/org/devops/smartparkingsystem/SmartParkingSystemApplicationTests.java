package org.devops.smartparkingsystem;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.modulith.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;
import org.springframework.modulith.docs.Documenter;

import java.lang.annotation.Documented;

@SpringBootTest
class SmartParkingSystemApplicationTests {

    @Test
    void contextLoads() {

        ApplicationModules  modules = ApplicationModules.of(
                SmartParkingSystemApplication.class
        ).verify();


        new Documenter(modules)
                .writeDocumentation();



;    }


}
