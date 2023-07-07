package com.example.phonecontacts;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
    info = @Info(title = "Phone contacts API", version = "0.1",
        license = @License(name = "Apache-2.0 license",
            url = "https://www.apache.org/licenses/LICENSE-2.0")
    )
)
@SecurityScheme(type = SecuritySchemeType.HTTP, scheme = "basic", name = "Auth")
public class PhoneContactsApplication {

  public static void main(String[] args) {
    SpringApplication.run(PhoneContactsApplication.class, args);
  }

}
