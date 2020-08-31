package Taku.app.core.controllers;

import Taku.app.core.payload.request.RegisterRequest;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.services.images.AmazonClient;
import Taku.app.core.services.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/social")
public class SocialController {


    private AmazonClient amazonClient;

    @Autowired
    SocialController(AmazonClient amazonClient) {
        this.amazonClient = amazonClient;
    }

    @Autowired
    ProfileService profileService;

    @Autowired
    UserRepository userRepository;

    //Profile
    @PostMapping("/createProfile")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public ResponseEntity<?> createProfile(@Valid @RequestBody RegisterRequest signUpRequest){

        if (userRepository.existsByEmail(signUpRequest.getEmail())){
            User user = userRepository.findByEmail(signUpRequest.getEmail());
            profileService.createProfile(user);
        }

        
        return null;
    }

    @GetMapping("/retrieveProfile")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String getProfile() {
        return null;
    }

    @PutMapping("/updateProfile")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String updateProfile(){
        return null;
    }

    //admin only api
    @DeleteMapping("/admin/deleteProfile")
    @PreAuthorize("hasRole('Admin')")
    public String deleteProfile(){
        return null;
    }

    //Followers
    @GetMapping("/getFollower")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String getFollowers(){

        return null;
    }
    @PostMapping("/addFollower")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String addFollower(){

        return null;
    }
    @DeleteMapping("removeFollower")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String removeFollowers(){

        return null;
    }

    //Following
    @GetMapping("/getFollower")
    public String getFollowees(){

        return null;
    }
    @PostMapping("/addFollower")
    public String addFollowee(){

        return null;
    }
    @DeleteMapping("removeFollower")
    public String removeFollowee(){

        return null;
    }


    //Pictures
    @PostMapping("/uploadProfilePic")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadProfilePic(@RequestPart(value = "file") MultipartFile file) {
        return this.amazonClient.uploadFile(file);
    }

    @PostMapping("/uploadPhoto")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String uploadPhoto(@RequestPart(value = "file") MultipartFile file){
        return this.amazonClient.uploadFile(file);
    }

    @DeleteMapping("/deletePhoto")
    @PreAuthorize("hasRole('member') or hasRole('business') or hasRole('Admin')")
    public String deleteFile(@RequestPart(value = "url") String fileUrl) {
        return this.amazonClient.deleteFileFromS3Bucket(fileUrl);
    }
}
