package Taku.app.core.services.images;

import Taku.app.core.models.profile.Images;
import Taku.app.core.models.users.User;
import Taku.app.core.repositories.ImageRepository;
import Taku.app.core.repositories.UserRepository;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Optional;

@Service
public class AmazonClient {

    private AmazonS3 s3client;

    @Value("${amazonProperties.endpointUrl}")
    private String endpointUrl;

    @Value("${amazonProperties.bucketName}")
    private String bucketName;

    @Value("${amazonProperties.accessKey}")
    private String accessKey;

    @Value("${amazonProperties.secretKey}")
    private String secretKey;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ImageRepository imageRepository;


    @PostConstruct
    private void initializeAmazon() {
        AWSCredentials credentials = new BasicAWSCredentials(this.accessKey, this.secretKey);
        this.s3client = new AmazonS3Client(credentials);
    }

    //Normal photos
    public String uploadFile(MultipartFile multipartFile, Long id) {
        String fileUrl = "";
        //Check if file is an image

        //upload image
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<User> person = userRepository.findById(id);
        User user = person.get();

        Images images = new Images(fileUrl, user);
        images.setPhoto_type(Images.TYPE_POST);
        imageRepository.save(images);
        return fileUrl;
    }

    //Profile Avatars
    public String uploadProfilePic(MultipartFile multipartFile, Long id) {
        String fileUrl = "";
        //Check if file is an image

        //upload image
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<User> person = userRepository.findById(id);
        User user = person.get();
        user.getProfile().setAvatar_reference(fileUrl);
        userRepository.save(user);

        Images images = new Images(fileUrl, user);
        images.setPhoto_type(Images.TYPE_AVATAR);
        imageRepository.save(images);
        return fileUrl;
    }

    //Profile Cover Image
    public String uploadCoverImage(MultipartFile multipartFile, Long id) {
        String fileUrl = "";
        //Check if file is an image

        //upload image
        try {
            File file = convertMultiPartToFile(multipartFile);
            String fileName = generateFileName(multipartFile);
            fileUrl = endpointUrl + "/" + bucketName + "/" + fileName;
            uploadFileTos3bucket(fileName, file);
            file.delete();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Optional<User> person = userRepository.findById(id);
        User user = person.get();
        user.getProfile().setCover_image_reference(fileUrl);
        userRepository.save(user);

        Images images = new Images(fileUrl, user);
        images.setPhoto_type(Images.TYPE_COVER);
        imageRepository.save(images);
        return fileUrl;
    }

    private File convertMultiPartToFile(MultipartFile file) throws IOException {
        File convFile = new File(file.getOriginalFilename());
        FileOutputStream fos = new FileOutputStream(convFile);
        fos.write(file.getBytes());
        fos.close();
        return convFile;
    }

    private String generateFileName(MultipartFile multiPart) {
        return new Date().getTime() + "-" + multiPart.getOriginalFilename().replace(" ", "_");
    }

    private void uploadFileTos3bucket(String fileName, File file) {
        s3client.putObject(new PutObjectRequest(bucketName, fileName, file)
                .withCannedAcl(CannedAccessControlList.PublicRead));
    }

    public String deleteFileFromS3Bucket(String fileUrl) {
        String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
        s3client.deleteObject(new DeleteObjectRequest(bucketName, fileName));
        return "Successfully deleted";
    }
}
