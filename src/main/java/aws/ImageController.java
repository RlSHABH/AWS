package aws;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.Base64;
import javax.imageio.ImageIO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ImageController {

  @Autowired
  private AwsS3UploadDownloadFile awsS3UploadDownloadFile;

  @RequestMapping(value = "/upload-image", method = RequestMethod.POST)
  public Boolean addDefaultplanogramImage(
      @RequestBody String base64Image) {

    BufferedImage img = decodeToImage(base64Image.substring(base64Image.indexOf(",") + 1));

    try {

      File file = new File("/tmp/image.png");
      ImageIO.write(img, "png", file);
      awsS3UploadDownloadFile.uploadFile("image", file);
    } catch (Exception e) {

      return false;
    }
    return true;
  }

  private static BufferedImage decodeToImage(String imageString) {

    BufferedImage image = null;
    byte[] imageByte;
    try {

      imageByte = Base64.getDecoder().decode(imageString);

      ByteArrayInputStream bis = new ByteArrayInputStream(imageByte);
      image = ImageIO.read(bis);
      bis.close();
    } catch (Exception e) {                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                 

      e.printStackTrace();
    }
    return image;
  }
}
