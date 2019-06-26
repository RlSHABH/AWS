package aws;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "aws")
@SpringBootApplication
public class Application {

  public static void main(String[] args)
  {
    SpringApplication.run(Application.class,args);
  }
}
