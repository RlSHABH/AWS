package aws;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AwsS3Configuration {

  @Bean
  public AmazonS3 s3client() {

    return AmazonS3ClientBuilder.standard()
        .withRegion(Regions.AP_SOUTH_1)
        .withCredentials(new InstanceProfileCredentialsProvider(false))
        .build();
  }
}