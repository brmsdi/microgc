package microgc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

import java.util.Collections;

@ServletComponentScan
@SpringBootApplication
public class MicroGCApplication {
    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(MicroGCApplication.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
    }
}
