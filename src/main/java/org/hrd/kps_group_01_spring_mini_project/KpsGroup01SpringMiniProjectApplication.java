package org.hrd.kps_group_01_spring_mini_project;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer"
)
@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "Gamified Habit Tracker API",description = "API documentation for the Gamified Habit Tracker application",version = "1.0"))
public class KpsGroup01SpringMiniProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(KpsGroup01SpringMiniProjectApplication.class, args);
    }

}
