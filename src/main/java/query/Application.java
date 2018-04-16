package query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class Application {
    private static ConfigurableApplicationContext ctx;
    public static void main(String[] args) throws Exception {
        ctx = SpringApplication.run(Application.class, args);
    }
    public void shutdown() {
        ctx.close();
    }
}