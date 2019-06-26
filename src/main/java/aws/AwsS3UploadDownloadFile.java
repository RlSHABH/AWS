package aws;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;
import com.amazonaws.util.Md5Utils;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Path;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
public class AwsS3UploadDownloadFile {

  @Autowired private AwsS3Configuration awsS3Configuration;

  @Autowired private AmazonS3 amazonS3;

  @Autowired private Environment environment;

  public void uploadFile(String keyName, File fileToBeUploaded) throws Exception {
    assert amazonS3 != null;
    try {
      String bucketName = environment.getProperty("S3.bucket.name");

      PutObjectRequest req = new PutObjectRequest(bucketName, keyName, fileToBeUploaded);
      req.putCustomRequestHeader("Content-MD5", Md5Utils.md5AsBase64(fileToBeUploaded));
      amazonS3.putObject(req);

    } catch (AmazonServiceException ase) {
     throw new Exception(ase);
    }
  }

  public void downloadFile(String fileObjKeyName, Path destinationFilePath)
      throws Exception {
    assert amazonS3 != null;
    String bucketName = environment.getProperty("S3.bucket.name");

    S3Object s3object = amazonS3.getObject(new GetObjectRequest(bucketName, fileObjKeyName));

    try (S3ObjectInputStream is = s3object.getObjectContent();
         FileOutputStream fos = new FileOutputStream(destinationFilePath.toFile())) {
      IOUtils.copy(is, fos);
    } catch (AmazonServiceException ase) {
      throw new Exception(ase);
    }
  }
}
