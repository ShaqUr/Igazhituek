package igazhituek.kereszt;

import igazhituek.api.UserApiController;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EntityScan("igazhituek.model")
@ComponentScan(basePackageClasses = UserApiController.class)
@ComponentScan("igazhituek.service")
@ComponentScan("igazhituek.repository")
@ComponentScan("igazhituek.model")
@ComponentScan("igazhituek.api")
@ComponentScan("igazhituek.exceptions")
public class KeresztApplication {
        
	public static void main(String[] args) {
                //szia laczik!
		SpringApplication.run(KeresztApplication.class, args);
	}
}