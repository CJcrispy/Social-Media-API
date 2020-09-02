package Taku.app.core.controllers;

import Taku.app.core.models.profile.Profile;
import Taku.app.core.models.users.User;
import Taku.app.core.payload.request.ProfileRequest;
import Taku.app.core.payload.request.RequestByEmail;
import Taku.app.core.repositories.ProfileRepository;
import Taku.app.core.repositories.UserRepository;
import Taku.app.core.services.images.AmazonClient;
import Taku.app.core.services.profile.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

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
    ProfileRepository profileRepository;

    @Autowired
    UserRepository userRepository;

    //Profile
    @PostMapping("/createProfile")
    public ResponseEntity<?> createProfile(@Valid @RequestBody RequestByEmail requestByEmail){

        if (userRepository.existsByEmail(requestByEmail.getEmail())){
            User user = userRepository.findByEmailIgnoreCase(requestByEmail.getEmail());
            profileService.createProfile(user);
            Profile profile = new Profile(user);
            profileRepository.save(profile);
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
    public String updateProfile(@Valid @RequestBody ProfileRequest profileRequest){

        profileService.validateProfile(profileRequest.getProfileId(), profileRequest.getProfileLink());



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
    @GetMapping("/getFollowing")
    public String getFollowing(){

        return null;
    }
    @PostMapping("/addFollowing")
    public String addFollowing(){

        return null;
    }
    @DeleteMapping("removeFollowing")
    public String removeFollowing(){

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
