package igazhituek.kereszt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("igazhituek.model")
public class KeresztApplication {
        
	public static void main(String[] args) {
                //szia laczik!
		SpringApplication.run(KeresztApplication.class, args);
	}
}